package mx.itesm.oddFellows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by OddFellows on 30/04/2017.
 */

public class MenuFinal implements Screen {

    private OddFellows oddFellows;
    private Pantalla pantalla;
    private AssetManager manager;
    private Music musicaFinal;

    private Texture texturaFondo;
    private Texture texturaExit;

    private Niveles nivelActual;
    private Personaje henric;

    private float colorFoto;
    private int posFondo;
    private Actor fondo;

    public MenuFinal(OddFellows oddFellows, Niveles nivelActual) {
        this.oddFellows = oddFellows;
        //manager = this.oddFellows.getAssetManager();
        // Obtener pantalla
        //pantalla = Pantalla.getInstanciaPantalla();
        //pantalla.resetCamara();
        pantalla = new Pantalla();
        this.nivelActual = nivelActual;
        //this.henric = Personaje.getInstanciaPersonaje();
    }

    @Override
    public void show() {
        manager = Nivel.getManager();
        // Cuando cargan la pantalla
        cargarTexturas();
        crearObjetos();
        //henric = Personaje.getInstanciaPersonaje();
        /*if (henric!=null){
            henric.pararSonido();
        }*/
        colorFoto = 0;
        musicaFinal.setLooping(true);
        if(Configuraciones.isMusicOn){
            musicaFinal.play();
        }

    }

    // Metodo para iniciar texturas de pantalla
    private void cargarTexturas() {
        texturaFondo = manager.get("Pantalla/Fondo/fotoFinal.jpg");
        //texturaExit = manager.get("Pantalla/btnExit.png");
       // musicaFinal = manager.get("Musica/funeralMarch.mp3");
        // PARA HACER PRUEBAS RAPIDO
        // texturaFondo = new Texture("Pantalla/Fondo/fotoFinal.jpg");
        texturaExit = manager.get("Pantalla/btnExit.png");
        musicaFinal = manager.get("Musica/funeralMarch.mp3");
    }

    // Metodo para crear objetos en pantalla
    private void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        // Agrega la imagen de fondo
        Image imgFondo = new Image(texturaFondo);
        imgFondo.setPosition(0,0);
        imgFondo.setColor(0, 0, 0, 1);
        pantalla.escena.addActor(imgFondo);
        posFondo = pantalla.escena.getActors().size - 1;
        fondo = pantalla.escena.getActors().peek();

        // Botón de Exit
        TextureRegionDrawable trdExit = new
                TextureRegionDrawable(new TextureRegion(texturaExit));
        // Colocar boton de Exit
        ImageButton btnExit = new ImageButton(trdExit);
        btnExit.setPosition(10,10);
        pantalla.escena.addActor(btnExit);

        // Acciones de botones
        // Boton salir
        btnExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Salir***");
                //manager.clear();
                /*Configuraciones.agregarContadorMuertes();
                Gdx.app.log("Contador muertes", ""+Configuraciones.contadorMuertes );
                musicaFinal.stop();
                Juego.actual = null;
                henric.pararSonido();
                henric.reset();
                henric.setLocalizacion(Personaje.Localizacion.CABANA);
                henric.vaciarInventario();
                henric.setVida(100);*/
                pantalla.resetCamara();
                pantalla.escena.getActors().removeIndex(posFondo);
                //Nivel.borrarMapas();
                oddFellows.setScreen(new PantallaCargando(oddFellows,Niveles.MENU_PRINCIPAL));
                //musicaFinal.stop();
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
        if (colorFoto < 1) {
            colorFoto += delta / 2;
        }
        fondo.setColor(colorFoto, colorFoto, colorFoto, 1);
    }

    private void escribirEnPantalla() {
//        pantalla.batch.begin();
//        textoMuerto.mostrarMensajes(pantalla.batch,Color.SCARLET,"YOU'RE DEAD",1*pantalla.getANCHO()/2,5*pantalla.getALTO()/6+30);
//        textoContinuar.mostrarMensajes(pantalla.batch,Color.BLACK,"Continue",1*pantalla.getANCHO()/2,2*pantalla.getALTO()/3-30);
//        pantalla.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        pantalla.resize(width,height);
    }

    @Override
    public void pause() {
        //musicaFinal.pause();

    }

    @Override
    public void resume() {
        cargarTexturas();
        crearObjetos();

    }

    @Override
    public void hide() {
        Gdx.app.log("Hide","mescondi");
        musicaFinal.stop();

    }
    @Override
    public void dispose() {
        texturaFondo.dispose();
        musicaFinal.dispose();
    }

}
