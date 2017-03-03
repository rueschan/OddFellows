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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by OddFellows on 14/02/2017.
 */
public abstract class Nivel implements Screen{

    protected OddFellows oddFellows;
    protected Pantalla pantalla;
    protected Juego juego;

    // Mapa
    public static final float ANCHO_MAPA = 2560;
    protected OrthogonalTiledMapRenderer renderer; // Dibuja el mapa
    protected TiledMap mapa;

    protected Texture texturaFondo;
    protected Texture texturaBotonPausa;

    //HUD
    protected OrthographicCamera camaraHUD;
    protected Viewport vistaHUD;
    protected Stage escenaHUD;
    protected Touchpad pad;

    // private Elemento[] items;

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
        cargarJuego();
    }

    protected void cargarJuego(){
        juego = Juego.getJuego(oddFellows);
    };

    protected abstract void crearObjetos();

    protected abstract void cargarTexturas();

    protected void crearRecursos(Pantalla pantalla, String nombreMapa) {

        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(nombreMapa, TiledMap.class);

        manager.finishLoading();    // Carga los recursos
        mapa = manager.get(nombreMapa);

        pantalla.batch = new SpriteBatch();
        renderer = new OrthogonalTiledMapRenderer(mapa, pantalla.batch);
        renderer.setView(pantalla.camara);
    }

    protected void crearHUD(Pantalla pantalla) {
        // Cámara HUD
        camaraHUD = new OrthographicCamera(pantalla.getANCHO(),pantalla.getALTO());
        camaraHUD.position.set(pantalla.getANCHO()/2, pantalla.getALTO()/2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(pantalla.getANCHO(), pantalla.getALTO(), camaraHUD);

        // HUD
        Skin skin = new Skin();
        skin.add("padBack", new Texture("Pad/padBack.png"));
        skin.add("padKnob", new Texture("Pad/padKnob.png"));

        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("padBack");
        estilo.knob = skin.getDrawable("padKnob");

        pad = new Touchpad(20, estilo);
        pad.setBounds(0, 0, 200, 200);
        pad.setColor(1,1,1,0.4f);

        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad) actor;
//                if (pad.getKnobPercentX()>0.20) {
//                    mario.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_DERECHA);
//                } else if (pad.getKnobPercentX()<-0.20){
//                    mario.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_IZQUIERDA);
//                } else {
//                    mario.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);

                // DESAPARECER PAD
                // pad.setColor(1,1,1,0);
//                }
            }
        });

        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);

        escenaHUD.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                pad.setPosition(x-pad.getWidth()/2,y-pad.getHeight()/2);
            }
        });
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();

        Gdx.app.log("Nivel","Activo");
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
}
