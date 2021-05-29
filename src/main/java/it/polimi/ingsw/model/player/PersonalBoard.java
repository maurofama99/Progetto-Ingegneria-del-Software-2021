package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
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
public class PersonalBoard{
    private final Warehouse warehouse;
    private SerializableWarehouse serializableWarehouse;
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

    public PersonalBoard(SerializableWarehouse wH, Slot[] s, FaithTrack ft, ArrayList<LeaderCard> lC){
        this.warehouse =null;
        this.serializableWarehouse = wH;
        this.slots[0] = s[0];
        this.slots[1] = s[1];
        this.slots[2] = s[2];
        this.faithTrack = ft;
        this.activeLeaderCards = lC;
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

    public void setFaithTrack(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
    }

    public void setSerializableWarehouse(SerializableWarehouse serializableWarehouse) {
        this.serializableWarehouse = serializableWarehouse;
    }

    public void setSlots(Slot[] slots) {
        this.slots = slots;
    }

    public void setActiveLeaderCards(ArrayList<LeaderCard> activeLeaderCards) {
        this.activeLeaderCards = activeLeaderCards;
    }

    public void addLeaderCard(LeaderCard leaderCardToActivate){
        activeLeaderCards.add(leaderCardToActivate);
    }

    /**
     * Checks if the player has activated a type of leader card.
     * @param effectType leader card's type of effect.
     * @return If the player has activated effectType.
     */

    public boolean hasEffect(EffectType effectType) {
        for (int i=0; i<activeLeaderCards.size();i++) {
            if (activeLeaderCards.get(i).getLeaderEffect().getEffectType().equals(effectType))
                return true;
        }
        return false;
    }

}
