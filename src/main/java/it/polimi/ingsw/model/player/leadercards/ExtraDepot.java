package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ExtraDepot implements LeaderEffect {

    private Resource toStore;
    private ArrayList<Resource> resourceRequired;

    @Override
    public void doEffect(Player player) {
        //aggiunge un second floor in qualche modo

    }

    @Override
    public boolean checkRequirementsLeaderCard(Player player) {
        try {
            player.getPersonalBoard().getWarehouse().removeResources(resourceRequired);
            return true;
        } catch (NoSuchElementException ex){
            return false;
        }
    }
}
