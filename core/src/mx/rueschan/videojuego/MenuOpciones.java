package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
 * Created by Rubén Escalante on 09/02/2017.
 */

public class MenuOpciones implements Screen{

    private final OddFellows oddFellows;
    private final Pantalla pantalla;
    private MenuPausa menuPausa = null;

    // Texturas de opciones
    private Texture texturaFondo;
    private Texture texturaExit;
    private Texture texturaSonido;
    private Texture texturaFX;


    // Flag para determinar si viene del juego o del menú
    boolean partidaEnCurso;

    public MenuOpciones(OddFellows oddFellows, boolean partidaEnCurso) {
        this.oddFellows = oddFellows;
        // Obtener pantalla
        pantalla = Pantalla.getInstanciaPantalla();
        this.partidaEnCurso = partidaEnCurso;
    }

    public MenuOpciones(OddFellows oddFellows, boolean partidaEnCurso, MenuPausa pausa) {
        this.oddFellows = oddFellows;
        // Obtener pantalla
        pantalla = Pantalla.getInstanciaPantalla();
        this.partidaEnCurso = partidaEnCurso;
        this.menuPausa = pausa;
    }

    @Override
    public void show() {
        // Cuando cargan la pantalla
        cargarTexturas();
        crearObjetos();
        oddFellows.tocarMusica();
    }

    private void cargarTexturas() {
        texturaFondo = new Texture("Pantalla/Fondo/FondoOpciones.jpg");
        texturaExit = new Texture("Pantalla/btnExit.png");
        texturaSonido = new Texture("Pantalla/Audio.png");
        texturaFX = new Texture("Pantalla/ecualizador.png");
    }

    private void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        // Agrega la imagen de fondo
        Image imgFondo = new Image(texturaFondo);
        imgFondo.setSize(pantalla.getANCHO(), pantalla.getALTO());
        pantalla.escena.addActor(imgFondo);

        // Botón de Exit
        TextureRegionDrawable trdExit = new
                TextureRegionDrawable(new TextureRegion(texturaExit));
        // Colocar boton de Exit
        ImageButton btnExit = new ImageButton(trdExit);
        btnExit.setPosition(10,10);
        pantalla.escena.addActor(btnExit);
        // Botón de sonido
        TextureRegionDrawable trdSonido = new
                TextureRegionDrawable(new TextureRegion(texturaSonido));
        // Colocar botón de sonido
        ImageButton btnMusica = new ImageButton(trdSonido);
        btnMusica.setPosition(pantalla.getANCHO()/3 + 200 - btnMusica.getWidth()/2,
                5*pantalla.getALTO()/6 - btnMusica.getHeight()/2);
        pantalla.escena.addActor(btnMusica);
        // Boton de efectos
        TextureRegionDrawable trdFX = new
                TextureRegionDrawable(new TextureRegion(texturaFX));
        // Colocar botón de efectos
        ImageButton btnFX = new ImageButton(trdFX);
        btnFX.setPosition(pantalla.getANCHO()/3 + 200- btnFX.getWidth()/2,
                3*pantalla.getALTO()/6 - btnFX.getHeight()/2);
        pantalla.escena.addActor(btnFX);

        // Acciones de botones
            // Boton opciones
        btnExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Salir***");
                if (partidaEnCurso) {
                    // Si se accede desde el juego
                    oddFellows.setScreen(menuPausa);
                } else {
                    // Si se accede desde el menu
                    //musicaFondo.pause();
                    oddFellows.setScreen(new MenuPrincipal(oddFellows));
                }
            }
        });

        btnMusica.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Configuraciones.cambiaMusica();
                oddFellows.tocarMusica();
                Gdx.app.log("clicked", "***audio "+Configuraciones.isMusicOn+"***");
            }
        });

        btnFX.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Configuraciones.cambiaFx();
                Gdx.app.log("clicked", "***FX "+Configuraciones.isFxOn+"***");
            }
        });

        Gdx.input.setCatchBackKey(true);

        // Detectar botón físico "return"
        if (pantalla.escena.keyUp(Input.Keys.BACK)){
            // DEBUG
            Gdx.app.log("Btn BACK", "Atras en opciones con escena");
            if (partidaEnCurso){
                // Regresa al menú de pausa
                 oddFellows.setScreen(menuPausa);
            }
            else {
                //Regresar al MenuPrincipal
                oddFellows.setScreen(new MenuPrincipal(oddFellows));
            }
        }
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();
        escribirEnPantalla();
        // Detectar botón físico "return"
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if (partidaEnCurso){
                // Regresa al menú de pausa
                oddFellows.setScreen(menuPausa);
            }
            else {
                //Regresar al MenuPrincipal
                oddFellows.setScreen(new MenuPrincipal(oddFellows));
            }
        }
    }

    private void escribirEnPantalla() {

        String mensajeMusica;
        String mensajeFX;

        pantalla.batch.begin();

        // Texto de musica
        pantalla.texto.mostrarMensajes(pantalla.batch, Color.BLACK, "Music",
                pantalla.getANCHO()/3 + 200, 5*pantalla.getALTO()/6 - 80);
        if (Configuraciones.isMusicOn) {
            mensajeMusica = "ON";
        } else {
            mensajeMusica = "OFF";
        }
        pantalla.texto.mostrarMensajes(pantalla.batch, Color.WHITE, mensajeMusica,
                pantalla.getANCHO()/3 + 400, 5*pantalla.getALTO()/6-11);

        // Texto de efectos
        pantalla.texto.mostrarMensajes(pantalla.batch, Color.BLACK, "Effects",
                pantalla.getANCHO()/3 + 200, 3*pantalla.getALTO()/6 - 80);
        if (Configuraciones.isFxOn) {
            mensajeFX = "ON";
        } else {
            mensajeFX = "OFF";
        }
        pantalla.texto.mostrarMensajes(pantalla.batch, Color.WHITE, mensajeFX,
                pantalla.getANCHO()/3 + 400, 3*pantalla.getALTO()/6);

        pantalla.batch.end();

        mensajeMusica = null;
        mensajeFX = null;
    }

    @Override
    public void resize(int width, int height) {

        pantalla.resize(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        cargarTexturas();
        crearObjetos();
        oddFellows.tocarMusica();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
        texturaExit.dispose();
    }
}
