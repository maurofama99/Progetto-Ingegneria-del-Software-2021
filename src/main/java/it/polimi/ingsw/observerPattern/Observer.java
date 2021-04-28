package it.polimi.ingsw.observerPattern;

import it.polimi.ingsw.network.Message;

import java.io.IOException;

public interface Observer {

    void update (Message message) throws IOException;
}
