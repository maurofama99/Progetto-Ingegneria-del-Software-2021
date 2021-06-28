package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * Deposit of the player. Three floors with specific rules to be used. Second floors can be added by
 * leader cards eventually. We use an index+1 to control the capacity of a floor only for the main three floors
 */
public class Depot {

    private final ArrayList<Optional<Resource>> floors = new ArrayList<>();
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
     * Places the selected resource in the floor. Need to check if floor is empty and if not, if it's
     * the same resource and if the floor is not full
     *
     * @param resourceToPlace which resource goes in the depot
     * @param floor which floor will store the resource
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
        else if (index>0 && extraFloors.get(index).isPresent()
                && ((extraFloors.get(index).get().getQnt() + resourceToPlace.getQnt()) > 2))
            throw new IllegalArgumentException("There is not enough space in the extra floor");
        else
            extraFloors.get(index).get().setQnt(extraFloors.get(index).get().getQnt() + resourceToPlace.getQnt());

    }

    /**
     * Switches resources between two floors only if is a legal move
     * @requires (source >=1 && source <=3) && (destination >=1 && destination <=3)
     */
    public void switchFloors(int source, int destination) {
        if (destination==4) switchFloorToExtra(source);
        else {
            source -= 1;
            destination -= 1;
            if (floors.get(source).isEmpty() && floors.get(destination).isEmpty()) {
                assert true;
            } else if (floors.get(source).isEmpty() && floors.get(destination).isPresent()) {
                if (floors.get(destination).get().getQnt() <= source + 1) {
                    floors.set(source, floors.get(destination));
                    floors.set(destination, Optional.empty());
                } else throw new IllegalArgumentException("There is not enough space to swap these floors.");
            } else if (floors.get(source).isPresent() && floors.get(destination).isEmpty()) {
                if (floors.get(source).get().getQnt() <= destination + 1) {
                    floors.set(destination, floors.get(source));
                    floors.set(source, Optional.empty());
                } else throw new IllegalArgumentException("There is not enough space to swap these floors.");
            } else if ((floors.get(source).get().getQnt() <= destination + 1) && (floors.get(destination).get().getQnt() <= source + 1)) {
                Collections.swap(this.floors, source, destination);
            } else throw new IllegalArgumentException("There is not enough space to swap these floors.");
        }
    }



    /**
     * Puts one resource of the source floor resources in the extra depot if legal
     * @param source Source floor
     */
    public void switchFloorToExtra(int source) {
        source -= 1;
        int destination = -1;

        if ((extraFloors.get(0).isEmpty() && extraFloors.get(1).isEmpty())){
            assert true;
        } else {
            for (Optional<Resource> floor : extraFloors){
                if (floor.isPresent() && floor.get().getType().equals(floors.get(source).get().getType())){
                    destination = extraFloors.indexOf(floor);
                }
            }
        }

        if (destination != -1 && floors.get(source).isPresent()) {

            if (extraFloors.get(destination).isPresent() && extraFloors.get(destination).get().getQnt()==2){
                throw new IllegalArgumentException("There is not enough space in the extra floor.");
            }
            else if(floors.get(source).get().getQnt()==1){
                extraFloors.get(destination).get().setQnt(extraFloors.get(destination).get().getQnt()+1);
                floors.set(source, Optional.empty());
            }
            else if(floors.get(source).get().getQnt()>1){
                extraFloors.get(destination).get().setQnt(extraFloors.get(destination).get().getQnt()+1);
                floors.get(source).get().setQnt(floors.get(source).get().getQnt()-1);
            } else throw new IllegalArgumentException("Should not enter in this condition");
        } else throw new IllegalArgumentException("You don't have any extra floor available or source floor is empty");

    }


    /**
     * Checks availability of the resources in all the players depots for doing a certain action
     * @param resources the resources needed for the action
     * @return null if the resources are available or the missing resources otherwise
     * @throws CloneNotSupportedException If resource cloning fails
     */
    public ArrayList<Resource> checkAvailabilityDepot(ArrayList<Resource> resources) throws CloneNotSupportedException {
        ArrayList<Resource> missingResources = new ArrayList<>();
        ArrayList<Resource> resourcesToCheck = new ArrayList<>();
        for (Resource resource : resources){
            resourcesToCheck.add((Resource) resource.clone());
        }
        int j=0;
        boolean checked = false;
        while (j<resourcesToCheck.size()) {
            for (int i = 0; i < 3; i++) {
                if (floors.get(i).isPresent() && floors.get(i).get().getType().equals(resourcesToCheck.get(j).getType())) {
                    if (resourcesToCheck.get(j).getQnt() > floors.get(i).get().getQnt()) {
                        missingResources.add(new Resource(resourcesToCheck.get(j).getQnt() - floors.get(i).get().getQnt(), resourcesToCheck.get(j).getType()));
                    }
                    checked = true;
                }
            }
            if (!checked) {
                missingResources.add(resourcesToCheck.get(j));
            }
            j++;
            checked = false;
        }

        if (missingResources.size() == 0) return null;
        else return missingResources;
    }

}
