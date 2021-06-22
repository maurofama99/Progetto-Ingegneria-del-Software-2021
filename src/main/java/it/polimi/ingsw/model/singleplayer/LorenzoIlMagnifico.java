package it.polimi.ingsw.model.singleplayer;


import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.network.messagessc.EndGame;
import it.polimi.ingsw.network.messagessc.TurnFavorTiles;

import java.util.ArrayList;

/**
 * The "player" we fight against in single player. It is very different from a normal player
 * and he plays at the end of any turn
 */
public class LorenzoIlMagnifico {

    private Token showedToken;

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
        singlePlayer.getPersonalBoard().getFaithTrack()
                .setBlackCrossPosition(singlePlayer.getPersonalBoard().getFaithTrack().getBlackCrossPosition() + steps);
        singlePlayer.getPersonalBoard().getFaithTrack()
                .checkBlackCrossPosition(singlePlayer, singlePlayer.getPersonalBoard().getFaithTrack().getBlackCrossPosition());
    }


    /**
     * Lorenzo's gameplay: turns the token and activates its effect. Nothing else, nothing more
     * @param t table, used to get the token stack
     */
    public void turnToken(Table t){
        showedToken.activateAction(t);

        t.getTokenStack().remove(0);
        t.getTokenStack().add(5, showedToken);

        setShowedToken(t.getTokenStack().get(0));
    }

    @Override
    public String toString() {
        return "showedToken=" + showedToken.getTokenAction();
    }
}
