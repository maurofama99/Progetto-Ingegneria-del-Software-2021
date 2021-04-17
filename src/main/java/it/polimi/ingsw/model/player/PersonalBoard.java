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
    private Player player;
    private ArrayList<LeaderCard> activeLeaderCards;

    public Player getPlayer() { return player; }

    public void setFaithTrack(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public PersonalBoard(Warehouse warehouse) {
        this.warehouse = warehouse;
        slots[0] = new Slot(SlotNumber.FIRST);
        slots[1] = new Slot(SlotNumber.SECOND);
        slots[2] = new Slot(SlotNumber.THIRD);
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
        if (getPlayer().hasEffect(EffectType.ADDPRODUCTION)){
            ArrayList<Resource> resourcesToRemove = new ArrayList<>();
            ArrayList<Resource> resourcesToAdd = new ArrayList<>();

            resourcesToAdd.add(resourceChosenByPlayer);
            resourcesToRemove.add((Resource) leaderCard.getLeaderEffect().getObject());

            player.getPersonalBoard().getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);
            player.getPersonalBoard().getWarehouse().removeResources(resourcesToRemove);

            player.getPersonalBoard().getFaithTrack().getFaithMarker().moveForward(player.getPlayerFaithMarker(), 1);

        }
    }

}
