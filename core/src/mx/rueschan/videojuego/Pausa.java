package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Alejandro on 16/02/2017.
 */

public class Pausa implements Screen {

    private final OddFellows oddFellows;
    private final Pantalla pantalla;
    private final Nivel nvlActivo;
    private final Pausa actual;

    // Texturas del menú
    private Texture texturaFondo;
    private Texture texturaBotonJugar;
    private Texture texturaBotonSalir;
    private Texture texturaBotonOpciones;

    public Pausa(OddFellows oddFellows, Nivel nvl) {
        this.oddFellows = oddFellows;
        pantalla = Pantalla.getInstanciaPantalla();
        nvlActivo = nvl;
        actual = this;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondo = new Texture("fondoExtras.jpg");
        texturaBotonJugar = new Texture("Letrero.png");
        texturaBotonSalir = new Texture("btnExit.png");
        texturaBotonOpciones = new Texture("BotonOpcionesHerramientas.png");
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
        btnSalir.setPosition(3*pantalla.getANCHO()/6 - btnSalir.getWidth()/2, pantalla.getALTO()/6);
        pantalla.escena.addActor(btnSalir);

        // Crear botón opciones
        TextureRegionDrawable trdBtnOpciones = new
                TextureRegionDrawable(new TextureRegion(texturaBotonOpciones));
        // Colocar botón opciones
        ImageButton btnOpciones = new ImageButton(trdBtnOpciones);
        btnOpciones.setPosition(3*pantalla.getANCHO()/6 - btnOpciones.getWidth()/2, 4* pantalla.getALTO()/6
                - btnOpciones.getHeight()/2);
        pantalla.escena.addActor(btnOpciones);

        // Click botón salir
        btnSalir.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Salir***");
                oddFellows.setScreen(new Menu(oddFellows));
            }
        });
        // Click botón opciones
        btnOpciones.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Cambio a opciones***");
                oddFellows.setScreen(new Opciones(oddFellows, true, actual));
            }
        });
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
        crearObjetos();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

