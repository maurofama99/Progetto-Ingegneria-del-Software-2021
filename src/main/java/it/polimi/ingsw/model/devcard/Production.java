package it.polimi.ingsw.model.devcard;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;

/**
 * This is the class Production. It is used for initializing dev. cards and put the production
 * attribute in them.
 */
public class Production {
    private final String productionName;
    private ArrayList<Resource> input;
    private ArrayList<Resource> output;

    public ArrayList<Resource> getInput() {
        return input;
    }

    public ArrayList<Resource> getOutput() {
        return output;
    }

    public Production(String productionName, ArrayList<Resource> input, ArrayList<Resource> output) {
        this.productionName = productionName;
        this.input = input;
        this.output = output;
    }

    public void setInput(ArrayList<Resource> input) {
        this.input = input;
    }

    //per non ripetere il codice si potrebbe passare direttamente l'array di risorse come argomento

    /**
     * This method checks if the player has enough resources to start the production.
     * @param player for knowing where to check from
     * @return true if the requirements are met.
     * @throws CloneNotSupportedException
     */
    public boolean checkInputResource(Player player) throws CloneNotSupportedException {

        boolean finded=false, goout=false;
        //deep copying required resources
        ArrayList<Resource> requirements = new ArrayList<>(input.size());
        for (Resource resource : input) {
            requirements.add((Resource) resource.clone());
        }

        //checking one by one if the required resources are in the warehouse
        //-> if yes, replacing it with a white resource(in copied array)

        //checking first floor
        while (!finded && !goout) {
            for (Resource res : requirements){
                if (player.getPersonalBoard().getWarehouse().getFirstFloor().getStoredResource().isPresent()){
                    if (player.getPersonalBoard().getWarehouse().getFirstFloor().getStoredResource().get().getType().equals(res.getType())){
                        if (player.getPersonalBoard().getWarehouse().getFirstFloor().getStoredResource().get().getQnt() >= res.getQnt() && !finded) {
                            finded = true;
                            res.setType(ResourceType.WHITERESOURCE);
                            res.setQnt(0);
                        }
                    }
                }
            }
            goout=true;
        }

        finded=false;
        goout=false;

        //checking second floor
        while (!finded && !goout) {
            for (Resource res : requirements){
                if (player.getPersonalBoard().getWarehouse().getSecondFloor().getStoredResource().isPresent()){
                    if (player.getPersonalBoard().getWarehouse().getSecondFloor().getStoredResource().get().getType().equals(res.getType())){
                        if (player.getPersonalBoard().getWarehouse().getSecondFloor().getStoredResource().get().getQnt() >= res.getQnt() && !finded) {
                            finded = true;
                            res.setType(ResourceType.WHITERESOURCE);
                            res.setQnt(0);
                        }
                    }
                }
            }
            goout=true;
        }

        finded=false;
        goout=false;

        //checking third floor
        while (!finded && !goout) {
            for (Resource res : requirements){
                if (player.getPersonalBoard().getWarehouse().getThirdFloor().getStoredResource().isPresent()){
                    if (player.getPersonalBoard().getWarehouse().getThirdFloor().getStoredResource().get().getType().equals(res.getType())){
                        if (player.getPersonalBoard().getWarehouse().getThirdFloor().getStoredResource().get().getQnt() >= res.getQnt() && !finded) {
                            finded = true;
                            res.setType(ResourceType.WHITERESOURCE);
                            res.setQnt(0);
                        }
                    }
                }
            }
            goout=true;
        }

        finded=false;
        goout=false;

        //checking strongbox
        while(!finded && !goout){
            for (Resource res : requirements){
                if (player.getPersonalBoard().getWarehouse().getStrongBox().checkAvailabilityStrongBox(res) && !finded){
                    finded = true;
                    res.setType(ResourceType.WHITERESOURCE);
                    res.setQnt(0);
                }
            }
            goout=true;
        }

        //if all the elements of the copied array are white resources, return true
        return requirements.parallelStream()
                .allMatch(o -> o.getType().equals(ResourceType.WHITERESOURCE));

    }
}
