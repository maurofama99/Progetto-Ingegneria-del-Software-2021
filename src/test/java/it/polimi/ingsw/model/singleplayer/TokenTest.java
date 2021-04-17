package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.devcard.Color;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TokenTest {

    public ArrayList<Token> tokenStack;

    @Test
    public void createStack() {
        tokenStack = new ArrayList<>();

        Token token0 = new Token(new RemoveCardsAction(Color.GREEN), false);
        Token token1 = new Token(new RemoveCardsAction(Color.BLUE), false);
        Token token2 = new Token(new RemoveCardsAction(Color.YELLOW), false);
        Token token3 = new Token(new RemoveCardsAction(Color.PURPLE), false);
        Token token4 = new Token(new MoveAction(2), false);
        Token token5 = new Token(new MoveAction(1), false);

        tokenStack.add(token0);
        tokenStack.add(token1);
        tokenStack.add(token2);
        tokenStack.add(token3);
        tokenStack.add(token4);
        tokenStack.add(token5);

        Collections.shuffle(tokenStack, new Random());

        System.out.println(tokenStack.get(0).getTokenAction());
        System.out.println(tokenStack.get(1).getTokenAction());
        System.out.println(tokenStack.get(2).getTokenAction());
        System.out.println(tokenStack.get(3).getTokenAction());
        System.out.println(tokenStack.get(4).getTokenAction());
        System.out.println(tokenStack.get(5).getTokenAction());
    }


}

