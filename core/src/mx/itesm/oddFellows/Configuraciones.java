package mx.itesm.oddFellows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Odd Fellows on 25/03/2017.
 */

/* SE PODRÁ USAR PARA MOSTRAR LOS COLECCIONABLES */

public class Configuraciones {
    static boolean isMusicOn = true;
    static boolean isFxOn = true;
    static String easterCreditos ="";
    static boolean juegoFinalizado = false;
    static Integer contadorMedkit = 0;
    static Integer contadorJabali = 0;
    static Integer contadorMuertes = 0;
    static Integer contadorMuertesDoradas = 0;

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

    static void agregarEasterCreditos(String agregar){
        Preferences preferences = Gdx.app.getPreferences("easterCreditos");
       easterCreditos += agregar;
        preferences.putString("easterCreditos",easterCreditos);
        preferences.flush();
    }

    static String obtenerEasterCreditos(){
        Preferences preferences = Gdx.app.getPreferences("easterCreditos");
        easterCreditos = preferences.getString("easterCreditos");
       return easterCreditos;
    }

    static void borrarEasterCreditos(){
        Preferences preferences = Gdx.app.getPreferences("easterCreditos");
        preferences.putString("easterCreditos","");
        preferences.flush();
    }

    static void abrirUltimaPuerta(){
        Preferences preferences = Gdx.app.getPreferences("juegoFinaizado");
        preferences.putBoolean("juegoFinaizado", true);
        preferences.flush();
    }

    static boolean ultimaPuertaAbierta() {
        Preferences preferences = Gdx.app.getPreferences("juegoFinaizado");
        juegoFinalizado = preferences.getBoolean("juegoFinaizado", true);
        return juegoFinalizado;
    }

    static void agregarContadorMedkit(){
        Preferences preferences = Gdx.app.getPreferences("contadorMedkit");
        contadorMedkit++;
        preferences.putInteger("contadorMedkit",contadorMedkit);
        preferences.flush();
    }
    static void agregarContadorJabali(){
        Preferences preferences = Gdx.app.getPreferences("contadorJabali");
        contadorJabali++;
        preferences.putInteger("contadorJabali",contadorJabali);
        preferences.flush();
    }
    static void agregarContadorMuertes(){
        Preferences preferences = Gdx.app.getPreferences("contadorMuertes");
        contadorMuertes++;
        preferences.putInteger("contadorMuertes",contadorMuertes);
        preferences.flush();
    }
    static void borrarContadorMuertesDoradas(){
        Preferences preferences = Gdx.app.getPreferences("contadorMuertesDoradas");
        contadorMuertesDoradas=0;
        preferences.putInteger("contadorMuertesDoradas",contadorMuertesDoradas);
        preferences.flush();
    }
    static void agregarContadorMuertesDoradas(){
        Preferences preferences = Gdx.app.getPreferences("contadorMuertesDoradas");
        contadorMuertesDoradas++;
        preferences.putInteger("contadorMuertesDoradas",contadorMuertesDoradas);
        preferences.flush();
    }

    static void reset() {
        Preferences preferences = Gdx.app.getPreferences("sonido");
        preferences.putBoolean("musica",true);
        preferences.putBoolean("efectos",true);
        preferences.flush();
        borrarContadorMuertesDoradas();
        preferences = Gdx.app.getPreferences("contadorJabali");
        preferences.putInteger("contadorJabali", 0);
        preferences.flush();
        preferences = Gdx.app.getPreferences("contadorMuertes");
        preferences.putInteger("contadorMuertes", 0);
        preferences.flush();
        preferences = Gdx.app.getPreferences("contadorMedkit");
        preferences.putInteger("contadorMedkit", 0);
        preferences.flush();
        preferences = Gdx.app.getPreferences("juegoFinaizado");
        preferences.putBoolean("juegoFinaizado", false);
        preferences.flush();
        borrarEasterCreditos();

    }
}
