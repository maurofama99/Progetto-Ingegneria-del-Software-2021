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
        //creo una copia dell'array di required resources
        ArrayList<Resource> requirements = new ArrayList<>(requirementsDevCard);

        //controllo una per una se le risorse richieste sono presenti nel warehouse
        //-> se disponibile la sostituisco con white resource (nell'array copia)

        //controllo first floor
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

        //controllo second floor
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

        //controllo third floor
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

        //controllo strongbox
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

        //se tutti gli elementi dell'array di copia sono white resource ritorna true
        return requirements.parallelStream()
                .allMatch(o -> o.getType().equals(ResourceType.WHITERESOURCE));

    }

}
