package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.client.ServerHandler;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.view.View;


//gestisce tutti gli stati che il player può avere, comprese le mosse

public class PlayerController {

    private ServerHandler serverHandler;
    //la view verrà inizializzata CLI o GUI a seconda dei parametri passati, in questa parte di codice è invisibile la scelta
    private View view;

    public PlayerController(View view) {
        this.view = view;
    }

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    //dal secondo turno quindi quando il gioco sarà in IN_GAME state
    //il giocatore può fare le sue solite azioni:

    public void receiveMessage(Message msg){

        switch (msg.getMessageType()){
            case LOGIN_REQUEST:
                //se il messaggio ricevuto è di LOGIN REQUEST allora invia i tuoi login data
                view.fetchNickname(this); //questo metodo oltre al suo obiettivo deve anche chiamare il metodo che manda il messaggio di risposta
                break;

        }
    }

    public void sendNickname(String nickname){
        serverHandler.sendMessage(new LoginData(nickname));
    }

    //deve anche avere l'array list di player che sono nella partita
    //così quando l'active player è nello stato done ("ho finito quel che dovevo fare")
    // posso cambiare active player e scorrere array in avanti

    //
    //






    //il primo turno della partita di un giocatore fa il setup del gioco
    //quindi in base all'ordine dei player, il client sceglierà una risorsa o più,
    // si muove sul faith track
    //scarta leadercard


}
