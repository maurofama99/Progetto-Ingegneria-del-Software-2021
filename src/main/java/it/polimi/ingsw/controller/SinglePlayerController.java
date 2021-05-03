package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;
import it.polimi.ingsw.model.singleplayer.RemoveCardsAction;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

/**
 * This is the single player controller
 */
public class SinglePlayerController {
    private GameController gameController;
    private LorenzoIlMagnifico lorenzoIlMagnifico;
    private Player singlePlayer;
    private VirtualView virtualView;
    private Table table;
    private int devCardsBought = 0;
    private ArrayList<Integer> devCardsRemoved;



    public GameController getGameController() {
        return gameController;
    }

    public LorenzoIlMagnifico getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }

    public Player getSinglePlayer() {
        return singlePlayer;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public Table getTable() {
        return table;
    }

    public int getDevCardsBought() {
        return devCardsBought;
    }

    public ArrayList<Integer> getDevCardsRemoved() {
        return devCardsRemoved;
    }

    /**
     * Setup of Lorenzo and the token stack in the table
     */
    public void setUpSinglePlayer(){
        lorenzoIlMagnifico = new LorenzoIlMagnifico();
        table.setNumPlayers(1);
        table.createTokenStack();
        table.addPlayer("nickname");
    }

    /**
     * What lorenzo does in his turn
     */
    public void lorenzoTurn(){
        lorenzoIlMagnifico.turnToken(table);
        lorenzoIlMagnifico.getShowedToken();
    }

    /**
     * Rises the amount of dev cards bought by the player and the remove counter of the cards
     * @param devCard the devcard that has been bought
     */
    public void cardBought(DevCard devCard){
        devCardsBought++;
        switch(devCard.getCardColor()){
            case GREEN:
                devCardsRemoved.set(0, devCardsRemoved.get(0)+1);
                if(devCardsRemoved.get(0)==12)
                    endGame();
                else
                    break;
            case BLUE:
                devCardsRemoved.set(1, devCardsRemoved.get(1)+1);
                if(devCardsRemoved.get(1)==12)
                    endGame();
                else
                    break;
            case YELLOW:
                devCardsRemoved.set(2, devCardsRemoved.get(2)+1);
                if(devCardsRemoved.get(2)==12)
                    endGame();
                else
                    break;
            case PURPLE:
                devCardsRemoved.set(3, devCardsRemoved.get(3)+1);
                if(devCardsRemoved.get(3)==12)
                    endGame();
                else
                    break;
        }
    }

    /**
     * Creates an arraylist to keep track of the dev cards removed, both by buying or removed by tokens
     */
    public void createCardsRemoved(){
        devCardsRemoved = new ArrayList<Integer>();
        devCardsRemoved.add(0);
        devCardsRemoved.add(0);
        devCardsRemoved.add(0);
        devCardsRemoved.add(0);
    }

    /**
     * This method rises the dev cards removed.
     */
    public void cardRemoved(){
        switch(lorenzoIlMagnifico.getShowedToken().getRemoveColor((RemoveCardsAction) lorenzoIlMagnifico.getShowedToken().getTokenAction())){
            case GREEN:
                devCardsRemoved.set(0, devCardsRemoved.get(0)+2);
                if(devCardsRemoved.get(0)==12)
                    endGame();
                else
                    break;
            case BLUE:
                devCardsRemoved.set(1, devCardsRemoved.get(1)+2);
                if(devCardsRemoved.get(1)==12)
                    endGame();
                else
                    break;
            case YELLOW:
                devCardsRemoved.set(2, devCardsRemoved.get(2)+2);
                if(devCardsRemoved.get(2)==12)
                    endGame();
                else
                    break;
            case PURPLE:
                devCardsRemoved.set(3, devCardsRemoved.get(3)+2);
                if(devCardsRemoved.get(3)==12)
                    endGame();
                else
                    break;
        }
    }

    /**
     * ends the game when certain conditions are met
     */
    public void endGame(){
        if(singlePlayer.getPersonalBoard().getFaithTrack().getFaithMarkerPosition()==24 || getDevCardsBought() == 7)
            System.out.println("Hai vinto!");
        else if(singlePlayer.getPersonalBoard().getFaithTrack().getBlackCrossPosition()==24 || devCardsRemoved.get(0) == 12
        || devCardsRemoved.get(1) == 12 || devCardsRemoved.get(2) == 12 || devCardsRemoved.get(3) == 12)
            System.out.println("Hai perso");
    }
}
