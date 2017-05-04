package mx.itesm.oddFellows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Odd Fellows on 14/02/2017.
 */

public class MenuCreditos implements Screen {

    private OddFellows oddFellows;
    private Pantalla pantalla;
    private final AssetManager manager;

    private Texture texturaFondo;
    private Texture texturaExit;

    private TextureRegionDrawable trdOscura;
    private ImageButton imgbtnJonathan;
    private ImageButton imgbtnRuben;
    private ImageButton imgbtnAlejandro;
    private ImageButton imgbtnAngel;

    public MenuCreditos(OddFellows oddFellows) {
        this.oddFellows = oddFellows;
        manager = this.oddFellows.getAssetManager();
        // Obtener pantalla
        pantalla = Pantalla.getInstanciaPantalla();
    }

    @Override
    public void show() {
        // Cuando cargan la pantalla
        cargarTexturas();
        crearObjetos();
        crearRegionEaster();
        oddFellows.tocarMusica();
    }

    private void cargarTexturas() {
        texturaFondo = manager.get("Pantalla/Fondo/fondoCreditos.png");
        texturaExit = manager.get("Pantalla/btnExit.png");
    }

    private void crearObjetos() {
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
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        pantalla.borrarPantalla();
        pantalla.escena.draw();

        //escribirEnPantalla();
        // Detectar botón físico "return"
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            //Regresar al MenuPrincipal
            oddFellows.setScreen(new MenuPrincipal(oddFellows));
        }
    }
    private void escribirEnPantalla() {
        pantalla.batch.begin();
        pantalla.batch.end();
    }

    public void crearRegionEaster(){
        Configuraciones.borrarEasterCreditos();
        int pantAlto = (int)(pantalla.getALTO());
        int pantAncho = (int)(pantalla.getANCHO());
        Pixmap pixmapVerde = new Pixmap(pantAncho/8, pantAlto/5, Pixmap.Format.RGBA8888);
        pixmapVerde.setColor( 0f, 0f, 0f, 0f );
        pixmapVerde.fillRectangle(0,0,pantAncho,pantAlto);
        trdOscura = new TextureRegionDrawable(new TextureRegion(new Texture(pixmapVerde)));
        pixmapVerde.dispose();
        //Jonathan
        imgbtnJonathan = new ImageButton(trdOscura);
        imgbtnJonathan.setPosition(190,380);

        //Ruben
        imgbtnRuben = new ImageButton(trdOscura);
        imgbtnRuben.setPosition(970,220);

        //Alejandro
        imgbtnAlejandro = new ImageButton(trdOscura);
        imgbtnAlejandro.setPosition(445,240);

        //Angel
        imgbtnAngel = new ImageButton(trdOscura);
        imgbtnAngel.setPosition(705,345);

        imgbtnJonathan.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Jonathan***");
                Configuraciones.agregarEasterCreditos("M");
                Gdx.app.log("Sumado", "***Palabra Secreta ahora:"+Configuraciones.obtenerEasterCreditos()+"***");
            }
        });
        imgbtnRuben.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Ruben***");
                Configuraciones.agregarEasterCreditos("Z");
                Gdx.app.log("Sumado", "***Palabra Secreta ahora:"+Configuraciones.obtenerEasterCreditos()+"***");
            }
        });
        imgbtnAlejandro.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Alejandro***");
                Configuraciones.agregarEasterCreditos("O");
                Gdx.app.log("Sumado", "***Palabra Secreta ahora:"+Configuraciones.obtenerEasterCreditos()+"***");
            }
        });
        imgbtnAngel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Angel***");
                Configuraciones.agregarEasterCreditos("A");
                Gdx.app.log("Sumado", "***Palabra Secreta ahora:"+Configuraciones.obtenerEasterCreditos()+"***");
            }
        });

        pantalla.escena.addActor(imgbtnJonathan);
        pantalla.escena.addActor(imgbtnRuben);
        pantalla.escena.addActor(imgbtnAlejandro);
        pantalla.escena.addActor(imgbtnAngel);
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
        crearRegionEaster();
        oddFellows.tocarMusica();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texturaExit.dispose();
        texturaFondo.dispose();
    }
}
