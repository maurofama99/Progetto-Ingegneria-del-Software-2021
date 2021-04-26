package it.polimi.ingsw.network.server.answers;

import java.io.Serializable;

public class SerializedAnswers implements Serializable{

    private Answer serverAnswer;

    public void setServerAnswer(Answer serverAnswer) {
        this.serverAnswer = serverAnswer;
    }

    public Answer getServerAnswer() {
        return serverAnswer;
    }


}
