package it.polimi.ingsw.model.player.faithtrack;

import java.io.Serializable;

/**
 * Normal tile of the track. It just "exists" as a spot to place the marker.
 */
public class Normal extends Tile implements Serializable {

    public Normal(int position, boolean isFirstSection, boolean isSecondSection, boolean isThirdSection) {
        super(position, isFirstSection, isSecondSection, isThirdSection);
    }
}
