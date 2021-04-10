package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;

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

    public boolean getIsTokenDiscarded() {
        return isTokenDiscarded;
    }

    public void setTokenDiscarded(boolean tokenDiscarded) {
        isTokenDiscarded = tokenDiscarded;
    }

    public void activateAction(Table t){
        //activate the action and discards the token if is not yet discarded (shouldn't happen, but checking is good)
        if(!getIsTokenDiscarded()){
            this.tokenAction.doAction(t);
            setTokenDiscarded(true);
        }
        else
            System.out.println("Token is already discarded. What happened?");
    }

}
