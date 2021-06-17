package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messagescs.DoneAction;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.scenes.*;
import javafx.application.Platform;
import javafx.event.Event;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The main class that manages changing scenes, creates the controllers for them and keeps them updated
 * using the messages from the server and clients. It is unique for every client that is playing a game.
 */
public class Gui extends ClientObservable implements View {

    private final static  String ERROR = "ERROR";
    private final static String START_SCENE = "start_scene.fxml";
    private Client client;
    private final ModelView modelView;
    private boolean firstPlayer = false;
    private boolean solo = false;

    /**
     * Constructor of the GUI, sets the ModelView
     */
    public Gui() {
        this.modelView = new ModelView(this);
    }

    /**
     * Sets the client
     * @param client the client to be set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    /**
     * Changes the scene to the login scene, where the player chooses a nickname and the numbers of players
     */
    @Override
    public void fetchNickname() {
        Platform.runLater(() -> SceneController.changeRootPane(clientObservers, "player_login_scene.fxml"));
    }

    /**
     * Same for the method above, but this is specifically when the player wants to play a single player game in GUI
     * @param event the event that will cause the change (usually a button click)
     */
    @Override
    public void localFetchNickname(Event event) {
        Platform.runLater(() ->((PlayerLoginSceneController)SceneController.changeRootPane(clientObservers, event, "player_login_scene.fxml")).setSolo(true));
        solo = true;
    }

    /**
     * Changes the scene to the resource choosing for the initial bonus of the player.
     */
    @Override
    public void fetchResourceType() {
        Platform.runLater(() -> {
            ResourceChoosingPopupController rcpc = new ResourceChoosingPopupController();
            rcpc.addAllClientObservers(clientObservers);
            SceneController.showPopup(rcpc, "resource_choosing_popup.fxml");
        });
    }

    /**
     * Makes the depot popup appear, letting the player know what resource he has to put and where he wants
     * to put it
     * @param resource the resource to be placed
     */
    @Override
    public void fetchResourcePlacement(Resource resource) {
        Platform.runLater(() -> {
            DepoPopupController dpc = new DepoPopupController(resource, modelView);
            dpc.addAllClientObservers(clientObservers);
            SceneController.showPopup(dpc, "depo_popup.fxml");
        });
    }

    /**
     * Asks what resource the player wants if two swap whites effect are active
     * @param type1 the swap white
     * @param type2 the second swap white
     */
    @Override
    public void fetchSwapWhite(ResourceType type1, ResourceType type2) {
        Platform.runLater(()-> {
            SwapWhitePopupController swpc = new SwapWhitePopupController(type1, type2);
            swpc.addAllClientObservers(clientObservers);
            SceneController.showPopup(swpc, "swap_white.fxml");
        });
    }

    /**
     * manages the popup for all the extra productions of the leader cards
     * @param resource
     */
    @Override
    public void fetchExtraProd(Resource resource) {
        Platform.runLater(()-> {
            ExtraProdPopupController eppc = new ExtraProdPopupController(resource, modelView);
            eppc.addAllClientObservers(clientObservers);
            SceneController.showPopup(eppc, "extra_prod.fxml");
        });
    }

    /**
     * Displays a simple popup with a title and a message
     * @param message the message shown at the center
     */
    @Override
    public void displayPopup(String message) {
        Platform.runLater(() -> {
            PopupSceneController psc = new PopupSceneController(message);
            psc.setTitleLabel("Info");
            psc.addAllClientObservers(clientObservers);
            SceneController.showPopup(psc, "popup_scene.fxml");
        });
    }

    /**
     * Displays the four leader cards in the scene where you choose two to discard
     * @param leaderCards the 4 shuffled leader cards
     */
    @Override
    public void displayLeaderCards(ArrayList<LeaderCard> leaderCards) {
        LeaderCardChoosingController llcc = new LeaderCardChoosingController(firstPlayer, solo);
        llcc.setLeaderCards(leaderCards);
        llcc.addAllClientObservers(clientObservers);
        Platform.runLater(() -> SceneController.changeRootPane(llcc, "leader_choosing.fxml"));
    }

    /**
     * Displays the dev card shop (updated)
     * @param devCards the devcards that are at the top in that moment
     */
    @Override
    public void displayDevCards(DevCard[][] devCards) {
        modelView.setShowedDeck(devCards);
    }

    /**
     * Displays a personal board with all the stuff on it updated
     * @param faithTrack the track fo the cross
     * @param slots the slots for the devcards
     * @param warehouse the depot
     */
    @Override
    public void displayPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse, ArrayList<LeaderCard> activeLeaderCards) {
        modelView.setFaithTrack(faithTrack);
        modelView.setSlots(slots);
        modelView.setWarehouse(warehouse);
        modelView.setActiveLeaderCards(activeLeaderCards);
    }

    /**
     * Updates the personal board of other people. Does not matter if they are playing with a CLI or a GUI,
     * we can show them anyway
     * @param name the nickname
     * @param fT player faithtrack
     * @param slots slots with devcards
     * @param wH depot
     * @param lC and leader cards activated
     */
    @Override
    public void updateOtherPersonalBoard(String name, FaithTrack fT, Slot[] slots, SerializableWarehouse wH, ArrayList<LeaderCard> lC) {
        modelView.updateOthersPB(name, fT, slots, wH, lC);
    }

    /**
     * Displays the personal board of the client owning this GUI and keeps it updated
     * @param faithTrack his faithtrack
     * @param slots his slots
     * @param warehouse his depot
     * @throws IOException if something in the personal board is missing or corrupted
     */
    @Override
    public void displayGUIPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse) throws IOException{
        Platform.runLater(()->{
            PersonalBoardSceneController pbsc = new PersonalBoardSceneController(modelView, firstPlayer);
            pbsc.addAllClientObservers(clientObservers);
            SceneController.changeRootPane(pbsc, "personal_board.fxml");
        });
    }

    /**
     * Displays the popup that lets the player choose his first action
     * @param message message to be shown
     */
    @Override
    public void fetchPlayerAction(String message) {
        Platform.runLater(() -> {
            ActionPopupController apc = new ActionPopupController(modelView);
            apc.addAllClientObservers(clientObservers);
            SceneController.showPopup(apc, "action_popup.fxml");
        });
    }

    /**
     * Popup for the leader cards, let the player activate them or not
     * @param message message shown
     * @param leaderCards leader cards of the player
     * @throws IOException if something in the popup is missing or corrupted
     */
    @Override
    public void fetchDoneAction(String message, ArrayList<LeaderCard> leaderCards) throws IOException {
            Platform.runLater(() -> {
                LeaderStartPopupController lssc = new LeaderStartPopupController(leaderCards, true, modelView);
                lssc.addAllClientObservers(clientObservers);
                SceneController.showPopup(lssc, "leader_popup.fxml");
            });
        
    }



    /**
     * Same as above, but this popup is shown at the end of the turn
     * @param leaderCards leader cards of the player
     * @param isEndTurn boolean value for knowing if it is the end of the turn
     */
    @Override
    public void fetchPlayLeader(ArrayList<LeaderCard> leaderCards, boolean isEndTurn) {
        Platform.runLater(()-> {
            LeaderStartPopupController lssc = new LeaderStartPopupController(leaderCards, isEndTurn, modelView);
            lssc.addAllClientObservers(clientObservers);
            SceneController.showPopup(lssc, "leader_popup.fxml");
        });
    }


    @Override
    public void displayDisconnectedMsg(String nicknameWhoDisconnected, String text) {

    }

    /**
     * Sets the market tray and keeps that updated for the show market action in the personal board
     * @param marketTray the current market tray
     */
    @Override
    public void displayMarket(MarketTray marketTray) {
        //Platform.runLater(()-> {
            // MarketPopupSceneController marketPopupSceneController = new MarketPopupSceneController(modelView);
            modelView.setMarketTray(marketTray);
            //marketPopupSceneController.addAllClientObservers(clientObservers);
        //});
    }

    @Override
    public void displayWinningMsg() {
        Platform.runLater(()-> {
            EndPopupSceneController epsc = new EndPopupSceneController("Thank you for playing, press \"OK\" to quit.");
            epsc.addAllClientObservers(clientObservers);
            SceneController.showPopup(epsc, "popup_scene.fxml");
        });
    }

    @Override
    public void displayToken(Token token) {
        Platform.runLater(() -> {
            SingleplayerPopupSceneController spps = new SingleplayerPopupSceneController(token);
            spps.addAllClientObservers(clientObservers);
            SceneController.showPopup(spps, "singleplayer_popup.fxml");
        });
    }

    /**
     * If a player disconnects the game is finished
     * @param nickname the nickname of the player who disconnected
     */
    @Override
    public void forcedEnd(String nickname) {
        Platform.runLater(() -> {
            EndPopupSceneController psc = new EndPopupSceneController(nickname + " left the game. The match ends now.");
            psc.addAllClientObservers(clientObservers);
            psc.setTitleLabel("Disconnection Alert");
            SceneController.showPopup(psc, "popup_scene.fxml");
        });
    }

    /**
     * Displays the basic production popup for switching 2 resources for one
     * @param arrow the arrow that is set as visible
     * @param message the message shown
     */
    @Override
    public void displayBasicProdPopup(int arrow, String message) {
        Platform.runLater(()-> {
            BasicProdPopupController bppc = new BasicProdPopupController(arrow, message);
            bppc.addAllClientObservers(clientObservers);
            SceneController.showPopup(bppc, "basic_prod.fxml");
        });
    }

    @Override
    public void displayGenericMessage(String genericMessage) {
        //used only in CLI mode
    }
}
