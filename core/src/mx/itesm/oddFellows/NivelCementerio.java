package mx.itesm.oddFellows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by Odd Fellows on 03/05/2017.
 */

public class NivelCementerio extends Nivel {

    private static NivelCementerio instancia;
    private static EstadoMapa estadoMapa = EstadoMapa.NO_CARGADO;

    //Mapa tipo tmx del Bosque
    private final String pathMapa = "NivelCementerio/cementerio.tmx";
    private final String pathMusica = "Musica/controlledChaos.mp3";
    private final String pathFxPasos = "Sonidos/pasoBosque.mp3";

    // Enemigos
    public NivelCementerio(OddFellows oddFellows) {
        super.oddFellows = oddFellows;
        super.pantalla = Pantalla.getInstanciaPantalla();
        Juego.actual = this;
    }

    public static NivelCementerio getNivelBosque(OddFellows oddFellows) {
        if (instancia == null) {
            instancia = new NivelCementerio(oddFellows);
        }
        return instancia;
    }

//   @Override
//    protected void cargarTexturas() {
//        //Textura de los diferentes elementos que componen el nivel
//       /* pathMapa = "NivelBosque/bosque.tmx";
//        pathMusica = "Musica/lostInForest.mp3";
//        pathFxPasos = "Sonidos/pasoBosque.mp3";*/
//    }

    @Override
    protected void crearObjetos() {
        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        //Pad
        Gdx.input.setCatchBackKey(true);
    }

    private void crearRecursosUnicos() {
        // TEST ENEMIGO
        //Dentro del cementerio
        crearEnemigo(320, 1408, Enemigo.Tipo.DUPLO);
        crearEnemigo(1152, 1152, Enemigo.Tipo.DUPLO);
        crearEnemigo(704, 1024, Enemigo.Tipo.MUTIS);
        crearEnemigo(1152, 1472, Enemigo.Tipo.MUTIS);

        //Fuera del cementerio
        crearEnemigo(1038, 448, Enemigo.Tipo.JABALI);
        crearEnemigo(1664, 576, Enemigo.Tipo.LOBO);
        crearEnemigo(2432, 256, Enemigo.Tipo.OSO);
        crearEnemigo(3008, 384, Enemigo.Tipo.JABALI);
        crearEnemigo(3136, 1472, Enemigo.Tipo.LOBO);
        crearEnemigo(4224, 1536, Enemigo.Tipo.OSO);
        crearEnemigo(4800, 896, Enemigo.Tipo.LOBO);
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

        // HUD
        pantalla.batch.setProjectionMatrix(camaraHUD.combined);
        mostrarItemSeleccionado();
        escenaHUD.draw();

        // Jugador
        henric.render(mapa, this);

        // Enemigos
        renderEnemigos(mapa);

        // Detectar botón físico "return", solo se activa cuando
        //&& !enInventario
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            if(enInventario) {
                enInventario = irInventario(enInventario, NivelCementerio.super.escenaHUD);
            }else if(enCarta){
                cerrarCarta();
            }
            else {
                //Abrir MenuPausa
                pausado = pausar(pausado, NivelCementerio.super.escenaHUD);
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
        getManager().unload("NivelCementerio/cementerio.tmx");
    }
}
