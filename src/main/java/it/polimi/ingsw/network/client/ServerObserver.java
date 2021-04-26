package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Message;

public interface ServerObserver {
    void handleMessage(Message message);
}
