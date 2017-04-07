package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

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
    protected ArrayList<Objeto> inventario = new ArrayList<Objeto>();
    protected Objeto seleccionado;
    protected boolean isArmado = false;

    // Mapa
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
    protected Objeto hp;
    protected Objeto barraHP;

    // Actores HUD
    public Image oscuroPausa;
    public Image cuadroInventario;
    public Image regresarInventario;
    public Image fondoMenuImagen;
    public Image cuadroPausa;
    protected Touchpad pad;
    public ImageButton btnInteraccion;
    public ImageButton btnInventario;
    public ImageButton btnCerrar;
    public ImageButton btnItem;
    public ImageButton btnEntrar;
    public ImageButton btnPausa;
    public ImageButton btnReanudar;
    public ImageButton btnSalir;
    public ImageButton btnFX;
    public ImageButton btnMusica;
    public String nombreActores[] = new String[16];

    //Indices
    protected int indiceActoresAntesPausa;
    protected int indiceActoresPausa;
    protected List<Integer> actoresAparte = new ArrayList<Integer>();
    protected List<String> actoresAparecenPausa = new ArrayList<String>();
    protected List<String> actoresAparecenInventario = new ArrayList<String>();
    protected List<String> actoresAparecenCarta = new ArrayList<String>();
    protected List<String> actoresAparecenInicialmente = new ArrayList<String>();



    //Pausa
    protected Texture regionPausa;
    protected Texture regionOscura;
    protected Texture fondoMenu;
    protected Boolean pausado;

    //En carta
    protected Boolean enCarta = false;

        //Textura en Menu Pausa
    protected Texture texturaBotonReanudar;
    protected Texture texturaBotonSalir;
    protected Texture texturaMusica;
    protected Texture texturaFX;

    //Inventario
    protected Boolean enInventario;

        //Textura en Inventario
    protected Texture regionInventario;
    protected Texture regionRegresarInventario;

    //INTERACCION
    private Texture texturaInteraccion;
    private Texture texturaAccion;
    public Objeto fondoAccion;
    private Texture texturaInventario;
    private TextureRegionDrawable trdBtnItem;
    public Objeto alertaAccion;
    protected Texture texturaEntrar;




    // Texturas carta e inventario
    Objeto fondoCarta;
    private Texture texturaCerrar;
    private Texto txt;

    //Manejador de assets
    protected static AssetManager manager;

    protected Music musicaFondo;
    protected Music musicaPausa;
    protected String pathMusicaPausa = "Musica/giantwyrm.mp3";
    protected Sound fxLlave;
    protected String pathFxLlave = "Sonidos/levantarLlave.mp3";
    protected Sound fxCarta;
    protected String pathFxCarta = "Sonidos/levantarPapel.mp3";
    protected Sound fxMartillo;
    protected String pathFxMartillo = "Sonidos/levantarMartillo.mp3";
    private Sound fxInventario;
    private String pathFxInventario = "Sonidos/zipper.mp3";

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
    }

    protected abstract void crearObjetos();

    protected abstract void cargarTexturas();

    protected void crearRecursos(Pantalla pantalla, String nombreMapa, String nombreMusicaFondo) {
        // Henric
        henric = Personaje.getInstanciaPersonaje();
//        texturaHenric = new Texture("Personaje/Henric.png");
//        henric = new Personaje(texturaHenric, pantalla.getANCHO()/2, pantalla.getALTO()/2,nombreFXPasos);

        // Texto cartas
        txt = new Texto();
        txt.hacerMensajes(new Color(0,0,0,1), "");

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
        manager.load(pathMusicaPausa,Music.class);
        manager.load(pathFxLlave, Sound.class);
        manager.load(pathFxCarta, Sound.class);
        manager.load(pathFxMartillo, Sound.class);
        manager.load(pathFxInventario, Sound.class);

        manager.finishLoading();    // Carga los recursos

        mapa = manager.get(nombreMapa);

        pantalla.batch = new SpriteBatch();
        renderer = new OrthogonalTiledMapRenderer(mapa, pantalla.batch);
        renderer.setView(pantalla.camara);
        musicaFondo = manager.get(nombreMusicaFondo);
        musicaFondo.setLooping(true);
        if (Configuraciones.isMusicOn)
            musicaFondo.play();

        // Sonidos generales
        fxLlave = manager.get(pathFxLlave);
        fxCarta = manager.get(pathFxCarta);
        fxMartillo = manager.get(pathFxMartillo);
        fxInventario = manager.get(pathFxInventario);

        
    }

    protected void crearElementosPantalla(final Pantalla pantalla){
        crearHUD(pantalla);
        crearAlerta();
        crearBtnInteraccion();
        crearBtnAccion();
        crearBtnInventario();
        crearCartas();
        crearPausa(escenaHUD);
        crearInventario(escenaHUD);
        addActoresHUD();
        crearAccionesBotones();
        mostrarHUDInicial();
//        agregarActoresInicialesHUD();
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
    }

    private void crearAccionesBotones() {
        btnInteraccion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!btnInteraccion.isDisabled()) {
                    Objeto obj = identificarItem(tileObjetivo);
                    henric.addInventario(obj);
                    tileObjetivo.setTile(null);
                }
            }
        });

        btnInventario.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inventario = henric.verInventario();
                // Muestra la pantalla de inventario
                enInventario = irInventario(enInventario,escenaHUD);
            }
        });

        btnCerrar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cerrarCarta();
            }
        });

        btnItem.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!btnItem.isDisabled()) {
                    ejecutarAccion();
                    Gdx.app.log("btnItem","");
                }
                Gdx.app.log("btnItem","fuera");
            }
        });

        btnEntrar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Para quitar la pausa
                oddFellows.setScreen(new NivelBosque(oddFellows));
                musicaFondo.stop();
                //henric.pararSonido();
            }
        });

        btnPausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Para quitar la pausa
                pausado = pausar(pausado, escenaHUD);
            }
        });

        btnReanudar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pausado = pausar(pausado,escenaHUD);
            }
        });

        btnSalir.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(pantalla.escena);
                musicaPausa.stop();
                oddFellows.crearMusica();
                juego.actual = null;
                henric.pararSonido();
                henric.reset();
                pantalla.resetCamara();
                oddFellows.setScreen(new MenuPrincipal(oddFellows));
            }
        });

        btnFX.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Configuraciones.cambiaFx();
            }
        });

        btnMusica.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Configuraciones.cambiaMusica();
                tocarMusica();
            }
        });
    }

    private void addActoresHUD() {

        escenaHUD = new Stage(vistaHUD);

//        public Image oscuroPausa;
        escenaHUD.addActor(oscuroPausa);//Actor en posicion 0
        escenaHUD.getActors().get(0).setName("oscuroPausa");
        escenaHUD.getActors().get(0).setVisible(false);
        oscuroPausa.setVisible(false);
        actoresAparecenInventario.add("oscuroPausa");
//        Gdx.app.log("woloo", "0");

//        public Image cuadroInventario;
        escenaHUD.addActor(cuadroInventario);//Actor en posicion 1
        escenaHUD.getActors().get(1).setName("cuadroInventario");
        cuadroInventario.setVisible(false);
        actoresAparecenInventario.add("cuadroInventario");
//        Gdx.app.log("woloo", "1");

//        public Image regresarInventario;
        escenaHUD.addActor(regresarInventario);//Actor en posicion 2
        escenaHUD.getActors().get(2).setName("regresarInventario");
        regresarInventario.setVisible(false);
        actoresAparecenInventario.add("regresarInventario");
//        Gdx.app.log("woloo", "2");

//        public Image fondoMenuImagen;
        escenaHUD.addActor(fondoMenuImagen);//Actor en posicion 3
        escenaHUD.getActors().get(3).setName("fondoMenuImagen");
        fondoMenuImagen.setVisible(false);
        actoresAparecenPausa.add("fondoMenuImagen");
//        Gdx.app.log("woloo", "3");

//        public Image cuadroPausa;
        escenaHUD.addActor(cuadroPausa);//Actor en posicion 4
        escenaHUD.getActors().get(4).setName("cuadroPausa");
        cuadroPausa.setVisible(false);
        actoresAparecenPausa.add("cuadroPausa");
//        Gdx.app.log("woloo", "4");

//        protected Touchpad pad;
        escenaHUD.addActor(pad);//Actor en posicion 5
        escenaHUD.getActors().get(5).setName("pad");
        pad.setVisible(false);
        actoresAparecenInicialmente.add("pad");
//        Gdx.app.log("woloo", "5");

//        public ImageButton btnInteraccion;
        escenaHUD.addActor(btnInteraccion);//Actor en posicion 6
        escenaHUD.getActors().get(6).setName("btnInteraccion");
        btnInteraccion.setVisible(false);
        actoresAparecenInicialmente.add("btnInteraccion");
//        Gdx.app.log("woloo", "6");

//        public ImageButton btnInventario;
        escenaHUD.addActor(btnInventario);//Actor en posicion 7
        escenaHUD.getActors().get(7).setName("btnInventario");
        btnInventario.setVisible(false);
        actoresAparecenInicialmente.add("btnInventario");
//        Gdx.app.log("woloo", "7");

//        public ImageButton btnCerrar;
        escenaHUD.addActor(btnCerrar);//Actor en posicion 8
        escenaHUD.getActors().get(8).setName("btnCerrar");
        btnCerrar.setVisible(false);
        actoresAparecenCarta.add("btnCerrar");
//        Gdx.app.log("woloo", "8");

//        public ImageButton btnItem;
        escenaHUD.addActor(btnItem);//Actor en posicion 9
        escenaHUD.getActors().get(9).setName("btnItem");
        btnItem.setVisible(false);
//        Gdx.app.log("woloo", "9");

//        public ImageButton btnEntrar;
        escenaHUD.addActor(btnEntrar);//Actor en posicion 10
        escenaHUD.getActors().get(10).setName("btnEntrar");
        btnEntrar.setVisible(false);
//        Gdx.app.log("woloo", "10");

//        public ImageButton btnPausa;
        escenaHUD.addActor(btnPausa);//Actor en posicion 11
        escenaHUD.getActors().get(11).setName("btnPausa");
        btnPausa.setVisible(false);
        actoresAparecenInicialmente.add("btnPausa");
//        Gdx.app.log("woloo", "11");

//        public ImageButton btnReanudar;
        escenaHUD.addActor(btnReanudar);//Actor en posicion 12
        escenaHUD.getActors().get(12).setName("btnReanudar");
        btnReanudar.setVisible(false);
        actoresAparecenPausa.add("btnReanudar");
//        Gdx.app.log("woloo", "12");

//        public ImageButton btnSalir;
        escenaHUD.addActor(btnSalir);//Actor en posicion 13
        escenaHUD.getActors().get(13).setName("btnSalir");
        btnSalir.setVisible(false);
        actoresAparecenPausa.add("btnSalir");
//        Gdx.app.log("woloo", "13");

//        public ImageButton btnFX;
        escenaHUD.addActor(btnFX);//Actor en posicion 14
        escenaHUD.getActors().get(14).setName("btnFX");
        btnFX.setVisible(false);
        actoresAparecenPausa.add("btnFX");
//        Gdx.app.log("woloo", "14");

//        public ImageButton btnMusica;
        escenaHUD.addActor(btnMusica);//Actor en posicion 15
        escenaHUD.getActors().get(15).setName("btnMusica");
        btnMusica.setVisible(false);
        actoresAparecenPausa.add("btnMusica");
//        Gdx.app.log("woloo", "15");

        for (int i = 0; i < escenaHUD.getActors().size; i++) {
            nombreActores[i] = escenaHUD.getActors().get(i).getName();
        }

        // Crea los limites de pad y botones, es decir, evita que se cree el pad sobre los botones
        escenaHUD.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (y < pantalla.getALTO()/6 && x > pantalla.getANCHO()* 6.5/10) {

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

    private void mostrarHUDInicial(){
        int actorHUD = escenaHUD.getActors().size-1;
        Actor a;

        for (;actorHUD >= 0; actorHUD--){
            a = escenaHUD.getActors().get(actorHUD);
            if (actoresAparecenInicialmente.contains(a.getName())) {
                a.setVisible(true);
            }
            else {
                a.setVisible(false);
            }
        }
    }

    private void crearAlerta(){
        //// Asignar textura a sprite de acción
        texturaAccion = new Texture("Pantalla/Accion.png");
        alertaAccion = new Objeto(texturaAccion, 0, 0);
        alertaAccion.sprite.setColor(1, 1, 1, 0);
    }

    private void crearBtnInteraccion(){
        //// Asignar textura al boton de interación
        texturaInteraccion = new Texture("Pantalla/BotonInteraccion.png");
        TextureRegionDrawable trdBtnInteraccion = new
                TextureRegionDrawable(new TextureRegion(texturaInteraccion));

        // Colocar boton de interación
        btnInteraccion = new ImageButton(trdBtnInteraccion);
        btnInteraccion.setPosition(pantalla.getANCHO()-btnInteraccion.getWidth()-pantalla.getANCHO()*.02f,
                pantalla.getALTO()*.02f);
        btnInteraccion.setColor(1,1,1,0.4f);

        //Boton Salir
        texturaEntrar = new Texture("Pantalla/entrar.png");
        TextureRegionDrawable trdBtnentrar = new
                TextureRegionDrawable(new TextureRegion(texturaEntrar));
        btnEntrar = new ImageButton(trdBtnentrar);


        actoresAparte.add(5);
        btnEntrar.setVisible(false);
        btnEntrar.setPosition(pantalla.getANCHO()-btnEntrar.getWidth()-pantalla.getANCHO()*.02f,
                pantalla.getALTO()*.02f);
    }

    private void crearBtnAccion(){
        //// Asignar textura al boton de acción
        texturaAccion = new Texture("Pantalla/baseItems.png");
        fondoAccion = new Objeto(texturaAccion, pantalla.getANCHO()-texturaAccion.getWidth()-pantalla.getANCHO()*.14f,
                pantalla.getALTO()*.02f);

//        TextureRegionDrawable trdBtnAccion = new
//                TextureRegionDrawable(new TextureRegion(texturaAccion));
//
//        // Colocar boton de acción
//        btnAccion = new ImageButton(trdBtnAccion);
//        btnAccion.setPosition(pantalla.getANCHO()-btnAccion.getWidth()-pantalla.getANCHO()*.14f,
//                pantalla.getALTO()*.02f);
//        btnAccion.setColor(1,1,1,0.4f);
//
//        btnAccion.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (!btnAccion.isDisabled()) {
//                }
//            }
//        });

        //// Asignar textura al boton de item
        if (seleccionado != null) {
            trdBtnItem = new
                    TextureRegionDrawable(new TextureRegion(seleccionado.sprite.getTexture()));
        } else {
            trdBtnItem = new TextureRegionDrawable(new TextureRegion(texturaAccion));
        }

        // Colocar boton de item provicional
        btnItem = new ImageButton(trdBtnItem);
    }

    private void crearBtnInventario(){

        //// Asignar textura al boton de inventario
        texturaInventario = new Texture("Pantalla/inventario.png");
        TextureRegionDrawable trdBtnInventario = new
                TextureRegionDrawable(new TextureRegion(texturaInventario));

        // Colocar boton de inventario
        btnInventario = new ImageButton(trdBtnInventario);
        btnInventario.setPosition(pantalla.getANCHO()-btnInventario.getWidth()-pantalla.getANCHO()*.26f,
                pantalla.getALTO()*.02f);
        btnInventario.setColor(1,1,1,1);
    }

    private void crearCartas(){
        // Cartas
        Texture pathFondoCarta = new Texture("Pantalla/fondoCarta.png");
        fondoCarta = new Objeto(pathFondoCarta, pantalla.getANCHO()/2 - pathFondoCarta.getWidth()/2, 0);
        fondoCarta.sprite.setColor(1,1,1,0);

        //Crear ligero cambio oscuro a la pantalla
        Pixmap pixmapOscuro = new Pixmap((int)(pantalla.getANCHO()), (int)(pantalla.getALTO()), Pixmap.Format.RGBA8888 );
        pixmapOscuro.setColor( 0f, 0f, 0f, 0.65f );
        pixmapOscuro.fillRectangle(0,0,(int)pantalla.getANCHO(),(int)pantalla.getALTO());
        regionOscura = new Texture( pixmapOscuro );
        pixmapOscuro.dispose();
        oscuroPausa = new Image(regionOscura);
        oscuroPausa.setPosition(0,0);
//        oscuroPausa.setVisible(false);

        //// Asignar textura al boton de cerrar carta
        texturaCerrar = new Texture("Pantalla/cerrar.png");
        TextureRegionDrawable trdBtnCerrar = new
                TextureRegionDrawable(new TextureRegion(texturaCerrar));

        // Colocar boton de cerrar carta
        btnCerrar = new ImageButton(trdBtnCerrar);
        btnCerrar.setPosition(pantalla.getANCHO()/2 + pathFondoCarta.getWidth()/2 - 100,
                pantalla.getALTO()*9/10);
        btnCerrar.setVisible(false);
    }

//    private void agregarActoresInicialesHUD(){
//        escenaHUD = new Stage(vistaHUD);
//        escenaHUD.addActor(pad);//Actor en posicion 0
//        escenaHUD.getActors().get(0).setName("Pad");
//        indiceActoresAntesPausa = 0;
//        escenaHUD.addActor(btnInteraccion);//Actor en posicion 1
//        escenaHUD.getActors().get(1).setName("Interaccion");
//        indiceActoresAntesPausa++;
////        escenaHUD.addActor(btnAccion);//Actor en posicion 2
////        escenaHUD.getActors().get(2).setName("Accion");
////        indiceActoresAntesPausa++;
//        escenaHUD.addActor(btnInventario);//Actor en posicion 2
//        escenaHUD.getActors().get(2).setName("Inventario");
//        indiceActoresAntesPausa++;
//        escenaHUD.addActor(btnCerrar);//Actor en posicion 3
//        escenaHUD.getActors().get(3).setName("Cerrar");
//        indiceActoresAntesPausa++;
//        escenaHUD.addActor(btnItem);//Actor en posicion 4
//        escenaHUD.getActors().get(4).setName("Item");
//        indiceActoresAntesPausa++;
//        //Anadir en lista de casos aparte
//        actoresAparte.add(3);
//
//        //Boton Salir
//        texturaEntrar = new Texture("Pantalla/entrar.png");
//        TextureRegionDrawable trdBtnentrar = new
//                TextureRegionDrawable(new TextureRegion(texturaEntrar));
//        btnEntrar = new ImageButton(trdBtnentrar);
//
//        escenaHUD.addActor(btnEntrar);//posicion 5
//        actoresAparte.add(5);
//        btnEntrar.setVisible(false);
//        btnEntrar.setPosition(pantalla.getANCHO()-btnEntrar.getWidth()-pantalla.getANCHO()*.02f,
//                pantalla.getALTO()*.02f);
//
//        // Interaccion boton entrar ( CAMBIAR NIVEL )
//        btnEntrar.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                //Para quitar la pausa
//                oddFellows.setScreen(new NivelBosque(oddFellows));
//                musicaFondo.stop();
//                //henric.pararSonido();
//            }
//        });
//        //Aumentar indice por btn entrar
//        indiceActoresAntesPausa++;
//
//        escenaHUD.addListener(new ClickListener() {
//
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                if (y < pantalla.getALTO()/6 && x > pantalla.getANCHO()* 6.5/10) {
//
//                } else {
//                    pad.setSize(200, 200);
//                    pad.setPosition(x - pad.getWidth() / 2, y - pad.getHeight() / 2);
//                    pad.setColor(1, 1, 1, 0.4f);
//                    henric.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
//                    henric.setEstadoMovimientoVertical(Personaje.EstadoMovimientoVertical.QUIETO_Y);
//                }
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                pad.setColor(1,1,1,0);
//                pad.setSize(pantalla.getANCHO()*2,pantalla.getALTO()*2);
//                pad.setPosition(pantalla.getANCHO()/2 - pad.getWidth()/2,
//                        pantalla.getALTO()/2 - pad.getHeight()/2);
//                henric.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
//                henric.setEstadoMovimientoVertical(Personaje.EstadoMovimientoVertical.QUIETO_Y);
//            }
//        });
//    }

    private Objeto identificarItem(TiledMapTileLayer.Cell celda) {
        // ID:
        // Llaves: 1
        // Carta: 2
        // Martillo: 10
        int prueba = Integer.parseInt(celda.getTile().getProperties().get("IDItem").toString());
        switch (prueba){
            case 1: //Llave
                if (Configuraciones.isFxOn)
                    fxLlave.play();
                Llave llave;
                return llave = new Llave(0, 0, (int) (Math.random()*10) + 1); // Valores del 1 al 10
            case 2:
                if (Configuraciones.isFxOn)
                    fxCarta.play();
                Carta carta;
                if (juego.actual.getClass().equals(NivelCabana.class)) {
                    carta = new Carta(0, 0, 1);
                } else {
                    carta = new Carta(0, 0, (int) (Math.random() * 10) + 1);
                }
                mostrarCarta(carta);
                return carta;
            case 10:
                Texture texturaMartillo = new Texture("Items/martillo.png");
                Arma martillo;
                if (Configuraciones.isFxOn)
                    fxMartillo.play();

                return martillo = new Arma(texturaMartillo, 0, 0, 30, "martillo", "romper");
        }
        return null;
    }

    private void mostrarCarta(Carta carta) {
        enCarta=true;

        fondoCarta.sprite.setColor(1,1,1,1);
        fondoAccion.sprite.setColor(1,1,1,0);
        btnItem.setColor(1,1,1,0);

        if (Configuraciones.isFxOn)
            fxCarta.play();

        txt.cambiarMensaje(carta.getTexto());

        //Se le resta uno por ser el tamano y no la posición, se le resta otro para no contar con el botón de pausa
        int actorHUD = escenaHUD.getActors().size-1;
        Actor a;

        for (;actorHUD >= 0; actorHUD--){
            a = escenaHUD.getActors().get(actorHUD);
            if (actoresAparecenCarta.contains(a.getName())) {
                a.setVisible(enCarta);
            }else {
                a.setVisible(!enCarta);
                escenaHUD.getActors().set(9, btnItem);
            }
        }
//        for (;actorHUD >= 0; actorHUD--){
//            a = escenaHUD.getActors().get(actorHUD);
//            if (a.getName() != "Cerrar" && a.getName() != "oscuroPausa") {
//                escenaHUD.getActors().get(actorHUD).setVisible(false);
//                btnItem.setColor(1,1,1,0);
//                escenaHUD.getActors().set(4, btnItem);
//            } else {
//                a.setVisible(true);
//            }
//        }
    }

    private void cerrarCarta() {
        enCarta = false;

        fondoCarta.sprite.setColor(1,1,1,0);
        btnItem.setColor(1,1,1,1);
        fondoAccion.sprite.setColor(1,1,1,1);

        fxCarta.play();
        txt.cambiarMensaje("");

        //Se le resta uno por ser el tamano y no la posición, se le resta otro para no contar con el botón de pausa
        int actorHUD = escenaHUD.getActors().size-1;
        Actor a;

        mostrarHUDInicial();
//        for (;actorHUD >= 0; actorHUD--){
//            a = escenaHUD.getActors().get(actorHUD);
//            if (a.getName() != "Cerrar" && !isPausa) {
//                escenaHUD.getActors().get(actorHUD).setVisible(true);
//
//                escenaHUD.getActors().set(9, btnItem);
//            } else if (a.getName() == "Cerrar"){
//                a.setVisible(false);
//                isPausa = false;
//            } else {
//                a.setVisible(false);
//            }
//            if (a.getName() == "Pausa") {
//                a.setVisible(true);
//            }
//        }
    }

    private void mostrarInventario(ArrayList<Objeto> inventario, boolean inInventario) {
        // Desplazamiento en X y Y
        float despX = 140;
        float despY = 140;

        // ITEMS
        ArrayList<ImageButton> items = new ArrayList<ImageButton>(inventario.size());
        float x = (pantalla.getANCHO()/2 - regionInventario.getWidth()/2 + regionInventario.getWidth()*0.18f);
        float y = (pantalla.getALTO()*0.765f);
        if (!inventario.isEmpty()) {
            for (final Objeto item: inventario) {
                item.sprite.getTexture();

                // Crear boton item
                TextureRegionDrawable trdBtnItemInv = new
                        TextureRegionDrawable(new TextureRegion(item.sprite.getTexture()));
                // Colocar botón item
                ImageButton btnItemInv = new ImageButton(trdBtnItemInv);
                btnItemInv.setPosition(x - btnItemInv.getWidth()/2, y);

                // Interaccion boton item
                btnItemInv.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        isArmado = false;
                        seleccionado = item;
                        enInventario = irInventario(enInventario, escenaHUD);
                        Texture textura = new Texture("Personaje/Henric.png");
                        henric.setSprite(new TextureRegion(textura).split(96, 96));
                    }
                });

                x += despX;
                if (x >= ((pantalla.getANCHO()/2 - regionInventario.getWidth()/2 + regionInventario.getWidth()*0.18f)
                        + despX*5)) {
                    x = (pantalla.getANCHO()/2 - regionInventario.getWidth()/2 + regionInventario.getWidth()*0.18f);
                    y -= despY;
                }

                items.add(btnItemInv);
                escenaHUD.addActor(btnItemInv);
                btnItemInv.setVisible(inInventario);
                btnItemInv.setName("btnItemInv");
                actoresAparecenInventario.add("btnItemInv");
            }
        }
    }

    protected void mostrarItemSeleccionado() {
        //// Asignar textura al boton de item
        if (seleccionado != null) {
            trdBtnItem = new
                    TextureRegionDrawable(new TextureRegion(seleccionado.sprite.getTexture()));
            btnItem = new ImageButton(trdBtnItem);
            btnItem.setPosition(pantalla.getANCHO()-btnItem.getWidth()*1.5f-pantalla.getANCHO()*.14f,
                    btnItem.getHeight()/2 + pantalla.getALTO()*.02f);
        } else {
            trdBtnItem = new TextureRegionDrawable(new TextureRegion(texturaAccion));
            btnItem = new ImageButton(trdBtnItem);
            btnItem.setPosition(pantalla.getANCHO()-btnItem.getWidth()-pantalla.getANCHO()*.14f,
                    pantalla.getALTO()*.02f);
        }

        if (seleccionado instanceof Arma && !isArmado) {
            isArmado = true;
            Arma arma = (Arma) seleccionado;
            if (arma.getNombre() == "martillo") {
                Texture textura = new Texture("Personaje/HendricMartilloCorriendo.png");
                henric.setSprite(new TextureRegion(textura).split(96, 96));
            }
        }
        if (!enInventario && !pausado && !enCarta) {
            escenaHUD.getActors().set(9, btnItem);
            escenaHUD.getActors().get(9).setName("btnItem");
            btnItem.setVisible(true);

            btnItem.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                        ejecutarAccion();
                }
            });
        }
    }

    private void ejecutarAccion() {
        if (seleccionado instanceof Arma) {
            Arma arma = (Arma) seleccionado;
            if (arma.getNombre() == "martillo") {
                romper();
            }
        } else if (seleccionado instanceof Carta) {
            Carta carta = (Carta) seleccionado;
            mostrarCarta(carta);
        } else if (seleccionado instanceof  Llave) {
            Llave llave = (Llave) seleccionado;
        }
    }

    private void romper() {
        if (Configuraciones.isFxOn) {
            fxMartillo.play();
        }
        Texture textura = new Texture("Personaje/HendricMartilloAtaque.png");
        henric.usarArma(new TextureRegion(textura).split(120, 96));
        if (tileInteractivo != null) {
            tileInteractivo.setTile(null);
        }
    }

    // SE CORRE 1 VEZ POR FRAME
    protected void dibujar(SpriteBatch batch) {

        // ACTUALIZA POSICION EN NIVEL GRANDE
        barraHP.sprite.setPosition(pantalla.getCamaraX() + 10,
                pantalla.getCamaraY() + pantalla.getALTO() - 10 - texturaHP.getHeight());
        hp.sprite.setPosition(pantalla.getCamaraX() + 10,
                pantalla.getCamaraY() + pantalla.getALTO() - 10 - texturaHP.getHeight());
        fondoAccion.sprite.setPosition(pantalla.getCamaraX() + pantalla.getANCHO()-texturaAccion.getWidth()-pantalla.getANCHO()*.14f,
                pantalla.getCamaraY() + pantalla.getALTO()*.02f);
        fondoCarta.sprite.setPosition(pantalla.getCamaraX() + pantalla.getANCHO()/2 - fondoCarta.sprite.getWidth()/2,
                pantalla.getCamaraY());

        // DIBUJA
        alertaAccion.dibujar(batch);
        henric.dibujar(batch);
        hp.dibujar(batch);
        barraHP.dibujar(batch);
        fondoCarta.dibujar(batch);
        fondoAccion.dibujar(batch);
        txt.escribir(batch, fondoCarta.sprite.getX() + 80,
                pantalla.getCamaraY() + fondoCarta.sprite.getHeight() - 60);
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();
    }

    protected void crearPausa(final Stage escenaHUD){

        pausado=false;
        musicaPausa = manager.get(pathMusicaPausa);
        musicaPausa.setLooping(true);

        //Textura de cuadro de pausa
        // Crear menú semi transparente
        Pixmap pixmap = new Pixmap((int)(pantalla.getANCHO()*0.45f), (int)(pantalla.getALTO()*0.8f), Pixmap.Format.RGBA8888 );
        //pixmap.setColor( 0.2f, 0.3f, 0.5f, 0.65f );
        pixmap.setColor( 0f, 0f, 0f, 0.65f );
        pixmap.fillRectangle(0,0,(int)pantalla.getANCHO(),(int)pantalla.getALTO());
        regionPausa = new Texture( pixmap );
        pixmap.dispose();
        cuadroPausa = new Image(regionPausa);
        cuadroPausa.setPosition(0.275f*pantalla.getANCHO(), 0.1f*pantalla.getALTO());

        //Crear Fondo de pantalla de pausa
        fondoMenu = new Texture("Pantalla/Fondo/fondoPausa.png");
        fondoMenuImagen = new Image(fondoMenu);
        fondoMenuImagen.setPosition(0,0);

        /*
        //Crear ligero cambio oscuro a la pantalla
        Pixmap pixmapOscuro = new Pixmap((int)(pantalla.getANCHO()), (int)(pantalla.getALTO()), Pixmap.Format.RGBA8888 );
        pixmapOscuro.setColor( 0f, 0f, 0f, 0.65f );
        pixmapOscuro.fillRectangle(0,0,(int)pantalla.getANCHO(),(int)pantalla.getALTO());
        regionOscura = new Texture( pixmapOscuro );
        pixmapOscuro.dispose();
        Image oscuroPausa = new Image(regionOscura);
        oscuroPausa.setPosition(0,0);
        */

        //Crear texturas
        texturaBotonPausa = new Texture("Pantalla/BotonPausa64.png");
        texturaBotonReanudar = new Texture("Pantalla/Tabla.png");
        texturaBotonSalir = new Texture("Pantalla/Tabla.png");
        texturaMusica = new Texture("Pantalla/Audio.png");
        texturaFX = new Texture("Pantalla/ecualizador.png");

        //Crear boton Pausa
        TextureRegionDrawable trdBtnPausa = new
                TextureRegionDrawable(new TextureRegion(texturaBotonPausa));
        // Colocar boton de pausa
        btnPausa = new ImageButton(trdBtnPausa);
        btnPausa.setPosition(pantalla.getANCHO()-btnPausa.getWidth()-pantalla.getANCHO()*.02f,
                pantalla.getALTO()-btnPausa.getHeight()-pantalla.getALTO()*.02f);

        //Crear boton Reanudar
        TextureRegionDrawable trdBtnReanudar = new
                TextureRegionDrawable(new TextureRegion(texturaBotonReanudar));
        // Colocar botón Reanudar
        btnReanudar = new ImageButton(trdBtnReanudar);
        btnReanudar.setPosition(pantalla.getANCHO()/2 - btnReanudar.getWidth()/2,3*pantalla.getALTO()/4
                - btnReanudar.getHeight()/2);

        //Crear boton Salir
        TextureRegionDrawable trdBtnSalir = new
                TextureRegionDrawable(new TextureRegion(texturaBotonSalir));
        // Colocar botón Salir
        btnSalir = new ImageButton(trdBtnSalir);
        btnSalir.setPosition(pantalla.getANCHO()/2 - btnSalir.getWidth()/2,pantalla.getALTO()/4
                - btnSalir.getHeight()/2);

        //Crear boton Efectos
        TextureRegionDrawable trdBtnFX = new
                TextureRegionDrawable(new TextureRegion(texturaFX));
        // Colocar botón Efectos
        btnFX = new ImageButton(trdBtnFX);
        btnFX.setPosition(3*pantalla.getANCHO()/5 - btnFX.getWidth()/2,pantalla.getALTO()/2
                - btnFX.getHeight()/2);

        //Crear boton Musica
        TextureRegionDrawable trdBtnMusica = new
                TextureRegionDrawable(new TextureRegion(texturaMusica));
        // Colocar botón Musica
        btnMusica = new ImageButton(trdBtnMusica);
        btnMusica.setPosition(2*pantalla.getANCHO()/5 - btnMusica.getWidth()/2,pantalla.getALTO()/2
                - btnMusica.getHeight()/2);




//        //Botón pausa en actor posicion 6
//        escenaHUD.addActor(btnPausa);
//        escenaHUD.getActors().get(5).setName("Pausa");
//        indiceActoresAntesPausa++;
//        indiceActoresPausa+=indiceActoresAntesPausa;
//
//        //Cuadro de pausa actor posicion 6
//        escenaHUD.addActor(fondoMenuImagen);
//        fondoMenuImagen.setVisible(false);
//        indiceActoresPausa++;
//
//        //Cuadro de pausa actor posicion 7
//        escenaHUD.addActor(cuadroPausa);
//        cuadroPausa.setVisible(false);
//        indiceActoresPausa++;
//        //Cuadro de reanudar actor posicion 8
//        escenaHUD.addActor(btnReanudar);
//        btnReanudar.setVisible(false);
//        indiceActoresPausa++;
//        //Cuadro de salir actor posicion 9
//        escenaHUD.addActor(btnSalir);
//        btnSalir.setVisible(false);
//        indiceActoresPausa++;
//        //Cuadro de salir actor posicion 10
//        escenaHUD.addActor(btnFX);
//        btnFX.setVisible(false);
//        indiceActoresPausa++;
//        //Cuadro de salir actor posicion 11
//        escenaHUD.addActor(btnMusica);
//        btnMusica.setVisible(false);
//        indiceActoresPausa++;
    }


    protected void crearInventario(final Stage escenaHUD){

        //Booleano que determina si aparece el menu inventario
        enInventario=false;

        //Textura de cuadro de pausa
        regionInventario = new Texture( "Pantalla/fondoInventario.png" );
        cuadroInventario = new Image(regionInventario);
        cuadroInventario.setPosition(pantalla.getANCHO()/2 - regionInventario.getWidth()/2, 0.15f*pantalla.getALTO());

//        //Crear ligero cambio oscuro a la pantalla
//        Pixmap pixmapOscuro = new Pixmap((int)(pantalla.getANCHO()), (int)(pantalla.getALTO()), Pixmap.Format.RGBA8888 );
//        pixmapOscuro.setColor( 0f, 0f, 0f, 0.65f );
//        pixmapOscuro.fillRectangle(0,0,(int)pantalla.getANCHO(),(int)pantalla.getALTO());
//        regionOscura = new Texture( pixmapOscuro );
//        pixmapOscuro.dispose();
//        oscuroPausa = new Image(regionOscura);
//        oscuroPausa.setPosition(0,0);

        //Crear rectángulo de regreso
//        Pixmap pixmapRegresar = new Pixmap((int)(pantalla.getANCHO()*0.4f), (int)(pantalla.getALTO()*0.1f), Pixmap.Format.RGBA8888 ); // 512 x 128
//        pixmapRegresar.setColor( 0.6f, 0.2f, 0.8f, 0.85f );
//        pixmapRegresar.fillRectangle(0,0,(int)pantalla.getANCHO(),(int)pantalla.getALTO());
        regionRegresarInventario = new Texture( "Pantalla/btnSalirInventario.png" );
//        pixmapRegresar.dispose();
        regresarInventario = new Image(regionRegresarInventario);
        regresarInventario.setPosition(.3f*pantalla.getANCHO(),.05f*pantalla.getALTO());

//        //Cuadro de inventario actor posicion 13
//        escenaHUD.addActor(cuadroInventario);
//        cuadroInventario.setVisible(false);
//
//        //Cuadro de regresar actor posicion 14
//        escenaHUD.addActor(regresarInventario);
//        regresarInventario.setVisible(false);

        // Interaccion boton pausa
        regresarInventario.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Para quitar la pausa
                enInventario = irInventario(enInventario, escenaHUD);
            }
        });

//        //Boton Salir
//        texturaEntrar = new Texture("Pantalla/entrar.png");
//        TextureRegionDrawable trdBtnentrar = new
//                TextureRegionDrawable(new TextureRegion(texturaEntrar));
//        btnEntrar = new ImageButton(trdBtnentrar);
//
//        escenaHUD.addActor(btnEntrar);//posicion 15
//        actoresAparte.add(15);
//        btnEntrar.setVisible(false);
//        btnEntrar.setPosition(pantalla.getANCHO()-btnEntrar.getWidth()-pantalla.getANCHO()*.02f,
//        pantalla.getALTO()*.02f);
//
//        // Interaccion boton entrar ( CAMBIAR NIVEL )
//        btnEntrar.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                //Para quitar la pausa
//                oddFellows.setScreen(new NivelBosque(oddFellows));
//                musicaFondo.stop();
//                //henric.pararSonido();
//            }
//        });
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

    protected boolean pausar(boolean pausado, Stage escenaHUD){

//        //Posicion actual de los actores que se crean antes de pausa
//        int actoresNoPausa = 5;
//        //Posicion actual de los actores que se crean para pausa
//        int actoresPausa = 11;

        if (pausado){
            musicaPausa.pause();
            if (Configuraciones.isMusicOn)
                musicaFondo.play();
        }else{
            musicaFondo.pause();
            if (Configuraciones.isMusicOn)
                musicaPausa.play();
        }
        //En caso de que el booleano pausa sea verdadero
            //Pausado cambia de verdadero a falso o viceversa
            pausado = !pausado;

        int actorHUD = escenaHUD.getActors().size-1;
        Actor a;

        if (pausado == true){
        for (;actorHUD >= 0; actorHUD--){
            a = escenaHUD.getActors().get(actorHUD);
            if (actoresAparecenPausa.contains(a.getName())) {
                a.setVisible(pausado);
            }else {
                a.setVisible(!pausado);
            }
        }}else {
            mostrarHUDInicial();
        }

//            for (int actoresHUD = 0;actoresHUD <= indiceActoresPausa; actoresHUD++){
//                if (!actoresAparte.contains(actoresHUD)) {
//                    if (actoresHUD <= indiceActoresAntesPausa) {
//                        escenaHUD.getActors().get(actoresHUD).setVisible(!pausado);
//                    } else {
//                        escenaHUD.getActors().get(actoresHUD).setVisible(pausado);
//                    }
//                }
//            }

        return pausado;
    }

    protected boolean irInventario(boolean enInventario, Stage escenaHUD){
        if (Configuraciones.isFxOn) {
            fxInventario.play(1, 1, 0);
        }
        // Muestra los items
        mostrarInventario(inventario, !enInventario);

//        //Posición del ultimo actor
//        int maxActores = escenaHUD.getActors().size -1;
//        //Posicion actual de los actores que se crean antes de pausa
//        int actoresNoPausa = 5;
//        //Posicion actual de los actores que se crean para pausa
//        int actoresPausa = 11;

        //Cambia de valor el booleano enInventario
        enInventario= !enInventario;

//        int actorHUD = escenaHUD.getActors().size-1;
        int actorHUD = nombreActores.length-1;
        Actor a;

        if (enInventario == true){
        for (;actorHUD >= 0; actorHUD--){
            a = escenaHUD.getActors().get(actorHUD);
            if (actoresAparecenInventario.contains(a.getName())) {
                a.setVisible(enInventario);
            }else {
                a.setVisible(!enInventario);
            }
        }}else {
            mostrarHUDInicial();
        }

//        for (int actor=0; actor<=indiceActoresAntesPausa; actor++){
//            if (!actoresAparte.contains(actor)) {
//                escenaHUD.getActors().get(actor).setVisible(!enInventario);
//            }
//        }
//
//        //ciclo en el que se le agrega uno a los actores para no empezar con un elemento de pausa
//        for (int actor=indiceActoresPausa+1; actor<=maxActores; actor++){
//            if (!actoresAparte.contains(actor)) {
//                escenaHUD.getActors().get(actor).setVisible(enInventario);
//            }
//        }

        if (enInventario == true) {
            fondoAccion.sprite.setColor(1,1,1,0);
        } else {
            fondoAccion.sprite.setColor(1,1,1,1);
        }
        return enInventario;
    }

    //Se pone en el render del nivel particular
    protected void escribirMenuPausa(boolean pausado){
        String tituloReanudar;
        String tituloSalir;
        String tituloMusica;
        String tituloSonido;

        pantalla.batch.begin();

        //Para que el mensaje no aparzca cuando no está en pausa
        if (pausado) {
            tituloReanudar = "Resume";
            tituloSalir = "Exit";

            if (Configuraciones.isMusicOn) {
                tituloMusica = "Music: ON";
            }else {
                tituloMusica = "Music: OFF";
            }

            if(Configuraciones.isFxOn){
                tituloSonido = "SFX:   ON";
            }else {
                tituloSonido = "SFX:   OFF";
            }
        }
        //Cuando no está en pausa no aparecen los mensajes
        else {
            tituloReanudar = "";
            tituloSalir = "";
            tituloMusica = "";
            tituloSonido = "";
        }

        pantalla.texto.mostrarMensajes(pantalla.batch, new Color(1, 1, 1, 0.85f), tituloReanudar,
                pantalla.getANCHO() / 2, 3f*pantalla.getALTO()/4 + 10);

        pantalla.texto.mostrarMensajes(pantalla.batch, Color.WHITE, tituloMusica,
                2f * pantalla.getANCHO() / 5, pantalla.getALTO()/2 - 55);

        pantalla.texto.mostrarMensajes(pantalla.batch, Color.WHITE, tituloSonido,
                3f * pantalla.getANCHO() / 5, pantalla.getALTO()/2 - 55);

        pantalla.texto.mostrarMensajes(pantalla.batch, new Color(1, 1, 1, 0.85f), tituloSalir,
               pantalla.getANCHO() / 2, pantalla.getALTO()/4 + 10);

        pantalla.batch.end();
    }

    private void tocarMusica(){
        if (Configuraciones.isMusicOn)
            musicaPausa.play();
        else
            musicaPausa.pause();
    }


}
