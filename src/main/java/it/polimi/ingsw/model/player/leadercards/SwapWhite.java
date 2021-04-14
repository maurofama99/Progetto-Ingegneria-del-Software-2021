package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;

/**
 * Leader cards that give extra resources when getting white resources from the market
 */
public class SwapWhite implements LeaderEffect {

    private ArrayList<Color> cardColorRequired;
    private Resource newResource;

    /**
     * Method that changes the white marble in a resource.
     * @param player owner of the card
     */
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

    /**
     * Checks if the player can actually place the leader card
     * @param player owner of the card
     * @return true if the card can be placed, false if not
     */
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
