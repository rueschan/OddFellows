package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by OddFellows on 18/04/2017.
 */

public class PantallaCargando extends Pantalla {
    // Animación cargando
    private Sprite camillaCargando;

    // AssetManager
    private AssetManager manager;

    private OddFellows oddFellows;
    private Niveles siguienteNivel;
    private int porcentaje; // % de carga
    private Texto texto;

    private Texture texturaCargando;

//    private float tiempoVisible = 4.0f;
//    private float incrementoAlpha = 1/180f;
    private float alpha = 0f;
//    private int count = 0;

    public PantallaCargando(OddFellows oddFellows, Niveles siguienteNivel) {
        this.oddFellows = oddFellows;
        this.siguienteNivel = siguienteNivel;
        this.manager = oddFellows.getAssetManager();
    }

    @Override
    public void show() {
        texturaCargando = new Texture(Gdx.files.internal("Pantalla/Fondo/fondoCarga.png"));
        camillaCargando = new Sprite(texturaCargando);
        camillaCargando.setPosition(super.getANCHO()/2- camillaCargando.getWidth()/2,super.getALTO()/2- camillaCargando.getHeight()/2);
        cargarRecursosSigPantalla();
        texto = new Texto();
        texto.cambiarTamano(3f);
    }

    // Carga los recursos de la siguiente pantalla
    private void cargarRecursosSigPantalla() {
        //*************************+CAMARA cambio a estructuras if-else y el tiempo aumentó, no hubo mejora :'(
        manager = oddFellows.getAssetManager();
        porcentaje = 0;
        switch (siguienteNivel) {
            case MENU_PRINCIPAL:
                cargarRecursosMenuPrincipal();
                break;
            case NIVEL_CABANA:
                cargarRecursosNivelCabana();
                break;
            case NIVEL_BOSQUE:
                cargarRecursosNivelBosque();
                break;
        }
    }
    private void cargarRecursosNivel() {

    }
    private void cargarRecursosNivelBosque() {

    }

    private void cargarRecursosNivelCabana() {

    }

    private void cargarRecursosMenuPrincipal() {
        Gdx.app.log("cargarManager Menu","cargando");
        manager.load("Musica/chopinNocturne.mp3", Music.class);

        //MENU PRINCIPAL
        manager.load("Pantalla/Fondo/fondoMenu.png",Texture.class);
        manager.load("Pantalla/Letrero.png",Texture.class);
        manager.load("Pantalla/LibroCreditos.png",Texture.class);
        manager.load("Pantalla/BotonOpcionesHerramientas.png",Texture.class);
        manager.load("Pantalla/HojaCreditos.png",Texture.class);

        //MENU OPCIONES
        manager.load("Pantalla/Fondo/FondoOpciones.jpg",Texture.class);
        manager.load("Pantalla/Audio.png",Texture.class);
        manager.load("Pantalla/ecualizador.png",Texture.class);

        //MENU EXTRAS
        manager.load("Pantalla/Fondo/fondoExtras.png",Texture.class);

        //MENU CREDITOS
        manager.load("Pantalla/Fondo/fondoCreditos.png",Texture.class);

        //TEXTURA BOTON EXIT
        manager.load("Pantalla/btnExit.png",Texture.class);

    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        camillaCargando.draw(batch);
        camillaCargando.setAlpha(alpha);
        //CODIGO PARA EFECTO DESVANECER IMAGEN
        /*if(count >= 180){
            //incrementoAlpha *= -1;
            Gdx.app.log("count>240","ya valio");
            alpha = 0;
            count = 0;
        }
        alpha += incrementoAlpha;
        count++;*/
        alpha = porcentaje*0.01f;
        texto.cambiarMensaje(porcentaje +" %");
        texto.escribir(batch,1*super.getANCHO()/2-texto.getAnchoTexto()/3-20,5*super.getALTO()/6);
        batch.end();

        // Actualizar carga
        actualizarCargaRecursos();
    }

    private void actualizarCargaRecursos() {
        if (manager.update()) { // Terminó?
            //*************************+CAMARA cambio a estructuras if-else y el tiempo aumentó, no hubo mejora :'(
            switch (siguienteNivel) {
                case MENU_PRINCIPAL:
                    oddFellows.setScreen(new MenuPrincipal(
                    oddFellows));   // 100% de carga
                    break;

                case NIVEL_CABANA:
                    Gdx.app.log("Pantalla Cargando","cambio a NivelCabana");
                    oddFellows.setScreen(new NivelCabana(
                    oddFellows));   // 100% de carga
                    break;

                case NIVEL_BOSQUE:
                    Gdx.app.log("Pantalla Cargando","cambio a NivelBosque");
                    oddFellows.setScreen(new NivelBosque(
                    oddFellows));
                    break;
            }
        }
        porcentaje = (int)(manager.getProgress()*100);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaCargando.dispose();
    }
}
