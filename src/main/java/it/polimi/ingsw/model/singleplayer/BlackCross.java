package it.polimi.ingsw.model.singleplayer;

public class BlackCross {
    private int position;

    public BlackCross(int position) {
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //Function that makes the cross go forward. Here is called only by the tokens of Lorenzo
    public void moveForward(BlackCross bc, int steps) {
        bc.setPosition(bc.getPosition() + steps);
    }

    public int getPosition() {
        return this.position;
    }
}
