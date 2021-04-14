package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;

/**
 * Interface of the leader cards.
 */
public interface LeaderEffect{

    /**
     * Activates the effect of a card
     * @param player owner of the card
     */
    void doEffect(Player player);

    /**
     * Checks if the player can actually place the leader card
     * @param player owner of the card
     * @return true if the card can be placed, false if not
     */
    boolean checkRequirementsLeaderCard(Player player);

}
