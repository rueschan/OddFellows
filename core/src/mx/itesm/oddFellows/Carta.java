package mx.itesm.oddFellows;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

/**
 * Created by Odd Fellows on 26/03/2017.
 */

public class Carta extends Objeto {

    public static ArrayList<Integer> idCartas;
    private int idContenido;
    private String contenido;
    private Texture texturaCarta = new Texture("Items/carta.png");

    public static void llenarIdCartas() {
        idCartas = new ArrayList<Integer>();
        for (int i = 2; i < 10; i++) {
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
                // MENCIONA QUE LAS LLAVES PUEDEN NO FUNCIONAR (CABAÑA)
                return "My beloved Henric:\n" +
                        "\n" +
                        "I've mixed the keys you gave\n" +
                        "me, they all look the same \n" +
                        "next to each other.\n" +
                        "\n" +
                        "Sometimes the keys seem to\n" +
                        "work properly, but not always\n" +
                        "I guess they are getting rusty...\n" +
                        "\n" +
                        "Please don't get mad!\n"+
                        "\n" +
                        "\n" +
                        "\n" +
                        "                        Yours forever,\n" +
                        "                             your wife!";
            ////            |< TAMAÑO MAXIMO DE LA CARTA >|
            case 2:
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
            case 3:
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
            case 4:
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
        ////            |< TAMAÑO MAXIMO DE LA CARTA >|
            case 5:
                // HELP ME!
                return "hElp mE!!\n" +
                        "\n" +
                        "AnyoNE WhO rEadS this!\n" +
                        "\n" +
                        "All oF mY fRIEndS haVe bEen\n" +
                        " kiDNapEd bY a MAn!\n" +
                        "\n" +
                        "I don’t KnOW WhEre tO hIde.\n" +
                        "DAngeR iS everywh...\n";
        ////            |< TAMAÑO MAXIMO DE LA CARTA >|
            case 6:
                // (Run!)
                return "\n" +
                        "\n" +
                        "Fugi! Fugi! Fugi! Fugi! Fugi!\n" +
                        "Fugi! Fugi! Fugi! Fugi! Fugi!\n" +
                        "Fugi! Fugi! Fugi! Fugi! Fugi!\n" +
                        "Fugi! Fugi! Fugi! Fugi! Fugi!\n" +
                        "Fugi! Fugi! Fugi! Fugi! Fugi!\n" +
                        "Fugi! Fugi! Fugi! Fugi! Fugi!\n" +
                        "Fugi! Fugi! Fugi! Fugi! Fugi!\n" +
                        "Fugi! Fugi! Fugi! Fugi! Fugi!\n" +
                        "Fugi! Fugi! Fugi! Fugi! Fugi!\n" +
                        "Fugi! Fugi! Fugi! Fugi! Fugi!\n" +
                        "Fugi! Fugi! Fugi! Fugi! Fugi!\n" +
                        "Fugi! Fugi! Fugi!";
        ////            |< TAMAÑO MAXIMO DE LA CARTA >|
            case 7:
                // VISIT THE GRAVEYARD
                return "Mom:\n" +
                        "Yesterday I went to visit \n" +
                        "“Tata”. His tombstone looks \n" +
                        "good. Just some minor scratches.\n" +
                        "\n" +
                        "It looks the old clinic is under \n" +
                        "restoration, or at least I think \n" +
                        "so. I saw some lights and heard \n" +
                        "activity inside. Maybe it’s \n" +
                        "convenient to go there if you \n" +
                        "need a medic.\n" +
                        "\n" +
                        "Tomorrow I’ll go to ask when \n" +
                        "the opening is.\n" +
                        "\n" +
                        "See you soon “mama”.\n" +
                        "Love you!\n";
        ////            |< TAMAÑO MAXIMO DE LA CARTA >|
            case 8:
                return "\n" +
                        "- Anatoly\n" +
                        "21/Lupus/92kg/Transplant/Fail\n" +
                        "- Elena\n" +
                        "87/Cancer/61kg/Hybrid/Fail\n" +
                        "- Josef\n" +
                        "16/Flu/76kg/Hybrid/OK\n" +
                        "- Viktor\n" +
                        "30/Wounds/84kg/Implant/Fail\n" +
                        "- Darinka\n" +
                        "26/Wounds/67kg/Implant/OK\n" +
                        "- Gabor\n" +
                        "43/ALIVE/87kg/Leg implant/OK\n" +
                        "- Anna\n" +
                        "36/ALIVE/62kg/NA/NA \n" +
                        "\n" +
                        "\n" +
                        "NA = Not Available";
        ////            |< TAMAÑO MAXIMO DE LA CARTA >|
            case 9:
                return "Romanian Medicine Association\n" +
                        "Patient: Anatoly Romanov\n" +
                        "Medic: Henric Blaga\n" +
                        "Age: 21\tWeight: 92kg\n" +
                        "\n" +
                        "Cause of dead: Lupus\n" +
                        "Observations: Lupus caused \n" +
                        "by poor hygiene on “La Fonda”. \n" +
                        "Incomplete autopsy due to \n" +
                        "difficulties managing the \n" +
                        "skin. Corpse treatment \n" +
                        "extended 4 days. Chest opening \n" +
                        "suggested for organ inspection.\n";
        }

        return "";
    }

    public String getTexto() {
        return contenido;
    }
}
