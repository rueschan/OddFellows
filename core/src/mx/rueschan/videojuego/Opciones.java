package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Rubén Escalante on 09/02/2017.
 */

public class Opciones implements Screen{

    private final OddFellows oddFellows;
    private final Pantalla pantalla;

    // Texturas de opciones
    private Texture texturaFondo;

    public Opciones(OddFellows oddFellows) {
        this.oddFellows = oddFellows;
        // Obtener pantalla
        pantalla = Pantalla.getInstanciaPantalla();
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

        Gdx.input.setInputProcessor(pantalla.escena);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();
        // DEBUG
        debugear();
    }

    private void debugear(){
            Gdx.app.log("Actores:", pantalla.escena.getActors().toString());

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //texturaFondo.dispose();
    }
}
