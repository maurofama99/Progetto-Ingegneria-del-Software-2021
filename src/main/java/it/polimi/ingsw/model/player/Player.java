package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.leadercards.*;
import it.polimi.ingsw.model.player.warehouse.Depot;
import it.polimi.ingsw.model.player.warehouse.StrongBox;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.messagessc.GenericMessage;
import it.polimi.ingsw.observerPattern.Observable;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Player's class. Used a lot to set and get in other methods and interfaces
 */
public class Player extends Observable implements Serializable {
    private final String nickname;
    private boolean isCurrentTurn;
    private int turnOrder;
    private ArrayList<LeaderCard> leaderCards;
    private int victoryPoints;
    private PersonalBoard personalBoard;

    public Player(String nickname) {
        this.nickname = nickname;
        this.isCurrentTurn = false;
        this.turnOrder = 0;
        this.leaderCards = new ArrayList<>();
        this.personalBoard = new PersonalBoard(new Warehouse(new Depot(), new StrongBox()));
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isCurrentTurn() {
        return isCurrentTurn;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public void setCurrentTurn(boolean currentTurn) {
        isCurrentTurn = currentTurn;
    }

    public void setTurnOrder(int turnOrder) {
        this.turnOrder = turnOrder;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public void setPersonalBoard(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * The player can discard the leader card instead of placing them
     * @param leaderCard1 the first leader card
     * @param leaderCard2 the second leader card
     */
    public void discardLeader(int leaderCard1, int leaderCard2){
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

    public void activateLeaderCard(LeaderCard leaderCardToAct){
        if (!leaderCardToAct.getLeaderEffect().checkRequirementsLeaderCard(this))
            throw new IllegalArgumentException ("You still don't have the requirements");
        getPersonalBoard().addLeaderCard(leaderCardToAct);
        if (leaderCardToAct.getLeaderEffect().getEffectType().equals(EffectType.EXTRADEPOT)){
            if (getPersonalBoard().getWarehouse().getDepot().getExtraFloors().get(0).isEmpty()){
                getPersonalBoard().getWarehouse().getDepot().getExtraFloors().get(0).get().setType((ResourceType) leaderCardToAct.getLeaderEffect().getObject());
            }
            else if (getPersonalBoard().getWarehouse().getDepot().getExtraFloors().get(1).isEmpty())
                getPersonalBoard().getWarehouse().getDepot().getExtraFloors().get(1).get().setType((ResourceType) leaderCardToAct.getLeaderEffect().getObject());
        }
    }

    /**
     * The player decides to buy a dev card and place it. Calls all the methods to check if
     * it can be bought.
     * @param devCardToBuy the card selected.
     * @param slotNumber where the player wants to place the card he wants to buy.
     */

    public void buyDevCard(DevCard devCardToBuy, int slotNumber) throws IllegalAccessException {
        slotNumber = slotNumber -1;
        if (personalBoard.hasEffect(EffectType.DISCOUNT)){
            int i;
            //todo da sistemare questo metodo
            for (i=0; i<devCardToBuy.getRequirementsDevCard().size(); i++){
                if (personalBoard.getActiveLeaderCards().get(0).getLeaderEffect().getEffectType().equals(EffectType.DISCOUNT)
                        && devCardToBuy.getRequirementsDevCard().get(i).getType().equals(getPersonalBoard().getActiveLeaderCards().get(0).getLeaderEffect().getObject()))
                        devCardToBuy.getRequirementsDevCard().get(i).setQnt(devCardToBuy.getRequirementsDevCard().get(i).getQnt() - 1);

                else if (devCardToBuy.getRequirementsDevCard().get(i).getType().equals(getPersonalBoard().getActiveLeaderCards().get(0).getLeaderEffect().getObject()))
                    devCardToBuy.getRequirementsDevCard().get(i).setQnt(devCardToBuy.getRequirementsDevCard().get(i).getQnt() - 1);
            }
        }
        devCardToBuy.checkRequirements(this);
        getPersonalBoard().getSlots()[slotNumber].placeDevCard(devCardToBuy);
    }

    /**
     * when the player wants to activate a production of a development car he owns.
     * @param devCardToAct development card chosen by the player.
     */
    public void activateProd(DevCard devCardToAct){
        devCardToAct.getProduction().checkInputResource(this);
        getPersonalBoard().getWarehouse().getStrongBox().addResourceToStrongBox(devCardToAct.getProduction().getOutput());
    }

    /**
     * Setting boolean isCurrentTurn to false to end player's turn.
     */
    public void endTurn(){
        this.isCurrentTurn = false;
    }


}
