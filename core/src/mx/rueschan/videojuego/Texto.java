package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by OddFellows on 15/02/2017.
 */
public class Texto {

    private BitmapFont font;
    private GlyphLayout glyph;
    private float anchoTexto;
    private float altoTexto;

    public Texto() {
        font = new BitmapFont(Gdx.files.internal("letra.fnt"));
        glyph = new GlyphLayout();
    }

    public void mostrarMensajes(SpriteBatch batch, Color color, String mensaje, float x, float y) {
        glyph.reset();
        font.setColor(color);
        glyph.setText(font, mensaje);
        anchoTexto = glyph.width;
        altoTexto = glyph.height;
        font.draw(batch, glyph, x-anchoTexto/2, y);
    }

    public float getAnchoTexto() {
        return anchoTexto;
    }

    public float getAltoTexto() {
        return altoTexto;
    }
}
