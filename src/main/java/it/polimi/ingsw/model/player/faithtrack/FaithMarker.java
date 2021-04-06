package it.polimi.ingsw.model.player.faithtrack;

public class FaithMarker {

    private int position;

    public FaithMarker(int position) {
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //Function that moves the marker forward. Needs to be connected to FaithPnt
    public void moveForward(FaithMarker fm, int faithToAdd){
        fm.setPosition(fm.getPosition()+faithToAdd);
    }

    public int getPosition() {
        return position;
    }
}
