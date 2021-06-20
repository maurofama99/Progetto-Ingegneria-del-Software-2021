package it.polimi.ingsw.observerPattern;

import it.polimi.ingsw.network.Message;

import java.io.IOException;

/**
 * A simple observer interface with an update method
 */
public interface Observer {

    /**
    It is called whenever the observed class is changed.
     */
    void update (Message message) throws IOException;
}
