package it.polimi.ingsw.model.devcard;


import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This is the class of the single dev. card. Every card has its own stats to get initialized
 */
public class DevCard {
    private int level;
    @SerializedName("color")
    private Color color;
    private int victoryPointsDevCard;
    private boolean isPlaced;
    private ArrayList<Resource> requirementsDevCard;
    private Production production;



    public void setRequirementsDevCard(ArrayList<Resource> requirementsDevCard) {
        this.requirementsDevCard = requirementsDevCard;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    public int getLevel() {
        return level;
    }

    public Color getCardColor() {
        return color;
    }

    public int getVictoryPointsDevCard() {
        return victoryPointsDevCard;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public ArrayList<Resource> getRequirementsDevCard() {
        return requirementsDevCard;
    }

    public Production getProduction() {
        return production;
    }

    /**
     * This method is used to check if a player can activate a production or not. It copies the requirements
     * in an array and starts changing them in WhiteResources if it finds the required resource in the
     * player's warehouses. If all the copied array is made of WhiteResources when the method ends,
     * the requirements are met.
     * @param player is where the method will check for resources, both from his strongbox and deposit
     * @return true if the requirements are met, false otherwise
     * @throws CloneNotSupportedException
     */
    public boolean checkRequirements(Player player) throws CloneNotSupportedException {

        boolean found=false, goOut=false;
        //deep copying required resources' array in here
        ArrayList<Resource> requirements = new ArrayList<>(requirementsDevCard.size());
        for (Resource resource : requirementsDevCard) {
            requirements.add((Resource) resource.clone());
        }

        //checking one by one if the required resources are in the warehouse
        //-> if yes, replacing it with a white resource(in copied array)

        //checking first floor
        while (!found && !goOut) {
            for (Resource res : requirements){
                if (player.getPersonalBoard().getWarehouse().getFirstFloor().getStoredResource().isPresent()){
                    if (player.getPersonalBoard().getWarehouse().getFirstFloor().getStoredResource().get().getType().equals(res.getType())){
                        if (player.getPersonalBoard().getWarehouse().getFirstFloor().getStoredResource().get().getQnt() >= res.getQnt() && !found) {
                            found = true;
                            res.setType(ResourceType.WHITERESOURCE);
                            res.setQnt(0);
                        }
                    }
                }
            }
            goOut=true;
        }

        found=false;
        goOut=false;

        //checking second floor
        while (!found && !goOut) {
            for (Resource res : requirements){
                if (player.getPersonalBoard().getWarehouse().getSecondFloor().getStoredResource().isPresent()){
                    if (player.getPersonalBoard().getWarehouse().getSecondFloor().getStoredResource().get().getType().equals(res.getType())){
                        if (player.getPersonalBoard().getWarehouse().getSecondFloor().getStoredResource().get().getQnt() >= res.getQnt() && !found) {
                            found = true;
                            res.setType(ResourceType.WHITERESOURCE);
                            res.setQnt(0);
                        }
                    }
                }
            }
            goOut=true;
        }

        found=false;
        goOut=false;

        //checking third floor
        while (!found && !goOut) {
            for (Resource res : requirements){
                if (player.getPersonalBoard().getWarehouse().getThirdFloor().getStoredResource().isPresent()){
                    if (player.getPersonalBoard().getWarehouse().getThirdFloor().getStoredResource().get().getType().equals(res.getType())){
                        if (player.getPersonalBoard().getWarehouse().getThirdFloor().getStoredResource().get().getQnt() >= res.getQnt() && !found) {
                            found = true;
                            res.setType(ResourceType.WHITERESOURCE);
                            res.setQnt(0);
                        }
                    }
                }
            }
            goOut=true;
        }

        found=false;
        goOut=false;

        //checking strongbox
        while(!found && !goOut){
            for (Resource res : requirements){
                if (player.getPersonalBoard().getWarehouse().getStrongBox().checkAvailabilityStrongBox(res) && !found){
                    found = true;
                    res.setType(ResourceType.WHITERESOURCE);
                    res.setQnt(0);
                }
            }
            goOut=true;
        }

        //if all the elements of the copied array are white resource, returns true
        return requirements.parallelStream()
                .allMatch(o -> o.getType().equals(ResourceType.WHITERESOURCE));

    }

}
