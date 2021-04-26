package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.view.View;

public class PlayerController {

    private PlayerState playerState = PlayerState.IN_WAIT;
    private View view;

    //TODO: TROVARE IL MODO DI COLLEGARE LA VIEW AL CONTROLLER

    public PlayerController(View view) {
        this.view = view;
    }
    //dal secondo turno quindi quando il gioco sarà in IN_GAME state
    //il giocatore può fare le sue solite azioni:
    //

    public void receiveMessage(Message msg){

        switch (playerState){
            case IN_WAIT:
                //se il messaggio ricevuto è di LOGIN REQUEST allora invia i tuoi login data
                if(msg.getMessageType() == Content.LOGIN_REQUEST) {

                }
        }
    }






    //il primo turno della partita di un giocatore fa il setup del gioco
    //quindi in base all'ordine dei player, il client sceglierà una risorsa o più,
    // si muove sul faith track
    //scarta leadercard


}
