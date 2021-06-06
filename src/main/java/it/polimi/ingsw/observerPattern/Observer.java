package it.polimi.ingsw.observerPattern;

import it.polimi.ingsw.network.Message;

import java.io.IOException;

/**
 * A simple observer interface with an update method
 */
public interface Observer {

    void update (Message message) throws IOException;
}
