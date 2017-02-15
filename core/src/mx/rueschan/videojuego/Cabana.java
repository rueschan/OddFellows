package mx.rueschan.videojuego;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Rubén Escalante on 14/02/2017.
 */
public class Cabana extends Nivel {

    public Cabana(OddFellows oddFellows) {
        super.oddFellows = oddFellows;
        super.pantalla = Pantalla.getInstanciaPantalla();
    }

    @Override
    protected void cargarTexturas() {
        texturaFondo = new Texture("fondo.jpg");
    }

    @Override
    protected void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        // Agrega la imagen de fondo
        Image imgFondo = new Image(texturaFondo);
        imgFondo.setColor(0.2f,1,1,1);
        pantalla.escena.addActor(imgFondo);
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
        cargarJuego();
    }

    @Override
    public void render(float delta) {
        super.pantalla.borrarPantalla();
        super.pantalla.escena.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
