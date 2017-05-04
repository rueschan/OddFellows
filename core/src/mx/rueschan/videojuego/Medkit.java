package mx.rueschan.videojuego;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Odd Fellows on 30/04/2017.
 */

public class Medkit extends Objeto {

    private final int VIDA = 30;
    private Texture textura;

    public Medkit() {
        textura = new Texture("Items/medkit.png");
        sprite = new Sprite(textura);
    }
    public int getVIDA() {
        return VIDA;
    }
}
