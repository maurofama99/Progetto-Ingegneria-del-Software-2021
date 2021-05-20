package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;
import it.polimi.ingsw.model.singleplayer.RemoveCardsAction;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.DiscardLeader;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observerPattern.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the single player controller
 */
public class SinglePlayerController implements Observer {
    private GameController gameController;
    private Player singlePlayer;
    private VirtualView vv;
    private Table table;
    private HashMap<String, ClientHandler> playerClientHandlerHashMap = new HashMap<>();
    private int devCardsBought = 0;
    private ArrayList<Integer> devCardsRemoved;
    private SinglePlayerTableState singlePlayerTableState = SinglePlayerTableState.SETUP;

    public SinglePlayerController(Player singlePlayer) {
        this.table = new Table(singlePlayer);
        this.singlePlayer = singlePlayer;
    }



    public void setSinglePlayerTableState(SinglePlayerTableState singlePlayerTableState) {
        this.singlePlayerTableState = singlePlayerTableState;
    }

    public GameController getGameController() {
        return gameController;
    }


    public Player getSinglePlayer() {
        return singlePlayer;
    }

    public VirtualView getVirtualView() {
        return vv;
    }

    public void setVirtualView(VirtualView virtualView) {
        this.vv = virtualView;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
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

    public void receiveSPMessage(Message msg) throws IOException, IllegalAccessException, CloneNotSupportedException {
        switch(singlePlayerTableState){
            case SETUP:
                receiveSPMessageOnSetup(msg);
                break;
            case PLAYERS_TURN:
                receiveSPMessageOnPlayer(msg);
                break;
            case LORENZOS_TURN:
                lorenzoTurn();
                break;
            case END:
                receiveSPMessageOnEnd(msg);
                break;
        }
    }


    public void setUpSingleGame() throws IOException {
        vv.displayGenericMessage("You are going to play a singlePlayer game.\n Game is loading...\n");
        table.dealLeaderCards(singlePlayer.getNickname());
        setSinglePlayerTableState(SinglePlayerTableState.SETUP);
    }


    public void receiveSPMessageOnSetup(Message msg) throws IOException{
        gameController.receiveMessageOnSetup(msg);
        setSinglePlayerTableState(SinglePlayerTableState.PLAYERS_TURN);
    }

    public void receiveSPMessageOnPlayer(Message msg) throws IOException, IllegalAccessException, CloneNotSupportedException {
        gameController.receiveMessageInGame(msg);

    }


    public void receiveSPMessageOnEnd(Message msg){

    }

    /**
     * What lorenzo does in his turn
     */
    public void lorenzoTurn() throws IOException {
        vv.displayGenericMessage(table.getLorenzoIlMagnifico().getShowedToken().toString());
        table.getLorenzoIlMagnifico().turnToken(table);

        setSinglePlayerTableState(SinglePlayerTableState.PLAYERS_TURN);
        gameController.askPlayerAction(vv);
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
    /*
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

     */

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

    @Override
    public void update(Message message) throws IOException {

    }
}
