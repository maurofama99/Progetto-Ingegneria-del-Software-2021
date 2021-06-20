package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.*;

import java.io.Serializable;

/**
 * Class that extends {@link Tile}. It models a pope space tile of faith track.
 */

public class PopeSpace extends Tile implements Serializable {

    public PopeSpace(int position, boolean isFirstSection, boolean isSecondSection, boolean isThirdSection) {
        super(position, isFirstSection, isSecondSection, isThirdSection);
    }

    @Override
    public void addPoints(Player p) {}


}
