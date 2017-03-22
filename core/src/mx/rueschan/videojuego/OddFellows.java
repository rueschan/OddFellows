package mx.rueschan.videojuego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OddFellows extends Game {
	SpriteBatch batch;
	Texture img;
	private Music musicaFondo;

	@Override
	public void create () {
        musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("Musica/chopinNocturne.mp3"));
        musicaFondo.setLooping(true);
        setScreen(new MenuPrincipal(this));
	}
    public Music getMusicaFondo(){
        return musicaFondo;
    }
}
