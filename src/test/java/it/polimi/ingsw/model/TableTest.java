package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class TableTest {
    Table table = new Table();
    Player player1 = new Player("a");
    Player player2 = new Player("b");


    @Test
    public void testSetUpPlayer() {
        table.addPlayer("a");
        table.addPlayer("b");

        table.setNumPlayers(table.getPlayers().size());
        table.setPlayersInGame();

        assertTrue(table.getPlayers().get(0).getNickname().equals(player1.getNickname()) ||
                table.getPlayers().get(0).getNickname().equals(player2.getNickname()));
        assertEquals(2, table.getNumPlayers());
        assertEquals(table.getCurrentPlayer().getNickname(), table.getPlayers().get(0).getNickname());

        table.nextPlayer();
        assertEquals(table.getCurrentPlayer().getNickname(), table.getPlayers().get(1).getNickname());

        assertEquals(0,table.getCurrentPlayer().getLeaderCards().size());
        table.dealLeaderCards(table.getCurrentPlayer().getNickname());
        assertEquals(4,table.getCurrentPlayer().getLeaderCards().size());

    }


}