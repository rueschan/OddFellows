package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
    private Texture texturaBotonJugar;
    private Texture texturaBotonExtras;
    private Texture texturaBotonOpciones;
    private Texture texturaBotonCreditos;

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
        texturaFondo = new Texture("fondoMenu.png");
        texturaBotonJugar = new Texture("Letrero.png");
        texturaBotonExtras = new Texture("LibroCreditos.png");
        texturaBotonOpciones = new Texture("BotonOpcionesHerramientas.png");
        texturaBotonCreditos = new Texture("HojaCreditos.png");
    }

    // Metodo para crear objetos en pantalla
    private void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        // Agrega la imagen de fondo
        Image imgFondo = new Image(texturaFondo);
        pantalla.escena.addActor(imgFondo);

        // Asignar texturas a los botones
        TextureRegionDrawable trdBtnJugar = new
                TextureRegionDrawable(new TextureRegion(texturaBotonJugar));
        TextureRegionDrawable trdBtnExtras = new
                TextureRegionDrawable(new TextureRegion(texturaBotonExtras));
        TextureRegionDrawable trdBtnOpciones = new
                TextureRegionDrawable(new TextureRegion(texturaBotonOpciones));
        TextureRegionDrawable trdBtnCreditos = new
                TextureRegionDrawable(new TextureRegion(texturaBotonCreditos));

        // Colocar boton de juego
        ImageButton btnPlay = new ImageButton(trdBtnJugar);
        btnPlay.setPosition(3*pantalla.getANCHO()/6 - btnPlay.getWidth()/2, 0);
        pantalla.escena.addActor(btnPlay);

        // Colocar boton de opciones
        ImageButton btnOpciones = new ImageButton(trdBtnOpciones);
        btnOpciones.setPosition(3*pantalla.getANCHO()/6 - btnOpciones.getWidth()/2, 3* pantalla.getALTO()/6
                - btnOpciones.getHeight()/2);
        pantalla.escena.addActor(btnOpciones);

        // Colocar boton de extras
        ImageButton btnExtras = new ImageButton(trdBtnExtras);
        btnExtras.setPosition(1*pantalla.getANCHO()/6 - btnExtras.getWidth()/2, 3* pantalla.getALTO()/10
                - btnExtras.getHeight()/2);
        pantalla.escena.addActor(btnExtras);

        // Colocar boton de creditos
        ImageButton btnCreditos = new ImageButton(trdBtnCreditos);
        btnCreditos.setPosition(5*pantalla.getANCHO()/6 - btnCreditos.getWidth()/2, 3* pantalla.getALTO()/10
                - btnCreditos.getHeight()/2);
        pantalla.escena.addActor(btnCreditos);

        // Acciones de botones
            // Botón opciones
        btnOpciones.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Cambio a opciones***");
                oddFellows.setScreen(new Opciones(oddFellows, false));
            }
        });
            // Botón extras
        btnExtras.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Cambio a extras***");
                oddFellows.setScreen(new Extras(oddFellows));
            }
        });
            // Botón creditos
        btnCreditos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Cambio a creditos***");
                oddFellows.setScreen(new Creditos(oddFellows));
            }
        });
            // Botón jugar
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Cambio a juego***");
                Juego juego = Juego.getJuego(oddFellows);
                juego.iniciarJuego();
            }
        });

        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();

        escribirEnPantalla();
    }

    private void escribirEnPantalla() {
        pantalla.batch.begin();
        pantalla.texto.mostrarMensajes(pantalla.batch, Color.WHITE, "Options", 3*pantalla.getANCHO()/6, 3*pantalla.getALTO()/6 - 3*pantalla.texto.getAltoTexto());
        pantalla.texto.mostrarMensajes(pantalla.batch, Color.WHITE, "Extras", 1*pantalla.getANCHO()/6+pantalla.texto.getAnchoTexto()/15, 3*pantalla.getALTO()/10 - 3*pantalla.texto.getAltoTexto());
        pantalla.texto.mostrarMensajes(pantalla.batch, Color.WHITE, "Credits", 5*pantalla.getANCHO()/6, 3*pantalla.getALTO()/10 - 3*pantalla.texto.getAltoTexto());
        pantalla.texto.mostrarMensajes(pantalla.batch, new Color(1, 1, 1, 0.85f), "PLAY", (3*pantalla.getANCHO()/6)-pantalla.texto.getAnchoTexto()/11, 1.3f*pantalla.getALTO()/6);
        pantalla.batch.end();
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
        texturaBotonJugar.dispose();
        texturaBotonCreditos.dispose();
        texturaBotonExtras.dispose();
        texturaBotonOpciones.dispose();
    }
}
