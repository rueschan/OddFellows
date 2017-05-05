package mx.itesm.oddFellows;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

/**
 * Created by Odd Fellows on 25/03/2017.
 */

public class Llave extends Objeto {
    Texture texturaLlave;
    int idPuerta;
    //Lista de llaves
    public static ArrayList<Integer> idLlave;

    public Llave(float x, float y, int idPuerta) {
        texturaLlave = new Texture("Items/llave.png");
        sprite = new Sprite(texturaLlave);
        sprite.setPosition(x, y);
        sprite.setColor(1,1,1,0);
        this.idPuerta = idPuerta;
    }

    public int getIdPuerta() {
        return idPuerta;
    }

    public static void llenarIDLlave(){
        idLlave = new ArrayList<Integer>();
        for (int idLlaveCont = 1; idLlaveCont < 5; idLlaveCont++) {
            idLlave.add(idLlaveCont);
        }
        System.out.println(Llave.idLlave.toString());
    }
}
