package it.polimi.ingsw.model.singleplayer;


import it.polimi.ingsw.model.Table;

import java.util.ArrayList;

public class LorenzoIlMagnifico {

    private boolean isPlaying;
    private Token showedToken;
    private BlackCross blackCross;

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public Token getShowedToken() {
        return showedToken;
    }

    public void setShowedToken(Token showedToken) {
        this.showedToken = showedToken;
    }

    public BlackCross getBlackCross() {
        return blackCross;
    }

    public void setBlackCross(BlackCross blackCross) {
        this.blackCross = blackCross;
    }

    public void turnToken(Table t){
        //Sets the showed token and activates it
        setShowedToken(t.getTokenStack().get(0));
        this.showedToken.activateAction(t);
        //The played token is set to discarded after doing what he has to and placed on the bottom of the stack
        //Easier than creating a discarded stack and merge it later.
        ArrayList<Token> toReorder = t.getTokenStack();
        toReorder.add(t.getTokenStack().get(0));
        toReorder.remove(0);
        t.setTokenStack(toReorder);
    }

}
