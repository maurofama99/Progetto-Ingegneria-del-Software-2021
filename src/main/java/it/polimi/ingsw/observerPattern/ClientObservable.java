package it.polimi.ingsw.observerPattern;

import it.polimi.ingsw.network.Message;

import java.util.ArrayList;

public abstract class ClientObservable {
    private ArrayList<ClientObserver> clientObservers = new ArrayList<>();

    public void addClientObserver(ClientObserver clientObserver) {
        clientObservers.add(clientObserver);
    }

    public void notifyObservers(Message message){
        clientObservers.forEach(obs -> obs.update(message));
    }

}
