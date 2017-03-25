package mx.rueschan.videojuego;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Rubén Escalante on 25/03/2017.
 */

public class Llave extends Objeto {
    Texture texturaLlave;
    int idPuerta;

    public Llave(float x, float y, int idPuerta) {
        super();
        texturaLlave = new Texture("Items/Llave.png");
        sprite = new Sprite(texturaLlave);
        sprite.setPosition(x, y);
        sprite.setColor(1,1,1,0);
        this.idPuerta = idPuerta;
    }

    public int getIdPuerta() {
        return idPuerta;
    }
}