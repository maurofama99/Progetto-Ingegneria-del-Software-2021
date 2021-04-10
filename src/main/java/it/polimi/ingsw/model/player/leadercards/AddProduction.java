package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.faithtrack.FaithMarker;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;

public class AddProduction implements LeaderEffect {

    private DevCard cardRequired;
    private Resource resourceRequired;
    private Resource resourceChosedByPlayer;


    //aggiunge una produzione alla personal Board cioè:
    //input: una risorsa
    //output: un punto fede e una risorsa a scelta del giocatore
    @Override
    public void doEffect(Player player) {

        ArrayList<Resource> resourcesToRemove = new ArrayList<>();
        ArrayList<Resource> resourcesToAdd = new ArrayList<>();

        resourcesToAdd.add(resourceChosedByPlayer);
        resourcesToRemove.add(resourceRequired);

        player.getPersonalBoard().getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);
        player.getPersonalBoard().getWarehouse().removeResources(resourcesToRemove);

        player.getPersonalBoard().getFaithTrack().getFaithMarker().moveForward(player.getPlayerFaithMarker(), 1);

    }

    @Override
    public boolean checkRequirementsLeaderCard(Player player) {
        int i, k;
        for (i=0; i<3; i++){
            for (k=0; k<3; k++){
                if (player.getPersonalBoard().getSlots()[i].getCards().get(k).getLevel() == cardRequired.getLevel())
                    return true;
            }
        }
        return false;
    }



}
