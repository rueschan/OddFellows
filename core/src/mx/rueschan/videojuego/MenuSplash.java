package mx.rueschan.videojuego;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Odd Fellows on 27/03/2017.
 */

public class MenuSplash extends Pantalla {
    private float tiempoVisible = 2.2f;
    private OddFellows oddFellows;

    // Logo del tec
    private Texture texturaLogo;
    private Sprite spriteLogo;

    public MenuSplash(OddFellows oddFellows) {
        super();
        this.oddFellows = oddFellows;
    }

    @Override
    public void show() {
        texturaLogo = new Texture(Gdx.files.internal("logoTec.png"));
        spriteLogo = new Sprite(texturaLogo);
        spriteLogo.setPosition(super.getANCHO()/2-spriteLogo.getWidth()/2, super.getALTO()/2-spriteLogo.getHeight()/2);
        escalarLogo();
    }

    private void escalarLogo() {
        float factorCamara = super.getANCHO() / super.getALTO();
        float factorPantalla = 1.0f*Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float escala = factorCamara / factorPantalla;
        spriteLogo.setScale(escala, 1);
    }

    @Override
    public void render(float delta) {

        // Dibujar
        borrarPantalla();

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        // Dibuja el logo centrado
        spriteLogo.draw(batch);
        batch.end();

        // Actualizar para cambiar pantalla
        tiempoVisible -= delta;
        if (tiempoVisible<=0) {
            oddFellows.setScreen(new PantallaCargando(oddFellows,Niveles.MENU_PRINCIPAL));
        }
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
        escalarLogo();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texturaLogo.dispose();
    }
}
