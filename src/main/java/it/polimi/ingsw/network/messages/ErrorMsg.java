package it.polimi.ingsw.network.messages;

/**
 * A generic error message
 */
public abstract class ErrorMsg extends NetworkMessage {

    private final String error;

    public ErrorMsg(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }


}
