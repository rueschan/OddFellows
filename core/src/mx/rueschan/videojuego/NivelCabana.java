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
    protected Texture texturaHenric;


    public NivelCabana(OddFellows oddFellows) {
        super.oddFellows = oddFellows;
        super.pantalla = Pantalla.getInstanciaPantalla();
        actual = this;
    }

    @Override
    protected void cargarTexturas() {
        texturaFondo = new Texture("fondoCabana.jpg");
        texturaBotonPausa = new Texture("BotonPausa64.png");
        texturaLlave = new Texture("LlaveIcono.png");
        texturaMartillo = new Texture("Martillo32a96.png");
        texturaCarta = new Texture("Carta32a64.png");
        texturaHenric = new Texture("Henric.png");
    }

    @Override
    protected void crearObjetos() {


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



        //SPRITES
        //Agregar temporalmente imagenes como botones
        TextureRegionDrawable trdbtnLlave = new
                TextureRegionDrawable(new TextureRegion(texturaLlave));
        TextureRegionDrawable trdbtnMartillo = new
                TextureRegionDrawable(new TextureRegion(texturaMartillo));
        TextureRegionDrawable trdbtnCarta = new
                TextureRegionDrawable(new TextureRegion(texturaCarta));
        TextureRegionDrawable trdbtnHenric = new
                TextureRegionDrawable(new TextureRegion(texturaHenric));

        //Colocar temporalmente boton sprite
        // Llave
        ImageButton btnLlave = new ImageButton(trdbtnLlave);
        btnLlave.setPosition(350,550);
        pantalla.escena.addActor(btnLlave);

        //Martillo
        ImageButton btnMartillo = new ImageButton(trdbtnMartillo);
        btnMartillo.setPosition(1000,50);
        pantalla.escena.addActor(btnMartillo);

        //Carta
        ImageButton btnCarta = new ImageButton(trdbtnCarta);
        btnCarta.setPosition(500,550);
        pantalla.escena.addActor(btnCarta);

        //Henric
        ImageButton btnHenric = new ImageButton(trdbtnHenric);
        btnHenric.setPosition(pantalla.getANCHO()/2 - btnHenric.getWidth()/2,
                pantalla.getALTO()/2 - btnHenric.getHeight()/2);
        pantalla.escena.addActor(btnHenric);


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
        texturaHenric.dispose();
    }
}
