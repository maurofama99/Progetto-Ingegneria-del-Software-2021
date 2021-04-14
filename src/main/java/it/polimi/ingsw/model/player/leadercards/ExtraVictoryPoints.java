package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;

/**
 * Leader cards that add a lot of VPs, no other effects. They are an "extra"
 */
public class ExtraVictoryPoints implements LeaderEffect {

    private int victoryPoints;

    /**
     * Gives the VPs to the player
     * @param player owner of the card
     */
    @Override
    public void doEffect(Player player) {
        int temp = player.getVictoryPoints();
        player.setVictoryPoints(temp + victoryPoints);
    }

    /**
     * Checks if the player can actually place the leader card
     * @param player owner of the card
     * @return true if the card can be placed, false if not
     */
    @Override
    public boolean checkRequirementsLeaderCard(Player player) {
        //problema ci sono due carte di questo tipo con
        //requirements completamente diversi
        //1: 14 punti fede, 2: una carta di ogni colore
        return false;
    }
}
