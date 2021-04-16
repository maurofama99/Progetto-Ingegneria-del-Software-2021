package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.faithtrack.FaithMarker;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;

import java.util.ArrayList;

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

    public void addInitialBonus(){

    }

    public void discardLeader(LeaderCard leaderCard1, LeaderCard leaderCard2){

    }

    public void goesMarket(){

    }

    public void buyDevCard(DevCard devCardToBuy){

    }

    public void activateProd(DevCard devCardToAct){

    }

    public void useLeaderCard(LeaderCard leaderCardToUse){

    }

    public void activateLeaderCard(LeaderCard leaderCardToAct){

    }

    public void endTurn(){
        this.isCurrentTurn = false;
    }

    /*public int addPointFiveResources(PersonalBoard personalBoard){
        return numberofresources/5;
    }*/


}
