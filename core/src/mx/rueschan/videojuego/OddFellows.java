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

    @Override
    public void create () {
        crearMusica();
        Configuraciones.cargarEstatusSonido();
        setScreen(new MenuSplash(this));
    }
    public void crearMusica() {
        musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("Musica/chopinNocturne.mp3"));
        musicaFondo.setLooping(true);
    }

    public void tocarMusica() {
        if (Configuraciones.isMusicOn)
            musicaFondo.play();
        else
            musicaFondo.pause();
    }
    public void pararMusica() { musicaFondo.stop();}

    public void eliminarMusica () { musicaFondo.dispose();}

    public Music getMusicaFondo(){
        return musicaFondo;
    }
}
