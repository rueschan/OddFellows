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
            ////        |< TAMAÑO MAXIMO DE LA CARTA >|
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
        ////            |< TAMAÑO MAXIMO DE LA CARTA >|
            case 1:
                // CARTA DE DIVORCIO
                return "Catrusa, Romain \n" +
                        "November 3th, 1972\n" +
                        "\n" +
                        "Mr. Antonescu:\n" +
                        "Henric has been behaving\n" +
                        "stranger and stranger. Please\n" +
                        "arrange our divorce documents\n" +
                        "as soon as possible. I am\n" +
                        "afraid!\n" +
                        "\n" +
                        "Please answer to my parents\n" +
                        "address. I don’t want Henric\n" +
                        "to get angrier.\n" +
                        "\n" +
                        "Thank you for your services.\n" +
                        "I trust you.\n" +
                        "Mrs. Anna Blaga\n";
        ////            |< TAMAÑO MAXIMO DE LA CARTA >|
            case 2:
                // MEDICINA PERDIDA
                return "Catrusa, Romain \n" +
                        "September 26th, 1972\n" +
                        "\n" +
                        "Dr. Comănesco:\n" +
                        "I never got the medicines you\n" +
                        "send me with Henric. Maybe he\n" +
                        "took them for the clinic.\n" +
                        "That’s selfish from him.\n" +
                        "\n" +
                        "Anyway, thank you for your\n" +
                        "help! I’m feeling better.\n" +
                        "Mrs. Anna Blaga\n";
        ////            |< TAMAÑO MAXIMO DE LA CARTA >|
            case 3:
                // REPORTE POLICIA
                return "Catrusa Police Department\n" +
                        "\n" +
                        "Officer: Gabor Dobrescu\n" +
                        "Date: October 15th, 1972\n" +
                        "Case: 31032321\n" +
                        "\n" +
                        "Description: Missing people\n" +
                        "near old clinic. Visitors of\n" +
                        "the local graveyard report\n" +
                        "weird noises coming from the\n" +
                        "forest and recent activity in\n" +
                        "the abandoned clinic. I suspect\n" +
                        "it is due to the increase in\n" +
                        "the population of wild animals\n" +
                        "in the area.\n";
            case 4:
                // LISTA
            case 11:
                // IMAGEN DE HENRIC Y SU ESPOSA (CON NOMBRES)
        }

        return "";
    }

    public String getTexto() {
        return contenido;
    }
}
