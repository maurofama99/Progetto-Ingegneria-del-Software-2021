package it.polimi.ingsw.observerPattern;

import it.polimi.ingsw.network.Message;

/**
 * A simple interface for an observer
 */
public interface ClientObserver {

    void update (Message message);

}
