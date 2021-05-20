package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.Color;

/**
 * General class of the tokens.
 */
public class Token {
    private TokenAction tokenAction;
    private boolean isTokenDiscarded;

    public Token(TokenAction tokenAction, boolean isTokenDiscarded) {
        this.tokenAction = tokenAction;
        this.isTokenDiscarded = isTokenDiscarded;
    }

    public TokenAction getTokenAction() {
        return tokenAction;
    }

    public Color getRemoveColor(RemoveCardsAction removeCardsAction){
        return removeCardsAction.getDevCardColor();
    }

    public boolean getIsTokenDiscarded() {
        return isTokenDiscarded;
    }

    public void setTokenDiscarded(boolean tokenDiscarded) {
        isTokenDiscarded = tokenDiscarded;
    }

    /**
     * When a token is activated it changes its state in discarded, since it can't be used
     * again if the stack is not shuffled
     * @param t table where the stack is
     */
    public void activateAction(Table t){
        //activate the action and discards the token if is not yet discarded (shouldn't happen, but checking is good)
        if(!getIsTokenDiscarded()){
            tokenAction.doAction(t);
            setTokenDiscarded(true);
        }
        else
            System.out.println("Token is already discarded. What happened?");
    }

}
