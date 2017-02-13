package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Rubén Escalante on 09/02/2017.
 */

public class Opciones implements Screen{

    private final OddFellows oddFellows;
    private final Pantalla pantalla;

    // Texturas de opciones
    private Texture texturaFondo;

    // Flag para determinar si viene del juego o del menú
    boolean partidaEnCurso;

    public Opciones(OddFellows oddFellows, boolean partidaEnCurso) {
        this.oddFellows = oddFellows;
        // Obtener pantalla
        pantalla = Pantalla.getInstanciaPantalla();
        this.partidaEnCurso = partidaEnCurso;
    }

    @Override
    public void show() {
        // Cuando cargan la pantalla
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondo = new Texture("pez.jpg");
    }

    private void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        // Agrega la imagen de fondo
        Image imgFondo = new Image(texturaFondo);
        imgFondo.setColor(1,0.3f,0.5f,1);
        pantalla.escena.addActor(imgFondo);

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
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
    }
}
