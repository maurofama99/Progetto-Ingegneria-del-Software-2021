package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.observerPattern.Observable;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.scenes.*;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;

public class Gui extends ClientObservable implements View {

    private final static  String ERROR = "ERROR";
    private final static String START_SCENE = "start_scene.fxml";
    private Client client;
    private ModelView modelView;

    public Gui() {
        this.modelView = new ModelView(this);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void fetchNickname() {
        Platform.runLater(() -> SceneController.changeRootPane(clientObservers, "player_login_scene.fxml"));
    }

    @Override
    public void fetchResourceType() throws IOException {
        Platform.runLater(() -> SceneController.changeRootPane(clientObservers, "resource_choosing.fxml"));
    }

    @Override
    public void fetchResourcePlacement() {
        Platform.runLater(() -> SceneController.showDepositPopup(clientObservers, "stone"));
    }

    @Override
    public void fetchSwapWhite(ResourceType type1, ResourceType type2) throws IOException {

    }

    @Override
    public void fetchExtraProd(Resource resource) throws IOException {

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
        // used only in CLI mode
    }

    @Override
    public void updateOtherPersonalBoard(String name, FaithTrack fT, Slot[] slots, SerializableWarehouse wH, ArrayList<LeaderCard> lC) {

    }

    @Override
    public void displayGUIPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse) throws IOException{
        Platform.runLater(()->SceneController.changeRootPane(clientObservers, "personal_board.fxml"));
    }

    @Override
    public void fetchPlayerAction(String message) {
        Platform.runLater(() -> SceneController.showActionPopup(clientObservers, message));
    }

    @Override
    public void fetchDoneAction(String message, ArrayList<LeaderCard> leaderCards) throws IOException {

    }

    @Override
    public void fetchPlayLeader(ArrayList<LeaderCard> leaderCards, boolean isEndTurn) throws IOException {
        LeaderStartSceneController lssc = new LeaderStartSceneController(leaderCards, isEndTurn);
        lssc.addAllClientObservers(clientObservers);
        //Platform.runLater(()-> SceneController.LeaderStartPopup(lssc, "leader_popup.fxml"));
        //TODO: Fare costruttori con il controller anche per i popup.
        // Consiglio: meglio fare uno showPopup che va bene per tutti (come changeRootPane) piuttosto che un metodo specifico per ognuno, tanto sono tutti uguali,
        // al massimo se c'è bisogno di settare qualche parametro particolare si fanno degli override del costruttore
    }


    @Override
    public void displayDisconnectedMsg(String nicknameWhoDisconnected, String text) {

    }


    @Override
    public void displayMarket(MarketTray marketTray) {

    }

    @Override
    public void displayWinningMsg(String win) {

    }

    @Override
    public void displayToken(Token token) {

    }

    @Override
    public void forcedEnd(String nickname) {
        Platform.runLater(() -> SceneController.showPopup(clientObservers, nickname + " left the game. The match ends now."));
        System.exit(0);
    }

    @Override
    public void displayGenericMessage(String genericMessage) throws IOException {
        //used only in CLI mode
    }
}
