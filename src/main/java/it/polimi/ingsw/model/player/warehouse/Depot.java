package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Deposit of the player. Three floors with specific rules to be used. Second floors can be added by
 * leader cards eventually
 */
public class Depot {

    //il deposito è un arraylist di floor in cui i primi tre elementi sono rispettivamente 1st, 2nd e 3rd floor e si possono
    //eventualmente aggiungere dinamicamente degli extra floor
    //per controllare la capacità del piano si usa l'indice+1 solo per i primi 3 piani, gli eventuali restanti sono extradepot da 2 risorse

    private ArrayList<Optional<Resource>> floors = new ArrayList<>();
    //gli extra floors vengono inizializzati ad empty, la leader card ExtraDepot (quando attivata) provvederà a piazzare il tipo di risorsa con quantità 0
    private ArrayList<Optional<Resource>> extraFloors = new ArrayList<>();

    public Depot() {
        for (int i = 0; i < 3; i++) {
            floors.add(Optional.empty());
        }
        for (int i = 0; i < 2; i++) {
            extraFloors.add(Optional.empty());
        }
    }

    public ArrayList<Optional<Resource>> getFloors() {
        return floors;
    }

    //@requires floor >=1 && floor <=3

    /**
     * Sets a floor empty
     * @param floor floor to be set
     */
    public void setEmptyFloor(int floor) {
        floors.set(floor - 1, Optional.empty());
    }



    /**
     * Places the selected resource in the floor. Need to check if floor is empty and if not, if it's
     * the same resource and if the floor is not full
     * @param resourceToPlace which resource goes in the depot
     * @param floor which floor will store the resource
     */
    //@requires floor >=1 && floor <=3
    public void addResourceToDepot(Resource resourceToPlace, int floor) {
        floor = floor - 1;
        if (resourceToPlace.getType().equals(ResourceType.WHITERESOURCE))
            throw new IllegalArgumentException("It's not possible to add a white resource in depot");
        else if (resourceToPlace.getType().equals(ResourceType.FAITHPOINT))
            throw new IllegalArgumentException("It's not possible to add a faith point in depot");
        else {//se la risorsa è già presente in un altro piano non puoi aggiungere resourceToPlace
            for (int i = 0; i < 3; i++) {
                if (floors.get(i).isPresent() && floors.get(i).get().getType().equals(resourceToPlace.getType()))
                    throw new IllegalArgumentException("Argument resourceToPlace " + resourceToPlace + " is already in floor " + i); //TODO eccezioni risorsa scartata restituisce faith point
            }
            //se nel piano è presente un altro tipo di risorsa non puoi aggiungere resourceToPlace
            if (floors.get(floor).isPresent() && !floors.get(floor).get().getType().equals(resourceToPlace.getType()))
                throw new IllegalArgumentException("In the requested floor (" + floor + ")" + " there is another type of resource (toPlace:" + resourceToPlace.getType() + " present: " + floors.get(floor).get().getType());
            //se la risorsa eccede di quantità ripetto al piano non puoi aggiungere resourceToPlace
            if (floors.get(floor).isPresent() && ((floors.get(floor).get().getQnt() + resourceToPlace.getQnt()) > floor + 1))
                throw new IllegalArgumentException("There is not enough space in the floor");
            //se il piano è vuoto e la risorsa è in quantità maggiore alla capacità del piano non puoi aggiungere resourceToPlace
            if (floors.get(floor).isEmpty() && resourceToPlace.getQnt() > floor + 1)
                throw new IllegalArgumentException("There is not enough space in the floor");

            if (floors.get(floor).isPresent()) {
                floors.get(floor).get().setQnt(floors.get(floor).get().getQnt() + resourceToPlace.getQnt());
            } else floors.set(floor, Optional.of(new Resource(resourceToPlace.getQnt(), resourceToPlace.getType())));

        }
    }

    public ArrayList<Optional<Resource>> getExtraFloors() {
        return extraFloors;
    }

    //@requires (floor==1 || floor==2) && resourceToPlace.qnt >= 0

    /**
     * Method called when there is an extra floor (or two) and is used to store things in there
     * @param resourceToPlace which resource goes there
     * @param floor where the resource will be stored
     */
    public void addResourceToExtraDepot(Resource resourceToPlace, int floor) {
        floor = floor - 1;
        if (resourceToPlace.getType().equals(ResourceType.WHITERESOURCE))
            throw new IllegalArgumentException("It's not possible to add a white resource in depot");
        else if (resourceToPlace.getType().equals(ResourceType.FAITHPOINT))
            throw new IllegalArgumentException("It's not possible to add a faith point in depot");
        else if (extraFloors.get(floor).isEmpty())
            throw new IllegalArgumentException("Extra floor " + floor + " is not activted");
        else if (extraFloors.get(floor).isPresent() && !extraFloors.get(floor).get().getType().equals(resourceToPlace.getType()))
            throw new IllegalArgumentException("This extra floor can contain only " + extraFloors.get(floor).get().getType() + " and you are trying to place " + resourceToPlace.getType());
        else {
            //se la risorsa eccede di quantità ripetto al piano non puoi aggiungere resourceToPlace
            if (extraFloors.get(floor).isPresent() && ((extraFloors.get(floor).get().getQnt() + resourceToPlace.getQnt()) > 2))
                throw new IllegalArgumentException("There is not enough space in the extra floor");
            //se il piano è vuoto e la risorsa è in quantità maggiore alla capacità del piano non puoi aggiungere resourceToPlace
            if (extraFloors.get(floor).isEmpty() && resourceToPlace.getQnt() > 2)
                throw new IllegalArgumentException("There is not enough space in the extra floor");

            if (extraFloors.get(floor).isPresent()) {
                extraFloors.get(floor).get().setQnt(extraFloors.get(floor).get().getQnt() + resourceToPlace.getQnt());
            } else extraFloors.get(floor).get().setQnt(resourceToPlace.getQnt());
        }
    }

}
