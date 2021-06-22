package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.Color;

import it.polimi.ingsw.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class SinglePlayerTest {

    public LorenzoIlMagnifico lorenzo;
    public Player single;
    Table table;
    Token token0, token1, token2, token3, token4, token5;

    @Before
    public void setUp(){
        lorenzo = new LorenzoIlMagnifico();
        single = new Player("Pippo");
        table = new Table(single);
        token0 = new Token(new RemoveCardsAction(Color.GREEN));
        token1 = new Token(new RemoveCardsAction(Color.BLUE));
        token2 = new Token(new MoveAction(2));
        token3 = new Token(new MoveAction(1));
        token4 = new Token(new RemoveCardsAction(Color.YELLOW));
        token5 = new Token(new RemoveCardsAction(Color.PURPLE));
    }

    @Test
    public void testRemoveCardsToken() {

        lorenzo.setShowedToken(token0);
        lorenzo.turnToken(table);
        lorenzo.setShowedToken(token0);
        lorenzo.turnToken(table);
        assertEquals("green", token0.getTokenAction().toStringGui());
        assertNull(table.getDevCardsDeck().getDevCard(1, 2));

        lorenzo.setShowedToken(token1);
        lorenzo.turnToken(table);
        lorenzo.setShowedToken(token1);
        lorenzo.turnToken(table);
        assertNull(table.getDevCardsDeck().getDevCard(1, 3));
        lorenzo.setShowedToken(token1);
        lorenzo.turnToken(table);
        assertEquals(2, table.getDevCardsDeck().getFullDeck()[1][2].size());

        lorenzo.setShowedToken(token4);
        lorenzo.turnToken(table);
        assertEquals(2, table.getDevCardsDeck().getFullDeck()[0][0].size());
        lorenzo.setShowedToken(token4);
        lorenzo.turnToken(table);
        lorenzo.setShowedToken(token4);
        lorenzo.turnToken(table);
        assertEquals(2, table.getDevCardsDeck().getFullDeck()[1][0].size());

        lorenzo.setShowedToken(token5);
        lorenzo.turnToken(table);
        assertEquals(2, table.getDevCardsDeck().getFullDeck()[0][3].size());
        lorenzo.setShowedToken(token5);
        lorenzo.turnToken(table);
        lorenzo.setShowedToken(token5);
        lorenzo.turnToken(table);
        lorenzo.setShowedToken(token5);
        lorenzo.turnToken(table);
        assertEquals("purple", token5.getTokenAction().toStringGui());
        assertNull(table.getDevCardsDeck().getDevCard(2, 4));
        assertEquals(4, table.getDevCardsDeck().getFullDeck()[2][3].size());

    }

    @Test
    public void testMoveActionToken(){
        lorenzo.setShowedToken(token2);
        lorenzo.turnToken(table);
        assertEquals(2, table.getSinglePlayer().getPersonalBoard().getFaithTrack().getBlackCrossPosition());

        lorenzo.setShowedToken(token3);
        lorenzo.turnToken(table);
        assertEquals(3, table.getSinglePlayer().getPersonalBoard().getFaithTrack().getBlackCrossPosition());
    }
}

