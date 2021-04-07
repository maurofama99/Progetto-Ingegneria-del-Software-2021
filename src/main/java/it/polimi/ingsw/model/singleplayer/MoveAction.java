package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;

import java.util.ArrayList;
import java.util.Collections;

public class MoveAction implements TokenAction{

    private int moveNumber;

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    @Override
    public void doAction() {
        if(getMoveNumber()==2){
            //move two spaces
        }
        else if(getMoveNumber()==1){
            //move 1 and shuffle stack
        }
    }
}
