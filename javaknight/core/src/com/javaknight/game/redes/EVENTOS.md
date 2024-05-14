# Eventos cliente

| Nombre    | Desc               | Data          | Tipos                     |
|-----------|--------------------|---------------|---------------------------|
| conectar  | Conectar al server | x             |                           |
| moverse   | me movi            | W # S # A # D | bool # bool # bool # bool |
| disparar  | dispare            | X # Y         | float # float             |
| habilidad | use habilidad      |               |                           |

moverse#true#false#false#false

# Server events

Eventos que el server le envia a el cliente

| Nombre    | Desc                               | Data               | Tipos                |
|-----------|------------------------------------|--------------------|----------------------|
| conectado | Enviarle al cliente que se conecto | id cliente         | int                  |
| nivel*    | ID DE NIVEL A CAMBIAR              | nivel              | int                  |
| mover     | Se movio un jugador                | id cliente # x # y | int # float # float  |
| disparo   | Bala                               | x # y # enemigo    | float # float # bool |

_* Nivel 0: safezone_






