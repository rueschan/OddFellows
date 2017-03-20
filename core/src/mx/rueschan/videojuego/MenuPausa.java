package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
 * Created by OddFellows on 16/02/2017.
 */

public class MenuPausa implements Screen {

    private final OddFellows oddFellows;
    private final Pantalla pantalla;
    private final Nivel nvlActivo;
    private final MenuPausa actual;

    // Texturas del menú
    private Texture texturaFondo;
    private Texture texturaBotonJugar;
    private Texture texturaBotonSalir;
    private Texture texturaBotonOpciones;

    private Procesador procesador;

    public MenuPausa(OddFellows oddFellows, Nivel nvl) {
        this.oddFellows = oddFellows;
        pantalla = Pantalla.getInstanciaPantalla();
        nvlActivo = nvl;
        actual = this;
        //procesador = new Procesador();
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondo = new Texture("Pantalla/Fondo/fondoPausa.jpg");
        texturaBotonJugar = new Texture("Pantalla/Tabla.png");
        texturaBotonSalir = new Texture("Pantalla/btnExit.png");
        texturaBotonOpciones = new Texture("Pantalla/Tabla.png");
    }

    private void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        // Agrega la imagen de fondo
        Image imgFondo = new Image(texturaFondo);
        pantalla.escena.addActor(imgFondo);

        // Crear botón salir
        TextureRegionDrawable trdBtnSalir = new
                TextureRegionDrawable(new TextureRegion(texturaBotonSalir));
        // Colocar botón salir
        ImageButton btnSalir = new ImageButton(trdBtnSalir);
        btnSalir.setPosition(10, 10);
        pantalla.escena.addActor(btnSalir);

        // Crear botón opciones
        TextureRegionDrawable trdBtnOpciones = new
                TextureRegionDrawable(new TextureRegion(texturaBotonOpciones));
        // Colocar botón opciones
        ImageButton btnOpciones = new ImageButton(trdBtnOpciones);
        btnOpciones.setPosition(3*pantalla.getANCHO()/6 - btnOpciones.getWidth()/2, 1* pantalla.getALTO()/6
                - btnOpciones.getHeight()/2);
        pantalla.escena.addActor(btnOpciones);

        // Crear botón opciones
        TextureRegionDrawable trdBtnPlay = new
                TextureRegionDrawable(new TextureRegion(texturaBotonJugar));
        // Colocar botón opciones
        ImageButton btnPlay = new ImageButton(trdBtnPlay);
        btnPlay.setPosition(3*pantalla.getANCHO()/6 - btnPlay.getWidth()/2, 3* pantalla.getALTO()/6
                - btnPlay.getHeight()/2);
        pantalla.escena.addActor(btnPlay);

        // Click botón salir
        btnSalir.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Salir***");
                oddFellows.setScreen(new MenuPrincipal(oddFellows));
            }
        });
        // Click botón opciones
        btnOpciones.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Cambio a opciones***");
                oddFellows.setScreen(new MenuOpciones(oddFellows, true, actual));
            }
        });
        // Click botón jugar
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Cambio a juego***");
                oddFellows.setScreen(nvlActivo);
            }
        });
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();

        // Detectar botón físico "return"
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            //Regresar al Nivel
            oddFellows.setScreen(nvlActivo);
        }

        escribirEnPantalla();
    }

    private void escribirEnPantalla() {
        pantalla.batch.begin();
        pantalla.texto.mostrarMensajes(pantalla.batch, new Color(1, 1, 1, 0.85f), "Resume",
                3*pantalla.getANCHO()/6, 3* pantalla.getALTO()/6);
        pantalla.texto.mostrarMensajes(pantalla.batch, new Color(1, 1, 1, 0.85f), "Options",
                3*pantalla.getANCHO()/6, 1* pantalla.getALTO()/6);
        pantalla.batch.end();
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
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    class Procesador implements InputProcessor{

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}

