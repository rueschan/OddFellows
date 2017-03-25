package mx.rueschan.videojuego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OddFellows extends Game {
	SpriteBatch batch;
	Texture img;
	private Music musicaFondo;
    private boolean isMusicOn;
    private boolean isFxOn;

	@Override
	public void create () {
        cargarEstatusSonido();
        crearMusica();
        setScreen(new MenuPrincipal(this));
	}
    public void crearMusica() {
        musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("Musica/chopinNocturne.mp3"));
        musicaFondo.setLooping(true);
    }
    private void cargarEstatusSonido(){
        Preferences preferences = Gdx.app.getPreferences("musica");
        isMusicOn = preferences.getBoolean("musica");
        preferences = Gdx.app.getPreferences("efectos");
        isFxOn = preferences.getBoolean("efectos");
    }

    public void cambiaMusica(){
        isMusicOn = !isMusicOn;
        guardarEstatusMusica();
    }
    public void cambiaFx() {
        isFxOn = !isFxOn;
        guardarEstatusEfectos();
    }
    private void guardarEstatusMusica(){
        Preferences preferences = Gdx.app.getPreferences("musica");
        preferences.putBoolean("musica",isMusicOn);
        preferences.flush();
    }
    private void guardarEstatusEfectos(){
        Preferences preferences = Gdx.app.getPreferences("efectos");
        preferences.putBoolean("efectos",isFxOn);
        preferences.flush();
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
