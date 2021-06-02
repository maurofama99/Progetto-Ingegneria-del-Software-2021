package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;
import it.polimi.ingsw.model.singleplayer.RemoveCardsAction;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.DiscardLeader;
import it.polimi.ingsw.network.messagessc.TurnToken;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observerPattern.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the single player controller
 */
public class SinglePlayerController{
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

    public void endGame(boolean isPlayerWinner) throws IOException {
        if (isPlayerWinner) {
            vv.displayGenericMessage("YOU WON!");
            vv.displayPopup("YOU WON!");
        }
        else {
            vv.displayGenericMessage("YOU LOST!");
            vv.displayPopup("YOU LOST!");
        }

        //todo chiudere la partita

    }

    /**
     * What lorenzo does in his turn
     */
    public void lorenzoTurn() throws IOException {
        table.getLorenzoIlMagnifico().turnToken(table);
        vv.displayToken(table.getLorenzoIlMagnifico().getShowedToken());

        for (int i=1; i<5;i++){
            if (table.getDevCardsDeck().getDevCard(3, i) ==null){
                gameController.endSoloGame(false);
            }
        }

        setSinglePlayerTableState(SinglePlayerTableState.PLAYERS_TURN);
        gameController.askPlayerAction(vv);
    }
}
