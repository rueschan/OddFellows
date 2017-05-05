package mx.itesm.oddFellows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by OddFellows on 30/04/2017.
 */

public class MenuGameOver implements Screen {

    private OddFellows oddFellows;
    private Pantalla pantalla;
    private AssetManager manager;
    private Music musicaMuerto;

    private Texture texturaFondo;
    private Texture texturaExit;
    private Texture texturaTabla;
    private Texto textoMuerto;
    private Texto textoContinuar;

    private Niveles nivelActual;
    private Personaje henric;

    public MenuGameOver(OddFellows oddFellows,Niveles nivelActual) {
        this.oddFellows = oddFellows;
        manager = this.oddFellows.getAssetManager();
        // Obtener pantalla
        //pantalla = Pantalla.getInstanciaPantalla();
        //pantalla.resetCamara();
        pantalla = new Pantalla();
        this.nivelActual = nivelActual;
        this.henric = Personaje.getInstanciaPersonaje();
    }

    @Override
    public void show() {
        // Cuando cargan la pantalla
        cargarTexturas();
        crearObjetos();
        //henric = Personaje.getInstanciaPersonaje();
        /*if (henric!=null){
            henric.pararSonido();
        }*/
        musicaMuerto.setLooping(true);
        if(Configuraciones.isMusicOn){
            musicaMuerto.play();
        }
        textoMuerto = new Texto();
        textoMuerto.cambiarTamano(3f);
        textoContinuar = new Texto();
        textoContinuar.cambiarTamano(2f);

    }

    // Metodo para iniciar texturas de pantalla
    private void cargarTexturas() {
        texturaFondo = manager.get("Pantalla/Fondo/fondoGameOver.png");
        texturaExit = manager.get("Pantalla/btnExit.png");
        musicaMuerto = manager.get("Musica/moonlight.mp3");
        texturaTabla = manager.get("Pantalla/Vacio.png");
        // PARA HACER PRUEBAS RAPIDO
        /*texturaFondo = new Texture("Pantalla/Fondo/fondoGameOver.png");
        texturaExit = new Texture("Pantalla/btnExit.png");
        musicaMuerto = Gdx.audio.newMusic(Gdx.files.internal("Musica/moonlight.mp3"));
        texturaTabla = new Texture("Pantalla/Vacio.png");*/
    }

    // Metodo para crear objetos en pantalla
    private void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        // Agrega la imagen de fondo
        Image imgFondo = new Image(texturaFondo);
        imgFondo.setPosition(0,0);
        pantalla.escena.addActor(imgFondo);

        // Botón de Exit
        TextureRegionDrawable trdExit = new
                TextureRegionDrawable(new TextureRegion(texturaExit));
        TextureRegionDrawable trdContinue = new
                TextureRegionDrawable(new TextureRegion(texturaTabla));
        // Colocar boton de Exit
        ImageButton btnExit = new ImageButton(trdExit);
        btnExit.setPosition(10,10);
        pantalla.escena.addActor(btnExit);

        ImageButton btnContinue = new ImageButton(trdContinue);
        btnContinue.setPosition(1*pantalla.getANCHO()/3+80,2*pantalla.getALTO()/3-110);
        pantalla.escena.addActor(btnContinue);

        // Acciones de botones
        // Boton salir
        btnExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Salir***");
                //manager.clear();
                Configuraciones.agregarContadorMuertes();
                Gdx.app.log("Contador muertes", ""+Configuraciones.contadorMuertes );
                musicaMuerto.stop();
                Juego.actual = null;
                henric.pararSonido();
                henric.reset();
                henric.setLocalizacion(Personaje.Localizacion.CABANA);
                henric.vaciarInventario();
                henric.setVida(100);
                pantalla.resetCamara();
                Nivel.borrarMapas();
                oddFellows.setScreen(new PantallaCargando(oddFellows,Niveles.MENU_PRINCIPAL));
                //musicaMuerto.stop();
                //pantalla.resetCamara();
            }
        });
        btnContinue.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Reanudar***");
                //manager.clear();
                Configuraciones.agregarContadorMuertes();
                Gdx.app.log("Contador muertes", ""+Configuraciones.contadorMuertes );
                musicaMuerto.stop();
                //juego.actual = null;
                //henric.pararSonido();
                henric.reset();
                henric.setLocalizacion(Personaje.Localizacion.CABANA);
                //henric.vaciarInventario();
                henric.setVida(75);
                pantalla.resetCamara();
                oddFellows.setScreen(new PantallaCargando(oddFellows, Niveles.NIVEL_CABANA));
                musicaMuerto.stop();
                //pantalla.resetCamara();
            }
        });
        Gdx.input.setCatchBackKey(true);
        //Asignar procesador de entrada al menú
        Gdx.input.setInputProcessor(pantalla.escena);
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();

        escribirEnPantalla();
    }

    private void escribirEnPantalla() {
        pantalla.batch.begin();
        textoMuerto.mostrarMensajes(pantalla.batch,Color.SCARLET,"YOU'RE DEAD",1*pantalla.getANCHO()/2,5*pantalla.getALTO()/6+30);
        textoContinuar.mostrarMensajes(pantalla.batch,Color.BLACK,"Continue",1*pantalla.getANCHO()/2,2*pantalla.getALTO()/3-30);
        pantalla.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        pantalla.resize(width,height);
    }

    @Override
    public void pause() {
        //musicaMuerto.pause();

    }

    @Override
    public void resume() {
        cargarTexturas();
        crearObjetos();

    }

    @Override
    public void hide() {
        Gdx.app.log("Hide","mescondi");
        musicaMuerto.stop();

    }
    @Override
    public void dispose() {
        texturaFondo.dispose();
        musicaMuerto.dispose();
        texturaTabla.dispose();
    }

}
