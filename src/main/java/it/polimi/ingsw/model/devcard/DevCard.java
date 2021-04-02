package it.polimi.ingsw.model.devcard;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.*;
import java.util.ArrayList;

public class DevCard {
    private ArrayList<Resource> cost;
    private int level;
    private Color cardColor;
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

    public void setCost(ArrayList<Resource> cost) {
        this.cost = cost;
    }

    public ArrayList<Resource> getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    public Color getCardColor() {
        return cardColor;
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

    public boolean checkRequirements(Player player){

        boolean finded=false, goout=false;
        //copying required resources' array in here
        ArrayList<Resource> requirements = new ArrayList<>(requirementsDevCard);

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

        //if all the elements of the copied array are white resource, returns true
        return requirements.parallelStream()
                .allMatch(o -> o.getType().equals(ResourceType.WHITERESOURCE));

    }

}
