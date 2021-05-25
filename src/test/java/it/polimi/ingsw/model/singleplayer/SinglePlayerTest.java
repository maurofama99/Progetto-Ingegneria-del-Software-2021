package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.devcard.Deck;
import it.polimi.ingsw.model.player.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.*;


public class SinglePlayerTest {

    public LorenzoIlMagnifico lorenzo = new LorenzoIlMagnifico();
    public Player single = new Player("vale");
    public ArrayList<Token> tokenStack;

    @Test
    public void createStack() {
        Table table = new Table(single);
        tokenStack = new ArrayList<>();


        Token token0 = new Token(new RemoveCardsAction(Color.GREEN));
        Token token1 = new Token(new RemoveCardsAction(Color.BLUE));
        Token token2 = new Token(new RemoveCardsAction(Color.YELLOW));
        Token token3 = new Token(new RemoveCardsAction(Color.PURPLE));
        Token token4 = new Token(new MoveAction(2));
        Token token5 = new Token(new MoveAction(1));

        lorenzo.setShowedToken(token0);

        lorenzo.turnToken(table);

        lorenzo.setShowedToken(token0);

        lorenzo.turnToken(table);

        assertEquals(null, table.getDevCardsDeck().getDevCard(1, 1));


    }


}

