package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FaithTrackTest {

    FaithTrack testFT;
    Player p;

    @Before
    public void setUp() throws Exception {
        testFT = new FaithTrack();
        p = new Player("Test");
    }

    @Test
    public void testFirstFavorTile(){
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
    public void testSecondFavorTile() {
        p.getPersonalBoard().getFaithTrack().setFaithMarkerPosition(15);
        assertFalse(p.getPersonalBoard().getFaithTrack().isSecondFavorTile());
        p.getPersonalBoard().getFaithTrack().moveForward(p, 1);
        p.getPersonalBoard().getFaithTrack().getTrack()
                .get(p.getPersonalBoard().getFaithTrack().getFaithMarkerPosition())
                .turnFavorAddPoints(p, p.getPersonalBoard().getFaithTrack().getFaithMarkerPosition());
        assertTrue(p.getPersonalBoard().getFaithTrack().isSecondFavorTile());
        assertEquals(3, p.getVictoryPoints());
    }

    @Test
    public void testThirdFavorTile() {
        p.getPersonalBoard().getFaithTrack().setFaithMarkerPosition(23);
        assertFalse(p.getPersonalBoard().getFaithTrack().isThirdFavorTile());
        p.getPersonalBoard().getFaithTrack().moveForward(p, 1);
        p.getPersonalBoard().getFaithTrack().getTrack()
                .get(p.getPersonalBoard().getFaithTrack().getFaithMarkerPosition())
                .turnFavorAddPoints(p, p.getPersonalBoard().getFaithTrack().getFaithMarkerPosition());
        assertTrue(p.getPersonalBoard().getFaithTrack().isThirdFavorTile());
        assertEquals(8, p.getVictoryPoints());
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