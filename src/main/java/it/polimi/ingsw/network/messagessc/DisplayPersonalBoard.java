package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class DisplayPersonalBoard extends Message {

    private PersonalBoard personalBoard;

    public DisplayPersonalBoard(PersonalBoard personalBoard) {
        super("server", "client", Content.DISPLAY_PERSONALBOARD);
        this.personalBoard = personalBoard;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
