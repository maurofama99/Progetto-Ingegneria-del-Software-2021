package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;

/**
 * This class is for representing the connection with the client
 */
public abstract class ConnectionStatus {
    private boolean isConnected = true;
    private String token;

    public boolean isConnected() {
        return isConnected;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public abstract void sendMessage(Message message) throws IOException;

    /**
     * This method will disconect the client from the server
     */
    public abstract void disconnectClient();

    /**
     * Pings the client
     */
    public abstract void pingClient();


}
