package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;

import java.io.Serializable;
import java.util.Collections;

/**
 * Class for the two tokens that move the black cross
 */
public class MoveAction implements TokenAction, Serializable {

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

            Collections.shuffle(t.getTokenStack());

        }
    }

    @Override
    public String toString() {
        if (moveNumber == 1) {
            return  "-------------------------------\n"  +
                    "                                    " +
                    "|                             |\n"  +
                    "                                    " +
                    "|             +1              |\n" +
                    "                                    " +
                    "|             &               |\n"  +
                    "                                    " +
                    "|          SHUFFLE            |\n"  +
                    "                                    "+
                    "|                             |\n"  +
                    "                                    " +
                    "-------------------------------\n";
        }
        else if (moveNumber == 2){
            return  "-------------------------------\n"  +
                    "                                    "+
                    "|                             |\n"  +
                    "                                    "+
                    "|                             |\n" +
                    "                                    "+
                    "|             +2              |\n"  +
                    "                                    "+
                    "|                             |\n"  +
                    "                                    "+
                    "|                             |\n"  +
                    "                                    "+
                    "-------------------------------\n";
        }
        return "";
    }
}
