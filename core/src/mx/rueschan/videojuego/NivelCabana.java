package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by OddFellows on 14/02/2017.
 */
public class NivelCabana extends Nivel {

    private static NivelCabana instancia;
    private static EstadoMapa estadoMapa = EstadoMapa.NO_CARGADO;

    // Recursos
    private final String pathMapa = "NivelCabana/Cabana.tmx";
    private final String pathMusica = "Musica/ofeliasdream.mp3";
    private final String pathFxPasos = "Sonidos/pasoMadera.mp3";
//
//

    private AssetManager manager;


    public NivelCabana(OddFellows oddFellows) {
        super.oddFellows = oddFellows;
        super.pantalla = Pantalla.getInstanciaPantalla();
        manager = super.oddFellows.getAssetManager();
        Juego.actual = this;
    }

    public static NivelCabana getNivelCabana(OddFellows oddFellows) {
        if (instancia == null) {
            instancia = new NivelCabana(oddFellows);
        }
        return instancia;
    }

//    @Override
//    protected void cargarTexturas() {
//        // Recursos
////        pathMapa = "NivelCabana/Cabana.tmx";
////        pathMusica = "Musica/ofeliasdream.mp3";
////        pathFxPasos = "Sonidos/pasoMadera.mp3";
//    }

    @Override
    protected void crearObjetos() {

        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        //Pad
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
//        cargarTexturas();

        // Crear mapa
        super.crearRecursos(pantalla, pathMapa, pathMusica);
        estadoMapa = EstadoMapa.CARGADO;
        henric.setFxPasos(pathFxPasos);

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

//        long inicio = System.nanoTime();
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
//        super.henric.dibujar(pantalla.batch);
//        hp.dibujar(pantalla.batch);
//        barraHP.dibujar(pantalla.batch);
        pantalla.batch.end();
//        long fin = System.nanoTime();
        // *** R
//        System.out.println((fin - inicio) / 1000);  // SE CAMBIARON DOS LLAMADAS A LA CLASE "NIVEL" POR SOLO UNA. SE REDUCE EL TIEMPO DE 400 ns aprox A 300 ns aprox

//        long iniMemoria = Gdx.app.getNativeHeap();
        // HUD
        pantalla.batch.setProjectionMatrix(camaraHUD.combined);
        mostrarItemSeleccionado();
        escenaHUD.draw();

        // Jugador
        henric.render(mapa, this);
//        long finMemoria = Gdx.app.getNativeHeap();
//        System.out.println((finMemoria - iniMemoria));

        FPSLogger logger = new FPSLogger();
        logger.log();

        // Detectar botón físico "return", solo se activa cuando
        //&& !enInventario
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            if(enInventario) {
                enInventario = irInventario(enInventario, NivelCabana.super.escenaHUD);
            }else if(enCarta){
                cerrarCarta();
            }
             else {
                //Abrir MenuPausa
                pausado = pausar(pausado, NivelCabana.super.escenaHUD);
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
        getManager().unload("NivelCabana/Cabana.tmx");
    }
}
