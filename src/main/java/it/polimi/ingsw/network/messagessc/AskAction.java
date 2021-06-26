package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Server asks the player what action he wants to do at the start of his turn
 */
public class AskAction extends Message {
    String question;

    /**
     * Constructor of the message
     */
    public AskAction() {
        super("server", "client", Content.ASK_ACTION);
        this.question = "It's your turn!\nWhat do you wanna do now? (Type MARKET, PRODUCTION, BUY)";
    }

    public String getQuestion() {
        return question;
    }
}
