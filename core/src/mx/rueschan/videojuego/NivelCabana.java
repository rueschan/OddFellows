package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by OddFellows on 14/02/2017.
 */
public class NivelCabana extends Nivel {

    private Nivel actual;


    // Textura imagenes
//    private Texture texturaLlave;
//    private Texture texturaMartillo;
//    private Texture texturaCarta;
//    private Texture texturaHenric;

    // Recursos
    private String pathMapa;
    private String pathMusica;

    //Musica
    private Music musicaFondo;


    public NivelCabana(OddFellows oddFellows) {
        super.oddFellows = oddFellows;
        super.pantalla = Pantalla.getInstanciaPantalla();
        actual = this;
    }

    @Override
    protected void cargarTexturas() {
//        texturaFondo = new Texture("NivelCabana/fondoCabana.jpg");
        texturaBotonPausa = new Texture("Pantalla/BotonPausa64.png");
//        texturaLlave = new Texture("NivelCabana/LlaveIcono.png");
//        texturaMartillo = new Texture("NivelCabana/Martillo32a96.png");
//        texturaCarta = new Texture("NivelCabana/Carta32a64.png");
//        texturaHenric = new Texture("Personaje/Henric.png");

        // Recursos
        pathMapa = "Mapa/Cabana.tmx";
        pathMusica = "Musica/ofeliasdream.mp3";
    }

    @Override
    protected void crearObjetos() {

        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
//        // Agrega la imagen de fondo
//        Image imgFondo = new Image(texturaFondo);
//        pantalla.escena.addActor(imgFondo);


        //// Asignar textura al boton de pausa
        TextureRegionDrawable trdBtnPausa = new
                TextureRegionDrawable(new TextureRegion(texturaBotonPausa));


        // Colocar boton de pausa
        ImageButton btnPausa = new ImageButton(trdBtnPausa);
        btnPausa.setPosition(pantalla.getANCHO()-btnPausa.getWidth()-pantalla.getANCHO()*.02f,
                pantalla.getALTO()-btnPausa.getHeight()-pantalla.getALTO()*.02f);
        super.escenaHUD.addActor(btnPausa);



        //SPRITES
        //Agregar temporalmente imagenes como botones
//        TextureRegionDrawable trdbtnLlave = new
//                TextureRegionDrawable(new TextureRegion(texturaLlave));
//        TextureRegionDrawable trdbtnMartillo = new
//                TextureRegionDrawable(new TextureRegion(texturaMartillo));
//        TextureRegionDrawable trdbtnCarta = new
//                TextureRegionDrawable(new TextureRegion(texturaCarta));
//        TextureRegionDrawable trdbtnHenric = new
//                TextureRegionDrawable(new TextureRegion(texturaHenric));

//        //Colocar temporalmente boton sprite
//        // Llave
//        ImageButton btnLlave = new ImageButton(trdbtnLlave);
//        btnLlave.setPosition(350,550);
//        pantalla.escena.addActor(btnLlave);
//
//        //Martillo
//        ImageButton btnMartillo = new ImageButton(trdbtnMartillo);
//        btnMartillo.setPosition(1000,20);
//        pantalla.escena.addActor(btnMartillo);
//
//        //Carta
//        ImageButton btnCarta = new ImageButton(trdbtnCarta);
//        btnCarta.setPosition(500,550);
//        pantalla.escena.addActor(btnCarta);

        //Henric
//        ImageButton btnHenric = new ImageButton(trdbtnHenric);
//        btnHenric.setPosition(pantalla.getANCHO()/2 - btnHenric.getWidth()/2,
//                pantalla.getALTO()/2 - btnHenric.getHeight()/2);
//        pantalla.escena.addActor(btnHenric);


        // Acciones de botones
        // Botón opciones
        btnPausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Para quitar la pausa
                pausado = pausar(pausado, NivelCabana.super.escenaHUD);

                //Gdx.input.setInputProcessor(pantalla.escena);
                //NivelCabana.super.musicaFondo.pause();
                //oddFellows.setScreen(new MenuPausa(oddFellows, actual));
            }
        });
        //Pad


        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
        cargarTexturas();

        // Crear mapa
        super.crearRecursos(pantalla, pathMapa,pathMusica);

        //Creación de HUD
        super.crearHUD(pantalla);
        super.crearPausa(escenaHUD);

        // Objetos
        crearObjetos();
        cargarJuego();

        Gdx.input.setInputProcessor(super.escenaHUD);

    }

    @Override
    public void render(float delta) {

        super.pantalla.borrarPantalla();

        // Mapa
        pantalla.batch.setProjectionMatrix(pantalla.camara.combined);
        super.renderer.setView(pantalla.camara);
        super.renderer.render();

        // Elementos juego
        super.pantalla.escena.draw();
        pantalla.batch.begin();
        super.dibujar(pantalla.batch);
//        super.henric.dibujar(pantalla.batch);
//        hp.dibujar(pantalla.batch);
//        barraHP.dibujar(pantalla.batch);
        pantalla.batch.end();

        // HUD
        pantalla.batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();

        // Jugador
        henric.actualizar(mapa);
        henric.interactuar(this);



        // Detectar botón físico "return"
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            //Abrir MenuPausa
            pausado = pausar(pausado, NivelCabana.super.escenaHUD);

            //oddFellows.setScreen(new MenuPausa(oddFellows, actual));
        }
        escribirMenuPausa(pausado, NivelCabana.super.musicaFondo);
    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
//        texturaFondo.dispose();
        texturaBotonPausa.dispose();
//        texturaLlave.dispose();
//        texturaMartillo.dispose();
//        texturaCarta.dispose();
//        texturaHenric.dispose();
    }

}
