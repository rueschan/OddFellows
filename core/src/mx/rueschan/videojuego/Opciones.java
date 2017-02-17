package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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

public class Opciones implements Screen{

    private final OddFellows oddFellows;
    private final Pantalla pantalla;
    private Pausa menuPausa = null;

    // Texturas de opciones
    private Texture texturaFondo;
    private Texture texturaExit;
    private Texture texturaSonido;
    private Texture texturaFX;

    // Estado de opciones (SE DEBE CAMBIAR CUANDO SE TENGA MEMORIA)
    private boolean isAudioOn = true;
    private boolean isFxOn = true;

    // Flag para determinar si viene del juego o del menú
    boolean partidaEnCurso;

    public Opciones(OddFellows oddFellows, boolean partidaEnCurso) {
        this.oddFellows = oddFellows;
        // Obtener pantalla
        pantalla = Pantalla.getInstanciaPantalla();
        this.partidaEnCurso = partidaEnCurso;
    }

    public Opciones(OddFellows oddFellows, boolean partidaEnCurso, Pausa pausa) {
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
    }

    private void cargarTexturas() {
        texturaFondo = new Texture("HojaCreditos.png");
        texturaExit = new Texture("btnExit.png");
        texturaSonido = new Texture("Audio.png");
        texturaFX = new Texture("ecualizador.png");
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
        ImageButton btnSonido = new ImageButton(trdSonido);
        btnSonido.setPosition(pantalla.getANCHO()/3 + 100 - btnSonido.getWidth()/2,
                2*pantalla.getALTO()/3 - btnSonido.getHeight()/2);
        pantalla.escena.addActor(btnSonido);
        // Boton de efectos
        TextureRegionDrawable trdFX = new
                TextureRegionDrawable(new TextureRegion(texturaFX));
        // Colocar botón de efectos
        ImageButton btnFX = new ImageButton(trdFX);
        btnFX.setPosition(pantalla.getANCHO()/3 + 100- btnFX.getWidth()/2,
                pantalla.getALTO()/3 - btnFX.getHeight()/2);
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
                    oddFellows.setScreen(new Menu(oddFellows));
                }
            }
        });

        btnSonido.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***audio***");
                isAudioOn = !isAudioOn;
            }
        });

        btnFX.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***FX***");
                isFxOn = !isFxOn;
            }
        });

        Gdx.input.setCatchBackKey(true);

        // Detectar botón físico "return"
        if (pantalla.escena.keyUp(Input.Keys.BACK)){
            // DEBUG
            Gdx.app.log("Btn BACK", "Atras en opciones con escena");
            /*if (partidaEnCurso){
                // Regresa al menú de pausa
            }
            else {
                oddFellows.setScreen(new Menu(oddFellows));
            }*/
        }
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();

        escribirEnPantalla();
    }

    private void escribirEnPantalla() {

        String mensajeAudio;
        String mensajeFX;

        pantalla.batch.begin();

        // Texto de sonido
        pantalla.texto.mostrarMensajes(pantalla.batch, Color.WHITE, "Music",
                pantalla.getANCHO()/3 + 100, 2*pantalla.getALTO()/3 - 80);
        if (isAudioOn) {
            mensajeAudio = "ON";
        } else {
            mensajeAudio = "OFF";
        }
        pantalla.texto.mostrarMensajes(pantalla.batch, Color.WHITE, mensajeAudio,
                pantalla.getANCHO()/3 + 300, 2*pantalla.getALTO()/3);

        // Texto de efectos
        pantalla.texto.mostrarMensajes(pantalla.batch, Color.WHITE, "Effects",
                pantalla.getANCHO()/3 + 100, pantalla.getALTO()/3 - 80);
        if (isFxOn) {
            mensajeFX = "ON";
        } else {
            mensajeFX = "OFF";
        }
        pantalla.texto.mostrarMensajes(pantalla.batch, Color.WHITE, mensajeFX,
                pantalla.getANCHO()/3 + 300, pantalla.getALTO()/3);

        pantalla.batch.end();

        mensajeAudio = null;
        mensajeFX = null;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        cargarTexturas();
        crearObjetos();
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
