package mx.rueschan.videojuego;

/**
 * Created by Odd Fellows on 08/03/2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Personaje extends Objeto {

    private static Personaje instancia = null;

    private float velocidadX = 0;      // Velocidad en x
    private float velocidadY = 0;      // Velocidad en y

//    private float camaraX = 0;
//    private float camaraY = 0;

    private float vida = 100;
    private int dano = 0;

    // Elementos interactuables
    private ArrayList<Objeto> inventario;
    private Enemigo enemigoCercano;

    private Animation<TextureRegion> spriteAnimado;         // Animación caminando
    private Animation<TextureRegion> animacionPrevia;       // Animación previa
    private Animation<TextureRegion> animacionAtaque;       // Animación ataque
    private float timerAnimacion;                           // Tiempo para cambiar frames de la animación
    private TextureRegion[][] texturaPersonaje;
    private TextureRegion[][] trAtaque;
    private TextureRegion regionPruebaOrientacion;

    private EstadoMovimiento estadoMovimiento = EstadoMovimiento.QUIETO_X;
    private EstadoMovimientoVertical estadoMovimientoVertical = EstadoMovimientoVertical.QUIETO_Y;
    private boolean veDerecha;
    private boolean estaVivo;

    // Lugar donde se encuentra
    private Localizacion localizacion = Localizacion.CABANA;
    public Destino destino;

    // ASSETS
    private static AssetManager manager = Nivel.getManager();
    private Music fxPasos;
    private String pathFxPasos = "Sonidos/pasoMadera.mp3";
    private Sound fxAccion;
    private final String pathFxAccion = "Sonidos/alerta.mp3";
    private Texture texturaAtaque;
    private String pathTexturaHenricAtaqueMartillo = "Personaje/HendricMartilloAtaque.png";
    private String pathTexturaHenric = "Personaje/Henric.png";

    // Estado de acción
    private boolean estatusAccion = false;

    // Recibe una imagen con varios frames (ver marioSprite.png)
    public Personaje(Texture textura, float x, float y) {
        Pantalla pantalla = Pantalla.getInstanciaPantalla();
        estaVivo = true;

        // Crea inventario
        inventario = new ArrayList<Objeto>(10);
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);
        // La divide en 4 frames de 32x64 (ver marioSprite.png)
        texturaPersonaje = texturaCompleta.split(96,96);
        // Crea la animación con tiempo de 0.15 segundos entre frames.

        spriteAnimado = new Animation(0.15f, texturaPersonaje[0][2], texturaPersonaje[0][1] );
        animacionPrevia = new Animation(0.15f, texturaPersonaje[0][2], texturaPersonaje[0][1] );
        animacionAtaque = null;

        manager = Nivel.getManager();
        //texturaAtaque = new Texture("Personaje/HendricMartilloAtaque.png");
        texturaAtaque = manager.get(pathTexturaHenricAtaqueMartillo);
        trAtaque = new TextureRegion(texturaAtaque).split(120, 96);
        animacionAtaque = new Animation(0.1f, trAtaque[0][2], trAtaque[0][1] );
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO_X
        sprite.setPosition(x,y);    // Posición inicial
        veDerecha = false; // El sprite esta viendo a la izquierda
        enemigoCercano = null; // Inicia en null porque no hay enemigos cerca (próximos)

        // ASSET MANAGER
        //manager = Nivel.getManager();
        //manager.load(pathFxPasos, Music.class);
        //manager.load(pathFxAccion, Sound.class);
        //manager.finishLoading();    // Carga los recursos

        //fxPasos = manager.get(pathFxPasos);
        //fxPasos.setLooping(true);
        //fxPasos.pause();
        fxAccion = manager.get(pathFxAccion);

//        camaraX = pantalla.camara.position.x;
//        camaraY = pantalla.camara.position.y;
    }

    // Método para obtener personaje o crearlo
    public static Personaje getInstanciaPersonaje() {
        if (instancia == null) {
            Pantalla pantalla = Pantalla.getInstanciaPantalla();
            //Texture texturaHenric = new Texture("Personaje/Henric.png");
            //Gdx.app.log("Personaje getInstanciaPersonaje"," "+manager.toString());
            Texture texturaHenric = manager.get("Personaje/Henric.png");
            instancia = new Personaje(texturaHenric, pantalla.getANCHO()/2, pantalla.getALTO()/2);
        }
        return instancia;
    }

    public void reset() {
        //TextureRegion texturaCompleta = new TextureRegion(new Texture("Personaje/Henric.png"));
        Texture texturaHenric = manager.get("Personaje/Henric.png");
        TextureRegion texturaCompleta = new TextureRegion(texturaHenric);
        // La divide en 4 frames de 32x64 (ver marioSprite.png)
        texturaPersonaje = texturaCompleta.split(96,96);
        // Crea la animación con tiempo de 0.15 segundos entre frames.

        spriteAnimado = new Animation(0.15f, texturaPersonaje[0][2], texturaPersonaje[0][1] );
        animacionPrevia = new Animation(0.15f, texturaPersonaje[0][2], texturaPersonaje[0][1] );
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO_X
        sprite.setPosition(Pantalla.getInstanciaPantalla().getANCHO()/2,
                Pantalla.getInstanciaPantalla().getALTO()/2);    // Posición inicial
        veDerecha = false; // El sprite esta viendo a la izquierda
        enemigoCercano = null; // Inicia en null porque no hay enemigos cerca (próximos)
    }

    public void vaciarInventario() {
        inventario.clear();
    }

    public void setFxPasos(String nuevoFx) {
        //manager.unload(pathFxPasos);
        //manager.load(nuevoFx, Music.class);
        //manager.finishLoadingAsset(nuevoFx);
        fxPasos = manager.get(nuevoFx);
        fxPasos.setLooping(true);
        fxPasos.pause();

        pathFxPasos = nuevoFx;
    }

    public void setSprite(TextureRegion[][] texturaPersonaje) {
        this.texturaPersonaje = texturaPersonaje;
        spriteAnimado = new Animation(0.15f, texturaPersonaje[0][2], texturaPersonaje[0][1] );
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        float x = sprite.getX();
        float y = sprite.getY();
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO_X
        sprite.setPosition(x, y);    // Posición inicial
    }

    public void usarArma() {
        // SE EVITA LA CREACION DE TEXTURAS DENTRO DEL MÉTODO ***R
//        long inicio = System.nanoTime();

//        this.texturaPersonaje = trAtaque;
//        animacionAtaque = new Animation(0.1f, texturaPersonaje[0][2], texturaPersonaje[0][1] );
        // INTENTO DE ARREGLO A ANIMACIÓN DE GOLPEO
        if (estadoMovimiento != EstadoMovimiento.ATACAR && spriteAnimado != animacionAtaque) {
            animacionPrevia = spriteAnimado;
        }
        estadoMovimiento = EstadoMovimiento.ATACAR;
        spriteAnimado = animacionAtaque;
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.REVERSED);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        float x = sprite.getX();
        float y = sprite.getY();
        sprite = new Sprite(trAtaque[0][0]);    // QUIETO_X
        sprite.setPosition(x, y);    // Posición inicial

        if (enemigoCercano != null) {
            if (enemigoCercano.getEstadoEnemigo() != Enemigo.EstadoEnemigo.MUERTO) {
                enemigoCercano.herir(dano);
            } else {
                enemigoCercano = null;
            }

        }

//        long fin = System.nanoTime();
//        System.out.println((fin - inicio) / 1000);
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getDano() {
        return dano;
    }

    public void addInventario(Objeto item) {
        inventario.add(item);
    }

    public ArrayList<Objeto> verInventario() {
        return inventario;
    }

    // SE CORRE CADA FRAME
    private void orientarAnimacion(SpriteBatch batch) {
        timerAnimacion += Gdx.graphics.getDeltaTime();
        // Frame que se dibujará
        regionPruebaOrientacion = spriteAnimado.getKeyFrame(timerAnimacion);
        if (estadoMovimiento == EstadoMovimiento.MOV_DERECHA) {
            veDerecha = true;
//            if (!region.isFlipX()) {
//                region.flip(true,false);
//            }
        }
        else if (estadoMovimiento == EstadoMovimiento.MOV_IZQUIERDA) {
            veDerecha = false;
//            if (region.isFlipX()) {
//                region.flip(true,false);
//            }
        }

//        if (estadoMovimiento == EstadoMovimiento.ATACAR && veDerecha && !region.isFlipX()) {
//            System.out.println("der");
//            region.flip(true, false);
//        } else if (estadoMovimiento == EstadoMovimiento.ATACAR && !veDerecha && region.isFlipX()) {
//            System.out.println("Izq");
//            region.flip(true, false);
//        }

        if (veDerecha && !regionPruebaOrientacion.isFlipX()) {
            regionPruebaOrientacion.flip(true, false);
        } else if (!veDerecha && regionPruebaOrientacion.isFlipX()) {
            regionPruebaOrientacion.flip(true, false);
        }

        batch.draw(regionPruebaOrientacion,sprite.getX(),sprite.getY());
    }

    // Sonido al caminar
    public void darPaso() {
        if (estadoMovimiento == EstadoMovimiento.QUIETO_X && estadoMovimientoVertical == EstadoMovimientoVertical.QUIETO_Y
              || estadoMovimiento == EstadoMovimiento.ATACAR) {
            fxPasos.pause();
        } else {
            if (Configuraciones.isFxOn && estaVivo) {
                fxPasos.play();
            }
        }
    }

    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {

        boolean enMovimiento = false;

        // Dibuja el personaje dependiendo del estadoMovimiento
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
            case ATACAR:
                enMovimiento = true;
                break;
            case QUIETO_X:
                sprite.draw(batch); // Dibuja el sprite estático
                break;
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
            orientarAnimacion(batch);
        }
    }

    public void moverCamara(TiledMap mapa) {
        // PRUEBA DE MEDIR MAPA PARA MOVER
        int ANCHO_MAPA = mapa.getProperties().get("width", Integer.class);
        int ALTO_MAPA = mapa.getProperties().get("height", Integer.class);
        if (ANCHO_MAPA > 20 || ALTO_MAPA > 12) {
            Pantalla.getInstanciaPantalla().camara.position.set(sprite.getX(),
                    sprite.getY(), 0);
        }
    }

    // Actualiza el sprite, de acuerdo al estadoMovimiento y estadoSalto
    private void actualizar(TiledMap mapa) {
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
                moverHorizontal(mapa);
                darPaso();
                moverCamara(mapa);
                break;
            case QUIETO_X:
                darPaso();
                break;
        }
        switch (estadoMovimientoVertical) {
            case MOV_ARRIBA:
            case MOV_ABAJO:
                moverVertical(mapa);
                darPaso();
                moverCamara(mapa);
                break;
            case QUIETO_Y:
                darPaso();
                break;
        }

        // Actualiza animación dependiendo de que hace
        if (spriteAnimado.isAnimationFinished(timerAnimacion) && estadoMovimiento == EstadoMovimiento.ATACAR) {
            estadoMovimiento = EstadoMovimiento.QUIETO_X;
            spriteAnimado = animacionPrevia;
        }
    }


    //WOLOLOLO
    private void interactuar(Nivel nivel) {
        TiledMapTileLayer.Cell celda;

        celda = buscaItems(nivel.mapa);
        if (celda != null) {
            nivel.btnInteraccion.setColor(1,1,1,1);
            nivel.btnInteraccion.setDisabled(false);
            nivel.tileObjetivo = celda;
        } else {
            nivel.btnInteraccion.setColor(1,1,1,0.4f);
            nivel.btnInteraccion.setDisabled(true);
            nivel.tileObjetivo = null;
        }

        celda = buscaInteractivos(nivel.mapa);
        if (celda != null) {
            nivel.alertaAccion.sprite.setPosition(sprite.getX(), sprite.getY() + 100);
            nivel.alertaAccion.sprite.setColor(1,1,1,1);
//            nivel.btnAccion.setDisabled(false);
            nivel.tileInteractivo = celda;
            if (!estatusAccion) {
                if (Configuraciones.isFxOn)
                    fxAccion.play(0.5f);
                estatusAccion = true;
            }
        } else {
            nivel.alertaAccion.sprite.setPosition(0, 0);
            nivel.alertaAccion.sprite.setColor(1,1,1,0);
//            nivel.btnAccion.setDisabled(true);
            nivel.tileInteractivo = null;
            estatusAccion = false;
        }

        celda = buscaSalida(nivel.mapa);
        //Aparte de checar la celda, checa que no esté en otra interfaz
        if (celda != null && !nivel.pausado && !nivel.enInventario && !nivel.enCarta) {
            nivel.btnEntrar.setDisabled(false);
            nivel.btnEntrar.setVisible(true);
        } else {
            nivel.btnEntrar.setDisabled(true);
            nivel.btnEntrar.setVisible(false);
        }
    }


    // Mueve el personaje a la derecha/izquierda, prueba choques con paredes
    private void moverHorizontal(TiledMap mapa) {
//        Pantalla pantalla = Pantalla.getInstanciaPantalla();
        // Obtiene la primer capa del mapa (en este caso es la única)
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Limites");
        // Ejecutar movimiento horizontal
        float nuevaX = sprite.getX();
        // ¿Quiere ir a la Derecha?
        if ( estadoMovimiento==EstadoMovimiento.MOV_DERECHA) {
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
            if ( celdaDerechaAbajo == null && celdaDerechaArriba == null &&
                    celdaDerechaCentro == null) {
                // Ejecutar movimiento horizontal
                nuevaX += velocidadX;
                // Revisa si hay colisión con enemigo
                if (!colisionaEnemigo(enemigoCercano, nuevaX, 'x')) {
                    // Prueba que no salga del mundo por la derecha
                    if (nuevaX <= (mapa.getProperties().get("width", Integer.class) * 64) - sprite.getWidth()) {
                        sprite.setX(nuevaX);
//                    camaraX += velocidadX;
                    }
                }
            }
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimiento==EstadoMovimiento.MOV_IZQUIERDA) {
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
            if ( celdaIzquierdaAbajo == null && celdaIzquierdaArriba == null &&
                    celdaIzquierdaCentro == null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaX += velocidadX;
                // Revisa si hay colisión con enemigo
                if (!colisionaEnemigo(enemigoCercano, nuevaX, 'x')) {
                    if (nuevaX >= 0) {
                        sprite.setX(nuevaX);
//                    camaraX += velocidadX;
                    }
                }
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
        if ( estadoMovimientoVertical==EstadoMovimientoVertical.MOV_ARRIBA ) {
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
            if ( celdaArribaIzq == null && celdaArribaDer == null &&
                    celdaArribaCentro == null) {
                // Ejecutar movimiento horizontal
                nuevaY += velocidadY;
                // Revisa si hay colisión con enemigo
                if (!colisionaEnemigo(enemigoCercano, nuevaY, 'y')) {
                    // Prueba que no salga del mundo por la arriba
                    if (nuevaY <= (mapa.getProperties().get("height", Integer.class) * 64) - sprite.getHeight()) {
                        sprite.setY(nuevaY);
//                    camaraY += velocidadY;
                    }
                }
            }
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimientoVertical==EstadoMovimientoVertical.MOV_ABAJO ) {
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
            if ( celdaAbajoIzq == null && celdaAbajoDer == null &&
                    celdaAbajoCentro == null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaY += velocidadY;
                // Revisa si hay colisión con enemigo
                if (!colisionaEnemigo(enemigoCercano, nuevaY, 'y')) {
                    if (nuevaY >= 0) {
                        sprite.setY(nuevaY);
//                    camaraY += velocidadY;
                    }
                }
            }
        }
    }

    public void setEnemigoCercano(Enemigo enemigo) {
        enemigoCercano = enemigo;
    }

    public boolean colisionaEnemigo(Enemigo enemigo, float diferencia, char orientacion) {
        Rectangle rect = sprite.getBoundingRectangle();
        int xRect = (int) rect.getX();
        int yRect = (int) rect.getY();

        if (enemigo != null) {
            Rectangle rectEnemigo = enemigo.sprite.getBoundingRectangle();

            if (orientacion == 'x') {
                rect.setPosition(diferencia, yRect);
            } else if (orientacion == 'y') {
                rect.setPosition(xRect, diferencia);
            }

            if (rect.overlaps(rectEnemigo)) {
                return true;
            }
        }
        return false;
    }

    public TiledMapTileLayer.Cell buscaItems(TiledMap mapa) {

        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Recolectables");
        TiledMapTileLayer.Cell celda;

        int xPersonaje = (int) sprite.getX();
        int yPersonaje = (int) sprite.getY();

        for (int x = xPersonaje - 64; x <= xPersonaje + (64*2); x += 32) {
            for (int y = yPersonaje - 64; y <= yPersonaje + (64*2); y += 32) {
                celda = capa.getCell((x / 64), (y / 64));
                if (celda != null && celda.getTile() != null) {
                    return celda;
                }
            }
        }
        return null;
    }

    public TiledMapTileLayer.Cell buscaInteractivos(TiledMap mapa) {

        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Interactivos");
        TiledMapTileLayer.Cell celda;

        int xPersonaje = (int) sprite.getX();
        int yPersonaje = (int) sprite.getY();

        for (int x = xPersonaje - 64; x <= xPersonaje + (64*2); x += 32) {
            for (int y = yPersonaje - 64; y <= yPersonaje + (64*2); y += 32) {
                celda = capa.getCell((x / 64), (y / 64));
                if (celda != null && celda.getTile() != null) {
                    return celda;
                }
            }
        }
        return null;
    }

    public TiledMapTileLayer.Cell buscaPuertas(TiledMap mapa){
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Puerta");
        TiledMapTileLayer.Cell celda;

        int xPersonaje = (int) sprite.getX();
        int yPersonaje = (int) sprite.getY();

        for (int x = xPersonaje - 64; x <= xPersonaje + (64*2); x += 32) {
            for (int y = yPersonaje - 64; y <= yPersonaje + (64*2); y += 32) {
                celda = capa.getCell((x / 64), (y / 64));
                if (celda != null && celda.getTile() != null) {
                    return celda;
                }
            }
        }

        return null;
    }

    public TiledMapTileLayer.Cell buscaSalida(TiledMap mapa) {

        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Interactivos");
        TiledMapTileLayer capaSalidas = (TiledMapTileLayer) mapa.getLayers().get("Salidas");
        TiledMapTileLayer.Cell celda;
        TiledMapTileLayer.Cell celdaSalida;

        int xPersonaje = (int) sprite.getX();
        int yPersonaje = (int) sprite.getY();

        for (int x = xPersonaje - 64; x <= xPersonaje + (64*2); x += 32) {
            for (int y = yPersonaje - 64; y <= yPersonaje + (64*2); y += 32) {
                // Obtiene celda que bloquea la salida
                celda = capa.getCell((x / 64), (y / 64));
                // Obtiene la salida
                celdaSalida = capaSalidas.getCell((x / 64), (y / 64));
                // Si hay una salida sin necesidad de interactuar
                if (celdaSalida != null) {
                    // SE JUNTO EL CONSTRUCTOR DE UN ID QUE SE EJECUTABA DENTRO DE CADA IF, ASI COMO TAMBIEN SE JUNTARON LOS IF
                    Integer id = 0;
                    try {
                        id = (Integer) celdaSalida.getTile().getProperties().get("lugar");
                    } catch (ClassCastException e) {
                        id = Integer.parseInt((String) celdaSalida.getTile().getProperties().get("lugar"));
                    }
                    Nivel.setNivelObjetivo(id);

                    try {
                        if (celda.getTile() == null) {
                            return celdaSalida;
                        }
                    } catch (NullPointerException e) {
                        Object tipo = (String) celdaSalida.getTile().getProperties().get("tipo");
                        if ("entrada".equals(tipo)) {
                            return  celdaSalida;
                        }
                    }



                }

                // Si hay una salida y nada la bloquea...
//                if (celdaSalida != null && celda.getTile() == null) {
//                    return celdaSalida;
//                }
            }
        }
        return null;
    }

    public float getVida(){
        return vida;
    }

    public void setVida(float cambio){
        vida = cambio;
        if (vida<=0){
            estaVivo = false;
        }
        else{
            estaVivo = true;
        }
    }

    // Accesor de estadoMovimiento
    public EstadoMovimiento getEstadoMovimiento() {
        return estadoMovimiento;
    }

    // Modificador de estadoMovimiento
    public void setEstadoMovimiento(EstadoMovimiento estadoMovimiento) {
        if (this.estadoMovimiento == EstadoMovimiento.ATACAR) {
            spriteAnimado = animacionPrevia;
        }
        this.estadoMovimiento = estadoMovimiento;
    }

    // Accesor de estadoMovimiento vertical
    public EstadoMovimientoVertical getEstadoMovimientoVertical() {
        return estadoMovimientoVertical;
    }

    // Modificador de movimiento vertical
    public void setEstadoMovimientoVertical(EstadoMovimientoVertical estadoMovimientoVertical) {
        this.estadoMovimientoVertical = estadoMovimientoVertical;
    }

    public void setVelocidadX(float vx) {
        this.velocidadX = vx;
    }

    public void setVelocidadY(float vy) {
        this.velocidadY = vy;
    }

    public void pararSonido(){
        fxAccion.stop();
        fxPasos.stop();
    }

    public void setViaje() {
        float x = 0;
        float y = 0;
        float ancho = Pantalla.getInstanciaPantalla().getANCHO();
        float alto = Pantalla.getInstanciaPantalla().getALTO();
        switch (localizacion) {
            case BOSQUE:
                switch (destino) {
                    case CABANA:
                        x = ancho / 2;
                        y = alto / 2;
                        localizacion = Localizacion.CABANA;
                        break;
                    case CLINICA:
                        // CAMBIAR
                        x = ancho / 2;
                        y = alto / 2;
                        localizacion = Localizacion.CLINICA;
                        break;
                }
                break;
            case CABANA:
                x = 704; // X fuera de las escaleras de la cabana
                y = 2688; // Y fuera de las escaleras de la cabana
                localizacion = Localizacion.BOSQUE;
                break;
        }
        reset();
        sprite.setPosition(x, y);
        Pantalla.getInstanciaPantalla().camara.position.set(sprite.getX(),
                sprite.getY(), 0);
    }

//    public void setLugarEnemigo(LugarEnemigo lugar) {
//        lugarEnemigo = lugar;
//    }

//    public LugarEnemigo getLugarEnemigo() {
//        return lugarEnemigo;
//    }

    public void render(TiledMap mapa, Nivel nivel) {
        actualizar(mapa);
        interactuar(nivel);
    }

    public enum EstadoMovimiento {
        QUIETO_X,
        MOV_IZQUIERDA,
        MOV_DERECHA,
        ATACAR
    }

    public enum EstadoMovimientoVertical {
        MOV_ARRIBA,
        MOV_ABAJO,
        QUIETO_Y
    }

    public enum Localizacion {
        BOSQUE,
        CABANA,
        CLINICA
    }

    public enum Destino {
        BOSQUE,
        CABANA,
        CLINICA
    }

    public enum Viaje {
        BOSQUE_CABANA,
        BOSQUE_CLINICA,
        CABANA_BOSQUE,
        CLINICA_BOSQUE
    }
}
