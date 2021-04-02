package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

public class BlackCross {

    private int position;

    public BlackCross(int position) {
        this.position = position;
    }

    //Function that makes the cross go forward. Here is called only by the tokens of Lorenzo
    public void moveForward(BlackCross bc){
        position = bc.getPosition();
        position++;
    }

    public int getPosition() {
        return this.position;
    }
}
