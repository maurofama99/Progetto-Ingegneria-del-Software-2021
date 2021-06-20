package it.polimi.ingsw.observerPattern;

import it.polimi.ingsw.network.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for the observable objects. It has an Arraylist of observers, and methods to add observers to this
 * list. It also notifies them.
 */
public abstract class ClientObservable {

    /**
     * List of client observers.
     */
    protected final ArrayList<ClientObserver> clientObservers = new ArrayList<>();

    /**
     * Adds a new observer to the list of client observers
     * @param clientObserver is the new client observer
     */
    public void addClientObserver(ClientObserver clientObserver) {
        clientObservers.add(clientObserver);
    }

    /**
     * Adds a list of observers.
     * @param observerList the list of observers to be added to the observers' list.
     */
    public void addAllClientObservers(List<ClientObserver> observerList) {
        clientObservers.addAll(observerList);
    }

    public void notifyObservers(Message message){
        clientObservers.forEach(obs -> obs.update(message));
    }

}
