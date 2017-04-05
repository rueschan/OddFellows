package mx.rueschan.videojuego;

/**
 * Created by OddFellows on 04/04/2017.
 */

public class Enemigo extends Objeto {

    float poderAtaque = 50;

    //Método que se acercará al personaje más no indicará como atacar
    private void Perseguir(Personaje jugador) {

        //Posiciones del enemigo antes de hacer el movimiento
        float enemigoPosicionX = this.sprite.getX();
        float enemigoPosicionY = this.sprite.getY();

        //Posiciones del jugador antes de hacer el movimiento
        float jugadorPosicionX = jugador.sprite.getX();
        float jugadorPosicionY = jugador.sprite.getY();

        //Tamaño del jugador
        float jugadorAncho = jugador.sprite.getWidth();
        float jugadorAlto = jugador.sprite.getHeight();


//        //Checa primero a la posición X
//        if (enemigoPosicionX > jugadorPosicionX) {
//            enemigoPosicionX--;
//        } else if (enemigoPosicsionX < jugadorPosicionX) {
//            enemigoPosicionX++;
//        }
//
//        //Despúes checa a la posición en Y
//        if (enemigoPosicionY > jugadorPosicionY) {
//            enemigoPosicionY--;
//        } else if (enemigoPosicionY < jugadorPosicionY) {
//            enemigoPosicionY++;
//        }

        //Filtro en caso de que esten en la misma posición
        if (enemigoPosicionX==jugadorPosicionX  &&  enemigoPosicionY==jugadorPosicionY){

        }

        //En el caso en que el jugador se encuentre a la derecha y arriba del enemigo
        else if (enemigoPosicionX<=jugadorPosicionX  &&  enemigoPosicionY<=jugadorPosicionY){
            //Aquí va como moverlo con otro método jejeje

            //
        }

        //En el caso en que el jugador se encuentre a la derecha y abajo del enemigo
        else if (enemigoPosicionX<=jugadorPosicionX  &&  enemigoPosicionY>=jugadorPosicionY){
            //Aquí va como moverlo con otro método jejeje
        }

        //En el caso en que el jugador se encuentre a la izquierda y arriba del enemigo
        else if (enemigoPosicionX>=jugadorPosicionX  &&  enemigoPosicionY<=jugadorPosicionY){
            //Aquí va como moverlo con otro método jejeje
        }

        //En el caso en que el jugador se encuentre a la izquierda y abajo del enemigo
        else if (enemigoPosicionX>=jugadorPosicionX  &&  enemigoPosicionY>=jugadorPosicionY){
            //Aquí va como moverlo con otro método jejeje
        }

    }

    //Método que determinará si se hará un ataque al personaje y realizará el daño correspondiente
    private void Atacar(Personaje jugador, float multiplicadorDano){
        //Vida del jugador
        float vidaJugador = jugador.getVida();
        //El daño que causará será el daño fijo que hará cada enemigo por un multiplicador dado por el nivel
        float danoAJugador = poderAtaque*multiplicadorDano;

        //Disminuir la vida del jugador por la cantidad de daño que puede generar el enemigo
        jugador.setVida(vidaJugador - danoAJugador);

        //En caso de que la vida llegue a ser cero o menor a cero

    }

}
