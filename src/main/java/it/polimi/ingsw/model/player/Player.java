package it.polimi.ingsw.model.player;


import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.leadercards.*;
import it.polimi.ingsw.model.player.warehouse.Depot;
import it.polimi.ingsw.model.player.warehouse.StrongBox;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.messagessc.GenericMessage;
import it.polimi.ingsw.network.messagessc.NoAvailableResources;
import it.polimi.ingsw.observerPattern.Observable;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Player's class. Used a lot to set and get in other methods and interfaces
 */
public class Player extends Observable implements Serializable {

    private final String nickname;
    private int turnOrder;
    private ArrayList<LeaderCard> leaderCards;
    private int victoryPoints;
    private PersonalBoard personalBoard;
    private int counterDevCards = 0;

    public Player(String nickname) {
        this.nickname = nickname;
        this.turnOrder = 0;
        this.leaderCards = new ArrayList<>();
        this.personalBoard = new PersonalBoard(new Warehouse(new Depot(), new StrongBox()));
    }

    public String getNickname() {
        return nickname;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public int getCounterDevCards() {
        return counterDevCards;
    }

    public void setTurnOrder(int turnOrder) {
        this.turnOrder = turnOrder;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
        System.out.println(nickname + ": punti vittoria: " +victoryPoints);
    }

    public void setPersonalBoard(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

    /**
     * The player can discard two leader cards at the beginning of the game
     * @param leaderCard1 the first leader card
     * @param leaderCard2 the second leader card
     */
    public void discardLeader(int leaderCard1, int leaderCard2){
        //todo se local game no
        ArrayList<Integer> indexes = new ArrayList<>();
        indexes.add(leaderCard1);
        indexes.add(leaderCard2);
        indexes.sort(Collections.reverseOrder());
        for (int index : indexes){
            leaderCards.remove(index);
        }
        notifyObserver(new GenericMessage(leaderCards.toString()));
    }

    /**
     * Method that adds to Personal Board the card that the player wants to activate
     * @param leaderCardToAct leader card to activate
     */
    public void activateLeaderCard(int leaderCardToAct){
        if (!getLeaderCards().get(leaderCardToAct).getLeaderEffect().checkRequirementsLeaderCard(this))
            throw new IllegalArgumentException ("You still don't have the requirements");
        else {
            LeaderCard leaderCard = getLeaderCards().get(leaderCardToAct);
            getLeaderCards().remove(leaderCardToAct);
            getPersonalBoard().addLeaderCard(leaderCard);
            setVictoryPoints(getVictoryPoints()+leaderCard.getVictoryPoints());
            if (leaderCard.getLeaderEffect().getEffectType().equals(EffectType.EXTRADEPOT)) {
                if (getPersonalBoard().getWarehouse().getDepot().getExtraFloors().get(0).isEmpty()) {
                    getPersonalBoard().getWarehouse().getDepot().getExtraFloors().set(0, Optional.of(new Resource(0, (ResourceType) leaderCard.getLeaderEffect().getObject())));
                }
                else if (getPersonalBoard().getWarehouse().getDepot().getExtraFloors().get(1).isEmpty())
                    getPersonalBoard().getWarehouse().getDepot().getExtraFloors().set(1, Optional.of(new Resource(0, (ResourceType) leaderCard.getLeaderEffect().getObject())));
            }
        }
    }

    /**
     * The player decides to buy a dev card and place it. Calls all the methods to check if
     * it can be bought.
     * @param devCardToBuy the card selected.
     * @param slotNumber where the player wants to place the card he wants to buy.
     */
    public boolean buyDevCard(DevCard devCardToBuy, ArrayList<Resource> requirements, int slotNumber) throws IllegalAccessException {
        slotNumber = slotNumber -1;
        try {
            if (getPersonalBoard().getWarehouse().checkAvailabilityWarehouse(requirements)){
                getPersonalBoard().getSlots()[slotNumber].placeDevCard(devCardToBuy);
                devCardToBuy.checkRequirements(requirements, this);
                victoryPoints += devCardToBuy.getVictoryPointsDevCard();
                counterDevCards += 1;
                return true;
            }
            else return false;
        }
        catch (IllegalAccessException | CloneNotSupportedException e) {
            throw new IllegalAccessException(e.getMessage());
        }
    }

    /**
     * when the player wants to activate a production of a development car he owns.
     * @param slot slot chosen by the player.
     */
    public ArrayList<Resource> activateProd(int slot) throws CloneNotSupportedException {

        if (getPersonalBoard().getSlots()[slot].getShowedCard()== null){
            throw new NoSuchElementException("There is not a development card to activate in this slot");
        }
        DevCard devCardToAct = getPersonalBoard().getSlots()[slot].getShowedCard();
        ArrayList<Resource> output = new ArrayList<>();
        for (Resource resource : devCardToAct.getProduction().getOutput())
            output.add((Resource) resource.clone());


        if (!devCardToAct.getProduction().checkInputResource(this)) {
            throw new NoSuchElementException("You don't have the requirements to activate this production");
        }
        else {
            return output;
        }

    }



    /**
     * Method that swaps two resources chosen by the player to one single resource.
     * @param firstInput one resource used to product the resource in output.
     * @param secondInput one resource used to product the resource in output.
     * @param output one resource that the player gets by the production.
     */
    public Resource basicProduction(ResourceType firstInput, ResourceType secondInput, ResourceType output) throws CloneNotSupportedException {
        ArrayList<Resource> resourceToRemove = new ArrayList<>();
        resourceToRemove.add(new Resource(1, firstInput));
        resourceToRemove.add(new Resource(1, secondInput));

        Resource resourceToAdd  = new Resource(1, output);

        try {getPersonalBoard().getWarehouse().removeResources(resourceToRemove);}
        catch (NoSuchElementException e ){
            throw new NoSuchElementException("you don't have the requirements to activate this production");
        }

        return resourceToAdd;
    }


}
