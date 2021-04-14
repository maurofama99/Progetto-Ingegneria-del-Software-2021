package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;

/**
 * Class for the leader cards that add a production power
 */
public class AddProduction implements LeaderEffect {

    private DevCard cardRequired;
    private Resource resourceRequired;
    private Resource resourceChosenByPlayer;


    /**
     * Add a production power to the personal board of a player, that has a input of one resource
     * and gives a faith point AND a resource
     * @param player is used to know the owner of the card (and the production)
     */
    @Override
    public void doEffect(Player player) {

        ArrayList<Resource> resourcesToRemove = new ArrayList<>();
        ArrayList<Resource> resourcesToAdd = new ArrayList<>();

        resourcesToAdd.add(resourceChosenByPlayer);
        resourcesToRemove.add(resourceRequired);

        player.getPersonalBoard().getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);
        player.getPersonalBoard().getWarehouse().removeResources(resourcesToRemove);

        player.getPersonalBoard().getFaithTrack().getFaithMarker().moveForward(player.getPlayerFaithMarker(), 1);

    }

    /**
     * Checks if the player can actually place the leader card
     * @param player owner of the card
     * @return true if the card can be placed, false if not
     */
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
