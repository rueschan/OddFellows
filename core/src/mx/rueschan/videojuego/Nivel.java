package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    protected TiledMapTileLayer.Cell tileInteractivo;

    // Texturas HUD
    protected Texture texturaBotonPausa;
    protected Texture texturaHP;
    protected Texture texturaBarraHP;

    //HUD
    protected OrthographicCamera camaraHUD;
    protected Viewport vistaHUD;
    protected Stage escenaHUD;
    protected Touchpad pad;
    protected Objeto hp;
    protected Objeto barraHP;

    //Pausa
    protected Texture regionPausa;
    protected Boolean pausado;

        //Textura en Menu Pausa
    protected Texture texturaBotonReanudar;
    protected Texture texturaBotonSalir;
    protected Texture texturaSonido;
    protected Texture texturaFX;

    //INTERACCION
    protected Texture texturaInteraccin;
    public ImageButton btnInteraccion;
    protected Texture texturaAccion;
    public ImageButton btnAccion;
    public Objeto alertaAccion;

    //Manejador de assets
    protected static AssetManager manager;

    protected Music musicaFondo;
    protected Sound fxLlave;
    protected String pathFxLlave = "Sonidos/levantarLlave.mp3";
    protected Sound fxCarta;
    protected String pathFxCarta = "Sonidos/levantarPapel.mp3";

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

        // Vida
        texturaHP = new Texture("Pantalla/HP.png");
        texturaBarraHP = new Texture("Pantalla/BarraHP.png");
        hp = new Objeto(texturaHP, 10, pantalla.getALTO() - 10 - texturaHP.getHeight());
        barraHP = new Objeto(texturaBarraHP, 10, pantalla.getALTO() - 10 - texturaHP.getHeight());

        manager = new AssetManager();
        manager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(nombreMapa, TiledMap.class);
        manager.load(nombreMusicaFondo,Music.class);
        manager.load(pathFxLlave, Sound.class);
        manager.load(pathFxCarta, Sound.class);

        manager.finishLoading();    // Carga los recursos
        mapa = manager.get(nombreMapa);

        pantalla.batch = new SpriteBatch();
        renderer = new OrthogonalTiledMapRenderer(mapa, pantalla.batch);
        renderer.setView(pantalla.camara);
        musicaFondo = manager.get(nombreMusicaFondo);
        musicaFondo.setLooping(true);
        musicaFondo.play();

        // Sonidos generales
        fxLlave = manager.get(pathFxLlave);
        fxCarta = manager.get(pathFxCarta);
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
                henric.setVelocidadX(pad.getKnobPercentX() * 5);
                henric.setVelocidadY(pad.getKnobPercentY() * 5);

                if (pad.getKnobPercentY() == 0 && pad.getKnobPercentX() == 0) {
                    henric.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
                    henric.setEstadoMovimientoVertical(Personaje.EstadoMovimientoVertical.QUIETO_Y);
                }
            }
        });

            // INTERACCIÓN
        //// Asignar textura a botón de acción
        texturaAccion = new Texture("Pantalla/Accion.png");
        alertaAccion = new Objeto(texturaAccion, 0, 0);
        alertaAccion.sprite.setColor(1, 1, 1, 0);

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
                    Objeto obj = identificarItem(tileObjetivo);
                    henric.addInventario(obj);
                    tileObjetivo.setTile(null);
                }
            }
        });

        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);
        escenaHUD.addActor(btnInteraccion);
        //escenaHUD.addActor(btnAccion);
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

    private Objeto identificarItem(TiledMapTileLayer.Cell celda) {
        // ID:
        // Llaves: 49
        // Martillo: 81
        // Carta: 63
        Gdx.app.log("ItemID", String.valueOf(celda.getTile().getId()));
        switch (celda.getTile().getId()){
            case 49: //Llave
                fxLlave.play();
                Llave llave;
                return llave = new Llave(0, 0, (int) (Math.random()*10) + 1); // Valores del 1 al 10
            case 63:
                fxCarta.play();
                break;
            case 81:
                Texture texturaMartillo = new Texture("Items/Martillo.png");
                Arma martillo;
                return martillo = new Arma(texturaMartillo, 0, 0, 30, "romper");

        }
        return null;
    }

    protected void dibujar(SpriteBatch batch) {
        alertaAccion.dibujar(batch);
        henric.dibujar(batch);
        hp.dibujar(batch);
        barraHP.dibujar(batch);
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();

    }

    protected void crearPausa(Stage escenaHUD){

        pausado=false;

        //Textura de pausa
        // Crear triángulo transparente
        Pixmap pixmap = new Pixmap((int)(pantalla.getANCHO()*0.5f), (int)(pantalla.getALTO()*0.8f), Pixmap.Format.RGBA8888 );
        pixmap.setColor( 0.2f, 0.3f, 0.5f, 0.65f );
        pixmap.fillRectangle(0,0,(int)pantalla.getALTO(),(int)pantalla.getALTO());
        regionPausa = new Texture( pixmap );
        pixmap.dispose();
        Image cuadroPausa = new Image(regionPausa);
        cuadroPausa.setPosition(0.25f*pantalla.getANCHO(), 0.1f*pantalla.getALTO());

        //Crear texturas
        texturaBotonReanudar = new Texture("Pantalla/Tabla.png");
        texturaBotonSalir = new Texture("Pantalla/Tabla.png");
        texturaSonido = new Texture("Pantalla/Audio.png");
        texturaFX = new Texture("Pantalla/ecualizador.png");

        //Crear boton Reanudar
        TextureRegionDrawable trdBtnReanudar = new
                TextureRegionDrawable(new TextureRegion(texturaBotonReanudar));
        // Colocar botón Reanudar
        ImageButton btnReanudar = new ImageButton(trdBtnReanudar);
        btnReanudar.setPosition(pantalla.getANCHO()/2 - btnReanudar.getWidth()/2,3*pantalla.getALTO()/4
                - btnReanudar.getHeight()/2);

        //Crear boton Salir
        TextureRegionDrawable trdBtnSalir = new
                TextureRegionDrawable(new TextureRegion(texturaBotonSalir));
        // Colocar botón Salir
        ImageButton btnSalir = new ImageButton(trdBtnSalir);
        btnSalir.setPosition(pantalla.getANCHO()/2 - btnSalir.getWidth()/2,pantalla.getALTO()/4
                - btnSalir.getHeight()/2);


        //Crear boton Sonido
        TextureRegionDrawable trdBtnSonido = new
                TextureRegionDrawable(new TextureRegion(texturaSonido));
        // Colocar botón Sonido
        ImageButton btnSonido = new ImageButton(trdBtnSonido);
        btnSonido.setPosition(3*pantalla.getANCHO()/5 - btnSonido.getWidth()/2,pantalla.getALTO()/2
                - btnSonido.getHeight()/2);

        //Crear boton Musica
        TextureRegionDrawable trdBtnMusica = new
                TextureRegionDrawable(new TextureRegion(texturaFX));
        // Colocar botón Musica
        ImageButton btnMusica = new ImageButton(trdBtnMusica);
        btnMusica.setPosition(2*pantalla.getANCHO()/5 - btnMusica.getWidth()/2,pantalla.getALTO()/2
                - btnMusica.getHeight()/2);





        //Cuadro de pausa actor 3
        escenaHUD.addActor(cuadroPausa);
        cuadroPausa.setVisible(false);

        //Cuadro de reanudar actor 4
        escenaHUD.addActor(btnReanudar);
        btnReanudar.setVisible(false);

        //Cuadro de salir actor 5
        escenaHUD.addActor(btnSalir);
        btnSalir.setVisible(false);

        //Cuadro de salir actor 6
        escenaHUD.addActor(btnSonido);
        btnSonido.setVisible(false);

        //Cuadro de salir actor 7
        escenaHUD.addActor(btnMusica);
        btnMusica.setVisible(false);
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
