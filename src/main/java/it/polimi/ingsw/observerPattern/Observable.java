package it.polimi.ingsw.observerPattern;

import it.polimi.ingsw.network.Message;

import java.io.IOException;
import java.util.ArrayList;

//used by model, to notify controller and virtual view

public abstract class Observable {

    private ArrayList<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

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
