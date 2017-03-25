package mx.rueschan.videojuego;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by OddFellows on 17/02/2017.
 */

public class Arma extends Objeto {
    float dano;
    String special; // HABILIDAD ESPECIAL DEL ARMA (ROMPER, CORTAR, ETC)

    public Arma(Texture textura, float x, float y, float dano, String special) {
        super(textura, x, y);
        this.dano = dano;
        this.special = special;
        sprite.setColor(1,1,1,0);
    }


}
