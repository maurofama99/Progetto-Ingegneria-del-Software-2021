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
     * here we have an override of token action. Checks how many steps to do and eventually
     * if it is time to shuffle the stack
     * @param t where the stack is placed, table
     */
    @Override
    public void doAction(Table t) {
        if(getMoveNumber()==2){
            //move the BlackCross of 2
            t.getLorenzoIlMagnifico().moveBlackCross(t.getPlayers().get(0), getMoveNumber());
        }
        else if(getMoveNumber()==1){
            //move BlackCross of 1 and shuffle the stack. Also set all tokens to not discarded
            t.getLorenzoIlMagnifico().moveBlackCross(t.getPlayers().get(0), getMoveNumber());
            Collections.shuffle(t.getTokenStack(), new Random());
            for (int i = 0; i < 6; i++) {
                t.getTokenStack().get(i).setTokenDiscarded(false);
            }
        }
    }
}
