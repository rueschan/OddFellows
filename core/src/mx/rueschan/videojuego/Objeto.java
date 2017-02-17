package mx.rueschan.videojuego;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by OddFellows on 15/02/2017.
 */

public class Objeto {
    protected Sprite sprite;

    public Objeto(Texture textura, float x, float y){
        sprite = new Sprite(textura);
        sprite.setPosition(x,y);
    }

    public void dibujar(SpriteBatch batch){
        sprite.draw(batch);
    }
}
