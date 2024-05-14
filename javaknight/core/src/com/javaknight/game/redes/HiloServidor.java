package com.javaknight.game.redes;


import Sprites.Balas.BalaRed;
import com.javaknight.game.pantallas.PantallaServidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class HiloServidor extends Thread {
    private static final int PUERTO = 51234;
    private final PantallaServidor pantallaServidor;

    private DatagramSocket socket;
    private List<Conexion> listaClientes;

    boolean fin = false;

    public boolean listo;


    public HiloServidor(PantallaServidor pantallaServidor) {
        this.pantallaServidor = pantallaServidor;
        try {
            socket = new DatagramSocket(PUERTO);
            listaClientes = new ArrayList<>();
            System.out.println("Servidor iniciado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(!this.fin) {
            byte[] data = new byte[1024];
            DatagramPacket dp = new DatagramPacket(data,data.length);
            try {
                socket.receive(dp);
                processMessage(dp);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    private void processMessage(DatagramPacket dp) {
        String msg = (new String(dp.getData())).trim(); // hola#pepe#juan
        String[] msgComponents = msg.split("#"); // ["hola", "pepe", "juan"]
        
        //System.out.println("Client msg "+msg);
        int id = verificarExisteCliente(dp);
        switch (msgComponents[0]){
            case "conectar":
                if(listaClientes.size() >= 2) return;
                else {
                    Conexion c = new Conexion(dp.getAddress(), dp.getPort());
                    enviarMensaje("conectado#"+ listaClientes.size(),c);
                    /*enviarMensaje("mover#"+listaClientes.size()+"#"+pantallaServidor.players.get(id).getX()+"#"
                    +pantallaServidor.players.get(id).getY(),c);*/
                    listaClientes.add(c);
                }
                if(listaClientes.size() == 2){
                    listo = true;
                }
                break;
            case "moverse":
                boolean W = Boolean.parseBoolean(msgComponents[1]);
                boolean S = Boolean.parseBoolean(msgComponents[2]);
                boolean A = Boolean.parseBoolean(msgComponents[3]);
                boolean D = Boolean.parseBoolean(msgComponents[4]);
                pantallaServidor.players.get(id).handleInputs(W,S,A,D);
                enviarMensajeATodos("mover#"+id+"#"+pantallaServidor.players.get(id).getX()+"#"+pantallaServidor.players.get(id).getY());
                break;
            case "disparar":
                float x = Float.parseFloat(msgComponents[1]);
                float y = Float.parseFloat(msgComponents[2]);
                BalaRed bullet = pantallaServidor.players.get(id).getGun().shootRed(pantallaServidor.getBulletManager(), x,y,false);
                enviarMensajeATodos("disparo#"+id+"#"+bullet.x+"#"+bullet.y+"#"+bullet.directionX+"#"+ bullet.directionY+"#"+ bullet.damage);
            	break;
            default:
                return;
        }
    }



    public void enviarMensaje(String event, Conexion client) {
        byte[] data = event.getBytes();
        DatagramPacket dp = new DatagramPacket(data,data.length,client.getIp(),client.getPuerto());
        try {
            System.out.println(dp);
            socket.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void enviarMensajeATodos(String event) {
        //System.out.println("BC "+event); 
        for(Conexion c : listaClientes){
            enviarMensaje(event, c);
        }
    }

    private int verificarExisteCliente(DatagramPacket dp) {
        for(Conexion c : listaClientes){
            if(c.getIp().equals(dp.getAddress()) && c.getPuerto() == dp.getPort()){
                return listaClientes.indexOf(c);
            }
        }
        return -1;
    }

    /*public void reset(){
        listo = false;
        listaClientes.clear();
        cantidadClientes = 0;
        Globals.player0Money = Globals.INITIAL_MONEY;
        Globals.player1Money = Globals.INITIAL_MONEY;
    } */
    public List<Conexion> getListaClientes() {
        return listaClientes;
    }
}

