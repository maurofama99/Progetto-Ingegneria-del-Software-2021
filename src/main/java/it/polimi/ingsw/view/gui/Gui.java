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
import javafx.event.Event;

import java.io.IOException;
import java.util.ArrayList;

public class Gui extends ClientObservable implements View {

    private final static  String ERROR = "ERROR";
    private final static String START_SCENE = "start_scene.fxml";
    private Client client;
    private final ModelView modelView;

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
    public void localFetchNickname(Event event) {
        Platform.runLater(() ->((PlayerLoginSceneController)SceneController.changeRootPane(clientObservers, event, "player_login_scene.fxml")).setSolo(true));

    }

    @Override
    public void fetchResourceType() {
        Platform.runLater(() -> SceneController.changeRootPane(clientObservers, "resource_choosing.fxml"));
    }

    @Override
    public void fetchResourcePlacement(Resource resource) {
        Platform.runLater(() -> {
            DepoPopupController dpc = new DepoPopupController(resource, modelView);
            dpc.addAllClientObservers(clientObservers);
            SceneController.showPopup(dpc, "depo_popup.fxml");
        });
    }

    @Override
    public void fetchSwapWhite(ResourceType type1, ResourceType type2) {

    }

    @Override
    public void fetchExtraProd(Resource resource) {

    }

    @Override
    public void displayPopup(String message) {
        Platform.runLater(() -> {
            PopupSceneController psc = new PopupSceneController(message);
            psc.setTitleLabel("Info");
            psc.addAllClientObservers(clientObservers);
            SceneController.showPopup(psc, "popup_scene.fxml");
        });
    }

    @Override
    public void displayLeaderCards(ArrayList<LeaderCard> leaderCards) {
        LeaderCardChoosingController llcc = new LeaderCardChoosingController();
        llcc.setLeaderCards(leaderCards);
        llcc.addAllClientObservers(clientObservers);
        Platform.runLater(() -> SceneController.changeRootPane(llcc, "leader_choosing.fxml"));
    }

    @Override
    public void displayDevCards(DevCard[][] devCards) {
        modelView.setShowedDeck(devCards);
    }

    @Override
    public void displayPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse) {
        modelView.setFaithTrack(faithTrack);
        modelView.setSlots(slots);
        modelView.setWarehouse(warehouse);

    }

    @Override
    public void updateOtherPersonalBoard(String name, FaithTrack fT, Slot[] slots, SerializableWarehouse wH, ArrayList<LeaderCard> lC) {
        modelView.updateOthersPB(name, fT, slots, wH, lC);
    }

    @Override
    public void displayGUIPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse) throws IOException{
        Platform.runLater(()->{
            PersonalBoardSceneController pbsc = new PersonalBoardSceneController(modelView);
            pbsc.addAllClientObservers(clientObservers);
            SceneController.changeRootPane(pbsc, "personal_board.fxml");
        });
    }

    @Override
    public void fetchPlayerAction(String message) {
        Platform.runLater(() -> {
            ActionPopupController apc = new ActionPopupController(modelView);
            apc.addAllClientObservers(clientObservers);
            SceneController.showPopup(apc, "action_popup.fxml");
        });
    }

    @Override
    public void fetchDoneAction(String message, ArrayList<LeaderCard> leaderCards) throws IOException {
        Platform.runLater(()-> {
            LeaderStartSceneController lssc = new LeaderStartSceneController(leaderCards, true, modelView);
            lssc.addAllClientObservers(clientObservers);
            SceneController.showPopup(lssc, "leader_popup.fxml");
        });
    }

    @Override
    public void fetchPlayLeader(ArrayList<LeaderCard> leaderCards, boolean isEndTurn) {
        Platform.runLater(()-> {
            LeaderStartSceneController lssc = new LeaderStartSceneController(leaderCards, isEndTurn, modelView);
            lssc.addAllClientObservers(clientObservers);
            SceneController.showPopup(lssc, "leader_popup.fxml");
        });
    }


    @Override
    public void displayDisconnectedMsg(String nicknameWhoDisconnected, String text) {

    }


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
        System.exit(0);
    }

    @Override
    public void displayToken(Token token) {

    }

    @Override
    public void forcedEnd(String nickname) {
        Platform.runLater(() -> {
            PopupSceneController psc = new PopupSceneController(nickname + " left the game. The match ends now.");
            psc.addAllClientObservers(clientObservers);
            psc.setTitleLabel("Disconnection Alert");
            SceneController.showPopup(psc, "popup_scene.fxml");
        });
        System.exit(0);
    }

    @Override
    public void displayGenericMessage(String genericMessage) {
        //used only in CLI mode
    }
}
