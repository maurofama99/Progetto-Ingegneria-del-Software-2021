package it.polimi.ingsw.observerPattern;

import it.polimi.ingsw.network.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for the observable objects. It has an Arraylist of observers, and methods to add observers to this
 * list. It also notifies them.
 */
public abstract class ClientObservable {
    protected final ArrayList<ClientObserver> clientObservers = new ArrayList<>();

    public void addClientObserver(ClientObserver clientObserver) {
        clientObservers.add(clientObserver);
    }

    public void addAllClientObservers(List<ClientObserver> observerList) {
        clientObservers.addAll(observerList);
    }

    public void notifyObservers(Message message){
        clientObservers.forEach(obs -> obs.update(message));
    }

}
