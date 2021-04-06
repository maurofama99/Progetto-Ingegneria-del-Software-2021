package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class FaithMarkerTest {

    Player player = new Player("Test");
    FaithMarker testFM = new FaithMarker(0);
    FaithMarker playerFM = new FaithMarker(0);

    @Test
    public void setPosition() {
        testFM.setPosition(2);
        System.out.println(testFM.getPosition());
    }

    @Test
    public void moveForward() {
        player.setPlayerFaithMarker(playerFM);
        testFM.moveForward(testFM, 3);
        System.out.println(testFM.getPosition());
        testFM.moveForward(testFM, 2);
        System.out.println(testFM.getPosition());
        System.out.println(playerFM.getPosition());
        playerFM.setPosition(testFM.getPosition());
        System.out.println(playerFM.getPosition());
        System.out.println(player.getPlayerFaithMarker().getPosition());
    }

    @Test
    public void isChanging(){
        System.out.println(playerFM.getPosition());
    }
}