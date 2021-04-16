package it.polimi.ingsw.model.singleplayer;

/**
 * Essentially the FaithMarker of Lorenzo
 */
public class BlackCross {
    private int position;


    public BlackCross(int position) {
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Function that moves the black cross forward
     * @param bc the blackcross
     * @param steps how many steps it does
     */
    public void moveForward(BlackCross bc, int steps) {
        bc.setPosition(bc.getPosition() + steps);
    }

    public int getPosition() {
        return this.position;
    }
}
