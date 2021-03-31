package it.polimi.ingsw.model.player.faithtrack;

public class Tile {
    private int position;
    private boolean isFirstSection;
    private boolean isSecondSection;
    private boolean isThirdSection;

    public Tile(int position, boolean isFirstSection, boolean isSecondSection, boolean isThirdSection) {
        this.position = position;
        this.isFirstSection = isFirstSection;
        this.isSecondSection = isSecondSection;
        this.isThirdSection = isThirdSection;
    }

    //Function that add points to the player when a Tile is of type victory
    public void addPoints(){

    }

    public boolean isFirstSection() {
        return isFirstSection;
    }

    public boolean isSecondSection() {
        return isSecondSection;
    }

    public boolean isThirdSection() {
        return isThirdSection;
    }

    public int getPosition() {
        return position;
    }
}
