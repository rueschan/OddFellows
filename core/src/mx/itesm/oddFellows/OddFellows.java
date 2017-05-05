package mx.itesm.oddFellows;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class OddFellows extends Game {
    SpriteBatch batch;
    Texture img;
    private Music musicaFondo;
    private final String pathMusicaFondo = "Musica/chopinNocturne.mp3";
    private AssetManager assetManager;

    public OddFellows(){
        assetManager = new AssetManager();
    }

    @Override
    public void create () {
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        //crearMusica();
        Configuraciones.cargarEstatusSonido();
        setScreen(new MenuSplash(this));
        //setScreen(new MenuFinal(this,Niveles.MENU_PRINCIPAL));
    }
    public void crearMusica() {
        musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("Musica/chopinNocturne.mp3"));
        // Carga música en Asset Manager
//        assetManager.load(pathMusicaFondo, Music.class);
//        musicaFondo = assetManager.get(pathMusicaFondo); // AGREGADO
        musicaFondo.setLooping(true);
    }
    public void crearMusica(Music musicaFondo) {
        this.musicaFondo = musicaFondo;
        this.musicaFondo.setLooping(true);
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

    public AssetManager getAssetManager() { return assetManager;}
}
