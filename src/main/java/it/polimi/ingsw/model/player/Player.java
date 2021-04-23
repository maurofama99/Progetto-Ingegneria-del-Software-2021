package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.faithtrack.FaithMarker;
import it.polimi.ingsw.model.player.leadercards.*;
import it.polimi.ingsw.model.player.warehouse.Depot;
import it.polimi.ingsw.model.resources.ResourceType;


import java.util.ArrayList;

/**
 * Player's class. Used a lot to set and get in other methods and interfaces
 */
public class Player {
    private final String nickname;
    private boolean isCurrentTurn;

    private boolean hasMoved;
    private ArrayList<LeaderCard> leaderCards;
    private int victoryPoints;
    private FaithMarker playerFaithMarker;
    private PersonalBoard personalBoard;

    public Player(String nickname, PersonalBoard personalBoard) {
        this.nickname = nickname;
        this.isCurrentTurn = false;
        this.hasMoved = false;
        this.leaderCards = new ArrayList<>();
        this.personalBoard = personalBoard;
    }

    public FaithMarker getPlayerFaithMarker() {
        return playerFaithMarker;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public void setPlayerFaithMarker(FaithMarker playerFaithMarker) {
        this.playerFaithMarker = playerFaithMarker;
    }

    public void setPersonalBoard(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

    public Player(String nickname) {
        this.nickname = nickname;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * The player can discard the leader card instead of placing them
     * @param leaderCard1 the first leader card
     * @param leaderCard2 the second leader card
     */
    public void discardLeader(LeaderCard leaderCard1, LeaderCard leaderCard2){
        leaderCards.remove(leaderCard1);
        leaderCards.remove(leaderCard2);
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
        if (personalBoard.hasEffect(EffectType.DISCOUNT)){
            int i,k;
            for (i=0; i<devCardToBuy.getRequirementsDevCard().size(); i++){
                for (k=0; k<getPersonalBoard().getActiveLeaderCards().size();k++) {
                    if (devCardToBuy.getRequirementsDevCard().get(i).getType().equals(getPersonalBoard().getActiveLeaderCards().get(k).getLeaderEffect().getObject()))
                        devCardToBuy.getRequirementsDevCard().get(i).setQnt(devCardToBuy.getRequirementsDevCard().get(i).getQnt() - 1);
                }
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
