package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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

    // Texturas de opciones
    private Texture texturaFondo;
    private Texture texturaExit;

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
        texturaFondo = new Texture("HojaCreditos.png");
        texturaExit = new Texture("btnExit.png");
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
        btnExit.setPosition(0,0);
        pantalla.escena.addActor(btnExit);

        // Acciones de botones
            // Boton opciones
        btnExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Salir***");
                if (partidaEnCurso) {
                    // Si se accede desde el juego
                } else {
                    // Si se accede desde el menu
                    oddFellows.setScreen(new Menu(oddFellows));
                }
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
        texturaExit.dispose();
    }
}
