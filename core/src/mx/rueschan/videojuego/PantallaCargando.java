package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;

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

    // Carga los recursos en comun de todos los niveles
    private void cargarRecursosNivel() {
        Gdx.app.log("cargarManager Nivel","cargando");

        //Elementos del nivel y sonidos
        manager.load("Musica/giantwyrm.mp3", Music.class);
        manager.load("Sonidos/levantarLlave.mp3", Sound.class);
        manager.load("Sonidos/levantarPapel.mp3", Sound.class);
        manager.load("Sonidos/levantarMartillo.mp3", Sound.class);
        manager.load("Sonidos/zipperAbrir.mp3", Sound.class);
        manager.load("Sonidos/zipperCerrar.mp3", Sound.class);
        manager.load("Sonidos/alerta.mp3", Sound.class);
        manager.load("Sonidos/ataque.mp3", Sound.class);
        manager.load("Sonidos/abrirPuerta.mp3", Sound.class);
        manager.load("Sonidos/accionarCerrojo.mp3", Sound.class);
        manager.load("Sonidos/pickup.mp3", Sound.class);

        // Vida
        manager.load("Pantalla/HP.png", Texture.class);
        manager.load("Pantalla/BarraHP.png", Texture.class);

        // Henric
        cargarHenric();

        // Texturas enemigos
        cargarEnemigos();

        // Armas
        cargarArmas();

        // Mapas
        cargarMapas();

        manager.load("Pad/padBack.png",Texture.class);
        manager.load("Pad/padKnob.png",Texture.class);

        manager.load("Pantalla/Accion.png",Texture.class);
        manager.load("Pantalla/BotonInteraccion.png",Texture.class);
        manager.load("Pantalla/entrar.png",Texture.class);
        manager.load("Pantalla/baseItems.png",Texture.class);
        manager.load("Pantalla/inventario.png",Texture.class);
        manager.load("Pantalla/fondoCarta.png",Texture.class);
        manager.load("Pantalla/cerrar.png",Texture.class);

        //Personaje Corriendo
        manager.load("Personaje/HendricMartilloCorriendo.png",Texture.class);
        manager.load("Personaje/HenricCorriendoBate.png",Texture.class);
        manager.load("Personaje/HenricCorriendoAntorcha.png",Texture.class);
        manager.load("Personaje/HenricCorriendoBarredor.png",Texture.class);
        manager.load("Personaje/HenricLanzaCorriendo.png",Texture.class);
        manager.load("Personaje/HenricMacheteCorriendo.png",Texture.class);
        manager.load("Personaje/HendricLlave.png",Texture.class);
        manager.load("Pantalla/Fondo/fondoPausa.png",Texture.class);


        //Crear texturas
        manager.load("Pantalla/BotonPausa64.png",Texture.class);
        manager.load("Pantalla/Tabla.png",Texture.class);
        manager.load("Pantalla/Tabla.png",Texture.class);
        manager.load("Pantalla/Audio.png",Texture.class);
        manager.load("Pantalla/ecualizador.png",Texture.class);

        manager.load("Pantalla/fondoInventario.png",Texture.class);
        manager.load("Pantalla/btnSalirInventario.png",Texture.class);
        // GAME OVER
        manager.load("Musica/moonlight.mp3",Music.class);
        manager.load("Pantalla/Fondo/fondoGameOver.png",Texture.class);
        manager.load("Pantalla/Vacio.png",Texture.class);
        manager.load("Pantalla/btnExit.png",Texture.class);

    }
    private void cargarRecursosNivelBosque() {
        cargarRecursosNivel();
        manager.load("Musica/lostInForest.mp3",Music.class);
        manager.load("Sonidos/pasoBosque.mp3",Music.class);
    }

    private void cargarRecursosNivelCabana() {
        cargarRecursosNivel();
        Gdx.app.log("cargarManager Cabana","cargando");
        manager.load("Musica/ofeliasdream.mp3",Music.class);
        manager.load("Sonidos/pasoMadera.mp3", Music.class);
        manager.load("Sonidos/alerta.mp3",Sound.class);
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

    // Para que los mapas mantengan el estado en el que se dejarón
    private void cargarMapas() {
        if (NivelCabana.getEstadoMapa() == Nivel.EstadoMapa.NO_CARGADO) {
            manager.load("NivelCabana/Cabana.tmx", TiledMap.class);
        }
        if (NivelBosque.getEstadoMapa() == Nivel.EstadoMapa.NO_CARGADO) {
            manager.load("NivelBosque/bosque.tmx", TiledMap.class);
        }
    }

    private void cargarHenric() {
        //Texturas de Henric
        manager.load("Personaje/Henric.png", Texture.class);
        manager.load("Personaje/HendricMartilloAtaque.png", Texture.class);
        manager.load("Personaje/HenricBateAtaque.png", Texture.class);
        manager.load("Personaje/HenricAntorchaAtaque.png", Texture.class);
        manager.load("Personaje/HenricBarredorAtaque.png", Texture.class);
        manager.load("Personaje/HenricLanzaAtaque.png", Texture.class);
        manager.load("Personaje/HenricMacheteAtaque.png", Texture.class);

    }

    private void cargarArmas() {
        manager.load("Items/martillo.png", Texture.class);
        manager.load("Items/Machete.png", Texture.class);
        manager.load("Items/tridente.png", Texture.class);
        manager.load("Items/Bate.png", Texture.class);
        manager.load("Items/Antorcha.png", Texture.class);
        manager.load("Items/Barredor.png", Texture.class);
    }

    private void cargarEnemigos() {
        // Texturas de enemigos
        manager.load("Enemigo/jabali.png", Texture.class);
        manager.load("Enemigo/oso.png", Texture.class);
        manager.load("Enemigo/mutis.png", Texture.class);
        manager.load("Enemigo/duplo.png", Texture.class);
        manager.load("Enemigo/lobo.png", Texture.class);

        // Sonidos enemigos
        manager.load("Enemigo/jabaliAtaque.mp3",Music.class);
        manager.load("Enemigo/jabaliMuerte.mp3",Sound.class);
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
                    //Gdx.app.log("cargarManager Cabana TextHenric"," "+manager.isLoaded("Personaje/Henric.png"));
                    //Gdx.app.log("cargarManager Cabana Campana"," "+manager.isLoaded("Sonidos/alerta.mp3"));
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

    @Override
    public void resize(int width, int height) {
        super.resize(width,height);
    }
}
