package it.polimi.ingsw.network.server.answers;

public class ConnectionMessage implements Answer{
    private final boolean isConnected;
    private final String message;

    public ConnectionMessage(boolean isConnected, String message) {
        this.isConnected = isConnected;
        this.message = message;
    }

    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
