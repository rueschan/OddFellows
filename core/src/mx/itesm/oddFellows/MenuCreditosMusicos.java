package mx.itesm.oddFellows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Odd Fellows on 04/05/2017.
 */

public class MenuCreditosMusicos implements Screen {

    private OddFellows oddFellows;
    private Pantalla pantalla;
    private final AssetManager manager;

    private Texture texturaFondo;
    private Texture texturaExit;
    private Texture texturaBtn;

    private TextureRegionDrawable trdOscura;
    private ImageButton imgbtnKevin;
    private ImageButton imgbtnDoug;
    private ImageButton imgbtnChopin;
    private ImageButton imgbtnBeethoven;
    private ImageButton imgbtnFreeSound;

    private String compositorSeleccionado;
    private Texto textoPermisos;
    private String kevin;
    private String doug;
    private String chopin;
    private String beethoven;
    private String freeSound;

    public MenuCreditosMusicos(OddFellows oddFellows) {
        this.oddFellows = oddFellows;
        manager = this.oddFellows.getAssetManager();
        // Obtener pantalla
        pantalla = Pantalla.getInstanciaPantalla();
        compositorSeleccionado ="";
    }

    @Override
    public void show() {
        // Cuando cargan la pantalla
        cargarTexturas();
        crearObjetos();
        crearRegionInteraccion();
        //crearRegionEaster();
        oddFellows.tocarMusica();
        textoPermisos = new Texto();
    }

    private void cargarTexturas() {
        texturaFondo = manager.get("Pantalla/Fondo/fondoMusicos.png");
        texturaExit = manager.get("Pantalla/btnExit.png");
        kevin = "\"Controlled Chaos - no percussion\" - Cemetery\n" +
                "\"Quinn's Song: First Night\" - Infirmary\n" +
                "\"Giant Wyrm\" -  Pause"+"\n\nLicensed under Creative Commons: By Attribution 3.0 License\n" +
                "http://creativecommons.org/licenses/by/3.0/";
        doug = "\n\n\n\"Lost in the forest\" - Forest";
        chopin = "\n\n\n\"Nocturne No. 20 in C sharp minor OP. posth.\" - Main Menus" +
        "\n\"Piano Sonata No. 2 / Funeral March\" - Ending";
        beethoven = "\n\n\"Piano Sonata No. 14 - Beethoven -\n Adagio sostenuto\" - Game Over";
        freeSound = "\n\"Ofelia’s Dream\" - Cabin"+
                "\nRoyalty Free Music from Bensound\n"+
                "\nAll SFX taken from www.freesound.com\n"+
                "Samples licensed under the Atribution License";

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
                Gdx.app.log("clicked", "***Salir a Creditos***");
                oddFellows.setScreen(new MenuCreditos(oddFellows));
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
            oddFellows.setScreen(new MenuCreditos(oddFellows));
        }
        escribirEnPantalla();
    }
    private void escribirEnPantalla() {
        pantalla.batch.begin();
        textoPermisos.mostrarMensajes(pantalla.batch, Color.WHITE,compositorSeleccionado,
                pantalla.getANCHO()/2,pantalla.getALTO()/3+80);
        pantalla.batch.end();
    }

    public void crearRegionInteraccion(){
        int pantAlto = (int)(pantalla.getALTO());
        int pantAncho = (int)(pantalla.getANCHO());
        Pixmap pixmapVerde = new Pixmap(pantAncho/8, pantAlto/3+40, Pixmap.Format.RGBA8888);
        pixmapVerde.setColor( 0, 0f, 0f, 0f );
        pixmapVerde.fillRectangle(0,0,pantAncho,pantAlto);
        trdOscura = new TextureRegionDrawable(new TextureRegion(new Texture(pixmapVerde)));
        pixmapVerde.dispose();
        //Jonathan
        imgbtnKevin = new ImageButton(trdOscura);
        imgbtnKevin.setPosition(170,365);

        //Ruben
        imgbtnDoug = new ImageButton(trdOscura);
        imgbtnDoug.setPosition(365,370);

        //Alejandro
        imgbtnChopin = new ImageButton(trdOscura);
        imgbtnChopin.setPosition(560,370);

        //Angel
        imgbtnBeethoven = new ImageButton(trdOscura);
        imgbtnBeethoven.setPosition(750,370);

        //Angel
        imgbtnFreeSound = new ImageButton(trdOscura);
        imgbtnFreeSound.setPosition(950,370);

        imgbtnKevin.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Kevin MacLeod***");
                compositorSeleccionado = kevin;

            }
        });
        imgbtnDoug.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Doug***");
                compositorSeleccionado = doug;
            }
        });
        imgbtnChopin.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Chopin***");
                compositorSeleccionado = chopin;
            }
        });
        imgbtnBeethoven.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Beethoven***");
                compositorSeleccionado = beethoven;
            }
        });
        imgbtnFreeSound.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Freesound***");
                compositorSeleccionado = freeSound;
            }
        });

        pantalla.escena.addActor(imgbtnKevin);
        pantalla.escena.addActor(imgbtnDoug);
        pantalla.escena.addActor(imgbtnChopin);
        pantalla.escena.addActor(imgbtnBeethoven);
        pantalla.escena.addActor(imgbtnFreeSound);
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
        crearRegionInteraccion();
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
