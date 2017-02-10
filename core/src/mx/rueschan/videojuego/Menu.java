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
 * Created by Rubén Escalante on 08/02/2017.
 */

public class Menu implements Screen {

    private final OddFellows oddFellows;
    private final Pantalla pantalla;

    // Texturas del menú
    private Texture texturaFondo;
    private Texture texturaBotones;
    private Texture texturaBotonJugar;

    public Menu(OddFellows oddFellows) {
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

    // Metodo para iniciar texturas de pantalla
    private void cargarTexturas() {
        texturaFondo = new Texture("fondo.jpg");
        texturaBotones = new Texture("btn1.jpg");
        texturaBotonJugar = new Texture("btn2.jpg");
    }

    // Metodo para crear objetos en pantalla
    private void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        // Agrega la imagen de fondo
        Image imgFondo = new Image(texturaFondo);
        pantalla.escena.addActor(imgFondo);

        // Botón
        TextureRegionDrawable trdBtn = new
                TextureRegionDrawable(new TextureRegion(texturaBotones));
        // Colocar boton 1
        ImageButton btnPlay = new ImageButton(trdBtn);
        btnPlay.setPosition(2*pantalla.getANCHO()/5 - btnPlay.getWidth()/2, 2* pantalla.getALTO()/5
                - btnPlay.getHeight()/2);
        pantalla.escena.addActor(btnPlay);
        // Colocar boton de opciones
        ImageButton btnOpciones = new ImageButton(trdBtn);
        btnOpciones.setPosition(4*pantalla.getANCHO()/5 - btnOpciones.getWidth()/2, 2* pantalla.getALTO()/5
                - btnOpciones.getHeight()/2);
        pantalla.escena.addActor(btnOpciones);

        // Acciones de botones
            // Boton opciones
        btnOpciones.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Cambio a opciones***");
                oddFellows.setScreen(new Opciones(oddFellows));
            }
        });

        Gdx.input.setInputProcessor(pantalla.escena);
        Gdx.input.setCatchBackKey(false);
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
//        texturaFondo.dispose();
//        texturaBotones.dispose();
//        texturaBotonJugar.dispose();
    }
}
