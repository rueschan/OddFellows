package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by OddFellows on 14/02/2017.
 */
public abstract class Nivel implements Screen{

    protected static OddFellows oddFellows;
    protected Pantalla pantalla;
    protected Juego juego;
    protected static int idNvlObjetivo;

    // Personaje
    protected Personaje henric;
    protected Texture texturaHenric;
    protected ArrayList<Objeto> inventario = new ArrayList<Objeto>();
    protected Objeto seleccionado;
    protected boolean isArmado = false;

    // Enemigo
    protected ArrayList<Enemigo> listaEnemigos;
    protected Enemigo enemigo;

    // Mapa
    protected OrthogonalTiledMapRenderer renderer; // Dibuja el mapa
    protected TiledMap mapa;
    protected TiledMapTileLayer.Cell tileObjetivo;
    protected TiledMapTileLayer.Cell tileInteractivo;
    protected TiledMapTileLayer.Cell tilePuerta;

    // Texturas HUD
    protected Texture texturaBotonPausa;
    protected Texture texturaHP;
    protected Texture texturaBarraHP;

    //HUD
    protected OrthographicCamera camaraHUD;
    protected Viewport vistaHUD;
    protected Stage escenaHUD;
//    protected Objeto hp;
//    protected Objeto barraHP;
    protected Image hpAct;
    protected Image barraHPAct;
    private float anchoBarraHP;

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

    //Indices
    protected int indiceActoresAntesPausa;
    protected int indiceActoresPausa;
    protected List<Integer> actoresAparte = new ArrayList<Integer>();
    protected List<String> actoresAparecenPausa = new ArrayList<String>();
    protected List<String> actoresAparecenInventario = new ArrayList<String>();
    protected List<String> actoresAparecenCarta = new ArrayList<String>();
    protected List<String> actoresAparecenInicialmente = new ArrayList<String>();
//    protected List<String> nombreActores = new ArrayList<String>();
    protected boolean actoresCreados = false;
    protected int cantidadActores = 18;

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
//    public Objeto fondoAccion;
    protected Image fondoAccionBueno;
    private Texture texturaInventario;
    private TextureRegionDrawable trdBtnItem;
    public Objeto alertaAccion;
    protected Texture texturaEntrar;

    // Texturas carta e inventario
    Objeto fondoCarta;
    private Texture texturaCerrar;
    private Texto txt;

    //Manejador de assets
    protected AssetManager manager;

    protected Music musicaFondo;
    protected Music musicaPausa;
    protected Sound fxLlave;
    protected Sound fxCarta;
    protected Sound fxMartillo;
    protected Sound fxAtaque;
    protected Sound fxLevantar;
    private Sound fxInventarioAbrir;
    private Sound fxInventarioCerrar;
    protected Sound fxLlaveAbrir;
    protected Sound fxLlaveFallar;

    @Override
    public void show() {
//        cargarTexturas();
        crearObjetos();
        cargarJuego();
    }

    public static AssetManager getManager() {
        /*if (manager != null) {
            return manager;
        }
        return new AssetManager();*/
        return (oddFellows.getAssetManager());
    }

    protected void cargarJuego(){
        juego = Juego.getJuego(oddFellows);
    }

    protected abstract void crearObjetos();

//    protected abstract void cargarTexturas();

    protected void crearRecursos(Pantalla pantalla, String nombreMapa, String nombreMusicaFondo) {
        this.manager = oddFellows.getAssetManager();
        // Henric
        henric = Personaje.getInstanciaPersonaje();
//        texturaHenric = new Texture("Personaje/Henric.png");
//        henric = new Personaje(texturaHenric, pantalla.getANCHO()/2, pantalla.getALTO()/2,nombreFXPasos);

        // Texto cartas
        txt = new Texto();
        txt.hacerMensajes(new Color(0,0,0,1), "");

        // Vida
        //texturaHP = new Texture("Pantalla/HP.png");
        //texturaBarraHP = new Texture("Pantalla/BarraHP.png");
        texturaHP = manager.get("Pantalla/HP.png");
        texturaBarraHP = manager.get("Pantalla/BarraHP.png");
//        hp = new Objeto(texturaHP, 10, pantalla.getALTO() - 10 - texturaHP.getHeight());
//        barraHP = new Objeto(texturaBarraHP, 10, pantalla.getALTO() - 10 - texturaHP.getHeight());

        //AQUI LE TOY MOVIENDO
        hpAct = new Image(texturaHP);
        hpAct.setPosition(10, pantalla.getALTO() - 10 - texturaHP.getHeight()); // LE CAMBIE PORQUE CUANDO SE REDUCE LA VIDA EL

        barraHPAct = new Image(texturaBarraHP);
        // 110 px porque son los 10 por default más 10 por cada cuadro del circulo que dice HP (320 px de la imagen / 32 cuadros)
        barraHPAct.setPosition(110, pantalla.getALTO() - 10 - texturaHP.getHeight());
        anchoBarraHP = barraHPAct.getWidth();
        barraHPAct.setWidth(anchoBarraHP * (henric.getVida() / 100));



        //manager = new AssetManager();
        //manager.setLoader(TiledMap.class,
        //        new TmxMapLoader(new InternalFileHandleResolver()));
        //manager.load(nombreMapa, TiledMap.class);
        //manager.load(nombreMusicaFondo,Music.class);
        /*manager.load(pathMusicaPausa,Music.class);
        manager.load(pathFxLlave, Sound.class);
        manager.load(pathFxCarta, Sound.class);
        manager.load(pathFxMartillo, Sound.class);
        manager.load(pathFxInventarioAbrir, Sound.class);
        manager.load(pathFxInventarioCerrar, Sound.class);*/

        //manager.finishLoading();    // Carga los recursos
        mapa = manager.get(nombreMapa);

        pantalla.batch = new SpriteBatch();
        renderer = new OrthogonalTiledMapRenderer(mapa, pantalla.batch);
        renderer.setView(pantalla.camara);
        musicaFondo = manager.get(nombreMusicaFondo);
        musicaFondo.setLooping(true);
        if (Configuraciones.isMusicOn)
            musicaFondo.play();

        // Sonidos generales
        fxLlave = manager.get("Sonidos/levantarLlave.mp3");
        fxCarta = manager.get("Sonidos/levantarPapel.mp3");
        fxMartillo = manager.get("Sonidos/levantarMartillo.mp3");
        fxAtaque = manager.get("Sonidos/ataque.mp3");
        fxInventarioAbrir = manager.get("Sonidos/zipperAbrir.mp3");
        fxInventarioCerrar = manager.get("Sonidos/zipperCerrar.mp3");
        fxLlaveAbrir = manager.get("Sonidos/abrirPuerta.mp3");
        fxLlaveFallar = manager.get("Sonidos/accionarCerrojo.mp3");
        fxLevantar = manager.get("Sonidos/pickup.mp3");

        // Enemigos
        listaEnemigos = new ArrayList<Enemigo>();

    }

    protected void crearElementosPantalla(final Pantalla pantalla){
        crearHUD(pantalla);
        crearAlerta();
        crearBtnInteraccion();
        crearBtnAccion();
        crearBtnInventario();
        crearCartas();
        checarEaster();
        crearPausa(escenaHUD);
        crearInventario(escenaHUD);
        addActoresHUD();
        crearAccionesBotones();
        mostrarHUDInicial();
        //wololo
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
        //skin.add("padBack", new Texture("Pad/padBack.png"));
        //skin.add("padKnob", new Texture("Pad/padKnob.png"));
        Texture textura = manager.get("Pad/padBack.png");
        skin.add("padBack", new Texture("Pad/padBack.png"));
        textura = manager.get("Pad/padKnob.png");
        skin.add("padKnob", new Texture("Pad/padKnob.png"));


        final Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("padBack");
        estilo.knob = skin.getDrawable("padKnob");

        //****************************J//
        pad = new Touchpad(20, estilo);
        pad.setBounds(0,0,200,200);
        pad.setSize(pantalla.getANCHO()+pantalla.getANCHO(), pantalla.getALTO()+pantalla.getALTO());
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
                    henric.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO_X);
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
//                    Gdx.app.log("btnItem","");
                }
//                Gdx.app.log("btnItem","fuera");
            }
        });

        btnEntrar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Para quitar la pausa
                cambiarNivel(idNvlObjetivo);
//                oddFellows.setScreen(new NivelBosque(oddFellows));
//                musicaFondo.stop();
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
                musicaFondo.stop();
                oddFellows.crearMusica();
                Juego.actual = null;
                henric.pararSonido();
                henric.reset();
                henric.vaciarInventario();
                henric.setVida(100);
                pantalla.resetCamara();
//                manager.clear(); //DEBERÍA FUNCIONAR PERO NO CARGA LA TEXTURA DE HENRIC NORMAL NI LA ALERTA
                borrarMapas();
                oddFellows.setScreen(new PantallaCargando(oddFellows, Niveles.MENU_PRINCIPAL));


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

    public static void borrarMapas() {
        NivelCabana.reset();
        NivelBosque.reset();
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


//        public Image fondoAccionBueno;
        escenaHUD.addActor(fondoAccionBueno);//Actor en posicion 9
        escenaHUD.getActors().get(9).setName("fondoAccionBueno");
        fondoAccionBueno.setVisible(false);
        actoresAparecenInicialmente.add("fondoAccionBueno");
//        Gdx.app.log("woloo", "9");

//        public ImageButton btnItem;
        escenaHUD.addActor(btnItem);//Actor en posicion 10
        escenaHUD.getActors().get(10).setName("btnItem");
        btnItem.setVisible(false);
//        Gdx.app.log("woloo", "10");

//        public ImageButton btnEntrar;
        escenaHUD.addActor(btnEntrar);//Actor en posicion 11
        escenaHUD.getActors().get(11).setName("btnEntrar");
        btnEntrar.setVisible(false);
//        Gdx.app.log("woloo", "11");

//        public ImageButton btnPausa;
        escenaHUD.addActor(btnPausa);//Actor en posicion 12
        escenaHUD.getActors().get(12).setName("btnPausa");
        btnPausa.setVisible(false);
        actoresAparecenInicialmente.add("btnPausa");
//        Gdx.app.log("woloo", "12");

//        public ImageButton btnReanudar;
        escenaHUD.addActor(btnReanudar);//Actor en posicion 13
        escenaHUD.getActors().get(13).setName("btnReanudar");
        btnReanudar.setVisible(false);
        actoresAparecenPausa.add("btnReanudar");
//        Gdx.app.log("woloo", "13");

//        public ImageButton btnSalir;
        escenaHUD.addActor(btnSalir);//Actor en posicion 14
        escenaHUD.getActors().get(14).setName("btnSalir");
        btnSalir.setVisible(false);
        actoresAparecenPausa.add("btnSalir");
//        Gdx.app.log("woloo", "14");

//        public ImageButton btnFX;
        escenaHUD.addActor(btnFX);//Actor en posicion 15
        escenaHUD.getActors().get(15).setName("btnFX");
        btnFX.setVisible(false);
        actoresAparecenPausa.add("btnFX");
//        Gdx.app.log("woloo", "15");

//        public ImageButton btnMusica;
        escenaHUD.addActor(btnMusica);//Actor en posicion 16
        escenaHUD.getActors().get(16).setName("btnMusica");
        btnMusica.setVisible(false);
        actoresAparecenPausa.add("btnMusica");
//        Gdx.app.log("woloo", "16");

//        public Image hpAct;
        escenaHUD.addActor(hpAct);//Actor en posicion 17
        escenaHUD.getActors().get(17).setName("hpAct");
        hpAct.setVisible(false);
        actoresAparecenInicialmente.add("hpAct");
//        Gdx.app.log("woloo", "17");

//        public Image barraHPAct;
        escenaHUD.addActor(barraHPAct);//Actor en posicion 18
        escenaHUD.getActors().get(18).setName("barraHPAct");
        barraHPAct.setVisible(false);
        actoresAparecenInicialmente.add("barraHPAct");
//        Gdx.app.log("woloo", "18");



        // Crea los limites de pad y botones, es decir, evita que se cree el pad sobre los botones
        escenaHUD.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (y < pantalla.getALTO()/6 && x > pantalla.getANCHO()* 6.5/10) {

                } else {
                    pad.setSize(200, 200);
                    pad.setPosition(x - pad.getWidth() / 2, y - pad.getHeight() / 2);
                    pad.setColor(1, 1, 1, 0.4f);
                    henric.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO_X);
                    henric.setEstadoMovimientoVertical(Personaje.EstadoMovimientoVertical.QUIETO_Y);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pad.setColor(1,1,1,0);
                pad.setSize(pantalla.getANCHO()+pantalla.getANCHO(),pantalla.getALTO()+pantalla.getALTO());
                pad.setPosition(pantalla.getANCHO()/2 - pad.getWidth()/2,
                        pantalla.getALTO()/2 - pad.getHeight()/2);
                henric.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO_X);
                henric.setEstadoMovimientoVertical(Personaje.EstadoMovimientoVertical.QUIETO_Y);
            }
        });
    }

    private void mostrarHUDInicial(){
        Actor a;

        for (int actorHUD = escenaHUD.getActors().size-1;actorHUD >= 0; actorHUD--){
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
        //texturaAccion = new Texture("Pantalla/Accion.png");
        texturaAccion = manager.get("Pantalla/Accion.png");
        alertaAccion = new Objeto(0, 0, texturaAccion);
        alertaAccion.sprite.setColor(1, 1, 1, 0);
    }

    private void crearBtnInteraccion(){
        //// Asignar textura al boton de interación
        //texturaInteraccion = new Texture("Pantalla/BotonInteraccion.png");
        texturaInteraccion = manager.get("Pantalla/BotonInteraccion.png");
        TextureRegionDrawable trdBtnInteraccion = new
                TextureRegionDrawable(new TextureRegion(texturaInteraccion));

        // Colocar boton de interación
        btnInteraccion = new ImageButton(trdBtnInteraccion);
        btnInteraccion.setPosition(pantalla.getANCHO()-btnInteraccion.getWidth()-pantalla.getANCHO()*.02f,
                pantalla.getALTO()*.02f);
        btnInteraccion.setColor(1,1,1,0.4f);

        //Boton Salir
        //texturaEntrar = new Texture("Pantalla/entrar.png");
        texturaEntrar = manager.get("Pantalla/entrar.png");
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
        // texturaAccion = new Texture("Pantalla/baseItems.png");
        texturaAccion = manager.get("Pantalla/baseItems.png");
//        fondoAccion = new Objeto(texturaAccion, pantalla.getANCHO()-texturaAccion.getWidth()-pantalla.getANCHO()*.14f,
//                pantalla.getALTO()*.02f);

        fondoAccionBueno = new Image(texturaAccion);
        fondoAccionBueno.setPosition(pantalla.getANCHO()-texturaAccion.getWidth()-pantalla.getANCHO()*.14f,
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
        // texturaInventario = new Texture("Pantalla/inventario.png");
        texturaInventario = manager.get("Pantalla/inventario.png");
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
        //Texture pathFondoCarta = new Texture("Pantalla/fondoCarta.png");
        Texture pathFondoCarta = manager.get("Pantalla/fondoCarta.png");
        fondoCarta = new Objeto(pantalla.getANCHO()/2 - pathFondoCarta.getWidth()/2, 0, pathFondoCarta);
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
        //texturaCerrar = new Texture("Pantalla/cerrar.png");
        texturaCerrar = manager.get("Pantalla/cerrar.png");
        TextureRegionDrawable trdBtnCerrar = new
                TextureRegionDrawable(new TextureRegion(texturaCerrar));

        // Colocar boton de cerrar carta
        btnCerrar = new ImageButton(trdBtnCerrar);
        btnCerrar.setPosition(pantalla.getANCHO()/2 + pathFondoCarta.getWidth()/2 - 100,
                pantalla.getALTO()*9/10);
        btnCerrar.setVisible(false);
    }

    //EASTER
    private void checarEaster(){
        if (Configuraciones.obtenerEasterCreditos().contains("MOMAZO")) {
            henric.addInventario(new Arma(Arma.Tipo.TRIDENTE));
            Configuraciones.borrarEasterCreditos();
        }
    }

    private Objeto identificarItem(TiledMapTileLayer.Cell celda) {
        // ID:
        // Llaves: 1
        // Carta: 2
        // Martillo: 10
        String prueba = (String) celda.getTile().getProperties().get("item");
//        switch (prueba){
//            case "llave": //Llave
//                if (Configuraciones.isFxOn)
//                    fxLlave.play();
//                Llave llave;
//                return llave = new Llave(0, 0, (int) (Math.random()*10) + 1); // Valores del 1 al 10
//            case "carta":
//                if (Configuraciones.isFxOn)
//                    fxCarta.play();
//                Carta carta;
//                if (juego.actual.getClass().equals(NivelCabana.class)) {
//                    carta = new Carta(0, 0, 1);
//                } else {
//                    carta = new Carta(0, 0, (int) (Math.random() * 10) + 1);
//                }
//                mostrarCarta(carta);
//                return carta;
//            case "martillo":
//                Arma martillo;
//                if (Configuraciones.isFxOn)
//                    fxMartillo.play();
//                return martillo = new Arma(0, 0, Arma.Tipo.MARTILLO);
//        }
//        return null;


        //**********************************J//
        if (prueba.equals("llave")) {
            if (Configuraciones.isFxOn)
                fxLlave.play();
            Llave llave;
            return llave = new Llave(0, 0, (int) (Math.random() * 3) + 1); // Valores del 1 al 10

        } else if (prueba.equals("carta")) {
            if (Configuraciones.isFxOn)
                fxCarta.play();
            Carta carta;
            if (juego.actual.getClass().equals(NivelCabana.class)) {
                carta = new Carta(0, 0, 0);
            } else {
                Collections.shuffle(Carta.idCartas);
                System.out.println(Carta.idCartas.toString());
                int id = Carta.idCartas.remove(0);
                carta = new Carta(0, 0, id);
            }
            mostrarCarta(carta);
            return carta;

        } else if (prueba.equals("medkit")) {
            reproducirAudioRecoger();
            return new Medkit();

        } else if (prueba.equals("martillo")) {
            reproducirAudioRecoger();
            return new Arma(Arma.Tipo.MARTILLO);

        } else if (prueba.equals("machete")) {
            reproducirAudioRecoger();
            return new Arma(Arma.Tipo.MACHETE);

        } else if (prueba.equals("bate")) {
            reproducirAudioRecoger();
            return new Arma(Arma.Tipo.BATE);
        }
        return null;
    }

    private void reproducirAudioRecoger() {
        if (Configuraciones.isFxOn) {
            fxLevantar.play();
        }
    }

    private void mostrarCarta(Carta carta) {
        enCarta=true;

        fondoCarta.sprite.setColor(1,1,1,1);
//        fondoAccion.sprite.setColor(1,1,1,0);
        btnItem.setColor(1,1,1,0);

        if (Configuraciones.isFxOn){
            fxCarta.play();
        }

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
                escenaHUD.getActors().set(10, btnItem);
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
//        fondoAccion.sprite.setColor(1,1,1,1);
        if (Configuraciones.isFxOn)
            fxCarta.play();
        txt.cambiarMensaje("");

        mostrarHUDInicial();
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
                        //Texture textura = new Texture("Personaje/Henric.png");
                        Texture textura = manager.get("Personaje/Henric.png");
                        henric.setSprite(new TextureRegion(textura).split(96, 96));
                    }
                });

                x += despX;
                if (x >= ((pantalla.getANCHO()/2 - regionInventario.getWidth()/2 + regionInventario.getWidth()*0.18f)
                        + despX*4)) {
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

            Texture textura = manager.get("Personaje/HendricMartilloCorriendo.png");
            henric.setSprite(new TextureRegion(textura).split(96, 96));
        }
        if (seleccionado instanceof Llave && !isArmado) {
            isArmado = true;
            Llave llave = (Llave) seleccionado;

            Texture textura = manager.get("Personaje/HendricLlave.png");
            henric.setSprite(new TextureRegion(textura).split(96, 96));

        }

        if (!enInventario && !pausado && !enCarta) {
            escenaHUD.getActors().set(10, btnItem);
            escenaHUD.getActors().get(10).setName("btnItem");
            btnItem.setVisible(true);
            //henric.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
            btnItem.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                        ejecutarAccion();
                }
            });
        }
    }

    private void ejecutarAccion() {
        henric.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO_X);
        if (seleccionado instanceof Arma) {
            Arma arma = (Arma) seleccionado;
            henric.setDano(arma.getDano());
            golpear();

        } else if (seleccionado instanceof Carta) {
            Carta carta = (Carta) seleccionado;
            mostrarCarta(carta);

        } else if (seleccionado instanceof  Llave) {
            Llave llave = (Llave) seleccionado;
            abrirPuerta(llave);

        } else if(seleccionado instanceof  Medkit) {
            Medkit medkit = (Medkit) seleccionado;
            recuperarVida(medkit);
        }
    }

    private void recuperarVida(Medkit medkit) {
        float vidaHenric = henric.getVida();
        float vidaAcumulada = vidaHenric+medkit.getVIDA();
        henric.setVida(vidaAcumulada);
        //henric.eliminarElementoActual(medkit);
    }

    private void abrirPuerta(Llave llave) {

        if (tilePuerta != null){
            Integer id = 0;
            try {
                id = (Integer) tilePuerta.getTile().getProperties().get("valorLlave");
            } catch (ClassCastException e) {
                id = Integer.parseInt((String) tilePuerta.getTile().getProperties().get("valorLlave"));
            }
            if (id==llave.getIdPuerta()) {
                tilePuerta.setTile(null);
                if (Configuraciones.isFxOn){
                    fxLlaveAbrir.play();
                }
            }else{
                if (Configuraciones.isFxOn){
                    fxLlaveFallar.play();
                }
            }
        }
    }

//    private void abrirPuerta() {
//        if (tilePuerta != null) {
//            TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Puerta");
//            TiledMapTileLayer.Cell celda;
//
//            ArrayList<TiledMapTileLayer.Cell> cellArrayList = new ArrayList<TiledMapTileLayer.Cell>();
//            for (int i = 0; i < capa.getHeight(); i += 64) {
//                for (int j = 0; j < capa.getWidth(); j += 64) {
//                    celda = capa.getCell((i / 64), (j / 64));
//                    Gdx.app.log("ArrayCelda",cellArrayList.toString());
//                        cellArrayList.add(celda);
//                        //woLOLOLOLOLO
//                        Gdx.app.log("ArrayCelda",cellArrayList.toString());
//                }
//            }
//            for (TiledMapTileLayer.Cell celdaFor : cellArrayList) {
//                celdaFor.setTile(null);
//            }
//        }
//    }

    private void golpear() {
        if (Configuraciones.isFxOn) {
            if (!hayAtaqueAJugador()) {
                fxMartillo.play();
            } else {
                fxAtaque.play();
            }
        }
//        Ya no se crea la textura cada vez que se usa un arma
//        Texture textura = new Texture("Personaje/HendricMartilloAtaque.png");
        henric.usarArma();
        if (tileInteractivo != null) {
            tileInteractivo.setTile(null);
        }
    }

    // SE CORRE 1 VEZ POR FRAME
    protected void dibujar(SpriteBatch batch) {

        // ACTUALIZA POSICION EN NIVEL GRANDE
//        barraHP.sprite.setPosition(pantalla.getCamaraX() + 10,
//                pantalla.getCamaraY() + pantalla.getALTO() - 10 - texturaHP.getHeight());
//        hp.sprite.setPosition(pantalla.getCamaraX() + 10,
//                pantalla.getCamaraY() + pantalla.getALTO() - 10 - texturaHP.getHeight());
//        fondoAccion.sprite.setPosition(pantalla.getCamaraX() + pantalla.getANCHO()-texturaAccion.getWidth()-pantalla.getANCHO()*.14f,
//                pantalla.getCamaraY() + pantalla.getALTO()*.02f);
        fondoCarta.sprite.setPosition(pantalla.getCamaraX() + pantalla.getANCHO()/2 - fondoCarta.sprite.getWidth()/2,
                pantalla.getCamaraY());

        // DIBUJA
        alertaAccion.dibujar(batch);
        henric.dibujar(batch);
//        hp.dibujar(batch);
//        barraHP.dibujar(batch);
        fondoCarta.dibujar(batch);
//        fondoAccion.dibujar(batch);
        txt.escribir(batch, fondoCarta.sprite.getX() + 80,
                pantalla.getCamaraY() + fondoCarta.sprite.getHeight() - 60);
        dibujarEnemigos(batch);

    }

    private void dibujarEnemigos(SpriteBatch batch) {
        for (Enemigo enemigo : listaEnemigos) {
            enemigo.dibujar(batch);
        }
    }

    protected void renderEnemigos(TiledMap mapa) {
        for (Enemigo enemigo : listaEnemigos) {
            enemigo.actualizar(mapa);
        }
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();

        actualizarVida();
        hayAtaqueAJugador();
    }

    protected void crearPausa(final Stage escenaHUD){

        pausado=false;
        musicaPausa = manager.get("Musica/giantwyrm.mp3");
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
        //fondoMenu = new Texture("Pantalla/Fondo/fondoPausa.png");
        fondoMenu = manager.get("Pantalla/Fondo/fondoPausa.png");
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
        /*texturaBotonPausa = new Texture("Pantalla/BotonPausa64.png");
        texturaBotonReanudar = new Texture("Pantalla/Tabla.png");
        texturaBotonSalir = new Texture("Pantalla/Tabla.png");
        texturaMusica = new Texture("Pantalla/Audio.png");
        texturaFX = new Texture("Pantalla/ecualizador.png");*/
        texturaBotonPausa = manager.get("Pantalla/BotonPausa64.png");
        texturaBotonReanudar = manager.get("Pantalla/Tabla.png");
        texturaBotonSalir = manager.get("Pantalla/Tabla.png");
        texturaMusica = manager.get("Pantalla/Audio.png");
        texturaFX = manager.get("Pantalla/ecualizador.png");

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
    }


    protected void crearInventario(final Stage escenaHUD){

        //Booleano que determina si aparece el menu inventario
        enInventario=false;

        //Textura de cuadro de pausa
        //regionInventario = new Texture( "Pantalla/fondoInventario.png" );
        regionInventario = manager.get("Pantalla/fondoInventario.png");
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
        //regionRegresarInventario = new Texture( "Pantalla/btnSalirInventario.png" );
        regionRegresarInventario = manager.get("Pantalla/btnSalirInventario.png");
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
//        cargarTexturas();
        crearObjetos();
    }

    protected void cambiarNivel(int nvl) {
        //Nivel nivel = detectarNivel(nvl);
        //oddFellows.setScreen(nivel);
        //descargarManager(nvl);

        switch (nvl) {
            case 1: // CABAÑA
                henric.destino = Personaje.Destino.CABANA;
                Gdx.app.log("Cambiar Nivel"," "+"voy a la cabaña "+nvl);
                oddFellows.setScreen(new PantallaCargando(oddFellows,Niveles.NIVEL_CABANA));
                break;
            case 2: // BOSQUE
                henric.destino = Personaje.Destino.BOSQUE;
                Gdx.app.log("Cambiar Nivel"," "+"voy al bosque "+nvl);
                oddFellows.setScreen(new PantallaCargando(oddFellows,Niveles.NIVEL_BOSQUE));
                break;
        }
        musicaFondo.stop();
        henric.setViaje();
        descargarManager(nvl);
        listaEnemigos.clear();
        // juego.cargarNivel(nvl);
//        this.dispose();
    }

    private void descargarManager(int nvl) {
        Gdx.app.log("descargarManager Nivel","descargando");
        switch (nvl) {
            case 1: // VOY PARA LA CABAÑA
                Gdx.app.log("descargarManager Bosque","nvl "+nvl);
//                manager.unload("NivelBosque/bosque.tmx");
                manager.unload("Musica/lostInForest.mp3");
                manager.unload("Sonidos/pasoBosque.mp3");
                break;
            case 2: // VOY PARA EL BOSQUE CABAÑA
                Gdx.app.log("descargarManager Cabana","nvl "+nvl);
//                manager.unload("NivelCabana/Cabana.tmx");
                manager.unload("Musica/ofeliasdream.mp3");
                manager.unload("Sonidos/pasoMadera.mp3");
                break;
        }
    }
    private void descargarManager() {
        Gdx.app.log("descargarManager Nivel","descargando");

        manager.unload("Musica/giantwyrm.mp3");
        manager.unload("Sonidos/levantarLlave.mp3");
        manager.unload("Sonidos/levantarPapel.mp3");
        manager.unload("Sonidos/levantarMartillo.mp3");
        manager.unload("Sonidos/zipperAbrir.mp3");
        manager.unload("Sonidos/zipperCerrar.mp3");
        manager.unload("Sonidos/alerta.mp3");

        // Vida
        manager.unload("Pantalla/HP.png");
        manager.unload("Pantalla/BarraHP.png");

        //Texturas de Henric
        manager.unload("Personaje/Henric.png");
        manager.unload("Personaje/HendricMartilloAtaque.png");

        manager.unload("Pad/padBack.png");
        manager.unload("Pad/padKnob.png");

        manager.unload("Pantalla/Accion.png");
        manager.unload("Pantalla/BotonInteraccion.png");
        manager.unload("Pantalla/entrar.png");
        manager.unload("Pantalla/baseItems.png");
        manager.unload("Pantalla/inventario.png");
        manager.unload("Pantalla/fondoCarta.png");
        manager.unload("Pantalla/cerrar.png");
        manager.unload("Personaje/HendricMartilloCorriendo.png");
        manager.unload("Personaje/HendricLlave.png");
        manager.unload("Pantalla/Fondo/fondoPausa.png");

        //Crear texturas
        manager.unload("Pantalla/BotonPausa64.png");
        manager.unload("Pantalla/Tabla.png");
        manager.unload("Pantalla/Tabla.png");
        manager.unload("Pantalla/Audio.png");
        manager.unload("Pantalla/ecualizador.png");

        manager.unload("Pantalla/fondoInventario.png");
        manager.unload("Pantalla/btnSalirInventario.png");
        manager.finishLoading();
    }

    /*private void detectarNivel(int nvl) {
        switch (nvl) {
            case 1: // CABAÑA
                henric.destino = Personaje.Destino.CABANA;
               // return NivelCabana.getNivelCabana(oddFellows);
            case 2: // BOSQUE
                henric.destino = Personaje.Destino.BOSQUE;
               // return NivelBosque.getNivelBosque(oddFellows);
        }
        //return null;
    }*/

    public static void setNivelObjetivo(int n) {
        idNvlObjetivo = n;
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
        if (Configuraciones.isFxOn){
            if (enInventario)
                fxInventarioCerrar.play();
            else
                fxInventarioAbrir.play();
        }
        // Muestra los items
        mostrarInventario(inventario, !enInventario);
        enInventario= !enInventario;
//        //Posición del ultimo actor
//        int maxActores = escenaHUD.getActors().size -1;
//        //Posicion actual de los actores que se crean antes de pausa
//        int actoresNoPausa = 5;
//        //Posicion actual de los actores que se crean para pausa
//        int actoresPausa = 11;

        //Cambia de valor el booleano enInventario
//        int actorHUD = escenaHUD.getActors().size-1;
//        int actorHUD = nombreActores.size()-1;
        Actor a;

        if (enInventario == true){
        for (int actorHUD = cantidadActores; actorHUD >= 0; actorHUD--){
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
//            fondoAccion.sprite.setColor(1,1,1,0);
        } else {
//            fondoAccion.sprite.setColor(1,1,1,1);
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

    public void actualizarVida() {
        float vida = henric.getVida();

        if (vida <= 0) {
            gameOver();
        }
        barraHPAct.setWidth(anchoBarraHP * (henric.getVida() / 100));
    }

    private void gameOver() {
        Gdx.app.log("Nivel actualizarVida","***MUERTO*** nivel "+idNvlObjetivo);
        musicaFondo.stop();

        henric.reset();
        //henric.vaciarInventario();
        //henric.setVida(100);
        pantalla.resetCamara();
        //henric.setViaje();
        henric.pararSonido();
        henric.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO_X);
        henric.setEstadoMovimientoVertical(Personaje.EstadoMovimientoVertical.QUIETO_Y);
        descargarManager(idNvlObjetivo);
        henric.pararSonido();
        switch(idNvlObjetivo){
            case 1:     //Estoy en el bosque
                henric.destino = Personaje.Destino.BOSQUE;
                henric.pararSonido();

                Gdx.app.log("Nivel actualizarVida","me mori en el bosque "+idNvlObjetivo);
                oddFellows.setScreen(new MenuGameOver(oddFellows,Niveles.NIVEL_BOSQUE));
                henric.setVida(100);

                //henric.setViaje();
                break;
                /*case 2:     // Estoy en la cabaña  caso imposible
                    henric.destino = Personaje.Destino.CABANA;
                    oddFellows.setScreen(new PantallaCargando(oddFellows,Niveles.NIVEL_CABANA));
                    break;*/
        }

    }

    public boolean hayAtaqueAJugador () {
        final int EXTRA = 10;
        int posMuerto = -1;
        boolean hayEnemigo = false;

        Rectangle rectHenric = henric.sprite.getBoundingRectangle();
        int rectWidth = (int) rectHenric.getWidth();
        int rectHeight = (int) rectHenric.getHeight();
        Rectangle rectEnemigo;

        henric.setEnemigoCercano(null);
        for (Enemigo enemigo : listaEnemigos) {
            if (enemigo != null) {

                if (enemigo.getEstadoEnemigo() == Enemigo.EstadoEnemigo.MUERTO) {
                    enemigo = null;
                    posMuerto = listaEnemigos.indexOf(enemigo);
                    continue;
                }

                rectEnemigo = enemigo.sprite.getBoundingRectangle();

                // Asigna valor a las variables que se veran afectadas en el método
                enemigo.setTocaJugador(false);

                // Revisa si está arriba, abajo, a la derecha o a la izqierda (respectivamente)
                if (rectHenric.setSize(rectWidth + EXTRA, rectHeight + EXTRA).overlaps(rectEnemigo)) {
                    enemigo.setTocaJugador(true);
                    henric.setEnemigoCercano(enemigo);
                    hayEnemigo = true;
                    break;
                }
            }
        }
        if (posMuerto != -1) {
            listaEnemigos.remove(posMuerto);
        }
        if (hayEnemigo) {
            return true;
        }

        return false;
    }

    public static enum EstadoMapa {
        CARGADO,
        NO_CARGADO
    }

}
