package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.model.singleplayer.MoveAction;
import it.polimi.ingsw.model.singleplayer.RemoveCardsAction;
import it.polimi.ingsw.model.singleplayer.Token;
import org.junit.Test;

import static org.junit.Assert.*;

public class SwapWhitePopupControllerTest {

    @Test
    public void printTest(){

        ResourceType ty1;
        Resource res = new Resource(1,ResourceType.SERVANT);
        ty1=res.getType();

        System.out.println("/punchboard/resources/"+ty1.getResourceName().toUpperCase()+".png");
    }


}