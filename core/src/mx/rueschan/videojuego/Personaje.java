package mx.rueschan.videojuego;

/**
 * Created by Rubén Escalante on 08/03/2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;

public class Personaje extends Objeto
{
    private float velocidadX = 0;      // Velocidad en x
    private float velocidadY = 0;      // Velocidad en y

    private ArrayList<Objeto> inventario;

    private Animation<TextureRegion> spriteAnimado;         // Animación caminando
    private float timerAnimacion;                           // Tiempo para cambiar frames de la animación

    private EstadoMovimiento estadoMovimiento = EstadoMovimiento.QUIETO;
    private EstadoMovimientoVertical estadoMovimientoVertical = EstadoMovimientoVertical.QUIETO_Y;

    // ASSETS
    private AssetManager manager;
    public Sound fxPasos;
    protected String pathFxPasos = "Sonido/pasoMadera.ogg";


    // Recibe una imagen con varios frames (ver marioSprite.png)
    public Personaje(Texture textura, float x, float y) {
        // Crea inventario
        inventario = new ArrayList<Objeto>(10);
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);
        // La divide en 4 frames de 32x64 (ver marioSprite.png)
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(96,96);
        // Crea la animación con tiempo de 0.15 segundos entre frames.

        spriteAnimado = new Animation(0.15f, texturaPersonaje[0][2], texturaPersonaje[0][1] );
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO
        sprite.setPosition(x,y);    // Posición inicial

        // ASSET MANAGER
        manager = Nivel.getManager();
        manager.load(pathFxPasos,Sound.class);
        manager.finishLoading();    // Carga los recursos

        fxPasos = manager.get(pathFxPasos);
        fxPasos.loop(1,1.5f,0);
    }

    public void addInventario(Objeto item) {
        inventario.add(item);
    }

    private void animar(SpriteBatch batch) {
        timerAnimacion += Gdx.graphics.getDeltaTime();
        // Frame que se dibujará
        TextureRegion region = spriteAnimado.getKeyFrame(timerAnimacion);
        if (estadoMovimiento==EstadoMovimiento.MOV_DERECHA) {
            if (!region.isFlipX()) {
                region.flip(true,false);
            }
        } else {
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
            fxPasos.resume();
        }
    }

    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {

        boolean enMovimiento = false;

        // Dibuja el personaje dependiendo del estadoMovimiento
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
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
            sprite.draw(batch); // Dibuja el sprite estático
        } else {
            animar(batch);
        }
    }

    // Actualiza el sprite, de acuerdo al estadoMovimiento y estadoSalto
    public void actualizar(TiledMap mapa) {
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
                moverHorizontal(mapa);
                darPaso();
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
                break;
            case QUIETO_Y:
                darPaso();
                break;
        }
    }

    public void interactuar(Nivel nivel) {
        if (buscaItems(nivel.mapa) != null) {
            nivel.btnInteraccion.setColor(1,1,1,1);
            nivel.btnInteraccion.setDisabled(false);
            nivel.tileObjetivo = buscaItems(nivel.mapa);
        } else {
            nivel.btnInteraccion.setColor(1,1,1,0.4f);
            nivel.btnInteraccion.setDisabled(true);
            nivel.tileObjetivo = null;
        }
    }


    // Mueve el personaje a la derecha/izquierda, prueba choques con paredes
    private void moverHorizontal(TiledMap mapa) {
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
            if (celdaDerecha != null) {
                Object tipo = (String) celdaDerecha.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaDerecha = null;  // Puede pasar
                }
            }
            if ( celdaDerecha==null) {
                // Ejecutar movimiento horizontal
                nuevaX += velocidadX;
                // Prueba que no salga del mundo por la derecha
                if (nuevaX <= Pantalla.getInstanciaPantalla().getANCHO() - sprite.getWidth()) {
                    sprite.setX(nuevaX);
                }
            }
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimiento==EstadoMovimiento.MOV_IZQUIERDA) {
            int xIzq = (int) ((sprite.getX()) / 64);
            int y = (int) ((sprite.getY() + 10) / 64);
            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaIzquierda = capa.getCell(xIzq, y);
            if (celdaIzquierda != null) {
                Object tipo = (String) celdaIzquierda.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaIzquierda = null;  // Puede pasar
                }
            }
            if ( celdaIzquierda==null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaX += velocidadX;
                if (nuevaX >= 0) {
                    sprite.setX(nuevaX);
                }
            }
        }
    }

    // Mueve el personaje a la derecha/izquierda, prueba choques con paredes
    private void moverVertical(TiledMap mapa) {
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
            if (celdaArriba != null) {
                Object tipo = (String) celdaArriba.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaArriba = null;  // Puede pasar
                }
            }
            if ( celdaArriba==null) {
                // Ejecutar movimiento horizontal
                nuevaY += velocidadY;
                // Prueba que no salga del mundo por la arriba
                if (nuevaY <= Pantalla.getInstanciaPantalla().getALTO() - sprite.getHeight()) {
                    sprite.setY(nuevaY);
                }
            }
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimientoVertical==EstadoMovimientoVertical.MOV_ABAJO ) {
            int x = (int) ((sprite.getX() + 10) / 64);
            int yAbajo = (int) (sprite.getY() / 64);
            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaAbajo = capa.getCell(x, yAbajo);
            if (celdaAbajo != null) {
                Object tipo = (String) celdaAbajo.getTile().getProperties().get("tipo");
                if (!"obstaculo".equals(tipo)) {
                    celdaAbajo = null;  // Puede pasar
                }
            }
            if ( celdaAbajo==null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaY += velocidadY;
                if (nuevaY >= 0) {
                    sprite.setY(nuevaY);
                }
            }
        }
    }

    private final int DIFERENCIA = 64;
    private final int DIVISION = 64;

    public TiledMapTileLayer.Cell buscaItems(TiledMap mapa) {

        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Recolectables");
        int xPersonaje = (int) sprite.getX();
        int yPersonaje = (int) sprite.getY();

        // Celdas que rodean al personaje
        ArrayList<TiledMapTileLayer.Cell> celdas = new ArrayList<TiledMapTileLayer.Cell>(12);

        celdas.add(capa.getCell((xPersonaje - DIFERENCIA) / DIVISION, (yPersonaje + DIFERENCIA*2 - 1) / DIVISION));         // Arriba Izq
        celdas.add(capa.getCell((xPersonaje) / DIVISION, (yPersonaje + DIFERENCIA*2 - 1) / DIVISION));                      // Arriba CentroI
        celdas.add(capa.getCell((xPersonaje + DIFERENCIA) / DIVISION, (yPersonaje + DIFERENCIA*2 - 1) / DIVISION));         // Arriba CentroD
        celdas.add(capa.getCell((xPersonaje + DIFERENCIA*2 - 1) / DIVISION, (yPersonaje + DIFERENCIA*2 - 1) / DIVISION));   // Arriba Der

        celdas.add(capa.getCell((xPersonaje - DIFERENCIA) / DIVISION, (yPersonaje - DIFERENCIA) / DIVISION));               // Abajo Izq
        celdas.add(capa.getCell((xPersonaje) / DIVISION, (yPersonaje - DIFERENCIA) / DIVISION));                            // Abajo CentroI
        celdas.add(capa.getCell((xPersonaje + DIFERENCIA) / DIVISION, (yPersonaje - DIFERENCIA) / DIVISION));               // Abajo CentroD
        celdas.add(capa.getCell((xPersonaje + DIFERENCIA*2 - 1) / DIVISION, (yPersonaje - DIFERENCIA) / DIVISION));         // Abajo Der

        celdas.add(capa.getCell((xPersonaje - DIFERENCIA) / DIVISION, (yPersonaje) / DIVISION));                // Izq
        celdas.add(capa.getCell((xPersonaje) / DIVISION, (yPersonaje) / DIVISION));                             // CentroI
        celdas.add(capa.getCell((xPersonaje + DIFERENCIA) / DIVISION, (yPersonaje) / DIVISION));                // CentroD
        celdas.add(capa.getCell((xPersonaje + DIFERENCIA*2 - 1) / DIVISION, (yPersonaje) / DIVISION));          // Der


        for (TiledMapTileLayer.Cell c : celdas) {
            if (c != null && c.getTile() != null) {
                return c;
            }
        }
        return null;
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

    public enum EstadoMovimiento {
        QUIETO,
        MOV_IZQUIERDA,
        MOV_DERECHA
    }

    public enum EstadoMovimientoVertical {
        MOV_ARRIBA,
        MOV_ABAJO,
        QUIETO_Y
    }
}
