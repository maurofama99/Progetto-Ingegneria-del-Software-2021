package it.polimi.ingsw.model.singleplayer;

public class Token {
    private TokenAction tokenAction;
    private boolean isTokenDiscarded;

    public TokenAction getTokenAction() {
        return tokenAction;
    }

    public void setTokenAction(TokenAction tokenAction) {
        this.tokenAction = tokenAction;
    }

    public boolean isTokenDiscarded() {
        return isTokenDiscarded;
    }

    public void setTokenDiscarded(boolean tokenDiscarded) {
        isTokenDiscarded = tokenDiscarded;
    }

    public void activateAction(TokenAction ta){
        //activate the action and discards the token
    }
}
