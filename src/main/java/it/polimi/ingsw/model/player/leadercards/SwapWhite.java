package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;

public class SwapWhite implements LeaderEffect {

    private ArrayList<Color> cardColorRequired;
    private Resource newResource;


    //al mercato la whiteResource viene cambiata con un tipo di risorsa
    @Override
    public void doEffect(Player player) {
        int i, k;
        for (i=0; i<3;i++){
            for (k=0; k<4; k++) {
                if (player.getTable().getMarketTray().selectRow(i).get(k).getType().equals(ResourceType.WHITERESOURCE))
                    player.getTable().getMarketTray().selectRow(i).get(k).setType(newResource.getType());
                if(player.getTable().getMarketTray().getSlide().getType().equals(ResourceType.WHITERESOURCE))
                    player.getTable().getMarketTray().getSlide().setType(newResource.getType());
            }
        }
    }

    @Override
    public boolean checkRequirementsLeaderCard(Player player) {
        ArrayList<Color> checkColor = new ArrayList<>();
        for(Color cardColor: cardColorRequired){
            int i, k;
            for (i=0; i<3; i++){
                for (k=0;k<3; k++){
                    if (player.getPersonalBoard().getSlots()[i].getCards().get(k).getCardColor().equals(cardColor))
                        checkColor.add(cardColor);
                }
            }
        }

        return checkColor.containsAll(cardColorRequired);

    }
}
