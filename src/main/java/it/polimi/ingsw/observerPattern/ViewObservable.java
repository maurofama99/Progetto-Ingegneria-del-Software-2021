package it.polimi.ingsw.observerPattern;

import it.polimi.ingsw.network.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ViewObservable {

    protected final List<ViewObserver> observers = new ArrayList<>();

    public void addObserver(ViewObserver observer){
        observers.add(observer);
    }

    public void addAllObservers(List<ViewObserver> observerList) {
        observers.addAll(observerList);
    }

    public void notifyObserver(Consumer<ViewObserver> lambda){
        for (ViewObserver observer : observers) {
            lambda.accept(observer);
        }
    }
}
