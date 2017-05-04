package mx.itesm.oddFellows;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by OddFellows on 15/02/2017.
 */

public class Objeto
{
    protected Sprite sprite;// Imagen
    private String nombre;
    private Texture textura;


    public Objeto(float x, float y, Texture textura) {
        sprite = new Sprite(textura);
        sprite.setPosition(x, y);
    }

    public Objeto() {
    }

    public void dibujar(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}