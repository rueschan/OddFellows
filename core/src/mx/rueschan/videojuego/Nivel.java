package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by OddFellows on 14/02/2017.
 */
public abstract class Nivel implements Screen{

    protected OddFellows oddFellows;
    protected Pantalla pantalla;
    protected Juego juego;

    // Personaje
    protected Personaje henric;
    protected Texture texturaHenric;

    // Mapa
    public static final float ANCHO_MAPA = 2560;
    protected OrthogonalTiledMapRenderer renderer; // Dibuja el mapa
    protected TiledMap mapa;
    protected TiledMapTileLayer.Cell tileObjetivo;

    protected Texture texturaBotonPausa;

    //HUD
    protected OrthographicCamera camaraHUD;
    protected Viewport vistaHUD;
    protected Stage escenaHUD;
    protected Touchpad pad;

    //INTERACCION
    protected Texture texturaInteraccin;
    public ImageButton btnInteraccion;

    //Manejador de assets
    protected static AssetManager manager;

    protected Music musicaFondo;

    // private Elemento[] items;

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
        cargarJuego();
    }

    public static AssetManager getManager() {
        if (manager != null) {
            return manager;
        }
        return new AssetManager();
    }

    protected void cargarJuego(){
        juego = Juego.getJuego(oddFellows);
    };

    protected abstract void crearObjetos();

    protected abstract void cargarTexturas();

    protected void crearRecursos(Pantalla pantalla, String nombreMapa, String nombreMusicaFondo) {
        texturaHenric = new Texture("Personaje/Henric.png");
        henric = new Personaje(texturaHenric, pantalla.getANCHO()/2, pantalla.getALTO()/2);

        manager = new AssetManager();
        manager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(nombreMapa, TiledMap.class);
        manager.load(nombreMusicaFondo,Music.class);

        manager.finishLoading();    // Carga los recursos
        mapa = manager.get(nombreMapa);

        pantalla.batch = new SpriteBatch();
        renderer = new OrthogonalTiledMapRenderer(mapa, pantalla.batch);
        renderer.setView(pantalla.camara);
        musicaFondo = manager.get(nombreMusicaFondo);
        musicaFondo.setLooping(true);
        musicaFondo.play();
    }

    protected void crearHUD(final Pantalla pantalla) {
        // Cámara HUD
        camaraHUD = new OrthographicCamera(pantalla.getANCHO(),pantalla.getALTO());
        camaraHUD.position.set(pantalla.getANCHO()/2, pantalla.getALTO()/2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(pantalla.getANCHO(), pantalla.getALTO(), camaraHUD);

        // HUD
            // PAD
        Skin skin = new Skin();
        skin.add("padBack", new Texture("Pad/padBack.png"));
        skin.add("padKnob", new Texture("Pad/padKnob.png"));

        final Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("padBack");
        estilo.knob = skin.getDrawable("padKnob");

        pad = new Touchpad(20, estilo);
        pad.setBounds(0,0,200,200);
        pad.setSize(pantalla.getANCHO()*2, pantalla.getALTO()*2);
        pad.setPosition(pantalla.getANCHO()/2 - pad.getWidth()/2,
                pantalla.getALTO()/2 - pad.getHeight()/2);
        pad.setColor(1,1,1,0);

        pad.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad) actor;
                if (pad.getKnobPercentX() > 0.20) {
                    henric.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_DERECHA);
                } else if (pad.getKnobPercentX()< -0.20){
                    henric.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_IZQUIERDA);
                }

                if (pad.getKnobPercentY() > 0.20) {
                    henric.setEstadoMovimientoVertical(Personaje.EstadoMovimientoVertical.MOV_ARRIBA);
//                    henric.sprite.setPosition(henricX, henricY + 2*pad.getKnobPercentY());
//                    henricY = henric.sprite.getY();
                } else if (pad.getKnobPercentY() < -0.20) {
                    henric.setEstadoMovimientoVertical(Personaje.EstadoMovimientoVertical.MOV_ABAJO);
//                    henric.sprite.setPosition(henricX, henricY + 2*pad.getKnobPercentY());
//                    henricY = henric.sprite.getY();
                }

                // Asignar velocidades
                henric.setVelocidadX(pad.getKnobPercentX() * 4);
                henric.setVelocidadY(pad.getKnobPercentY() * 4);

                if (pad.getKnobPercentY() == 0 && pad.getKnobPercentX() == 0) {
                    henric.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
                    henric.setEstadoMovimientoVertical(Personaje.EstadoMovimientoVertical.QUIETO_Y);
                }
            }
        });

            // INTERACCIÓN
        //// Asignar textura al boton de interación
        texturaInteraccin = new Texture("Pantalla/BotonInteraccion.png");
        TextureRegionDrawable trdBtnInteraccion = new
                TextureRegionDrawable(new TextureRegion(texturaInteraccin));


        // Colocar boton de interación
        btnInteraccion = new ImageButton(trdBtnInteraccion);
        btnInteraccion.setPosition(pantalla.getANCHO()-btnInteraccion.getWidth()-pantalla.getANCHO()*.02f,
                pantalla.getALTO()*.02f);
        btnInteraccion.setColor(1,1,1,0.4f);

        btnInteraccion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!btnInteraccion.isDisabled()) {
                    Gdx.app.log("Btn", "Elimina!");
                    tileObjetivo.setTile(null);
                }
            }
        });

        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);
        escenaHUD.addActor(btnInteraccion);

        escenaHUD.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (y < pantalla.getALTO()/6 && x > pantalla.getANCHO()* 6/7) {

                } else {
                    pad.setSize(200, 200);
                    pad.setPosition(x - pad.getWidth() / 2, y - pad.getHeight() / 2);
                    pad.setColor(1, 1, 1, 0.4f);
                    henric.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
                    henric.setEstadoMovimientoVertical(Personaje.EstadoMovimientoVertical.QUIETO_Y);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pad.setColor(1,1,1,0);
                pad.setSize(pantalla.getANCHO()*2,pantalla.getALTO()*2);
                pad.setPosition(pantalla.getANCHO()/2 - pad.getWidth()/2,
                        pantalla.getALTO()/2 - pad.getHeight()/2);
                henric.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
                henric.setEstadoMovimientoVertical(Personaje.EstadoMovimientoVertical.QUIETO_Y);
            }
        });
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();

    }

    @Override
    public void resume() {
        cargarTexturas();
        crearObjetos();
    }

    protected void cambiarNivel(Nivel nvl) {
        juego.guardarStatus();
        // juego.cargarNivel(nvl);
        this.dispose();

    }

    @Override
    public void resize(int width, int height) {
        vistaHUD.update(width, height);
        pantalla.resize(width, height);
    }
}
