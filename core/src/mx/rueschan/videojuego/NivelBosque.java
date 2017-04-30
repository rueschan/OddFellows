package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Odd Fellows on 19/03/2017.
 */

public class NivelBosque extends Nivel {

    private Nivel actual;
    private static NivelBosque instancia;

    //Mapa tipo tmx del Bosque
    private final String pathMapa = "NivelBosque/bosque.tmx";
    private final String pathMusica = "Musica/lostInForest.mp3";
    private final String pathFxPasos = "Sonidos/pasoBosque.mp3";

    // Enemigos


    public NivelBosque(OddFellows oddFellows) {
        super.oddFellows = oddFellows;
        super.pantalla = Pantalla.getInstanciaPantalla();
        actual = this;
    }

    public static NivelBosque getNivelBosque(OddFellows oddFellows) {
        if (instancia == null) {
            instancia = new NivelBosque(oddFellows);
        }
        return instancia;
    }

   @Override
    protected void cargarTexturas() {
        //Textura de los diferentes elementos que componen el nivel
       /* pathMapa = "NivelBosque/bosque.tmx";
        pathMusica = "Musica/lostInForest.mp3";
        pathFxPasos = "Sonidos/pasoBosque.mp3";*/
    }

    @Override
    protected void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        //Pad
        Gdx.input.setCatchBackKey(true);
    }

    private void crearRecursosUnicos() {
        // TEST ENEMIGO
        enemigo = new Enemigo(800, 400, Enemigo.Tipo.JABALI);
        listaEnemigos.add(enemigo);
    }

    private void dibujarRecursosUnicos(SpriteBatch batch) {
        enemigo.dibujar(batch);
    }

    @Override
    public void show() {
       // cargarTexturas();

        // Crear mapa
        super.crearRecursos(pantalla, pathMapa, pathMusica);
//        henric.reset();
        henric.setFxPasos(pathFxPasos);
        crearRecursosUnicos();

        //Creación de HUD
        super.crearElementosPantalla(pantalla);
//        super.crearPausa(escenaHUD);
//        super.crearInventario(escenaHUD);

        // Objetos
        crearObjetos();
        cargarJuego();

        Gdx.input.setInputProcessor(super.escenaHUD);
    }

    @Override
    public void render(float delta) {

//        super.pantalla.borrarPantalla();
        super.render(delta);

        // Mapa
        pantalla.batch.setProjectionMatrix(pantalla.camara.combined);
        super.renderer.setView(pantalla.camara);
        super.renderer.render();

        // Elementos juego
//        super.pantalla.escena.draw();
        pantalla.batch.begin();
        super.dibujar(pantalla.batch);
        dibujarRecursosUnicos(pantalla.batch);
//        super.henric.dibujar(pantalla.batch);
//        hp.dibujar(pantalla.batch);
//        barraHP.dibujar(pantalla.batch);
        pantalla.batch.end();

        // HUD
        pantalla.batch.setProjectionMatrix(camaraHUD.combined);
        mostrarItemSeleccionado();
        escenaHUD.draw();

        // Jugador
        henric.render(mapa, this);

        // Enemigos
        enemigo.actualizar(mapa);

        // Detectar botón físico "return", solo se activa cuando
        //&& !enInventario
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            if(enInventario) {
                enInventario = irInventario(enInventario, NivelBosque.super.escenaHUD);
            }
            else {
                //Abrir MenuPausa
                pausado = pausar(pausado, NivelBosque.super.escenaHUD);
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
