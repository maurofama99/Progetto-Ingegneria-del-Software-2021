package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;

public class AddProduction implements LeaderEffect {

    private DevCard cardRequired;
    private Resource resourceRequired;


    @Override
    public void doEffect(Player player) {
        //aggiunge una produzione cioè:
        //input: una risorsa
        //output: un punto fede e una risorsa a scelta del giocatore

    }

    @Override
    public boolean checkRequirementsLeaderCard(Player player) {
        int i, k;
        for (i=0; i<3; i++){
            for (k=0; k<3; k++){
                if (player.getPersonalBoard().getSlots()[i].getCards().get(k).getLevel() == cardRequired.getLevel())
                    return true;
            }
        }

        return false;
    }



}
