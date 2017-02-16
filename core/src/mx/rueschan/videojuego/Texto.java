package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Rubén Escalante on 15/02/2017.
 */
public class Texto {

    private BitmapFont font;

    public Texto() {
        font = new BitmapFont(Gdx.files.internal("letra.fnt"));
    }

    public void mostrarMensajes(SpriteBatch batch, String mensaje, float x, float y) {
        GlyphLayout glyp = new GlyphLayout();
        glyp.setText(font, mensaje);
        float anchoTexto = glyp.width;
        batch.begin();
        font.draw(batch, glyp, x-anchoTexto/2, y);
        batch.end();
    }
}
