package mx.rueschan.videojuego;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by OddFellows on 04/04/2017.
 */

public class Enemigo extends Objeto {

    private float poderAtaque = 50;
    private float velocidadX = 0;
    private float velocidadY = 0;

    private Animation<TextureRegion> spriteAnimado;         // Animación caminando
    private Animation<TextureRegion> animacionPrevia;       // Animación previa
    private float timerAnimacion;                           // Tiempo para cambiar frames de la animación
    private TextureRegion[][] texturaEnemigo;

    private EstadoEnemigo estadoEnemigo = EstadoEnemigo.VAGANDO;
    private EstadoMovimiento estadoMovimiento = EstadoMovimiento.QUIETO_X;
    private EstadoMovimientoVertical estadoMovimientoVertical = EstadoMovimientoVertical.QUIETO_Y;

    public Enemigo(Texture textura, float x, float y) {
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);
        // La divide en 4 frames de 32x64 (ver marioSprite.png)
        texturaEnemigo = texturaCompleta.split(96,96);
        // Crea la animación con tiempo de 0.15 segundos entre frames.

        spriteAnimado = new Animation(0.15f, texturaEnemigo[0][2], texturaEnemigo[0][1] );
        animacionPrevia = new Animation(0.15f, texturaEnemigo[0][2], texturaEnemigo[0][1] );
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaEnemigo[0][0]);    // QUIETO_X
        sprite.setPosition(x,y);    // Posición inicial
    }

    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {

        sprite.draw(batch); // Dibuja el sprite estático

//        boolean enMovimiento = false;

//        // Dibuja el personaje dependiendo del estadoMovimiento
//        switch (estadoMovimiento) {
//            case MOV_DERECHA:
//            case MOV_IZQUIERDA:
//            case ATACAR:
//                enMovimiento = true;
//                break;
//            case QUIETO_X:
//                sprite.draw(batch); // Dibuja el sprite estático
//                break;
//        }
//
//        switch (estadoMovimientoVertical) {
//            case MOV_ABAJO:
//            case MOV_ARRIBA:
//                enMovimiento = true;
//        }
//
//        if (!enMovimiento) {
//            if (veDerecha && !sprite.isFlipX()) {
//                sprite.flip(true, false);
//            } else if(!veDerecha && sprite.isFlipX()) {
//                sprite.flip(true, false);
//            }
//            sprite.draw(batch); // Dibuja el sprite estático
//        } else {
//            animar(batch);
//        }
    }

    //Actualiza las acciones del enemigo
    public void actualizar(TiledMap mapa) {
        switch (estadoEnemigo) {
            case VAGANDO:
                moverAletorio(mapa);
        }
    }

    //Movimiento del enemigo
    private void moverAletorio(TiledMap mapa) {

        boolean randomMovimientoX = MathUtils.randomBoolean();
        boolean randomMovimientoY = MathUtils.randomBoolean();

        if (randomMovimientoX) {
            moverHorizontal(mapa);
        }

        if (randomMovimientoY) {
//            moverVertical(mapa);
        }
    }

    private void moverHorizontal(TiledMap mapa) {
        // Obtiene la primer capa del mapa (en este caso es la única)
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Limites");
        // Ejecutar movimiento horizontal
        float nuevaX = this.sprite.getX();

        int x = (int) ((sprite.getX() + 96) / 64);   // Convierte coordenadas del mundo en coordenadas del mapa
        int y = (int) ((sprite.getY() + 10) / 64);
        TiledMapTileLayer.Cell celdaDerecha = capa.getCell(x, y);
        y = (int) ((sprite.getY() + 86) / 64);
        TiledMapTileLayer.Cell celdaDerecha2 = capa.getCell(x, y);

        if (celdaDerecha != null) {
            Object tipo = (String) celdaDerecha.getTile().getProperties().get("tipo");
            if (!"obstaculo".equals(tipo)) {
                celdaDerecha = null;  // Puede pasar
            }
        }
        if (celdaDerecha2 != null) {
            Object tipo = (String) celdaDerecha2.getTile().getProperties().get("tipo");
            if (!"obstaculo".equals(tipo)) {
                celdaDerecha2 = null;  // Puede pasar
            }
        }
        if ( celdaDerecha == null && celdaDerecha2 == null) {
            // Ejecutar movimiento horizontal
            nuevaX += velocidadX;
            // Prueba que no salga del mundo por la derecha
            if (nuevaX <= (mapa.getProperties().get("width", Integer.class) * 64) - sprite.getWidth()) {
                sprite.setX(nuevaX);
//                    camaraX += velocidadX;
            }
        }


//        // ¿Quiere ir a la izquierda?
//        if ( estadoMovimiento==EstadoMovimiento.MOV_IZQUIERDA) {
//            int xIzq = (int) ((sprite.getX()) / 64);
//            int y = (int) ((sprite.getY() + 10) / 64);
//            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
//            TiledMapTileLayer.Cell celdaIzquierda = capa.getCell(xIzq, y);
//            y = (int) ((sprite.getY() + 86) / 64);
//            TiledMapTileLayer.Cell celdaIzquierda2 = capa.getCell(xIzq, y);
//            if (celdaIzquierda != null) {
//                Object tipo = (String) celdaIzquierda.getTile().getProperties().get("tipo");
//                if (!"obstaculo".equals(tipo)) {
//                    celdaIzquierda = null;  // Puede pasar
//                }
//            }
//            if (celdaIzquierda2 != null) {
//                Object tipo = (String) celdaIzquierda2.getTile().getProperties().get("tipo");
//                if (!"obstaculo".equals(tipo)) {
//                    celdaIzquierda2 = null;  // Puede pasar
//                }
//            }
//            if ( celdaIzquierda == null && celdaIzquierda2 == null) {
//                // Prueba que no salga del mundo por la izquierda
//                nuevaX += velocidadX;
//                if (nuevaX >= 0) {
//                    sprite.setX(nuevaX);
////                    camaraX += velocidadX;
//                }
//            }
//        }
    }

    //Método que se acercará al personaje más no indicará como atacar
    private void Perseguir(Personaje jugador) {

        //Posiciones del enemigo antes de hacer el movimiento
        float enemigoPosicionX = this.sprite.getX();
        float enemigoPosicionY = this.sprite.getY();

        //Posiciones del jugador antes de hacer el movimiento
        float jugadorPosicionX = jugador.sprite.getX();
        float jugadorPosicionY = jugador.sprite.getY();

        //Tamaño del jugador
        float jugadorAncho = jugador.sprite.getWidth();
        float jugadorAlto = jugador.sprite.getHeight();

        //Obtenemos la distancia
        float distancia = medirDistancia(jugadorPosicionX, jugadorPosicionY);

//        //Checa primero a la posición X
//        if (enemigoPosicionX > jugadorPosicionX) {
//            enemigoPosicionX--;
//        } else if (enemigoPosicsionX < jugadorPosicionX) {
//            enemigoPosicionX++;
//        }
//
//        //Despúes checa a la posición en Y
//        if (enemigoPosicionY > jugadorPosicionY) {
//            enemigoPosicionY--;
//        } else if (enemigoPosicionY < jugadorPosicionY) {
//            enemigoPosicionY++;
//        }

        //Filtro en caso de que esten en la misma posición
        if (enemigoPosicionX==jugadorPosicionX  &&  enemigoPosicionY==jugadorPosicionY){

        }

        //En el caso en que el jugador se encuentre a la derecha y arriba del enemigo
        else if (enemigoPosicionX<=jugadorPosicionX  &&  enemigoPosicionY<=jugadorPosicionY){
            //Aquí va como moverlo con otro método jejeje

            //
        }

        //En el caso en que el jugador se encuentre a la derecha y abajo del enemigo
        else if (enemigoPosicionX<=jugadorPosicionX  &&  enemigoPosicionY>=jugadorPosicionY){
            //Aquí va como moverlo con otro método jejeje
        }

        //En el caso en que el jugador se encuentre a la izquierda y arriba del enemigo
        else if (enemigoPosicionX>=jugadorPosicionX  &&  enemigoPosicionY<=jugadorPosicionY){
            //Aquí va como moverlo con otro método jejeje
        }

        //En el caso en que el jugador se encuentre a la izquierda y abajo del enemigo
        else if (enemigoPosicionX>=jugadorPosicionX  &&  enemigoPosicionY>=jugadorPosicionY){
            //Aquí va como moverlo con otro método jejeje
        }

    }

    // Mide distancia al Jugador
    private float medirDistancia(float jx, float jy) {
        float distancia;
        float enemigoX = this.sprite.getX();
        float enemigoY = this.sprite.getY();
        float difXSquare = (enemigoX - jx) * (enemigoX - jx);
        float difYSquare = (enemigoY - jy) * (enemigoY - jy);

        distancia = (float) Math.sqrt((double)difXSquare + difYSquare);

        return distancia;
    }


    //Método que determinará si se hará un ataque al personaje y realizará el daño correspondiente
    private void Atacar(Personaje jugador, float multiplicadorDano){
        //Vida del jugador
        float vidaJugador = jugador.getVida();
        //El daño que causará será el daño fijo que hará cada enemigo por un multiplicador dado por el nivel
        float danoAJugador = poderAtaque*multiplicadorDano;

        //Disminuir la vida del jugador por la cantidad de daño que puede generar el enemigo
        jugador.setVida(vidaJugador - danoAJugador);

        //En caso de que la vida llegue a ser cero o menor a cero

    }

    private enum EstadoEnemigo {
        PERSEGUIENDO,
        ATACANDO,
        VAGANDO,
        MUERTO
    }

    public enum EstadoMovimiento {
        QUIETO_X,
        MOV_IZQUIERDA,
        MOV_DERECHA
    }

    public enum EstadoMovimientoVertical {
        QUIETO_Y,
        MOV_ARRIBA,
        MOV_ABAJO
    }
}
