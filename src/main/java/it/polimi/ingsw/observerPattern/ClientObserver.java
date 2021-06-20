package it.polimi.ingsw.observerPattern;

import it.polimi.ingsw.network.Message;

/**
 * A simple interface for observer used to update clients.
 */
public interface ClientObserver {

    /**
     It is called whenever the observed class is changed.
     */
    void update (Message message);

}
