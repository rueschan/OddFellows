package mx.rueschan.videojuego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OddFellows extends Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
        setScreen(new MenuPrincipal(this));
	}
}
