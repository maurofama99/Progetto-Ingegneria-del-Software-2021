package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.faithtrack.FaithMarker;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;

import java.util.ArrayList;

public class Player {
    private String nickname;
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

    public Player(String nickname, boolean isCurrentTurn, Turn turnOrder, boolean hasMoved, ArrayList<LeaderCard> leaderCards, int victoryPoints, FaithMarker playerFaithMarker) {
        this.nickname = nickname;
        this.isCurrentTurn = isCurrentTurn;
        this.turnOrder = turnOrder;
        this.hasMoved = hasMoved;
        this.leaderCards = leaderCards;
        this.victoryPoints = victoryPoints;
        this.playerFaithMarker = playerFaithMarker;
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
