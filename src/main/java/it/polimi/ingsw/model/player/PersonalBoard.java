package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;

/**
 * Class of the personal board of every player. It does things like setting the tracks,
 * editing the slots...
 */
public class PersonalBoard {
    private final Warehouse warehouse;
    private Slot[] slots = new Slot[3];
    private FaithTrack faithTrack;
    private ArrayList<LeaderCard> activeLeaderCards;

    public PersonalBoard(Warehouse warehouse) {
        this.warehouse = warehouse;
        slots[0] = new Slot(SlotNumber.FIRST);
        slots[1] = new Slot(SlotNumber.SECOND);
        slots[2] = new Slot(SlotNumber.THIRD);
        this.activeLeaderCards = new ArrayList<>();
    }

    public void setFaithTrack(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public ArrayList<LeaderCard> getActiveLeaderCards() {
        return activeLeaderCards;
    }

    public Slot[] getSlots() {
        return slots;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void addLeaderCard(LeaderCard leaderCardToActivate){
        activeLeaderCards.add(leaderCardToActivate);
    }

    /**
     * Method that swaps two resources chosen by the player to one single resource.
     * @param firstInput one resource used to product the resource in output.
     * @param secondInput one resource used to product the resource in output.
     * @param Output one resource that the player gets by the production.
     */
    public void basicProduction(Resource firstInput, Resource secondInput, Resource Output){
        ArrayList<Resource> resourceToRemove = new ArrayList<>();
        resourceToRemove.add(firstInput);
        resourceToRemove.add(secondInput);

        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(Output);

        getWarehouse().removeResources(resourceToRemove);
        getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);
    }

    public void extraProduction(LeaderCard leaderCard, Resource resourceChosenByPlayer){

        if (hasEffect(EffectType.ADDPRODUCTION)){
            ArrayList<Resource> resourcesToRemove = new ArrayList<>();
            ArrayList<Resource> resourcesToAdd = new ArrayList<>();

            resourcesToAdd.add(resourceChosenByPlayer);
            resourcesToRemove.add((Resource) leaderCard.getLeaderEffect().getObject());

            warehouse.getStrongBox().addResourceToStrongBox(resourcesToAdd);
            warehouse.removeResources(resourcesToRemove);

            getFaithTrack().moveForward(1);

        }

    }

    /**
     * Checks if the player has activated a type of leader card.
     * @param effectType leader card's type of effect.
     * @return If the player has activated effectType.
     */

    public boolean hasEffect(EffectType effectType) {
        int i;
        for (i=0; i<activeLeaderCards.size();i++) {
            if (activeLeaderCards.get(i).getLeaderEffect().getEffectType().equals(effectType))
                return true;
        }
        return false;
    }

}
