package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FaithTrackTest {

    FaithTrack testFT = new FaithTrack();
    Player p = new Player("Test");

    @Test
    public void sayStuffOnTrack(){
        int position = 5;
        assertEquals(position, testFT.getTrack().get(position).getPosition());
        assertTrue(testFT.getTrack().get(position).isFirstSection());
        assertFalse(testFT.getTrack().get(position).isSecondSection());
        assertFalse(testFT.getTrack().get(position).isThirdSection());

        position=10;
        assertFalse(testFT.getTrack().get(position).isFirstSection());
    }

    @Test
    public void testTurnFavorTile(){
        p.setVictoryPoints(2);
        p.getPersonalBoard().getFaithTrack().setFaithMarkerPosition(6);

        assertFalse(p.getPersonalBoard().getFaithTrack().isFirstFavorTile());

        p.getPersonalBoard().getFaithTrack().moveForward(p, 2);
        p.getPersonalBoard().getFaithTrack().getTrack()
                .get(p.getPersonalBoard().getFaithTrack().getFaithMarkerPosition())
                .turnFavorAddPoints(p, p.getPersonalBoard().getFaithTrack().getFaithMarkerPosition());
        assertEquals(4, p.getVictoryPoints());
        assertTrue(p.getPersonalBoard().getFaithTrack().isFirstFavorTile());
        assertFalse(p.getPersonalBoard().getFaithTrack().isSecondFavorTile());
        assertEquals(8, p.getPersonalBoard().getFaithTrack().getFaithMarkerPosition());
    }

    @Test
    public void testAddPoints(){
        p.setVictoryPoints(1);
        p.getPersonalBoard().getFaithTrack().setFaithMarkerPosition(5);
        p.getPersonalBoard().getFaithTrack().moveForward(p, 1);
        assertEquals(2, p.getVictoryPoints());

        p.getPersonalBoard().getFaithTrack().moveForward(p, 3);
        assertEquals(4,p.getVictoryPoints());

    }

}