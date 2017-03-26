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
    protected Texture regionOscura;
    protected Texture fondoMenu;
    protected Boolean pausado;

        //Textura en Menu Pausa
    protected Texture texturaBotonReanudar;
    protected Texture texturaBotonSalir;
    protected Texture texturaMusica;
    protected Texture texturaFX;

    //INTERACCION
    private Texture texturaInteraccion;
    public ImageButton btnInteraccion;
    private Texture texturaAccion;
    public ImageButton btnAccion;
    private Texture texturaInventario;
    public ImageButton btnInventario;
    public Objeto alertaAccion;

    // Texturas carta e inventario
    Objeto fondoCarta;
    private Texture texturaCerrar;
    private ImageButton btnCerrar;

    //Manejador de assets
    protected static AssetManager manager;

    protected Music musicaFondo;
    protected Music musicaPausa;
    protected String pathMusicaPausa = "Musica/giantwyrm.mp3";
    protected Sound fxLlave;
    protected String pathFxLlave = "Sonidos/levantarLlave.mp3";
    protected Sound fxCarta;
    protected String pathFxCarta = "Sonidos/levantarPapel.mp3";
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
        manager.load(pathMusicaPausa,Music.class);
        manager.load(pathFxLlave, Sound.class);
        manager.load(pathFxCarta, Sound.class);
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
        fxInventario = manager.get(pathFxInventario);
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
        //// Asignar textura a sprite de acción
        texturaAccion = new Texture("Pantalla/Accion.png");
        alertaAccion = new Objeto(texturaAccion, 0, 0);
        alertaAccion.sprite.setColor(1, 1, 1, 0);

        //// Asignar textura al boton de interación
        texturaInteraccion = new Texture("Pantalla/BotonInteraccion.png");
        TextureRegionDrawable trdBtnInteraccion = new
                TextureRegionDrawable(new TextureRegion(texturaInteraccion));

        // Colocar boton de interación
        btnInteraccion = new ImageButton(trdBtnInteraccion);
        btnInteraccion.setPosition(pantalla.getANCHO()-btnInteraccion.getWidth()-pantalla.getANCHO()*.02f,
                pantalla.getALTO()*.02f);
        btnInteraccion.setColor(1,1,1,0.4f);

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

        //// Asignar textura al boton de acción
        texturaAccion = new Texture("Pantalla/baseItems.png");
        TextureRegionDrawable trdBtnAccion = new
                TextureRegionDrawable(new TextureRegion(texturaAccion));

        // Colocar boton de acción
        btnAccion = new ImageButton(trdBtnAccion);
        btnAccion.setPosition(pantalla.getANCHO()-btnAccion.getWidth()-pantalla.getANCHO()*.14f,
                pantalla.getALTO()*.02f);
        btnAccion.setColor(1,1,1,0.4f);

        btnAccion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!btnAccion.isDisabled()) {
                    Objeto obj = identificarItem(tileObjetivo);
                    henric.addInventario(obj);
                    tileObjetivo.setTile(null);
                }
            }
        });

        //// Asignar textura al boton de inventario
        texturaInventario = new Texture("Pantalla/inventario.png");
        TextureRegionDrawable trdBtnInventario = new
                TextureRegionDrawable(new TextureRegion(texturaInventario));

        // Colocar boton de inventario
        btnInventario = new ImageButton(trdBtnInventario);
        btnInventario.setPosition(pantalla.getANCHO()-btnInventario.getWidth()-pantalla.getANCHO()*.26f,
                pantalla.getALTO()*.02f);
        btnInventario.setColor(1,1,1,1);

        btnInventario.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fxInventario.play(1,2,0);
                ArrayList<Objeto> inventario;
                inventario = henric.verInventario();
            }
        });

        // Cartas
        Texture pathFondoCarta = new Texture("Pantalla/fondoCarta.png");
        fondoCarta = new Objeto(pathFondoCarta, pantalla.getANCHO()/2 - pathFondoCarta.getWidth()/2, 0);
        fondoCarta.sprite.setColor(1,1,1,0);

        //// Asignar textura al boton de cerrar carta
        texturaCerrar = new Texture("Pantalla/cerrar.png");
        TextureRegionDrawable trdBtnCerrar = new
                TextureRegionDrawable(new TextureRegion(texturaCerrar));

        // Colocar boton de cerrar carta
        btnCerrar = new ImageButton(trdBtnCerrar);
        btnCerrar.setPosition(pantalla.getANCHO()/2 + pathFondoCarta.getWidth()/2 - 100,
                pantalla.getALTO()*9/10);
        btnCerrar.setVisible(false);

        btnCerrar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cerrarCarta();
            }
        });

        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);//Actor en posicion 0
        escenaHUD.getActors().get(0).setName("Pad");
        escenaHUD.addActor(btnInteraccion);//Actor en posicion 1
        escenaHUD.getActors().get(1).setName("Interaccion");
        escenaHUD.addActor(btnAccion);//Actor en posicion 2
        escenaHUD.getActors().get(2).setName("Accion");
        escenaHUD.addActor(btnInventario);//Actor en posicion 3
        escenaHUD.getActors().get(3).setName("Inventario");
        escenaHUD.addActor(btnCerrar);
        escenaHUD.getActors().get(4).setName("Cerrar");

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
                mostrarCarta();
                return carta;
            case 10:
                Texture texturaMartillo = new Texture("Items/martillo.png");
                Arma martillo;
                return martillo = new Arma(texturaMartillo, 0, 0, 30, "romper");
        }
        return null;
    }

    private void mostrarCarta() {
        fondoCarta.sprite.setColor(1,1,1,1);
        fxCarta.play();

        //Se le resta uno por ser el tamano y no la posición, se le resta otro para no contar con el botón de pausa
        int actorHUD = escenaHUD.getActors().size-1;
        Actor a;

        for (;actorHUD >= 0; actorHUD--){
            a = escenaHUD.getActors().get(actorHUD);
            if (a.getName() != "Cerrar") {
                escenaHUD.getActors().get(actorHUD).setVisible(false);
            } else {
                a.setVisible(true);
            }
        }
    }

    private void cerrarCarta() {
        fondoCarta.sprite.setColor(1,1,1,0);
        fxCarta.play();
        boolean isPausa = true;

        //Se le resta uno por ser el tamano y no la posición, se le resta otro para no contar con el botón de pausa
        int actorHUD = escenaHUD.getActors().size-1;
        Actor a;

        for (;actorHUD >= 0; actorHUD--){
            a = escenaHUD.getActors().get(actorHUD);
            if (a.getName() != "Cerrar" && !isPausa) {
                escenaHUD.getActors().get(actorHUD).setVisible(true);
            } else if (a.getName() == "Cerrar"){
                a.setVisible(false);
                isPausa = false;
            } else {
                a.setVisible(false);
            }
        }
    }

    protected void dibujar(SpriteBatch batch) {
        alertaAccion.dibujar(batch);
        henric.dibujar(batch);
        hp.dibujar(batch);
        barraHP.dibujar(batch);
        fondoCarta.dibujar(batch);
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

        //Textura de pausa
        // Crear menú semi transparente
        Pixmap pixmap = new Pixmap((int)(pantalla.getANCHO()*0.45f), (int)(pantalla.getALTO()*0.8f), Pixmap.Format.RGBA8888 );
        //pixmap.setColor( 0.2f, 0.3f, 0.5f, 0.65f );
        pixmap.setColor( 0f, 0f, 0f, 0.65f );
        pixmap.fillRectangle(0,0,(int)pantalla.getANCHO(),(int)pantalla.getALTO());
        regionPausa = new Texture( pixmap );
        pixmap.dispose();
        Image cuadroPausa = new Image(regionPausa);
        cuadroPausa.setPosition(0.275f*pantalla.getANCHO(), 0.1f*pantalla.getALTO());

        //Crear Fondo de pantalla de pausa
        fondoMenu = new Texture("Pantalla/Fondo/fondoPausa.jpg");
        Image fondoMenuImagen = new Image(fondoMenu);
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
        ImageButton btnPausa = new ImageButton(trdBtnPausa);
        btnPausa.setPosition(pantalla.getANCHO()-btnPausa.getWidth()-pantalla.getANCHO()*.02f,
                pantalla.getALTO()-btnPausa.getHeight()-pantalla.getALTO()*.02f);

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

        //Crear boton Efectos
        TextureRegionDrawable trdBtnFX = new
                TextureRegionDrawable(new TextureRegion(texturaFX));
        // Colocar botón Efectos
        ImageButton btnFX = new ImageButton(trdBtnFX);
        btnFX.setPosition(3*pantalla.getANCHO()/5 - btnFX.getWidth()/2,pantalla.getALTO()/2
                - btnFX.getHeight()/2);

        //Crear boton Musica
        TextureRegionDrawable trdBtnMusica = new
                TextureRegionDrawable(new TextureRegion(texturaMusica));
        // Colocar botón Musica
        ImageButton btnMusica = new ImageButton(trdBtnMusica);
        btnMusica.setPosition(2*pantalla.getANCHO()/5 - btnMusica.getWidth()/2,pantalla.getALTO()/2
                - btnMusica.getHeight()/2);

        // Interaccion boton Musica
        btnMusica.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Configuraciones.cambiaMusica();
                tocarMusica();
                Gdx.app.log("clicked", "***audio "+Configuraciones.isMusicOn+"***");
            }
        });

        //Interaccion botón Efectos
        btnFX.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Configuraciones.cambiaFx();
                Gdx.app.log("clicked", "***FX "+Configuraciones.isFxOn+"***");
            }
        });

        //Interaccion boton reanudar
        btnReanudar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pausado = pausar(pausado,escenaHUD);
            }
        });

        //Interaccion boton salir
        btnSalir.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(pantalla.escena);
                musicaPausa.stop();
                oddFellows.crearMusica();
                juego.actual = null;
                oddFellows.setScreen(new MenuPrincipal(oddFellows));
            }
        });

        // Interaccion boton pausa
        btnPausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Para quitar la pausa
                pausado = pausar(pausado, escenaHUD);
            }
        });


        //Botón pausa en actor posicion 4
        escenaHUD.addActor(btnPausa);

        //Cuadro de pausa actor posicion 5
        escenaHUD.addActor(fondoMenuImagen);
        fondoMenuImagen.setVisible(false);

        //Cuadro de pausa actor posicion 6
        escenaHUD.addActor(cuadroPausa);
        cuadroPausa.setVisible(false);

        //Cuadro de reanudar actor posicion 7
        escenaHUD.addActor(btnReanudar);
        btnReanudar.setVisible(false);

        //Cuadro de salir actor posicion 8
        escenaHUD.addActor(btnSalir);
        btnSalir.setVisible(false);

        //Cuadro de salir actor posicion 9
        escenaHUD.addActor(btnFX);
        btnFX.setVisible(false);

        //Cuadro de salir actor posicion 10
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

    protected boolean pausar(boolean pausado, Stage escenaHUD){
        //Se le resta uno por ser el tamano y no la posición, se le resta otro para no contar con el botón de pausa
        int actorHUD = escenaHUD.getActors().size-1;
        //Los actores relacionados a pausa es 5 más el del botón pausa de la esquina
        int cantidadActoresPausa = 5;
        int actoresNoPausa = actorHUD-cantidadActoresPausa;
        //En caso de que el booleano pausa sea verdadero
        if (pausado){
            pausado = false;

            for (;actorHUD >= 0; actorHUD--){
                if(actorHUD>=actoresNoPausa){
                    escenaHUD.getActors().get(actorHUD).setVisible(pausado);
                }
                else{
                    escenaHUD.getActors().get(actorHUD).setVisible(!pausado);
                }
            }
            musicaPausa.pause();
            if (Configuraciones.isMusicOn)
                musicaFondo.play();
        }
        //En caso de que el booleano pausa sea falso
        else{
            pausado = true;

            for (;actorHUD >= 0; actorHUD--){
                if(actorHUD>=actoresNoPausa){
                    escenaHUD.getActors().get(actorHUD).setVisible(pausado);
                }
                else{
                    escenaHUD.getActors().get(actorHUD).setVisible(!pausado);
                }
            }
            musicaFondo.pause();
            if (Configuraciones.isMusicOn)
                musicaPausa.play();
        }
        return pausado;
    }

    //Se pone en el render del nivel particular
    protected void escribirMenuPausa(boolean pausado, Music musica){
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
        Gdx.app.log("tocarMusica Nivel","estado "+Configuraciones.isMusicOn);
        if (Configuraciones.isMusicOn)
            musicaPausa.play();
        else
            musicaPausa.pause();
    }
}
