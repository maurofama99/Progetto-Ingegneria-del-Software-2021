package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class AskAction extends Message {
    String question;

    public AskAction() {
        super("server", "client", Content.ASK_ACTION);
        this.question = "It's your turn!\nWhat do you wanna do now? (Type LEADER, MARKET, PRODUCTION, BUY)";
    }

    public String getQuestion() {
        return question;
    }
}
