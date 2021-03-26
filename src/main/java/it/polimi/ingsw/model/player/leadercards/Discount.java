package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;

public class Discount implements LeaderEffect {
    @Override
    public void doEffect() {

    }

    @Override
    public boolean checkRequirementsLeaderCard(Player player) {
        return false;
    }
}
