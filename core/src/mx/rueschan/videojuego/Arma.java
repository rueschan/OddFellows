package mx.rueschan.videojuego;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by OddFellows on 17/02/2017.
 */

public class Arma extends Objeto {

    private float dano;
    private String nombre;
    private String special; // HABILIDAD ESPECIAL DEL ARMA (ROMPER, CORTAR, ETC)
    private Tipo tipo;

    public Arma(Texture textura, float x, float y, float dano, String nombre, String special) {
        super(textura, x, y);
        this.dano = dano;
        this.nombre = nombre;
        this.special = special;
        sprite.setColor(1,1,1,0);
    }

    public String getNombre() {
        return nombre;
    }

    public float getDano() {
        return dano;
    }

    public String getSpecial() {
        return special;
    }

    public enum Tipo {
        MARTILLO,
        HACHA
    }

}
