package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.Random;

/**
 * Created by OddFellows on 04/04/2017.
 */

public class Enemigo extends Objeto {

    private float vida;
    private float poderAtaque;
    private int limiteMultiplicadorDano;
    private float VELOCIDAD;
    private int REACCION;                                   // Limite superior del random para moverAleatorio() (rápido: 3, lento: 5)

    // Personaje
    private Personaje henric;

    private TextureRegion texturaCompleta;                  // Textura completa
    private Animation<TextureRegion> spriteAnimado;         // Animación caminando
    private Animation<TextureRegion> animacionPrevia;       // Animación previa
    private Animation<TextureRegion> animacionAtaque;       // Animación ataque
    private float timerAnimacion;                           // Tiempo para cambiar frames de la animación
    private float timerMovimiento;                          // Tiempo para decidir un nuevo movimiento
    private float randomTiempoMovimiento;                       // Tiempo limite cambiar estado de movimiento
    private TextureRegion[][] texturaEnemigo;
    private boolean veDerecha;
    private boolean tocaJugador;
    private TextureRegion regionPruebaOrientacion;

    private Tipo tipoEnemigo;
    private EstadoEnemigo estadoEnemigo = EstadoEnemigo.VAGANDO;
    private EstadoMovimiento estadoMovimiento = EstadoMovimiento.QUIETO_X;
    private EstadoMovimientoVertical estadoMovimientoVertical = EstadoMovimientoVertical.QUIETO_Y;

    // Assets
    private static AssetManager manager = Nivel.getManager();
    private Texture textura;
    private Music fxAtaque;
    private Sound fxMuriendo;

    public Enemigo(float x, float y, Tipo tipo) {

        // Encuentra la instancia de henric
        henric = Personaje.getInstanciaPersonaje();

        // Le asigna una clase al enemigo creado
        tipoEnemigo = tipo;
        crearTipo();

//        // Lee la textura como región
//        TextureRegion texturaCompleta = new TextureRegion(textura);
//        // La divide en 4 frames de 32x64 (ver marioSprite.png)
//        texturaEnemigo = texturaCompleta.split(96,96);
//        // Crea la animación con tiempo de 0.15 segundos entre frames.
//
//        spriteAnimado = new Animation(0.15f, texturaEnemigo[0][2], texturaEnemigo[0][1] );
//        animacionPrevia = new Animation(0.15f, texturaEnemigo[0][2], texturaEnemigo[0][1] );
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        timerMovimiento = 0;
        randomTiempoMovimiento = new Random().nextFloat() * 3; // Random de 0.0 a 1.0 multiplicado por 3
        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaEnemigo[0][0]);    // QUIETO_X
        sprite.setPosition(x,y);    // Posición inicial

        veDerecha = false;
        tocaJugador = false;
    }

    private void crearTipo() {
        switch (tipoEnemigo) {
            case JABALI:
                vida = 45;
                VELOCIDAD = 3;
                REACCION = 5;
                poderAtaque = 8;
                limiteMultiplicadorDano = 3;

                // Assets
                textura = manager.get("Enemigo/jabali.png");
                fxAtaque = manager.get("Enemigo/jabaliAtaque.mp3");
                fxMuriendo = manager.get("Enemigo/jabaliMuerte.mp3");

                // Lee la textura como región
                texturaCompleta = new TextureRegion(textura);
                // La divide en 4 frames de 32x64 (ver marioSprite.png)
                texturaEnemigo = texturaCompleta.split(96,96);
                // Crea la animación con tiempo de 0.15 segundos entre frames.

                spriteAnimado = new Animation(0.8f / VELOCIDAD, texturaEnemigo[0][2], texturaEnemigo[0][3] );
                animacionPrevia = spriteAnimado;
                animacionAtaque = new Animation(0.4f / VELOCIDAD, texturaEnemigo[0][0], texturaEnemigo[0][1]);
                break;

            case DUPLO:
                vida = 120;
                VELOCIDAD = 6;
                REACCION = 3;
                poderAtaque = 10;
                limiteMultiplicadorDano = 4;

                // Assets
                textura = manager.get("Enemigo/duplo.png");
                fxAtaque = manager.get("Enemigo/duploAtaque.mp3");
                fxMuriendo = manager.get("Enemigo/duploMuerte.mp3");

                // Lee la textura como región
                texturaCompleta = new TextureRegion(textura);
                // La divide en 4 frames de 32x64 (ver marioSprite.png)
                texturaEnemigo = texturaCompleta.split(96,96);
                // Crea la animación con tiempo de 0.15 segundos entre frames.

                spriteAnimado = new Animation(0.8f / VELOCIDAD, texturaEnemigo[0][0], texturaEnemigo[0][1], texturaEnemigo[0][2] );
                animacionPrevia = spriteAnimado;
                animacionAtaque = new Animation(0.4f / VELOCIDAD, texturaEnemigo[0][3], texturaEnemigo[0][4], texturaEnemigo[0][5]);
                break;
            case OSO:
                vida = 150;
                VELOCIDAD = 3;
                REACCION = 4;
                poderAtaque = 20;
                limiteMultiplicadorDano = 2;

                // Assets
                textura = manager.get("Enemigo/oso.png");
                fxAtaque = manager.get("Enemigo/osoAtaque.mp3");
                fxMuriendo = manager.get("Enemigo/osoMuerte.mp3");

                // Lee la textura como región
                texturaCompleta = new TextureRegion(textura);
                // La divide en 4 frames de 32x64 (ver marioSprite.png)
                texturaEnemigo = texturaCompleta.split(96,96);
                // Crea la animación con tiempo de 0.15 segundos entre frames.

                spriteAnimado = new Animation(0.8f / VELOCIDAD, texturaEnemigo[0][2], texturaEnemigo[0][3], texturaEnemigo[0][1] );
                animacionPrevia = spriteAnimado;
                animacionAtaque = new Animation(0.4f / VELOCIDAD, texturaEnemigo[0][0], texturaEnemigo[0][4]);
                break;
            case LOBO:
                vida = 60;
                VELOCIDAD = 4;
                REACCION = 3;
                poderAtaque = 30;
                limiteMultiplicadorDano = 3;

                // Assets
                textura = manager.get("Enemigo/lobo.png");
                fxAtaque = manager.get("Enemigo/loboAtaque.mp3");
                fxMuriendo = manager.get("Enemigo/loboMuerte.mp3");

                // Lee la textura como región
                texturaCompleta = new TextureRegion(textura);
                // La divide en 4 frames de 32x64 (ver marioSprite.png)
                texturaEnemigo = texturaCompleta.split(96,96);
                // Crea la animación con tiempo de 0.15 segundos entre frames.

                spriteAnimado = new Animation(0.8f / VELOCIDAD, texturaEnemigo[0][1], texturaEnemigo[0][2]);
                animacionPrevia = spriteAnimado;
                animacionAtaque = new Animation(0.4f / VELOCIDAD, texturaEnemigo[0][0], texturaEnemigo[0][3]);
                break;
            case MUTIS:
                vida = 200;
                VELOCIDAD = 2;
                REACCION = 3;
                poderAtaque = 25;
                limiteMultiplicadorDano = 3;

                // Assets
                textura = manager.get("Enemigo/mutis.png");
                fxAtaque = manager.get("Enemigo/mutisAtaque.mp3");
                fxMuriendo = manager.get("Enemigo/mutisMuerte.mp3");

                // Lee la textura como región
                texturaCompleta = new TextureRegion(textura);
                // La divide en 4 frames de 32x64 (ver marioSprite.png)
                texturaEnemigo = texturaCompleta.split(96,96);
                // Crea la animación con tiempo de 0.15 segundos entre frames.

                spriteAnimado = new Animation(0.8f / VELOCIDAD, texturaEnemigo[0][1], texturaEnemigo[0][2]);
                animacionPrevia = spriteAnimado;
                animacionAtaque = new Animation(0.4f / VELOCIDAD, texturaEnemigo[0][0], texturaEnemigo[0][3]);
                break;
        }
    }


    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {

//        sprite.draw(batch); // Dibuja el sprite estático

        boolean enMovimiento = false;

        // Dibuja el personaje dependiendo del estadoMovimiento
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
//            case ATACAR:
                enMovimiento = true;
                break;
//            case QUIETO_X:x
//                sprite.draw(batch); // Dibuja el sprite estático
//                break;
        }

        switch (estadoMovimientoVertical) {
            case MOV_ABAJO:
            case MOV_ARRIBA:
                enMovimiento = true;
        }

        if (!enMovimiento) {
            if (veDerecha && !sprite.isFlipX()) {
                sprite.flip(true, false);
            } else if(!veDerecha && sprite.isFlipX()) {
                sprite.flip(true, false);
            }
            sprite.draw(batch); // Dibuja el sprite estático
        } else {
            animar(batch);
        }
    }

    // SE CORRE CADA FRAME
    private void animar(SpriteBatch batch) {
        timerAnimacion += Gdx.graphics.getDeltaTime();
        // Frame que se dibujará
        regionPruebaOrientacion = spriteAnimado.getKeyFrame(timerAnimacion);
        if (estadoMovimiento == EstadoMovimiento.MOV_DERECHA) {
            veDerecha = true;
        }
        else if (estadoMovimiento == EstadoMovimiento.MOV_IZQUIERDA) {
            veDerecha = false;
        }

        if (veDerecha && !regionPruebaOrientacion.isFlipX()) {
            regionPruebaOrientacion.flip(true, false);
        } else if (!veDerecha && regionPruebaOrientacion.isFlipX()) {
            regionPruebaOrientacion.flip(true, false);
        }

        batch.draw(regionPruebaOrientacion,sprite.getX(),sprite.getY());
    }

    //Actualiza las acciones del enemigo
    public void actualizar(TiledMap mapa) {

        if (estadoEnemigo == EstadoEnemigo.MUERTO) {
            morir();

        } else if (tocaJugador) {
            if (estadoEnemigo != EstadoEnemigo.ATACANDO && spriteAnimado != animacionAtaque) {
                estadoEnemigo = EstadoEnemigo.ATACANDO;
                animacionPrevia = spriteAnimado;
                spriteAnimado = animacionAtaque;
                // Animación infinita
                spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
                // Inicia el timer que contará tiempo para saber qué frame se dibuja
                timerAnimacion = 0;
            }
            atacar();

        } else {
            estadoEnemigo = EstadoEnemigo.VAGANDO;
            spriteAnimado = animacionPrevia;
            moverAletorio(mapa);
        }
    }

    //Movimiento del enemigo
    private void moverAletorio(TiledMap mapa) {
        timerMovimiento += Gdx.graphics.getDeltaTime();

        int randomMovimientoX = new Random().nextInt(REACCION + 1); // Random de 0 a REACCION
        int randomMovimientoY = new Random().nextInt(REACCION + 1); // Random de 0 a REACCION

//        Gdx.app.log("Tiempo", String.valueOf(timerMovimiento));
        if (timerMovimiento > randomTiempoMovimiento) {
            switch (randomMovimientoX) {
                case 0:
                    estadoMovimiento = EstadoMovimiento.MOV_IZQUIERDA;
                    break;
                case 1:
                    estadoMovimiento = EstadoMovimiento.MOV_DERECHA;
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                    estadoMovimiento = EstadoMovimiento.QUIETO_X;
                    break;
            }

            switch (randomMovimientoY) {
                case 0:
                    estadoMovimientoVertical = EstadoMovimientoVertical.MOV_ABAJO;
                    break;
                case 1:
                    estadoMovimientoVertical = EstadoMovimientoVertical.MOV_ARRIBA;
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                    estadoMovimientoVertical = EstadoMovimientoVertical.QUIETO_Y;
                    break;
            }
            timerMovimiento = 0;
            randomTiempoMovimiento = new Random().nextFloat() * 3; // Crea un nuevo valor para poner limite de tiempo de decision
        }

        // vvv DESCOMENTAR PARA PRUEBAS DE MOVIMIENTO
//        estadoMovimiento = EstadoMovimiento.MOV_DERECHA;
//        estadoMovimientoVertical = EstadoMovimientoVertical.QUIETO_Y;
        // ^^^ DESCOMENTAR PARA PRUEBAS DE MOVIMIENTO

        moverHorizontal(mapa);
        moverVertical(mapa);
    }

    // Mueve el personaje a la derecha/izquierda, prueba choques con paredes
    private void moverHorizontal(TiledMap mapa) {
//        Pantalla pantalla = Pantalla.getInstanciaPantalla();
        // Obtiene la primer capa del mapa (en este caso es la única)
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Limites");
        // Ejecutar movimiento horizontal
        float nuevaX = sprite.getX();
        // ¿Quiere ir a la Derecha?
        if ( estadoMovimiento == EstadoMovimiento.MOV_DERECHA) {
            // Obtiene el bloque del lado derecho. Asigna null si puede pasar.
            int x = (int) ((sprite.getX() + 96) / 64);   // Convierte coordenadas del mundo en coordenadas del mapa
            int y = (int) ((sprite.getY() + 10) / 64);
            TiledMapTileLayer.Cell celdaDerechaAbajo = capa.getCell(x, y);
            y = (int) ((sprite.getY() + 86) / 64);
            TiledMapTileLayer.Cell celdaDerechaArriba = capa.getCell(x, y);
            y = (int) ((sprite.getY() + 48) / 64);
            TiledMapTileLayer.Cell celdaDerechaCentro = capa.getCell(x, y);
            if (celdaDerechaAbajo != null) {
                Object tipo = (String) celdaDerechaAbajo.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaDerechaAbajo = null;  // Puede pasar
                }
            }
            if (celdaDerechaArriba != null) {
                Object tipo = (String) celdaDerechaArriba.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaDerechaArriba = null;  // Puede pasar
                }
            }
            if (celdaDerechaCentro != null) {
                Object tipo = (String) celdaDerechaCentro.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaDerechaCentro = null;  // Puede pasar
                }
            }
            if ( celdaDerechaAbajo == null && celdaDerechaArriba == null && celdaDerechaCentro == null) {
                // Ejecutar movimiento horizontal
                nuevaX += VELOCIDAD;
                // Prueba que no salga del mundo por la derecha
                if (nuevaX <= (mapa.getProperties().get("width", Integer.class) * 64) - sprite.getWidth()) {
                    sprite.setX(nuevaX);
//                    camaraX += velocidadX;
                }
            } else {
                estadoMovimiento = EstadoMovimiento.QUIETO_X;
            }
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimiento == EstadoMovimiento.MOV_IZQUIERDA) {
            int xIzq = (int) ((sprite.getX()) / 64);
            int y = (int) ((sprite.getY() + 10) / 64);
            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaIzquierdaAbajo = capa.getCell(xIzq, y);
            y = (int) ((sprite.getY() + 86) / 64);
            TiledMapTileLayer.Cell celdaIzquierdaArriba = capa.getCell(xIzq, y);
            y = (int) ((sprite.getY() + 48) / 64);
            TiledMapTileLayer.Cell celdaIzquierdaCentro = capa.getCell(xIzq, y);
            if (celdaIzquierdaAbajo != null) {
                Object tipo = (String) celdaIzquierdaAbajo.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaIzquierdaAbajo = null;  // Puede pasar
                }
            }
            if (celdaIzquierdaArriba != null) {
                Object tipo = (String) celdaIzquierdaArriba.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaIzquierdaArriba = null;  // Puede pasar
                }
            }
            if (celdaIzquierdaCentro != null) {
                Object tipo = (String) celdaIzquierdaCentro.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaIzquierdaCentro = null;  // Puede pasar
                }
            }
            if ( celdaIzquierdaAbajo == null && celdaIzquierdaArriba == null && celdaIzquierdaCentro == null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaX -= VELOCIDAD;
                if (nuevaX >= 0) {
                    sprite.setX(nuevaX);
//                    camaraX += velocidadX;
                }
            } else {
                estadoMovimiento = EstadoMovimiento.QUIETO_X;
            }
        }
    }

    // Mueve el personaje a la derecha/izquierda, prueba choques con paredes
    private void moverVertical(TiledMap mapa) {
//        Pantalla pantalla = Pantalla.getInstanciaPantalla();
        // Obtiene la primer capa del mapa (en este caso es la única)
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Limites");
        // Ejecutar movimiento horizontal
        float nuevaY = sprite.getY();
        // ¿Quiere ir a la Derecha?
        if ( estadoMovimientoVertical == EstadoMovimientoVertical.MOV_ARRIBA ) {
            // Obtiene el bloque de arriba. Asigna null si puede pasar.
            int x = (int) ((sprite.getX() + 10) / 64);   // Convierte coordenadas del mundo en coordenadas del mapa
            int y = (int) ((sprite.getY() + 96) / 64);
            TiledMapTileLayer.Cell celdaArribaIzq = capa.getCell(x, y);
            x = (int) ((sprite.getX() + 86) / 64);
            TiledMapTileLayer.Cell celdaArribaDer = capa.getCell(x, y);
            x = (int) ((sprite.getX() + 48) / 64);
            TiledMapTileLayer.Cell celdaArribaCentro = capa.getCell(x, y);
            if (celdaArribaIzq != null) {
                Object tipo = (String) celdaArribaIzq.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaArribaIzq = null;  // Puede pasar
                }
            }
            if (celdaArribaDer != null) {
                Object tipo = (String) celdaArribaDer.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaArribaDer = null;  // Puede pasar
                }
            }
            if (celdaArribaCentro != null) {
                Object tipo = (String) celdaArribaCentro.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaArribaCentro = null;  // Puede pasar
                }
            }
            if ( celdaArribaIzq == null && celdaArribaDer == null && celdaArribaCentro == null) {
                // Ejecutar movimiento horizontal
                nuevaY += VELOCIDAD;
                // Prueba que no salga del mundo por la arriba
                if (nuevaY <= (mapa.getProperties().get("height", Integer.class) * 64) - sprite.getHeight()) {
                    sprite.setY(nuevaY);
//                    camaraY += velocidadY;
                }
            } else {
                estadoMovimientoVertical = EstadoMovimientoVertical.QUIETO_Y;
            }
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimientoVertical == EstadoMovimientoVertical.MOV_ABAJO ) {
            int x = (int) ((sprite.getX() + 10) / 64);
            int yAbajo = (int) (sprite.getY() / 64);
            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaAbajoIzq = capa.getCell(x, yAbajo);
            x = (int) ((sprite.getX() +86) / 64);
            TiledMapTileLayer.Cell celdaAbajoDer = capa.getCell(x, yAbajo);
            x = (int) ((sprite.getX() + 48) / 64);
            TiledMapTileLayer.Cell celdaAbajoCentro = capa.getCell(x, yAbajo);
            if (celdaAbajoIzq != null) {
                Object tipo = (String) celdaAbajoIzq.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaAbajoIzq = null;  // Puede pasar
                }
            }
            if (celdaAbajoDer != null) {
                Object tipo = (String) celdaAbajoDer.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaAbajoDer = null;  // Puede pasar
                }
            }
            if (celdaAbajoCentro != null) {
                Object tipo = (String) celdaAbajoCentro.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaAbajoCentro = null;  // Puede pasar
                }
            }
            if ( celdaAbajoIzq == null && celdaAbajoDer == null && celdaAbajoCentro == null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaY -= VELOCIDAD;
                if (nuevaY >= 0) {
                    sprite.setY(nuevaY);
//                    camaraY += velocidadY;
                }
            } else {
                estadoMovimientoVertical = EstadoMovimientoVertical.QUIETO_Y;
            }
        }
    }

    public void setTocaJugador(boolean tocaJugador) {
        this.tocaJugador = tocaJugador;
    }

    //Método que se acercará al personaje más no indicará como atacar
    private void perseguir(Personaje jugador) {

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


    //Método que ataqua al personaje y realizará el daño correspondiente
    private void atacar(){
        //El daño que causará será el daño fijo que hará cada enemigo por un multiplicador dado por el nivel
        int multiplicadorDano = new Random().nextInt(limiteMultiplicadorDano) + 1;
        float danoAJugador = poderAtaque*multiplicadorDano / 100;
        //Obtiene la vida del personaje
        float vidaPersonaje = henric.getVida();

        // Cambia la vida del personaje
        if(Configuraciones.isFxOn){
            fxAtaque.play();
        }
        henric.setVida(vidaPersonaje - danoAJugador);
        //fxAtaque.pause();
    }

    public void herir(int dano) {
        this.vida -= dano;

        if (vida <= 0) {
            estadoEnemigo = EstadoEnemigo.MUERTO;
            fxAtaque.stop();
            if(Configuraciones.isFxOn){
                fxMuriendo.play();
            }
        }
    }

    public void morir() {
        estadoMovimiento = EstadoMovimiento.QUIETO_X;
        estadoMovimientoVertical = EstadoMovimientoVertical.QUIETO_Y;
        sprite.setColor(1,1,1,0);
        sprite.setPosition(-100, -100);
//        sprite = null;
    }

    public EstadoEnemigo getEstadoEnemigo() {
        return estadoEnemigo;
    }

    public enum EstadoEnemigo {
        PERSEGUIENDO,
        ATACANDO,
        VAGANDO,
        MUERTO
    }

    private enum EstadoMovimiento {
        QUIETO_X,
        MOV_IZQUIERDA,
        MOV_DERECHA
    }

    private enum EstadoMovimientoVertical {
        QUIETO_Y,
        MOV_ARRIBA,
        MOV_ABAJO
    }

    public enum Tipo {
        JABALI,
        OSO,
        DUPLO,
        LOBO,
        MUTIS
    }
}
