package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;

import java.util.Collections;
import java.util.Random;

/**
 * Class for the two tokens that move the black cross
 */
public class MoveAction implements TokenAction{

    private int moveNumber;

    public MoveAction(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    /**
     * Checks how many steps to do and eventually
     * if it is time to shuffle the stack
     * @param t where the stack is placed, table
     */
    @Override
    public void doAction(Table t) {
        if(getMoveNumber()==2){
            t.getLorenzoIlMagnifico().moveBlackCross(t.getCurrentPlayer(), getMoveNumber());
        }
        else if(getMoveNumber()==1){
            t.getLorenzoIlMagnifico().moveBlackCross(t.getCurrentPlayer(), getMoveNumber());
            Collections.shuffle(t.getTokenStack(), new Random());
            for (Token token : t.getTokenStack()) {
                token.setTokenDiscarded(false);
            }
        }
    }

    @Override
    public String toString() {
        return "MoveAction{" +
                "moveNumber=" + moveNumber +
                '}';
    }
}
