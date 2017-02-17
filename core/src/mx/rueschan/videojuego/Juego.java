package mx.rueschan.videojuego;

import java.io.File;

/**
 * Created by OddFellows on 14/02/2017.
 */

public class Juego {

    private OddFellows oddFellows;

    // Lista de niveles
    private Nivel cabana;
    private Nivel bosque;
    private Nivel[] niveles = {cabana, bosque};

    // Memoria del juego
    private File memoria;

    // Instancia de juego
    private static Juego instancia = null;

    public static Juego getJuego(OddFellows oddFellows) {
        if (instancia == null) {
            instancia = new Juego(oddFellows);
        }
        return instancia;
    }

    public Juego(OddFellows oddFellows) {
        this.oddFellows = oddFellows;
        // iniciarJuego();
    }

    public void iniciarJuego() {
        oddFellows.setScreen(new NivelCabana(oddFellows));
    }

    private void iniciarNivel() {
        // Se crea el siguiente nivel con su respectiva clase
    }

    public void guardarStatus() {
        // Guardar parametros de nivel en memoria
    }
}
