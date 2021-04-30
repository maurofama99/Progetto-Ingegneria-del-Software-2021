package it.polimi.ingsw.model.player.faithtrack;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FaithTrackTest {

    FaithTrack testFT = new FaithTrack();


    @Test
    public void sayStuffOnTrack(){
        testFT.createTrack();
        ArrayList<Tile> track = testFT.getTrack();
        int position = 5;
        assertEquals(position, track.get(position).getPosition());
        assertEquals(true, track.get(position).isFirstSection());
        assertEquals(false, track.get(position).isSecondSection());
        assertEquals(false, track.get(position).isThirdSection());
        System.out.println(track.get(position).getPosition());
        System.out.println(track.get(position).isFirstSection());
        System.out.println(track.get(position).isSecondSection());
        System.out.println(track.get(position).isThirdSection());

    }

}