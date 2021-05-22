package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.observerPattern.Observable;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.scenes.*;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;

public class Gui extends ClientObservable implements View {

    private final static  String ERROR = "ERROR";
    private final static String START_SCENE = "start_scene.fxml";
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void fetchNickname() {
        SceneController.changeRootPane(clientObservers, "player_login_scene.fxml");
    }

    @Override
    public void fetchResourceType() throws IOException {
        SceneController.changeRootPane(clientObservers, "resource_choosing.fxml");
    }

    @Override
    public void fetchResourcePlacement() throws IOException {
        Platform.runLater(() -> SceneController.showDepositPopup(clientObservers, "stone"));
    }

    @Override
    public void fetchSwapWhite(ResourceType type1, ResourceType type2) throws IOException {

    }

    @Override
    public void displayPopup(String message) {
        Platform.runLater(() -> SceneController.showPopup(clientObservers, message));
    }

    @Override
    public void displayLeaderCards(ArrayList<LeaderCard> leaderCards) throws IOException {
        Platform.runLater(() -> SceneController.changeRootPane(clientObservers, "leader_choosing.fxml"));
    }

    @Override
    public void displayDevCards(DevCard[][] devCards) throws IOException {

    }

    @Override
    public void displayPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse) {
        Platform.runLater(() -> SceneController.changeRootPane(clientObservers, "personal_board.fxml"));
    }

    @Override
    public void fetchPlayerAction(String message) throws IOException {

    }

    @Override
    public void fetchDoneAction(String message, ArrayList<LeaderCard> leaderCards) throws IOException {

    }

    @Override
    public void fetchPlayLeader(ArrayList<LeaderCard> leaderCards, boolean isEndTurn) throws IOException {

    }


    @Override
    public void displayDisconnectedMsg(String nicknameWhoDisconnected, String text) {

    }

    @Override
    public void displayErrorMsg(String errorMsg) {

    }


    @Override
    public void displayMarket(MarketTray marketTray) {

    }

    @Override
    public void displayWinningMsg(String win) {

    }

    @Override
    public void displayGenericMessage(String genericMessage) throws IOException {
        //used only in CLI mode
    }
}
