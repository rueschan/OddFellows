package mx.rueschan.videojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Rubén Escalante on 26/03/2017.
 */

public class Carta extends Objeto {
    private int idContenido;
    private String contenido;
    private Texture texturaCarta = new Texture("Items/carta.png");

    public Carta(float x, float y, int idContenido) {
        super();
        sprite = new Sprite(texturaCarta);
        sprite.setPosition(x, y);
        this.idContenido = idContenido;
        this.contenido = elegigContenido();
    }

    private String elegigContenido() {
        switch (idContenido) {
        ////            |< TAMAÑO MAXIMO DE LA CARTA >|
            case 1:
                // CARTA DE INTRODUCCIÓN (CABAÑA)
                return "Dear Idlen:\n" +
                        "The forest you recommended us\n" +
                        " is wonderful! The perfect\n" +
                        " camping site. Quiet. Lonely.\n" +
                        " Peaceful. We are very thankful.\n" +
                        "Every time Viktor has an idea,\n" +
                        " he searches in his backpack\n " +
                        "for the correct tool to achieve\n" +
                        " his goals. He is so exited!\n\n" +
                        "We have just scavenged for\n " +
                        "materials and useful items to\n " +
                        "build our camp. I hope this\n " +
                        "romantic travel goes well.\n\n" +
                        "\n" +
                        "See you soon! We are coming!\n" +
                        "With love, your sister. XOXOX\n";
        }

        return "";
    }

    public String getTexto() {
        return contenido;
    }
}
