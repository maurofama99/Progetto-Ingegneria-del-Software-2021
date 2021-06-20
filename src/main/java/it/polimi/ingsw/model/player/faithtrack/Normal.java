package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

/**
 * Class that extends {@link Tile}. It models a normal tile of faith track.
 */
public class Normal extends Tile implements Serializable {

    public Normal(int position, boolean isFirstSection, boolean isSecondSection, boolean isThirdSection) {
        super(position, isFirstSection, isSecondSection, isThirdSection);
    }

    @Override
    public void addPoints(Player p) { }

}
