package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.*;
import it.polimi.ingsw.network.messagessc.EndGame;
import it.polimi.ingsw.network.messagessc.EndSoloGame;
import it.polimi.ingsw.observerPattern.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.io.Serializable;

import java.io.IOException;

import java.util.Comparator;
import java.util.HashMap;

public class GameController implements Observer, Serializable {
    private PlayerController playerController;
    private SinglePlayerController singlePlayerController;

    private boolean singlePlayer;

    private Table table;
    private TableState tableState = TableState.WAITING_FOR_FIRSTPLAYER;

    private Resource resourceChosen = new Resource(1, ResourceType.WHITERESOURCE);
    private HashMap<String, VirtualView> vvMap = new HashMap<>();
    private String winner = "";
    private String endPlayer = "";
    private int endPlayerNumber =0;
    private boolean condition = false;
    private int playerCounter=0; //to count how many players discarded leaderCards

    public GameController(SinglePlayerController singlePlayerController) {
        this.singlePlayerController = singlePlayerController;
        this.singlePlayer = true;
    }

    public GameController() {
    }

    public void setVvMap(HashMap<String, VirtualView> vvMap) {
        this.vvMap = vvMap;
    }

    public HashMap<String, VirtualView> getVvMap() {
        return vvMap;
    }

    public Table getTable() {
        return table;
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setEndPlayerNumber(int endPlayerNumber) {
        this.endPlayerNumber = endPlayerNumber;
    }

    public void setTableState(TableState tableState) {
        this.tableState = tableState;
    }


    public void receiveMessage(Message msg) throws IOException, IllegalAccessException, CloneNotSupportedException {

        switch (tableState) {
            case SETUP:
                receiveMessageOnSetup(msg);
                break;
            case IN_GAME:
                receiveMessageInGame(msg);
                break;
            case END:
                receiveMessageOnEndGame(msg);
                break;
        }
    }

    public void startGame() throws IOException {
        for (String key : vvMap.keySet()) {
            vvMap.get(key).displayGenericMessage("All the players joined the game.\n Game is loading...\n");
        }
        setTableState(TableState.SETUP);
        setUpGame();
    }

    public void receiveMessageOnSetup(Message msg) throws IOException {
        VirtualView vv = vvMap.get(msg.getSenderUser());

        switch (msg.getMessageType()) {

            case RESOURCE_TYPE:
                vv.displayGenericMessage("You chose: " + ((ResourceTypeChosen)msg).getResourceType());
                resourceChosen.setType(((ResourceTypeChosen) msg).getResourceType());
                for (Player player : table.getPlayers()) {
                    if (msg.getSenderUser().equals(player.getNickname())){
                        vv.displayGenericMessage("\nIn which floor of the depot do you want to place this resource?");
                    }
                }
                vv.fetchResourcePlacement(resourceChosen);
                break;

            case RESOURCE_PLACEMENT:
                try {
                    for (Player player : table.getPlayers()) {
                        if (msg.getSenderUser().equals(player.getNickname()))
                            player.getPersonalBoard().getWarehouse().getDepot().addResourceToDepot(resourceChosen, Integer.parseInt(((ResourcePlacement) msg).getFloor()));
                    }
                    if (condition)
                        table.dealLeaderCards(msg.getSenderUser());
                } catch (NumberFormatException e){
                    vv.displayGenericMessage("You can't do this move now, please choose a floor");
                    vv.displayPopup("You can't do this move now, please choose a floor");
                    vv.fetchResourcePlacement(resourceChosen);
                }
                break;

            case DISCARD_LEADER:
                for (Player player : table.getPlayers()) {
                    if (msg.getSenderUser().equals(player.getNickname())) {
                        player.discardLeader(((DiscardLeader) msg).getLeaderCard1(), ((DiscardLeader) msg).getLeaderCard2());
                        playerCounter++;
                    }
                }

                if (playerCounter == table.getNumPlayers()) {
                    playerCounter=0;
                    setTableState(TableState.IN_GAME);
                    playerController = new PlayerController(this);
                    askPlayerAction(vvMap.get(table.getCurrentPlayer().getNickname()));
                }
                break;

        }

    }

    public void setUpGame() throws IOException {
        for (Player player : table.getPlayers()) {
            player.getPersonalBoard().getFaithTrack().addObserver(this);
            vvMap.get(player.getNickname()).displayPersonalBoard(player.getPersonalBoard().getFaithTrack(),
                    player.getPersonalBoard().getSlots(),
                    new SerializableWarehouse(player.getPersonalBoard().getWarehouse()), player.getPersonalBoard().getActiveLeaderCards());
        }
        table.setPlayersInGame();
        giveInitialBonus();

    }

    public void giveInitialBonus() throws IOException {

        vvMap.get(table.getPlayers().get(0).getNickname()).displayGenericMessage("You are the first player! \nYou now own the Inkwell!\n");
        vvMap.get(table.getPlayers().get(0).getNickname()).displayPopup("You are the first player! \nYou now own the Inkwell!\n");
        table.dealLeaderCards(table.getPlayers().get(0).getNickname());

        vvMap.get(table.getPlayers().get(1).getNickname()).displayGenericMessage("You are the second Player!\nYou have an initial bonus:\n1)one extra resource\n");
        vvMap.get(table.getPlayers().get(1).getNickname()).displayPopup("You are the second Player!\nYou have an initial bonus:\n1)one extra resource\n");
        vvMap.get(table.getPlayers().get(1).getNickname()).fetchResourceType();

        if (table.getNumPlayers() > 2) {
            vvMap.get(table.getPlayers().get(2).getNickname()).displayGenericMessage("You are the third player!\nYou have an initial bonus:\n1) one extra faithPoint \n2)one extra resource\n");
            vvMap.get(table.getPlayers().get(2).getNickname()).displayPopup("You are the third player!\nYou have an initial bonus:\n1) one extra faithPoint \n2)one extra resource\n");
            vvMap.get(table.getPlayers().get(2).getNickname()).fetchResourceType();
            table.getPlayers().get(2).getPersonalBoard().getFaithTrack().moveForward(table.getPlayers().get(2), 1);
        }
        if (table.getNumPlayers() > 3) {
            vvMap.get(table.getPlayers().get(3).getNickname()).displayGenericMessage("You are the fourth player!\nYou have an initial bonus:\n1) one extra faithPoint \n2)two extra resources\n");
            vvMap.get(table.getPlayers().get(3).getNickname()).displayPopup("You are the fourth player!\nYou have an initial bonus:\n1) one extra faithPoint \n2)two extra resources\n");
            vvMap.get(table.getPlayers().get(3).getNickname()).fetchResourceType();
            table.getPlayers().get(3).getPersonalBoard().getFaithTrack().moveForward(table.getPlayers().get(3), 1);
            vvMap.get(table.getPlayers().get(3).getNickname()).fetchResourceType();
        }
        condition = true;
    }

    public void receiveMessageInGame(Message msg) throws IOException, IllegalAccessException, CloneNotSupportedException {
        switch (msg.getMessageType()){
            case GOING_MARKET:
                playerController.setPlayerAction(PlayerAction.GOTO_MARKET);
                playerController.receiveMessage(msg);
                break;
            case ACTIVATE_PRODUCTION:
                playerController.setPlayerAction(PlayerAction.ACTIVATE_PRODUCTION);
                playerController.receiveMessage(msg);
                break;
            case BUY_DEVCARD:
                playerController.setPlayerAction(PlayerAction.BUY_DEVCARD);
                playerController.receiveMessage(msg);
                break;
            case ACTIVATE_LEADER:
                playerController.setPlayerAction(PlayerAction.ACTIVATE_LEADER);
                playerController.receiveMessage(msg);
                break;
            case DISCARDED_LEADER:
                playerController.setPlayerAction(PlayerAction.DISCARD_LEADER);
                playerController.receiveMessage(msg);
                break;
            case DONE_ACTION:
                if (singlePlayer){
                    singlePlayerController.setSinglePlayerTableState(SinglePlayerTableState.LORENZOS_TURN);
                    singlePlayerController.receiveSPMessage(msg);
                    break;
                }
                playerController.setPlayerAction(PlayerAction.WAITING);
                playerController.receiveMessage(msg);
                break;
            default:
                playerController.receiveMessage(msg);
                break;
        }
    }

    public void receiveMessageOnEndGame(Message msg) throws IOException, IllegalAccessException, CloneNotSupportedException {

        if (msg.getMessageType().equals(Content.DONE_ACTION)){

            if (msg.getSenderUser().equals(endPlayer))
                vvMap.get(endPlayer).displayGenericMessage("You made the game end! :)");
            else
                vvMap.get(msg.getSenderUser()).displayGenericMessage(endPlayer + " ended the game");

            vvMap.get(msg.getSenderUser()).displayGenericMessage
                    (       "--------------------------------\n" +
                            "|         Please wait...       |\n" +
                            "|   At the end of this round   |\n" +
                            "|         Game is over         |\n" +
                            "--------------------------------\n");

            if (endPlayerNumber < table.getNumPlayers()) {
                askPlayerAction(vvMap.get(table.getPlayers().get(endPlayerNumber).getNickname()));
                endPlayerNumber++;
            }
        }

        if (!msg.getSenderUser().equals(table.getPlayers().get(0).getNickname())){
            receiveMessageInGame(msg);
        }
        else{

            for (String key : vvMap.keySet()){
                for(Player player : table.getPlayers())
                    if (player.getNickname().equals(key))
                        vvMap.get(key).displayGenericMessage("You have totaled " + player.getVictoryPoints());
            }

            winner = table.getPlayers().stream()
                    .max(Comparator.comparing(Player :: getVictoryPoints))
                    .get().getNickname();

            table.getPlayers().forEach(player -> {
                try {
                    if (player.getNickname().equals(winner)) {
                        vvMap.get(player.getNickname()).displayGenericMessage("YOU WON THIS GAME!! \nCONGRATULATIONS!!");
                        vvMap.get(player.getNickname()).displayPopup("YOU WON THIS GAME!! \nCONGRATULATIONS!!");
                    } else {
                        vvMap.get(player.getNickname()).displayPopup("YOU LOST, I'M SORRY! \n NEXT TIME WILL GO BETTER!\n");
                        vvMap.get(player.getNickname()).displayGenericMessage("YOU LOST, I'M SORRY! \n NEXT TIME WILL GO BETTER!\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            endGame();

        }

    }

    /**
     * Sends information about the game at the beginning of a player's turn.
     * @param vv current player's virtual view
     * @throws IOException
     */

    public void askPlayerAction(VirtualView vv) throws IOException {
        vv.displayGUIPersonalBoard(table.getCurrentPlayer().getPersonalBoard().getFaithTrack(),
                table.getCurrentPlayer().getPersonalBoard().getSlots(),
                new SerializableWarehouse(table.getCurrentPlayer().getPersonalBoard().getWarehouse()));
        vv.displayMarketTray(table.getMarketTray());
        vv.displayDeck(table.getDevCardsDeck().showedCards());
        vv.displayPersonalBoard(table.getCurrentPlayer().getPersonalBoard().getFaithTrack(),
                table.getCurrentPlayer().getPersonalBoard().getSlots(),
                new SerializableWarehouse(table.getCurrentPlayer().getPersonalBoard().getWarehouse()), table.getCurrentPlayer().getPersonalBoard().getActiveLeaderCards());

        if (table.getCurrentPlayer().getLeaderCards().size() > 0)
            vv.fetchPlayLeader(table.getCurrentPlayer().getLeaderCards(), false);
        else
            vv.fetchPlayerAction();
    }

    public void forcedEndGame(String nickname) {
        for (String key : vvMap.keySet()){
            for(Player player : table.getPlayers())
                if (player.getNickname().equals(key)) {
                    try {
                        vvMap.get(key).forcedEnd(nickname);
                    } catch (IOException ignored) {}
                }
        }
    }

    public void endGame(){
        for (String key : vvMap.keySet()){
            for(Player player : table.getPlayers())
                if (player.getNickname().equals(key)) {
                    try {
                        vvMap.get(key).displayWinningMsg();
                    } catch (IOException ignored) {}
                }
        }
    }

    public void endSoloGame(boolean isWinner) throws IOException {
        singlePlayerController.setSinglePlayerTableState(SinglePlayerTableState.END);
        singlePlayerController.endGame(isWinner);
    }

    @Override
    public void update(Message message) throws IOException {

        switch (message.getMessageType()){
            case TURN_FAVORTILE:
                for (Player player : table.getPlayers()){
                    player.getPersonalBoard().getFaithTrack().getTrack().get(player.getPersonalBoard().getFaithTrack().getFaithMarkerPosition()).turnFavorAddPoints(player, player.getPersonalBoard().getFaithTrack().getFaithMarkerPosition());
                }
                break;

            case END_GAME:
                setTableState(TableState.END);
                endPlayer = message.getSenderUser();
                endPlayerNumber = ((EndGame) message).getPlayerNumber();
                break;

            case END_SOLOGAME:
                endSoloGame(((EndSoloGame)message).isPlayerWinner());
                break;
        }
    }


}

