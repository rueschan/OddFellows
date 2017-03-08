package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by OddFellows on 08/02/2017.
 */

public class Pantalla implements Screen {

    // Tamaño pantalla
    private final float ANCHO = 1280;
    private final float ALTO = 800;

    // Camara y vista
    public OrthographicCamera camara;
    public Viewport vista;

    // Escenas
    public Stage escena;
    public SpriteBatch batch;

    // Texto
    public Texto texto;

    // Pantalla existente
    private static Pantalla instancia = null;

    private Pantalla() {
        crearCamara();
        crearObjetos();
    }

    // Método para obtener pantalla o crearla
    public static Pantalla getInstanciaPantalla() {
        if (instancia == null) {
            instancia = new Pantalla();
        }
        return instancia;
    }

    // Métodos para iniciar variables de pantallas
    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2, ALTO/2, 0);
        camara.update();
        vista = new StretchViewport(ANCHO, ALTO, camara);
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escena = new Stage(vista, batch) {

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    // DEBUG
                    Gdx.app.log("Btn BACK", "Atras en opciones");
                    return true;
                }
                return super.keyDown(keycode);
            }
        };
        texto = new Texto();

        Gdx.input.setInputProcessor(escena);
    }

    // Metodos para obtener tamaño de pantalla
    public float getANCHO() {
        return ANCHO;
    }

    public float getALTO() {
        return ALTO;
    }

    // Metodo para borrar pantalla
    public void borrarPantalla() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        crearCamara();
        crearObjetos();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
         batch.dispose();
         escena.dispose();
         instancia.dispose();
    }
}
