package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;

public class ExtraVictoryPoints implements LeaderEffect {

    private int victoryPoints;

    @Override
    public void doEffect(Player player) {
        int temp = player.getVictoryPoints();
        player.setVictoryPoints(temp + victoryPoints);
    }

    @Override
    public boolean checkRequirementsLeaderCard(Player player) {
        //problema ci sono due carte di questo tipo con
        //requirements completamente diversi
        //1: 14 punti fede, 2: una carta di ogni colore
        return false;
    }
}
