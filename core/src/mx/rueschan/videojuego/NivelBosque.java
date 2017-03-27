package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Jonathan on 19/03/2017.
 */

public class NivelBosque extends Nivel {

    private Nivel actual;

    //Mapa tipo tmx del Bosque
    private String pathMapa;
    private String pathMusica;

    public NivelBosque(OddFellows oddFellows) {
        super.oddFellows = oddFellows;
        super.pantalla = Pantalla.getInstanciaPantalla();
        actual = this;
    }

    @Override
    protected void cargarTexturas() {
        //Textura de los diferentes elementos que componen el nivel

    }

    @Override
    protected void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();

        //Pad
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
        cargarTexturas();

        // Crear mapa
        super.crearRecursos(pantalla, pathMapa,pathMusica);

        //Creación de HUD
        super.crearHUD(pantalla);

        // Objetos
        crearObjetos();
        cargarJuego();

        Gdx.input.setInputProcessor(super.escenaHUD);
    }

    @Override
    public void render(float delta) {

        super.pantalla.borrarPantalla();

        // Mapa
        pantalla.batch.setProjectionMatrix(pantalla.camara.combined);;
        super.renderer.setView(pantalla.camara);
        super.renderer.render();

        // Elementos juego
        super.pantalla.escena.draw();
        pantalla.batch.begin();
        super.henric.dibujar(pantalla.batch);
        pantalla.batch.end();

        // HUD
        pantalla.batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();

        // Jugador
        henric.actualizar(mapa);

        // Detectar botón físico "return"
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            //Abrir MenuPausa
            oddFellows.setScreen(new MenuPausa(oddFellows, actual));
        }
    }

    @Override
    public void resize(int width, int height) {
        pantalla.resize(width, height);
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
