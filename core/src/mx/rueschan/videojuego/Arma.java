package mx.rueschan.videojuego;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by OddFellows on 17/02/2017.
 */

public class Arma extends Objeto {

    private float dano;
    private String special; // HABILIDAD ESPECIAL DEL ARMA (ROMPER, CORTAR, ETC)
    private Tipo tipoArma;
    private Texture textura;

    public Arma( float x, float y, Tipo tipo) {
        setTipoArma(tipo);
        crearTipo();
        sprite.setColor(1,1,1,0);
    }

    public float getDano() {
        return dano;
    }

    public String getSpecial() {
        return special;
    }

    public void setTipoArma(Tipo tipoArma) {
        this.tipoArma = tipoArma;
    }

    public Tipo getTipo(){
        return tipoArma;
    }

    public enum Tipo {
        MARTILLO,
        HACHA,
        MACHETE,
        ANTORCHA,
        LACOSADELOSPICOS,
        BAT
    }

    private void crearTipo() {
//        switch (tipoArma) {
//            case MARTILLO:
//                dano = 15;
//                special = "romper";
//                textura = new Texture("Items/martillo.png");
//                sprite = new Sprite(textura);
//                break;
//        }
        //Tipo
        if (tipoArma==Tipo.MARTILLO){
            dano = 15;
            special = "romper";
            textura = new Texture("Items/martillo.png");
            sprite = new Sprite(textura);
        }
    }
}
