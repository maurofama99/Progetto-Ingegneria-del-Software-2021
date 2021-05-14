package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Table;
import javafx.scene.control.Tab;
import org.junit.Test;

import static org.junit.Assert.*;

public class CliGraphicsTest {


    @Test
    public void testShoDeck(){
        Table table = new Table();
        CliGraphics graphics = new CliGraphics();

        graphics.showDevCardsDeck(table.getDevCardsDeck());

    }

}