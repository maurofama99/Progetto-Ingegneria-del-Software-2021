package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.*;

import java.io.Serializable;

/**
 * Pope space class, the tiles with the pope's hat on them to be clear. It decides if a player is
 * in range to turn the favor tile and get some VP.
 */
public class PopeSpace extends Tile implements Serializable {

    public PopeSpace(int position, boolean isFirstSection, boolean isSecondSection, boolean isThirdSection) {
        super(position, isFirstSection, isSecondSection, isThirdSection);
    }

    @Override
    public void addPoints(Player p) {}


}
