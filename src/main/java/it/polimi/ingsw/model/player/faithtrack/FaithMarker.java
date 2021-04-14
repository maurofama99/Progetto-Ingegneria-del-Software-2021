package it.polimi.ingsw.model.player.faithtrack;

/**
 * FaithMarker class, every player has one to put on his track.
 */
public class FaithMarker {

    private int position;

    public FaithMarker(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * This is the method that is called when faith points are produced. It moved the single marker forward
     * @param fm It's the player's FaithMarker
     * @param faithToAdd Number of steps to make.
     */
    public void moveForward(FaithMarker fm, int faithToAdd){
        //Function that moves the marker forward. Needs to be connected to FaithPnt
        fm.setPosition(fm.getPosition()+faithToAdd);
    }


}
