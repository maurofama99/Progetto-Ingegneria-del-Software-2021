package it.polimi.ingsw.observerPattern;

import it.polimi.ingsw.network.Message;

public interface ClientObserver {

    void update (Message message);


}
