package mx.rueschan.videojuego;

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
        this.contenido = elegigContenido(idContenido);
    }

    private String elegigContenido(int idContenido) {
        switch (idContenido) {
            case 1:
                // CARTA DE INTRODUCCIÓN (CABAÑA)
                return "Dear Idlen:\n" +
                        "The forest you recommended us is wonderful! The perfect camping site. " +
                        "Quiet. Lonely. Peaceful. We are very thankful.\n" +
                        "Every time Viktor has an idea, he searches in his backpack for the " +
                        "correct tool to achieve his goals. He is so exited!\n" +
                        "We have just scavenged for materials and useful items to build our camp." +
                        " I hope this romantic travel goes well.\n" +
                        "\n" +
                        "See you soon! We are coming!\n" +
                        "With love, your sister.\n";
        }

        return "";
    }
}
