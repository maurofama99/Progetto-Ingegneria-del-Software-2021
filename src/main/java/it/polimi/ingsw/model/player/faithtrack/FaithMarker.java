package it.polimi.ingsw.model.player.faithtrack;

public class FaithMarker {

    private int position;

    public FaithMarker(int position) {
        this.position = position;
    }

    //Function that sets marker at the start position. It is called by every player at the start of the game
    public void initPosition(){
        this.position = 0;
    }

    //Function that moves the marker forward. Needs to be connected to FaithPnt
    public void moveForward(FaithMarker fm){
        position = fm.getPosition();
        position++;
    }

    public int getPosition() {
        return position;
    }
}
