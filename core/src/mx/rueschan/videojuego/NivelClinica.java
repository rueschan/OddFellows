package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;

/**
 * Created by OddFellows on 01/05/2017.
 */

public class NivelClinica extends Nivel {

    //Nivel
    private static NivelClinica instancia;
    private static EstadoMapa estadoMapa = EstadoMapa.NO_CARGADO;

    // Recursos
    private final String pathMapa = "NivelClinica/Clinica.tmx";
    private final String pathMusica = "Musica/firstNight.mp3";
    private final String pathFxPasos = "Sonidos/pasosClinica.mp3";

    private AssetManager manager;

    public NivelClinica(OddFellows oddFellows) {
        super.oddFellows = oddFellows;
        super.pantalla = Pantalla.getInstanciaPantalla();
        manager = super.oddFellows.getAssetManager();
        Juego.actual = this;
    }

    public static NivelClinica getNivelClinica(OddFellows oddFellows) {
        if (instancia == null) {
            instancia = new NivelClinica(oddFellows);
        }
        return instancia;
    }

    @Override
    protected void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        //Pad
        Gdx.input.setCatchBackKey(true);
    }

    private void crearRecursosUnicos() {
        // Enemigos
        crearEnemigo(384, 3456, Enemigo.Tipo.DUPLO);
        crearEnemigo(768, 3264, Enemigo.Tipo.MUTIS);
        crearEnemigo(1216, 3648, Enemigo.Tipo.LOBO);
        crearEnemigo(448, 2560, Enemigo.Tipo.JABALI);
        crearEnemigo(1664, 2560, Enemigo.Tipo.JABALI);
        crearEnemigo(320, 1728, Enemigo.Tipo.DUPLO);
        crearEnemigo(320, 448, Enemigo.Tipo.MUTIS);
        crearEnemigo(1216, 768, Enemigo.Tipo.LOBO);
        crearEnemigo(2624, 704, Enemigo.Tipo.MUTIS);
        crearEnemigo(2752, 704, Enemigo.Tipo.MUTIS);
    }

    @Override
    public void show() {
        juego.actual = this;
        // cargarTexturas();

        // Crear mapa
        super.crearRecursos(pantalla, pathMapa, pathMusica);
        estadoMapa = EstadoMapa.CARGADO;
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

        super.render(delta);

        // Mapa
        pantalla.batch.setProjectionMatrix(pantalla.camara.combined);
        super.renderer.setView(pantalla.camara);
        super.renderer.render();

        // Elementos juego
        pantalla.batch.begin();
        super.dibujar(pantalla.batch);
        pantalla.batch.end();
        // HUD
        pantalla.batch.setProjectionMatrix(camaraHUD.combined);
        mostrarItemSeleccionado();
        escenaHUD.draw();

        // Jugador
        henric.render(mapa, this);
        renderEnemigos(mapa);

        FPSLogger logger = new FPSLogger();
        logger.log();

        // Detectar botón físico "return", solo se activa cuando
        //&& !enInventario
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            if(enInventario) {
                enInventario = irInventario(enInventario, NivelClinica.super.escenaHUD);
            }
            else {
                //Abrir MenuPausa
                pausado = pausar(pausado, NivelClinica.super.escenaHUD);
            }
        }
        escribirMenuPausa(pausado);
    }

    public static EstadoMapa getEstadoMapa() {
        return estadoMapa;
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

    public static void reset() {
        estadoMapa = EstadoMapa.NO_CARGADO;
        getManager().unload("NivelClinica/Clinica.tmx");
    }
}
