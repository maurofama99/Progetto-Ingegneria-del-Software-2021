package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import it.polimi.ingsw.observerPattern.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Class of the personal board of every player. It does things like setting the tracks,
 * editing the slots...
 */
public class PersonalBoard extends Observable{
    private final Warehouse warehouse;
    private Slot[] slots = new Slot[3];
    private FaithTrack faithTrack;
    private ArrayList<LeaderCard> activeLeaderCards;

    public PersonalBoard(Warehouse warehouse) {
        this.warehouse = warehouse;
        slots[0] = new Slot(1);
        slots[1] = new Slot(2);
        slots[2] = new Slot(3);
        this.faithTrack = new FaithTrack();
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
     * @param output one resource that the player gets by the production.
     */
    public void basicProduction(ResourceType firstInput, ResourceType secondInput, ResourceType output) throws CloneNotSupportedException {
        ArrayList<Resource> resourceToRemove = new ArrayList<>();
        resourceToRemove.add(new Resource(1, firstInput));
        resourceToRemove.add(new Resource(1, secondInput));

        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(new Resource(1, output));

        try {getWarehouse().removeResources(resourceToRemove);}
        catch (NoSuchElementException e ){
            //notifyObserver(new NoAvailableResources());
        }
        getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);
    }

    public void extraProduction(Player player, LeaderCard leaderCard, Resource resourceChosenByPlayer) throws CloneNotSupportedException {

        if (hasEffect(EffectType.ADDPRODUCTION)){
            ArrayList<Resource> resourcesToRemove = new ArrayList<>();
            ArrayList<Resource> resourcesToAdd = new ArrayList<>();

            resourcesToAdd.add(resourceChosenByPlayer);
            resourcesToRemove.add((Resource) leaderCard.getLeaderEffect().getObject());

            warehouse.getStrongBox().addResourceToStrongBox(resourcesToAdd);
            try {
                warehouse.removeResources(resourcesToRemove);
            }
            catch (NoSuchElementException e){
                //notifyObserver
            }

            getFaithTrack().moveForward(player, 1);

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

    @Override
    public String toString() {
        return  "\nFAITHTRACK: \n" + faithTrack +
                "\nWAREHOUSE: \n" + warehouse.toString() +
                "\nDEVELOPMENT CARD SLOTS: \n" + Arrays.toString(slots) +
                "\nACTIVE LEADER CARDS: " + activeLeaderCards.toString();
    }
}
