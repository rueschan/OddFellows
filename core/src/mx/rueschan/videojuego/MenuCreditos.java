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
 * Created by Odd Fellows on 14/02/2017.
 */

public class MenuCreditos implements Screen {

    private OddFellows oddFellows;
    private Pantalla pantalla;

    private Texture texturaFondo;
    private Texture texturaExit;

    public MenuCreditos(OddFellows oddFellows) {
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
        texturaFondo = new Texture("fondoCreditos.jpg");
        texturaExit = new Texture("btnExit.png");
    }

    private void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        // Agrega la imagen de fondo
        Image imgFondo = new Image(texturaFondo);
        pantalla.escena.addActor(imgFondo);

        // Botón de Exit
        TextureRegionDrawable trdExit = new
                TextureRegionDrawable(new TextureRegion(texturaExit));
        // Colocar boton de Exit
        ImageButton btnExit = new ImageButton(trdExit);
        btnExit.setPosition(10,10);
        pantalla.escena.addActor(btnExit);

        // Acciones de botones
            // Boton opciones
        btnExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Salir***");
                oddFellows.setScreen(new MenuPrincipal(oddFellows));
            }
        });

        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();

        // Detectar botón físico "return"
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            //Regresar al MenuPrincipal
            oddFellows.setScreen(new MenuPrincipal(oddFellows));
        }
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
        texturaExit.dispose();
        texturaFondo.dispose();
    }
}
