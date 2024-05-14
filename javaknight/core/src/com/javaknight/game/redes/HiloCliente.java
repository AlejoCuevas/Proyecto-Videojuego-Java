package com.javaknight.game.redes;

import Sprites.Balas.BalaRed;

import com.javaknight.game.entity.EnemyEntity;
import com.javaknight.game.entity.EntidadRed;
import com.javaknight.game.entity.Entity;
import com.javaknight.game.entity.boss.BossRed;
import com.javaknight.game.pantallas.PantallaOnline;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HiloCliente extends Thread{
    static DatagramSocket socket;
    static int puerto;
    static InetAddress ipServidor;
    private final PantallaOnline pantallaOnline;

    boolean end;

    public HiloCliente(PantallaOnline pantallaOnline) {
        this.pantallaOnline = pantallaOnline;
        try {
            socket = new DatagramSocket(); //auto
            ipServidor = InetAddress.getByName("255.255.255.255");
            puerto = 51234;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(!end) {
            byte[] data = new byte[1024];
            DatagramPacket dp = new DatagramPacket(data,data.length);
            try {
                socket.receive(dp);
                procesarMensaje(dp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void procesarMensaje(DatagramPacket dp) {
        String msg = (new String(dp.getData())).trim();
        String[] msgCompuesto = msg.split("#");
        switch (msgCompuesto[0]){
            case "conectado":
                RedesUtiles.clienteId = Integer.parseInt(msgCompuesto[1]);
                break;
            case "mover": {
                int id = Integer.parseInt(msgCompuesto[1]);
                float x = Float.parseFloat(msgCompuesto[2]);
                float y = Float.parseFloat(msgCompuesto[3]);
                pantallaOnline.players.get(id).setPosition(x, y);
                System.out.println("Mensaje del server: "+msg);
                break;
            }
            case "disparo":{
                int id = Integer.parseInt(msgCompuesto[1]);
                float x = Float.parseFloat(msgCompuesto[2]);
                float y = Float.parseFloat(msgCompuesto[3]);
                float dirx = Float.parseFloat(msgCompuesto[4]);
                float diry = Float.parseFloat(msgCompuesto[5]);
                float damage = Float.parseFloat(msgCompuesto[6]);
                //if(id == RedesUtiles.clienteId) return;
                BalaRed balared = new BalaRed(x,y,dirx,diry,damage,id==-1);
                pantallaOnline.getBulletManager().balasRed.add(balared);
                //BalaRed bullet = pantallaOnline.players.get(id).getGun().shootRed(pantallaOnline.getBulletManager(), x,y,false);
                break;
            }
            case "nivel":{
                int id = Integer.parseInt(msgCompuesto[1]);
                pantallaOnline.setNivel(id);
                break;
            }
            case "newenemy": {
                float x = Float.parseFloat(msgCompuesto[1]);
                float y = Float.parseFloat(msgCompuesto[2]);
                pantallaOnline.getEntityManager().entidadesRed.add(new EntidadRed(x,y));
                break;
            }
            case "newboss": {
                float x = Float.parseFloat(msgCompuesto[1]);
                float y = Float.parseFloat(msgCompuesto[2]);
                pantallaOnline.getEntityManager().entidadesRed.add(new BossRed(x,y));
                break;
            }
            case "enemy_die": {
                int id = Integer.parseInt(msgCompuesto[1]);
                if(pantallaOnline.getEntityManager().getEntities().size>=id) return;
                pantallaOnline.getEntityManager().getEntities().removeIndex(id);
                break;
            }
            case "enemy": {
                int id = Integer.parseInt(msgCompuesto[1]);
                float x = Float.parseFloat(msgCompuesto[2]);
                float y = Float.parseFloat(msgCompuesto[3]);
                if(pantallaOnline.getEntityManager().getEntities().size <= id) return;
                System.out.println("en "+pantallaOnline.getEntityManager().getEntities().size + " id" +id);
                Entity en = pantallaOnline.getEntityManager().getEntities().get(id);
                if(en == null) return;
                en.setPosition(x,y);
                break;
            }
            case "player": {
                int id = Integer.parseInt(msgCompuesto[1]);
                float life = Float.parseFloat(msgCompuesto[2]);
                pantallaOnline.players.get(id).life = life;
                break;
            }
            case "terminar": {
            	RedesUtiles.fin = true;
            	break;
            }
        }
    }


    public void enviarMensajeAlServer(String event) {
        System.out.println("Mensaje al server: "+event);
        byte[] data = event.getBytes();
        DatagramPacket dp = new DatagramPacket(data,data.length, ipServidor, puerto);
        try {
            socket.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

