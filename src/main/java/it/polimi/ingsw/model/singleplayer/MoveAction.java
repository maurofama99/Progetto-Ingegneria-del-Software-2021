package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;

import java.util.Collections;
import java.util.Random;

public class MoveAction implements TokenAction{

    private int moveNumber;

    public MoveAction(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    @Override
    public void doAction(Table t) {
        if(getMoveNumber()==2){
            //move the BlackCross of 2
            t.getLorenzoIlMagnifico().getBlackCross().moveForward(t.getLorenzoIlMagnifico().getBlackCross(), getMoveNumber());
        }
        else if(getMoveNumber()==1){
            //move BlackCross of 1 and shuffle the stack. Also set all tokens to not discarded
            t.getLorenzoIlMagnifico().getBlackCross().moveForward(t.getLorenzoIlMagnifico().getBlackCross(), getMoveNumber());
            Collections.shuffle(t.getTokenStack(), new Random());
            for (int i = 0; i < 6; i++) {
                t.getTokenStack().get(i).setTokenDiscarded(false);
            }
        }
    }
}
