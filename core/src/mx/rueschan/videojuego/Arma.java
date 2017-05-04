package mx.rueschan.videojuego;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by OddFellows on 17/02/2017.
 */

public class Arma extends Objeto {

    private int dano;
    private String special; // HABILIDAD ESPECIAL DEL ARMA (ROMPER, CORTAR, ETC)
    private Tipo tipoArma;
    private Texture textura;

    public Arma(Tipo tipo) {
        setTipoArma(tipo);
        crearTipo();
        sprite.setColor(1,1,1,0);
    }

    public int getDano() {
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

    private void crearTipo() {
        AssetManager manager = Nivel.getManager();
        switch (tipoArma) {
            case MARTILLO:
                dano = 15;
                special = "romper";
                textura = manager.get("Items/martillo.png");
                break;
            case MACHETE:
                dano = 25;
                special = "cortar";
                textura = manager.get("Items/Machete.png");
                break;
            case TRIDENTE:
                dano = 100;
                special = "dios";   // Hace todo porque YOLO
                textura = manager.get("Items/tridente.png");
                break;
            case BATE:
                dano = 40;
                special = "desangrar";
                textura = manager.get("Items/Bate.png");
                break;
            case ANTORCHA:
                dano = 55;
                special = "quemar";
                textura = manager.get("Items/Antorcha.png");
                break;
            case BARREDOR:
                dano = 45;
                special = "campechanear";
                textura = manager.get("Items/Barredor.png");
        }
        sprite = new Sprite(textura);


        //Tipo
//        if (tipoArma==Tipo.MARTILLO){//************************J//
//            dano = 15;
//            special = "romper";
//            textura = new Texture("Items/Martillo.png");
//            sprite = new Sprite(textura);
//        }
    }

    public enum Tipo {
        MARTILLO,
        BARREDOR,
        MACHETE,
        ANTORCHA,
        BATE,
        TRIDENTE
    }
}
