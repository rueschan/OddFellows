package mx.rueschan.videojuego;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

/**
 * Created by Rubén Escalante on 26/03/2017.
 */

public class Carta extends Objeto {

    public static ArrayList<Integer> idCartas;
    private int idContenido;
    private String contenido;
    private Texture texturaCarta = new Texture("Items/carta.png");

    public static void llenarIdCartas() {
        idCartas = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++) {
            idCartas.add(i);
        }
    }

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
            case 0:
                // CARTA DE INTRODUCCIÓN (CABAÑA)
                return "Dear Idlen:\n" +
                        "The forest you recommended us\n" +
                        " is wonderful! The perfect\n" +
                        " camping site. Quiet. Lonely.\n" +
                        " Peaceful. We are very thankful.\n" +
                        "Every time Viktor has an idea,\n" +
                        " he searches in his backpack\n " +
                        "for the correct tool to achieve\n" +
                        " his goals. He is so excited!\n\n" +
                        "We have just scavenged for\n " +
                        "materials and useful items to\n " +
                        "build our camp. I hope this\n " +
                        "romantic travel goes well.\n\n" +
                        "\n" +
                        "See you soon! We are coming!\n" +
                        "With love, your sister. XOXOX\n";
            case 2:
                // CARTA DE DIVORCIO
                return "\tCătrușa, Romain \n" +
                        "\tNovember 3th, 1972\n" +
                        "Mr. Antonescu:\n" +
                        "Henric has been behaving stranger\n" +
                        "and stranger. Please arrange\n" +
                        "our divorce documents as soon as\n" +
                        "possible. I am afraid!\n" +
                        "\n" +
                        "Please answer to my parents address.\n" +
                        "I don’t want Henric to get angrier.\n" +
                        "\n" +
                        "Thank you for your services.\n" +
                        "I trust you.\n" +
                        "Mrs. Anna Blaga\n";
        }

        return "";
    }

    public String getTexto() {
        return contenido;
    }
}
