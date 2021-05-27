package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * Deposit of the player. Three floors with specific rules to be used. Second floors can be added by
 * leader cards eventually
 */
public class Depot {

    //il deposito è un arraylist di floor in cui i primi tre elementi sono rispettivamente 1st, 2nd e 3rd floor e si possono
    //eventualmente aggiungere dinamicamente degli extra floor
    //per controllare la capacità del piano si usa l'indice+1 solo per i primi 3 piani, gli eventuali restanti sono extradepot da 2 risorse

    private final ArrayList<Optional<Resource>> floors = new ArrayList<>();
    //gli extra floors vengono inizializzati ad empty, la leader card ExtraDepot (quando attivata) provvederà a piazzare il tipo di risorsa con quantità 0
    private final ArrayList<Optional<Resource>> extraFloors = new ArrayList<>();

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

    public ArrayList<Resource> serializableFloorStamp(ArrayList<Optional<Resource>> resources){
        ArrayList<Resource> result = new ArrayList<>();

        for (Optional<Resource> res : resources){
            if (res.isEmpty()) result.add(new Resource(0, ResourceType.NULLRESOURCE));
            else res.ifPresent(resource -> result.add(new Resource(resource.getQnt(), resource.getType())));
        }

        return result;
    }


    /**
     * Sets a floor empty
     *
     * @param floor floor to be set
     */
    public void setEmptyFloor(int floor) {
        floors.set(floor - 1, Optional.empty());
    }


    /**
     * Places the selected resource in the floor. Need to check if floor is empty and if not, if it's
     * the same resource and if the floor is not full
     *
     * @param resourceToPlace which resource goes in the depot
     * @param floor           which floor will store the resource
     * @requires floor >=1 && floor <=3
     */
    public void addResourceToDepot(Resource resourceToPlace, int floor) {
        floor = floor - 1;

        for (int i = 0; i < 3; i++) {
            if (floors.get(i).isPresent() && floors.get(i).get().getType().equals(resourceToPlace.getType()) && i!=(floor))
                throw new IllegalArgumentException("Argument resourceToPlace " + resourceToPlace + " is already in floor " + (i+1));
            }

        if (floors.get(floor).isPresent() && !floors.get(floor).get().getType().equals(resourceToPlace.getType()))
            throw new IllegalArgumentException("In the requested floor (" + (floor+1) + ")" + " there is another type of resource (toPlace:" + resourceToPlace.getType() + " present: " + floors.get(floor).get().getType());

        if (floors.get(floor).isPresent() && ((floors.get(floor).get().getQnt() + resourceToPlace.getQnt()) > floor + 1)
            || floors.get(floor).isEmpty() && resourceToPlace.getQnt() > floor + 1)
            throw new IllegalArgumentException("There is not enough space in the floor");


        if (floors.get(floor).isPresent())
            floors.get(floor).get().setQnt(floors.get(floor).get().getQnt() + resourceToPlace.getQnt());
        else floors.set(floor, Optional.of(new Resource(resourceToPlace.getQnt(), resourceToPlace.getType())));

    }

    public ArrayList<Optional<Resource>> getExtraFloors() {
        return extraFloors;
    }


    /**
     * Method called when there is an extra floor (or two) and is used to store things in there
     *
     * @param resourceToPlace which resource goes there
     */
    public void addResourceToExtraDepot(Resource resourceToPlace) {
        int index = -1;

        if (extraFloors.get(0).isEmpty() && extraFloors.get(1).isEmpty())
            throw new IllegalArgumentException("You don't have extra floors activated.");
        for (int i = 0; i < 2; i++) {
            if (extraFloors.get(i).isPresent() && extraFloors.get(i).get().getType().equals(resourceToPlace.getType())){
                index = i;
            }
        }
        if (index<0)
            throw new IllegalArgumentException("You don't have extra floors available for these type of resource.");
        else if (index>0 &&
                extraFloors.get(index).isPresent() && ((extraFloors.get(index).get().getQnt() + resourceToPlace.getQnt()) > 2))
            throw new IllegalArgumentException("There is not enough space in the extra floor");
        else
            extraFloors.get(index).get().setQnt(extraFloors.get(index).get().getQnt() + resourceToPlace.getQnt());

    }


    /**
     * Switches resources between two floors only if is a legal move
     *
     * @requires (source >=1 && source <=3) && (destination >=1 && destination <=3)
     */
    public void switchFloors(int source, int destination) {
    source -= 1;
    destination -= 1;
        if (floors.get(source).isEmpty() && floors.get(destination).isEmpty()) {
            throw new IllegalArgumentException("You are trying to swap two empty floors.");
        }
        else if (floors.get(source).isEmpty() && floors.get(destination).isPresent()) {
            if (floors.get(destination).get().getQnt() <= source + 1) {
                floors.set(source, floors.get(destination));
                floors.set(destination, Optional.empty());
            } else throw new IllegalArgumentException("There is not enough space to swap these floors.");
        }
        else if (floors.get(source).isPresent() && floors.get(destination).isEmpty()) {
            if (floors.get(source).get().getQnt() <= destination + 1) {
                floors.set(destination, floors.get(source));
                floors.set(source, Optional.empty());
            } else throw new IllegalArgumentException("There is not enough space to swap these floors.");
        }
        else if ((floors.get(source).get().getQnt() <= destination + 1 ) && (floors.get(destination).get().getQnt() <= source+1)) {
            Collections.swap(this.floors, source, destination);
        } else throw new IllegalArgumentException("There is not enough space to swap these floors.");
    }

}
