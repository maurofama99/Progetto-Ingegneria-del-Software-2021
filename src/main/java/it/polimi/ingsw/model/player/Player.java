package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.faithtrack.FaithMarker;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;

import java.util.ArrayList;

/**
 * Player's class. Used a lot to set and get in other methods and interfaces
 */
public class Player {
    private final String nickname;
    private boolean isCurrentTurn;
    private Turn turnOrder;
    private boolean hasMoved;
    private ArrayList<LeaderCard> leaderCards;
    private int victoryPoints;
    private FaithMarker playerFaithMarker;
    private PersonalBoard personalBoard;

    public FaithMarker getPlayerFaithMarker() {
        return playerFaithMarker;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public void setTurnOrder(Turn turnOrder) {
        this.turnOrder = turnOrder;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public void setLeaderCards(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
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

    public void setCurrentTurn(boolean currentTurn) {
        isCurrentTurn = currentTurn;
    }

    public Player(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isCurrentTurn() {
        return isCurrentTurn;
    }

    public Turn getTurnOrder() {
        return turnOrder;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Added rule, adds VPs bonus
     */
    public void addInitialBonus(){

    }

    /**
     * The player can discard the leader card instead of placing them
     * @param leaderCard1 the first leader card
     * @param leaderCard2 the second leader card
     */
    public void discardLeader(LeaderCard leaderCard1, LeaderCard leaderCard2){

    }

    /**
     * The player selects a row and takes resources from the market
     */
    public void goesMarket(){

    }

    /**
     * The player decides to buy a dev card and place it. Calls all the methods to check if
     * it can be bought.
     * @param devCardToBuy the card selected.
     */
    public void buyDevCard(DevCard devCardToBuy){

    }

    /**
     * The player activates the production of one or more dev cards
     * @param devCardToAct dev card whose production will be used
     */
    public void activateProd(DevCard devCardToAct){

    }

    /**
     * Instead of discarding it, the player decides to place the leader card. Requirements need to be
     * checked
     * @param leaderCardToUse which leader card will be checked and eventually placed
     */
    public void useLeaderCard(LeaderCard leaderCardToUse){

    }

    /**
     * A placed leader card is activated
     * @param leaderCardToAct takes the leader effects and plays it
     */
    public void activateLeaderCard(LeaderCard leaderCardToAct){

    }

    /**
     * When a condition is met, the player ends his turn
     */
    public void endTurn(){
        this.isCurrentTurn = false;
    }

/**
 * The game finishes and this method is called to calculate bonus VPs to add
 */
    /*public int addPointFiveResources(PersonalBoard personalBoard){
        return numberofresources/5;
    }*/


}
