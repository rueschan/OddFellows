package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Odd Fellows on 25/03/2017.
 */

/* SE PODRÁ USAR PARA MOSTRAR LOS COLECCIONABLES */

public class Configuraciones {
    static boolean isMusicOn = true;
    static boolean isFxOn = true;

    static void cargarEstatusSonido(){
        Preferences preferences = Gdx.app.getPreferences("sonido");
        isMusicOn = preferences.getBoolean("musica",true);
        isFxOn = preferences.getBoolean("efectos",true);
    }

    static void cambiaMusica(){
        isMusicOn = !isMusicOn;
        guardarEstatusMusica();
    }
    static void cambiaFx() {
        isFxOn = !isFxOn;
        guardarEstatusEfectos();
    }
    private static void guardarEstatusMusica(){
        Preferences preferences = Gdx.app.getPreferences("sonido");
        preferences.putBoolean("musica",isMusicOn);
        preferences.flush();
    }
    private static void guardarEstatusEfectos(){
        Preferences preferences = Gdx.app.getPreferences("sonido");
        preferences.putBoolean("efectos",isFxOn);
        preferences.flush();
    }
}
