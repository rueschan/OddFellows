package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by OddFellows on 14/02/2017.
 */
public class NivelCabana extends Nivel {

    private Nivel actual;

    // Recursos
    private String pathMapa;
    private String pathMusica;
    private String pathFxPasos;


    public NivelCabana(OddFellows oddFellows) {
        super.oddFellows = oddFellows;
        super.pantalla = Pantalla.getInstanciaPantalla();
        actual = this;
    }

    @Override
    protected void cargarTexturas() {

        // Recursos
        pathMapa = "NivelCabana/Cabana.tmx";
        pathMusica = "Musica/ofeliasdream.mp3";
        pathFxPasos = "Sonidos/pasoMadera.mp3";
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
        super.crearRecursos(pantalla, pathMapa, pathMusica);
        henric.setFxPasos(pathFxPasos);

        //Creación de HUD
        super.crearElementosPantalla(pantalla);
        super.crearPausa(escenaHUD);
        super.crearInventario(escenaHUD);

        // Objetos
        crearObjetos();
        cargarJuego();

        Gdx.input.setInputProcessor(super.escenaHUD);

    }

    @Override
    public void render(float delta) {

        super.pantalla.borrarPantalla();

        // Mapa
        pantalla.batch.setProjectionMatrix(pantalla.camara.combined);
        super.renderer.setView(pantalla.camara);
        super.renderer.render();

        // Elementos juego
        super.pantalla.escena.draw();
        pantalla.batch.begin();
        super.dibujar(pantalla.batch);
//        super.henric.dibujar(pantalla.batch);
//        hp.dibujar(pantalla.batch);
//        barraHP.dibujar(pantalla.batch);
        pantalla.batch.end();

        // HUD
        pantalla.batch.setProjectionMatrix(camaraHUD.combined);
        mostrarItemSeleccionado();
        escenaHUD.draw();

        // Jugador
        henric.actualizar(mapa);
        henric.interactuar(this);



        // Detectar botón físico "return", solo se activa cuando
        //&& !enInventario
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            if(enInventario) {
                enInventario = irInventario(enInventario, NivelCabana.super.escenaHUD);
            }
             else {
                //Abrir MenuPausa
                pausado = pausar(pausado, NivelCabana.super.escenaHUD);
            }

        }
        escribirMenuPausa(pausado);
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
