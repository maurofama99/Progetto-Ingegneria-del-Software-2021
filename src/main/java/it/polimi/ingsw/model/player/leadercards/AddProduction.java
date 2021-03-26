package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;

public class AddProduction implements LeaderEffect {

    private DevCard cardRequired;
   // private Resource resourceRequired;

    @Override
    public void doEffect() {

    }

    @Override
    public boolean checkRequirementsLeaderCard(Player player) {
        return false;
    }

    public DevCard getCardRequired() {
        return cardRequired;
    }

    /*public Resource getResourceRequired() {
        return resourceRequired;
    }
     */
}
