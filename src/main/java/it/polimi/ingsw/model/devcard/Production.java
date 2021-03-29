package it.polimi.ingsw.model.devcard;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;

public class Production {
    static private String productionName;
    private ArrayList<Resource> input;
    private ArrayList<Resource> output;

    public Production(ArrayList<Resource> input, ArrayList<Resource> output) {
        this.input = input;
        this.output = output;
    }

    public static String getProductionName() {
        return productionName;
    }

    public ArrayList<Resource> getInput() {
        return input;
    }

    public ArrayList<Resource> getOutput() {
        return output;
    }

    // checkResourceInput is used to check if the current player has the resources
    // needed to activate the production

    //public boolean checkResourceInput(Player) {}

}
