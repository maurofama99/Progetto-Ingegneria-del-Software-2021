package it.polimi.ingsw.model.singleplayer;


import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;

import java.util.ArrayList;

/**
 * The "player" we fight against in single player. It is very different from a normal player
 * and he plays at the end of any turn
 */
public class LorenzoIlMagnifico {

    private Token showedToken;

    public LorenzoIlMagnifico(Token showedToken) {
        this.showedToken = showedToken;
    }

    public Token getShowedToken() {
        return showedToken;
    }

    public void setShowedToken(Token showedToken) {
        this.showedToken = showedToken;
    }


    /**
     * Function that moves the black cross forward
     * @param steps how many steps it does
     */
    public void moveBlackCross(Player singlePlayer, int steps) {
        singlePlayer.getPersonalBoard().getFaithTrack().setBlackCrossPosition(singlePlayer.getPersonalBoard().getFaithTrack().getBlackCrossPosition() + steps);
    }

    /**
     * Lorenzo's gameplay: turns the token and activates its effect. Nothing else, nothing more
     * @param t table, used to get the token stack
     */
    public void turnToken(Table t){
        //Sets the showed token and activates it
        this.showedToken.activateAction(t);

        t.getTokenStack().remove(showedToken);
        t.getTokenStack().add(5, showedToken);

        setShowedToken(t.getTokenStack().get(0));
    }

    @Override
    public String toString() {
        return
                "showedToken=" + showedToken.getTokenAction();
    }
}
