package mx.rueschan.videojuego;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Rubén Escalante on 26/03/2017.
 */

public class Carta extends Objeto {
    private int idContenido;
    private String contenido;
    private Texture texturaCarta = new Texture("Items/carta.png");

    public Carta(float x, float y, int idContenido) {
        super();
        sprite = new Sprite(texturaCarta);
        sprite.setPosition(x, y);
        this.idContenido = idContenido;
        this.contenido = elegigContenido(idContenido);
    }

    private String elegigContenido(int idContenido) {
        switch (idContenido) {
            case 1:
                // CARTA DE INTRODUCCIÓN (CABAÑA)
                return "";
        }

        return "";
    }
}
