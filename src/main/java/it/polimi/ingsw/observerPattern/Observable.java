package it.polimi.ingsw.observerPattern;

import it.polimi.ingsw.network.Message;

import java.io.IOException;
import java.util.ArrayList;


/**
 * This observable class is used by the model to notify controller and virtual view
 */
public abstract class Observable {

    /**
     * List of observers
     */
    private ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Adds a new observer to the list of observers
     * @param observer is the new observer
     */
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    /**
     * Send a notify to all the observers about a change
     * @param message is the message that has to be notified to the observers
     */
    public void notifyObserver(Message message){
        observers.forEach(observer -> {
            try {
                observer.update(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



}
