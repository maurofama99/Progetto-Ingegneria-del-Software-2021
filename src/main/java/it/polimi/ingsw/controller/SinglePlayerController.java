package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagessc.EndSoloGame;
import it.polimi.ingsw.observerPattern.Observer;
import it.polimi.ingsw.view.VirtualView;
import it.polimi.ingsw.view.cli.CliColor;

import java.io.IOException;

/**
 * Manages the single player game.
 */
public class SinglePlayerController implements Observer {
    private GameController gameController;
    private final Player singlePlayer;
    private VirtualView vv;
    private final Table table;
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

    public SinglePlayerTableState getSinglePlayerTableState() {
        return singlePlayerTableState;
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

    /**
     * Receives a message in a single player game.
     * @param msg the message received
     * @throws IOException If virtual view fails to send message.
     * @throws IllegalAccessException If wrong developent card placement
     * @throws CloneNotSupportedException If resource cloning fails.
     */
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

    /**
     * Sets up the single player game. It makes the player choose the starting leader cards and starts the game.
     * @throws IOException If virtual view fails to send message.
     */
    public void setUpSingleGame() throws IOException {
        vv.displayGenericMessage("You are going to play a single player game.\n Game is loading...\n");
        table.getSinglePlayer().getPersonalBoard().getFaithTrack().addObserver(this);
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

    /**
     * Sends game results messages and ends the game.
     * @param isPlayerWinner True if the player won the game.
     * @throws IOException If virtual view fails to send message.
     */
    public void endSoloGame(boolean isPlayerWinner) throws IOException {
        setSinglePlayerTableState(SinglePlayerTableState.END);
        if (isPlayerWinner) {
            vv.displayGenericMessage("YOU WON!");
            vv.displayPopup("YOU WON!");
        }
        else {
            vv.displayGenericMessage
                    (CliColor.ANSI_BLUE.escape() +
                            "--------------------------------\n" +
                            "|           YOU LOST!          |\n" +
                            "|     LORENZO IL MAGNIFICO     |" +
                            "|    PLAYED BETTER THAN YOU!   |\n" +
                            "--------------------------------\n" + CliColor.RESET);
            vv.displayPopup("YOU LOST!\nLORENZO IL MAGNIFICO DEFEATED YOU.");
        }
        gameController.endGame();

    }

    /**
     * Take the action of Lorenzo's turn picking a token from the table. Every token corresponds to a possible Lorenzo Il Magnifico's action.
     */
    public void lorenzoTurn() throws IOException {
        table.getLorenzoIlMagnifico().turnToken(table);
        vv.displayToken(table.getLorenzoIlMagnifico().getShowedToken());
        vv.displayGUIPersonalBoard(gameController.getTable().getSinglePlayer().getPersonalBoard().getFaithTrack(),
                gameController.getTable().getSinglePlayer().getPersonalBoard().getSlots(),
                new SerializableWarehouse(gameController.getTable().getSinglePlayer().getPersonalBoard().getWarehouse()));
        for (int i=1; i<5;i++){
            if (table.getDevCardsDeck().getDevCard(3, i) ==null){
                endSoloGame(false);
            }
        }
        setSinglePlayerTableState(SinglePlayerTableState.PLAYERS_TURN);
        gameController.askPlayerAction(vv);

    }

    @Override
    public void update(Message message) throws IOException {
        switch (message.getMessageType()){
            case END_SOLOGAME:
                endSoloGame(((EndSoloGame) message).isPlayerWinner());
                break;
            case END_GAME:
                endSoloGame(true);
                break;
            case TURN_FAVORTILE:
                singlePlayer.getPersonalBoard().getFaithTrack().getTrack()
                        .get(singlePlayer.getPersonalBoard().getFaithTrack()
                                .getFaithMarkerPosition()).turnFavorAddPoints(singlePlayer, singlePlayer.getPersonalBoard()
                                        .getFaithTrack().getFaithMarkerPosition());
                break;

        }
    }

}
