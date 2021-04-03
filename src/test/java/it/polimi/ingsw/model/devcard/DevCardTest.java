package it.polimi.ingsw.model.devcard;

import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.*;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;

public class DevCardTest {
    Player p1;

    Resource[] resources = new Resource[4];
    FirstFloor fF = new FirstFloor();
    SecondFloor sF = new SecondFloor();
    ThirdFloor tF = new ThirdFloor();
    StrongBox sB = new StrongBox(resources);
    Warehouse wH = new Warehouse(fF,sF,tF,sB);
    PersonalBoard pB = new PersonalBoard(wH);
    DevCard dC;
    Production prod;
    ArrayList<Resource> input;
    ArrayList<Resource> requirements;

    boolean Test1() throws IllegalAccessException {
        p1 = new Player("Vale");
        p1.setPersonalBoard(pB);

        requirements = new ArrayList<>();
        requirements.add(new Resource(2,ResourceType.STONE));
        dC.setRequirementsDevCard(requirements);

        input = new ArrayList<>();
        input.add(new Resource(1, ResourceType.STONE));
        input.add(new Resource(2,ResourceType.COIN));
        prod.setInput(input);

        //TODO aggiungi risorse al warehouse

        //TEST:
        //      controlla se il player ha i requisiti per comprare la devcard dC
        //      si -> piazza la devcard dC nel primo slot della personal board
        //      controlla se il player ha i requisiti per attivare la produzione

        if(dC.checkRequirements(p1))
            p1.getPersonalBoard().getSlots()[0].PlaceDevCard(dC);
        return dC.getProduction().checkInputResource(p1);

    }


}
