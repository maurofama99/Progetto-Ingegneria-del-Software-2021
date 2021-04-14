package it.polimi.ingsw.model.player.faithtrack;

/**
 * Normal tile of the track. It just "exists" as a spot to place the marker.
 */
public class Normal extends Tile {

    public Normal(int position, boolean isFirstSection, boolean isSecondSection, boolean isThirdSection) {
        super(position, isFirstSection, isSecondSection, isThirdSection);
    }
}
