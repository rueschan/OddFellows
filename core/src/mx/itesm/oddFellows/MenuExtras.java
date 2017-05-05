package mx.itesm.oddFellows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
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


    public MenuExtras(OddFellows oddFellows) {
        this.oddFellows = oddFellows;
        manager = this.oddFellows.getAssetManager();
        pantalla = Pantalla.getInstanciaPantalla();
    }

    @Override
    public void show() {
        // Cuando cargan la pantalla
        cargarTexturas();
        crearObjetos();
        oddFellows.tocarMusica();
    }

    private void cargarTexturas() {
        /*texturaFondo = new Texture("Pantalla/Fondo/fondoExtras.png");
        texturaExit = new Texture("Pantalla/btnExit.png");*/
        texturaFondo = manager.get("Pantalla/Fondo/fondoExtras.png");
        texturaExit = manager.get("Pantalla/btnExit.png");
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
}
