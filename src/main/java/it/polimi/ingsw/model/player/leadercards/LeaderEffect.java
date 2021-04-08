package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;

public interface LeaderEffect{

    void doEffect(Player player);

    boolean checkRequirementsLeaderCard(Player player);

}
