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
    private boolean isMusicOn = true;
    private boolean isFxOn = true;

	@Override
	public void create () {
        crearMusica();
        setScreen(new MenuPrincipal(this));
	}
    public void crearMusica() {
        musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("Musica/chopinNocturne.mp3"));
        musicaFondo.setLooping(true);
    }
    public void cambiaMusica(){
        isMusicOn = !isMusicOn;
    }
    public void cambiaFx() {
        isFxOn = !isFxOn;
    }
    public void tocarMusica() {
        if (isMusicOn)
            musicaFondo.play();
        else
            musicaFondo.pause();
    }
    public void pararMusica() { musicaFondo.stop();}

    public void eliminarMusica () { musicaFondo.dispose();}

    public boolean isMusicOn() { return isMusicOn; }

    public boolean isFxOn() {
        return isFxOn;
    }

    public Music getMusicaFondo(){
        return musicaFondo;
    }





}
