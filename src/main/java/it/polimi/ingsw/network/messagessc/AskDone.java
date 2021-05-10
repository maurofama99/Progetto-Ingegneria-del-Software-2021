package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class AskDone extends Message {
    String Question;

    public AskDone() {
        super("server", "client", Content.ASK_DONE);
        Question = "Type DONE if you are done or LEADER if you want to play a leader card.";
    }

    public String getQuestion() {
        return Question;
    }
}
