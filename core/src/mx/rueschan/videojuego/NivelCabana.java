package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by OddFellows on 14/02/2017.
 */
public class NivelCabana extends Nivel {

    private Nivel actual;

    //Textura imagenes
    protected Texture texturaLlave;
    protected Texture texturaMartillo;
    protected Texture texturaCarta;


    //Ayuda a dibujar
   // private SpriteBatch batch;


    public NivelCabana(OddFellows oddFellows) {
        super.oddFellows = oddFellows;
        super.pantalla = Pantalla.getInstanciaPantalla();
        actual = this;
    }

    @Override
    protected void cargarTexturas() {
        texturaFondo = new Texture("fondoCabana.jpg");
        texturaBotonPausa = new Texture("BotonPausa64.png");
        /*texturaLlave = new Texture("LlaveIcono.png");
        texturaMartillo = new Texture("Martillo.png");
        texturaCarta = new Texture("HojaCreditos64.png");*/
    }

    @Override
    protected void crearObjetos() {

        //batch = new SpriteBatch();

        // Limpia escena de pantalla anterior
        pantalla.escena.clear();
        // Agrega la imagen de fondo
        Image imgFondo = new Image(texturaFondo);
        pantalla.escena.addActor(imgFondo);

        //// Asignar textura al boton de pausa
        TextureRegionDrawable trdBtnPausa = new
                TextureRegionDrawable(new TextureRegion(texturaBotonPausa));


        // Colocar boton de pausa
        ImageButton btnPausa = new ImageButton(trdBtnPausa);
        btnPausa.setPosition(1186,706);
        pantalla.escena.addActor(btnPausa);

        // Acciones de botones
        // Botón opciones
        btnPausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "***Cambio a pausa***");
                oddFellows.setScreen(new MenuPausa(oddFellows, actual));
            }
        });

        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
        cargarJuego();
    }

    @Override
    public void render(float delta) {
        super.pantalla.borrarPantalla();
        super.pantalla.escena.draw();

        // Detectar botón físico "return"
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            //Abrir MenuPausa
            oddFellows.setScreen(new MenuPausa(oddFellows,actual));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
        texturaBotonPausa.dispose();
        texturaLlave.dispose();
        texturaMartillo.dispose();
        texturaCarta.dispose();
    }
}
