package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.view.cli.CliColor;

import java.io.Serializable;
import java.util.Collections;

/**
 * Class for the two tokens that move the black cross
 */
public class MoveAction implements TokenAction, Serializable {

    private final int moveNumber;

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
            return CliColor.ANSI_BLUE.escape() + "LORENZO MOVED BLACK CROSS +1 and SHUFFLED TOKEN STACK" + CliColor.RESET;
        }
        else if (moveNumber == 2){
            return CliColor.ANSI_BLUE.escape() + "LORENZO MOVED BLACK CROSS +2" + CliColor.RESET;
        }
        return "";
    }

    /**
     * Used in the matching system between images and attributes in the GUI
     * @return a string (that should match a file name in the resources folder)
     */
    @Override
    public String toStringGui() {
        if (moveNumber == 2) return "blackcross";
        else if (moveNumber == 1) return "shuffle";
        return "";
    }
}
