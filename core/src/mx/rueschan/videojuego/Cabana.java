package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Rubén Escalante on 14/02/2017.
 */
public class Cabana extends Nivel {

    private Nivel actual;

    public Cabana(OddFellows oddFellows) {
        super.oddFellows = oddFellows;
        super.pantalla = Pantalla.getInstanciaPantalla();
        actual = this;
    }

    @Override
    protected void cargarTexturas() {
        texturaFondo = new Texture("fondoCabana.jpg");
        texturaBotonPausa = new Texture("BotonPausa64.png");
    }

    @Override
    protected void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        // Agrega la imagen de fondo
        Image imgFondo = new Image(texturaFondo);
        pantalla.escena.addActor(imgFondo);

        //// Asignar textura a lo boton de pausa
        TextureRegionDrawable trdBtnPausa = new
                TextureRegionDrawable(new TextureRegion(texturaBotonPausa));
        // Colocar boton de pausa
        ImageButton btnPausa = new ImageButton(trdBtnPausa);
        btnPausa.setPosition(1186,706);
        pantalla.escena.addActor(btnPausa);

        // Acciones de botones
        // Botón opciones
        btnPausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Cambio a pausa***");
                oddFellows.setScreen(new Pausa(oddFellows, actual));
            }
        });

        Gdx.input.setCatchBackKey(true);
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
        texturaFondo.dispose();
        texturaBotonPausa.dispose();

    }
}
