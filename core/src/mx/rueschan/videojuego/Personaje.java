package mx.rueschan.videojuego;

/**
 * Created by Odd Fellows on 08/03/2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;

public class Personaje extends Objeto {

    private static Personaje instancia = null;

    private float velocidadX = 0;      // Velocidad en x
    private float velocidadY = 0;      // Velocidad en y

//    private float camaraX = 0;
//    private float camaraY = 0;

    private float vida = 100;

    private ArrayList<Objeto> inventario;

    private Animation<TextureRegion> spriteAnimado;         // Animación caminando
    private Animation<TextureRegion> animacionPrevia;       // Animación previa
    private float timerAnimacion;                           // Tiempo para cambiar frames de la animación
    private TextureRegion[][] texturaPersonaje;

    private EstadoMovimiento estadoMovimiento = EstadoMovimiento.QUIETO;
    private EstadoMovimientoVertical estadoMovimientoVertical = EstadoMovimientoVertical.QUIETO_Y;
    private boolean veDerecha;

    // ASSETS
    private AssetManager manager;
    private Sound fxPasos;
    private String pathFxPasos = "Sonidos/pasoMadera.mp3";
    private Sound fxAccion;
    private String pathFxAccion = "Sonidos/alerta.mp3";

    // Estado de acción
    private boolean estatusAccion = false;

    // Recibe una imagen con varios frames (ver marioSprite.png)
    public Personaje(Texture textura, float x, float y) {
        Pantalla pantalla = Pantalla.getInstanciaPantalla();

        // Crea inventario
        inventario = new ArrayList<Objeto>(10);
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);
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
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO
        sprite.setPosition(x,y);    // Posición inicial
        veDerecha = false; // El sprite esta viendo a la izquierda

        // ASSET MANAGER
        manager = Nivel.getManager();
        manager.load(pathFxPasos, Sound.class);
        manager.load(pathFxAccion, Sound.class);
        manager.finishLoading();    // Carga los recursos

        fxPasos = manager.get(pathFxPasos);
        fxPasos.loop(1,1.5f,0);
        fxPasos.pause();
        fxAccion = manager.get(pathFxAccion);

//        camaraX = pantalla.camara.position.x;
//        camaraY = pantalla.camara.position.y;
    }

    // Método para obtener personaje o crearlo
    public static Personaje getInstanciaPersonaje() {
        if (instancia == null) {
            Pantalla pantalla = Pantalla.getInstanciaPantalla();
            Texture texturaHenric = new Texture("Personaje/Henric.png");
            instancia = new Personaje(texturaHenric, pantalla.getANCHO()/2, pantalla.getALTO()/2);
        }
        return instancia;
    }

    public void reset() {TextureRegion texturaCompleta = new TextureRegion(new Texture("Personaje/Henric.png"));
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
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO
        sprite.setPosition(Pantalla.getInstanciaPantalla().getANCHO()/2,
                Pantalla.getInstanciaPantalla().getALTO()/2);    // Posición inicial
        veDerecha = false; // El sprite esta viendo a la izquierda

    }

    public void setFxPasos(String nuevoFx) {
        manager.unload(pathFxPasos);
        manager.load(nuevoFx, Sound.class);
        manager.finishLoadingAsset(nuevoFx);
        fxPasos = manager.get(nuevoFx);
        fxPasos.toString();
        fxPasos.loop(1,1.5f,0);
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
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO
        sprite.setPosition(x, y);    // Posición inicial
    }

    public void usarArma(TextureRegion[][] textureRegions) {
        this.texturaPersonaje = textureRegions;
        if (estadoMovimiento != EstadoMovimiento.ATACAR) {
            animacionPrevia = spriteAnimado;
        }
        estadoMovimiento = EstadoMovimiento.ATACAR;
        spriteAnimado = new Animation(0.1f, texturaPersonaje[0][2], texturaPersonaje[0][1] );
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.REVERSED);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        float x = sprite.getX();
        float y = sprite.getY();
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO
        sprite.setPosition(x, y);    // Posición inicial
    }

    public void addInventario(Objeto item) {
        inventario.add(item);
    }

    public ArrayList<Objeto> verInventario() {
        return inventario;
    }

    // SE CORRE CADA FRAME
    private void animar(SpriteBatch batch) {
        timerAnimacion += Gdx.graphics.getDeltaTime();
        // Frame que se dibujará
        TextureRegion region = spriteAnimado.getKeyFrame(timerAnimacion);
        if (estadoMovimiento==EstadoMovimiento.MOV_DERECHA) {
            veDerecha = true;
            if (!region.isFlipX()) {
                region.flip(true,false);
            }
        }
        else {
            veDerecha = false;
            if (region.isFlipX()) {
                region.flip(true,false);
            }
        }
        batch.draw(region,sprite.getX(),sprite.getY());
    }

    // Sonido al caminar
    public void darPaso() {
        if (estadoMovimiento == EstadoMovimiento.QUIETO && estadoMovimientoVertical == EstadoMovimientoVertical.QUIETO_Y) {
            fxPasos.pause();
        } else {
            if (Configuraciones.isFxOn) {
                fxPasos.resume();
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
            case QUIETO:
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
            animar(batch);
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
    public void actualizar(TiledMap mapa) {
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
                moverHorizontal(mapa);
                darPaso();
                moverCamara(mapa);
                break;
            case QUIETO:
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
            estadoMovimiento = EstadoMovimiento.QUIETO;
            spriteAnimado = animacionPrevia;
        }
    }

    public void interactuar(Nivel nivel) {
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
        Pantalla pantalla = Pantalla.getInstanciaPantalla();
        // Obtiene la primer capa del mapa (en este caso es la única)
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Limites");
        // Ejecutar movimiento horizontal
        float nuevaX = sprite.getX();
        // ¿Quiere ir a la Derecha?
        if ( estadoMovimiento==EstadoMovimiento.MOV_DERECHA) {
            // Obtiene el bloque del lado derecho. Asigna null si puede pasar.
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
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimiento==EstadoMovimiento.MOV_IZQUIERDA) {
            int xIzq = (int) ((sprite.getX()) / 64);
            int y = (int) ((sprite.getY() + 10) / 64);
            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaIzquierda = capa.getCell(xIzq, y);
            y = (int) ((sprite.getY() + 86) / 64);
            TiledMapTileLayer.Cell celdaIzquierda2 = capa.getCell(xIzq, y);
            if (celdaIzquierda != null) {
                Object tipo = (String) celdaIzquierda.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaIzquierda = null;  // Puede pasar
                }
            }
            if (celdaIzquierda2 != null) {
                Object tipo = (String) celdaIzquierda2.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaIzquierda2 = null;  // Puede pasar
                }
            }
            if ( celdaIzquierda == null && celdaIzquierda2 == null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaX += velocidadX;
                if (nuevaX >= 0) {
                    sprite.setX(nuevaX);
//                    camaraX += velocidadX;
                }
            }
        }
    }

    // Mueve el personaje a la derecha/izquierda, prueba choques con paredes
    private void moverVertical(TiledMap mapa) {
        Pantalla pantalla = Pantalla.getInstanciaPantalla();
        // Obtiene la primer capa del mapa (en este caso es la única)
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Limites");
        // Ejecutar movimiento horizontal
        float nuevaY = sprite.getY();
        // ¿Quiere ir a la Derecha?
        if ( estadoMovimientoVertical==EstadoMovimientoVertical.MOV_ARRIBA ) {
            // Obtiene el bloque de arriba. Asigna null si puede pasar.
            int x = (int) ((sprite.getX() + 10) / 64);   // Convierte coordenadas del mundo en coordenadas del mapa
            int y = (int) ((sprite.getY() + 96) / 64);
            TiledMapTileLayer.Cell celdaArriba = capa.getCell(x, y);
            x = (int) ((sprite.getX() + 86) / 64);
            TiledMapTileLayer.Cell celdaArriba2 = capa.getCell(x, y);
            if (celdaArriba != null) {
                Object tipo = (String) celdaArriba.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaArriba = null;  // Puede pasar
                }
            }
            if (celdaArriba2 != null) {
                Object tipo = (String) celdaArriba2.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaArriba2 = null;  // Puede pasar
                }
            }
            if ( celdaArriba == null && celdaArriba2 == null) {
                // Ejecutar movimiento horizontal
                nuevaY += velocidadY;
                // Prueba que no salga del mundo por la arriba
                if (nuevaY <= (mapa.getProperties().get("height", Integer.class) * 64) - sprite.getHeight()) {
                    sprite.setY(nuevaY);
//                    camaraY += velocidadY;
                }
            }
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimientoVertical==EstadoMovimientoVertical.MOV_ABAJO ) {
            int x = (int) ((sprite.getX() + 10) / 64);
            int yAbajo = (int) (sprite.getY() / 64);
            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaAbajo = capa.getCell(x, yAbajo);
            x = (int) ((sprite.getX() +86) / 64);
            TiledMapTileLayer.Cell celdaAbajo2 = capa.getCell(x, yAbajo);
            if (celdaAbajo != null) {
                Object tipo = (String) celdaAbajo.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaAbajo = null;  // Puede pasar
                }
            }
            if (celdaAbajo2 != null) {
                Object tipo = (String) celdaAbajo2.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaAbajo2 = null;  // Puede pasar
                }
            }
            if ( celdaAbajo == null && celdaAbajo2 == null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaY += velocidadY;
                if (nuevaY >= 0) {
                    sprite.setY(nuevaY);
//                    camaraY += velocidadY;
                }
            }
        }
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
                // Si hay una salida y nada la bloquea...
                if (celdaSalida != null && celda.getTile() == null) {
                    return celdaSalida;
                }
            }
        }
        return null;
    }

    public float getVida(){
        return vida;
    }

    public void setVida(float cambio){
        vida = cambio;
    }

    // Accesor de estadoMovimiento
    public EstadoMovimiento getEstadoMovimiento() {
        return estadoMovimiento;
    }

    // Modificador de estadoMovimiento
    public void setEstadoMovimiento(EstadoMovimiento estadoMovimiento) {
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

    public enum EstadoMovimiento {
        QUIETO,
        MOV_IZQUIERDA,
        MOV_DERECHA,
        ATACAR
    }

    public enum EstadoMovimientoVertical {
        MOV_ARRIBA,
        MOV_ABAJO,
        QUIETO_Y
    }
}
