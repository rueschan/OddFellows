package mx.rueschan.videojuego;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Rubén Escalante on 14/02/2017.
 */
public abstract class Nivel implements Screen{

    protected OddFellows oddFellows;
    protected Pantalla pantalla;
    protected Juego juego;

    protected Texture texturaFondo;

    // private Elemento[] items;

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
        cargarJuego();
    }

    protected void cargarJuego(){
        juego = Juego.getJuego(oddFellows);
    };

    protected abstract void crearObjetos();

    protected abstract void cargarTexturas();

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();
    }

    @Override
    public void resume() {
        cargarTexturas();
    }

    protected void cambiarNivel(Nivel nvl) {
        juego.guardarStatus();
        // juego.cargarNivel(nvl);
        this.dispose();

    }
}
