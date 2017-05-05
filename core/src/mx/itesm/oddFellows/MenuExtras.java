package mx.itesm.oddFellows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

/**
 * Created by OddFellows on 13/02/2017.
 */
public class MenuExtras implements Screen {

    private OddFellows oddFellows;
    private Pantalla pantalla;
    private final AssetManager manager;

    private Texture texturaFondo;
    private Texture texturaExit;
    private ArrayList<Objeto> logros;

    private float ANCHO = 0;
    private float ALTO = 0;


    private TextureRegionDrawable trdOscura;
    private ImageButton imgbtnBailarina;
    private ImageButton imgbtnCerdo;
    private ImageButton imgbtnCazuela;
    private ImageButton imgbtnMuletas;
    private ImageButton imgbtnTexto;


    private String extraSeleccionado;
    private Texto textoPermisos;
    private String bailarina;
    private String cerdo;
    private String cazuela;
    private String muletas;


    public MenuExtras(OddFellows oddFellows) {
        this.oddFellows = oddFellows;
        manager = this.oddFellows.getAssetManager();
        pantalla = Pantalla.getInstanciaPantalla();
        extraSeleccionado ="";
    }

    @Override
    public void show() {
        // Cuando cargan la pantalla
        cargarTexturas();
        crearObjetos();
        crearInteraccion();
        oddFellows.tocarMusica();
        textoPermisos = new Texto();
    }

    private void cargarTexturas() {
        /*texturaFondo = new Texture("Pantalla/Fondo/fondoExtras.png");
        texturaExit = new Texture("Pantalla/btnExit.png");*/
        texturaFondo = manager.get("Pantalla/Fondo/fondoExtras.png");
        texturaExit = manager.get("Pantalla/btnExit.png");
        bailarina = "Obtained after\n" +
                    "beating the game";
        cerdo =     "Slay 30 boars";
        cazuela =   "Use 20 medkits";
        muletas =   "Die 10 times";
    }

    private void crearObjetos() {
        ANCHO = pantalla.getANCHO();
        ALTO = pantalla.getALTO();
        logros = new ArrayList<Objeto>();
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

        crearLogros();
        verEstadoLogros();

        Gdx.input.setCatchBackKey(true);
    }

    private void verEstadoLogros() {
        if (Configuraciones.ultimaPuertaAbierta()) {
            logros.get(0).sprite.setColor(1, 1, 1, 1);
        }
        if (Configuraciones.contadorJabali>29){
            logros.get(1).sprite.setColor(1, 1, 1, 1);
        }
        if (Configuraciones.contadorMedkit>19){
            logros.get(2).sprite.setColor(1, 1, 1, 1);
        }
        if (Configuraciones.contadorMuertes>9){
            logros.get(3).sprite.setColor(1, 1, 1, 1);
        }
    }

    private void crearLogros() {
        agregarLogro(ANCHO * 1/4, ALTO * 6/10, new Texture("Extras/extraBailarina.png"));   // index 0
        logros.get(0).sprite.setColor(0, 0, 0, .3f);
        agregarLogro(ANCHO * 1/4, ALTO * 3/10, new Texture("Extras/extraCerdo.png"));       // index 1
        logros.get(1).sprite.setColor(0, 0, 0, .3f);
        agregarLogro(ANCHO * 2/3, ALTO * 6/10, new Texture("Extras/extraCazuela.png"));     // index 2
        logros.get(2).sprite.setColor(0, 0, 0, .3f);
        agregarLogro(ANCHO * 2/3, ALTO * 3/10, new Texture("Extras/extraMuletas.png"));     // index 3
        logros.get(3).sprite.setColor(0, 0, 0, .3f);
    }

    private void agregarLogro(float x, float y, Texture texture) {
        logros.add(new Objeto(x, y, texture));
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();

        pantalla.batch.begin();
        for (Objeto logro : logros) {
            logro.dibujar(pantalla.batch);
        }
        pantalla.batch.end();

        // Detectar botón físico "return"
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            //Regresar al MenuPrincipal
            oddFellows.setScreen(new MenuPrincipal(oddFellows));
        }
        escribirEnPantalla();
    }

    private void escribirEnPantalla() {
        pantalla.batch.begin();
        textoPermisos.cambiarTamano(2);
        textoPermisos.mostrarMensajes(pantalla.batch, Color.WHITE,extraSeleccionado,
                pantalla.getANCHO()/2+30,pantalla.getALTO()-30);
        pantalla.batch.end();
    }


    @Override
    public void resize(int width, int height) {
       pantalla.resize(width,height);

    }

    @Override
    public void pause() {

    }
    @Override
    public void resume() {
        cargarTexturas();
        crearObjetos();
        crearInteraccion();
        oddFellows.tocarMusica();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
        texturaExit.dispose();
    }

    private void crearInteraccion(){
        int pantAlto = (int)(pantalla.getALTO());
        int pantAncho = (int)(pantalla.getANCHO());
        Pixmap pixmapVerde = new Pixmap(pantAncho/8, pantAlto/5, Pixmap.Format.RGBA8888);
        pixmapVerde.setColor( .3f,.3f, .3f, 0f );
        pixmapVerde.fillRectangle(0,0,pantAncho,pantAlto);
        trdOscura = new TextureRegionDrawable(new TextureRegion(new Texture(pixmapVerde)));
        pixmapVerde.dispose();

        imgbtnBailarina = new ImageButton(trdOscura);
        imgbtnBailarina.setPosition(310,470);

        imgbtnCerdo = new ImageButton(trdOscura);
        imgbtnCerdo.setPosition(320,235);

        imgbtnCazuela = new ImageButton(trdOscura);
        imgbtnCazuela.setPosition(860,470);

        imgbtnMuletas = new ImageButton(trdOscura);
        imgbtnMuletas.setPosition(850,235);


        imgbtnBailarina.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Kevin MacLeod***");
//                compositorSeleccionado = kevin;
                extraSeleccionado = bailarina;
                imgbtnTexto.setVisible(true);
                imgbtnTexto.setPosition(360,610);

            }
        });
        imgbtnCerdo.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Doug***");
//                compositorSeleccionado = doug;
                extraSeleccionado = cerdo;
                imgbtnTexto.setVisible(true);
                imgbtnTexto.setPosition(360,710);
            }
        });
        imgbtnCazuela.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Chopin***");
//                compositorSeleccionado = chopin;
                extraSeleccionado = cazuela;
                imgbtnTexto.setVisible(true);
                imgbtnTexto.setPosition(360,710);
            }
        });
        imgbtnMuletas.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Beethoven***");
//                compositorSeleccionado = beethoven;
                extraSeleccionado = muletas;
                imgbtnTexto.setVisible(true);
                imgbtnTexto.setPosition(360,710);
            }
        });
        pantalla.escena.addActor(imgbtnBailarina);
        pantalla.escena.addActor(imgbtnCazuela);
        pantalla.escena.addActor(imgbtnCerdo);
        pantalla.escena.addActor(imgbtnMuletas);

        Pixmap pixmapAzul = new Pixmap(pantAncho/2, pantAlto/4, Pixmap.Format.RGBA8888);
        pixmapAzul.setColor( 0f,0f, 0f, .6f );
        pixmapAzul.fillRectangle(0,0,pantAncho,pantAlto);
        trdOscura = new TextureRegionDrawable(new TextureRegion(new Texture(pixmapAzul)));
        pixmapAzul.dispose();
        imgbtnTexto = new ImageButton(trdOscura);
        imgbtnTexto.setPosition(360,610);
        pantalla.escena.addActor(imgbtnTexto);
        imgbtnTexto.setVisible(false);
    }
}
