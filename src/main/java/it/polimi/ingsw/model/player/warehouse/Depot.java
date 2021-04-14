package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;
import java.util.Optional;

public class Depot {

    //il deposito è un arraylist di floor in cui i primi tre elementi sono rispettivamente 1st, 2nd e 3rd floor e si possono
    //eventualmente aggiungere dinamicamente degli extra floor
    //per controllare la capacità del piano si usa l'indice+1

    private ArrayList<Optional<Resource>> floors = new ArrayList<>();

    public Depot() {
        for(int i=0; i<3; i++) {
            floors.add(Optional.empty());
        }
    }

    public ArrayList<Optional<Resource>> getFloors() {
        return floors;
    }

    //@requires floor >=1 && floor <=3
    public void setEmptyFloor(int floor) {
        floors.set(floor-1, Optional.empty());
    }

    //aggiunge la risorsa 'resourceToPlace' nel piano 'floor' @requires floor >=1 && floor <=3
    public void  addResourceToDepot(Resource resourceToPlace, int floor){
        floor = floor-1;
        if (resourceToPlace.getType().equals(ResourceType.WHITERESOURCE)) throw new IllegalArgumentException("It's not possible to add a white resource in depot");
        else if (resourceToPlace.getType().equals(ResourceType.FAITHPOINT)) throw new IllegalArgumentException("It's not possible to add a faith point in depot");
        else {//se la risorsa è già presente in un altro piano non puoi aggiungere resourceToPlace
            for(int i=0; i<3; i++) {
                if (floors.get(i).isPresent() && floors.get(i).get().getType().equals(resourceToPlace.getType()))
                    throw new IllegalArgumentException("Argument resourceToPlace " + resourceToPlace + " is already in floor " + i); //TODO eccezioni risorsa scartata restituisce faith point
            }
            //se nel piano è presente un altro tipo di risorsa non puoi aggiungere resourceToPlace
            if (floors.get(floor).isPresent() && !floors.get(floor).get().getType().equals(resourceToPlace.getType())) throw new IllegalArgumentException("In the requested floor (" + floor + ")" + " there is another type of resource (toPlace:" + resourceToPlace.getType() + " present: " + floors.get(floor).get().getType() );
            //se la risorsa eccede di quantità ripetto al piano non puoi aggiungere resourceToPlace
            if (floors.get(floor).isPresent() && ((floors.get(floor).get().getQnt() + resourceToPlace.getQnt()) > floor+1)) throw new IllegalArgumentException("There is not enough space in the floor");

            if (floors.get(floor).isPresent()) {
                floors.get(floor).get().setQnt(floors.get(floor).get().getQnt() + resourceToPlace.getQnt());
            } else floors.set(floor, Optional.of(new Resource(resourceToPlace.getQnt(), resourceToPlace.getType())));

        }
    }
}
