package it.polimi.ingsw.model.player.faithtrack;

import org.junit.Test;

import static org.junit.Assert.*;

public class FaithTrackTest {

    @Test
    public void checkPosition() {
    }

    @Test
    public void createTrack() {
        FaithTrack f1 = new FaithTrack();
        f1.createTrack();
        System.out.println(f1.getTrack());
    }
}