package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.Color;

import java.io.Serializable;

/**
 * General class of the tokens.
 */
public class Token implements Serializable {
    private TokenAction tokenAction;

    public Token(TokenAction tokenAction) {
        this.tokenAction = tokenAction;
    }

    public TokenAction getTokenAction() {
        return tokenAction;
    }

    public Color getRemoveColor(RemoveCardsAction removeCardsAction){
        return removeCardsAction.getDevCardColor();
    }

    /**
     * When a token is activated it changes its state in discarded, since it can't be used
     * again if the stack is not shuffled
     * @param t table where the stack is
     */
    public void activateAction(Table t){
        tokenAction.doAction(t);
    }


}
