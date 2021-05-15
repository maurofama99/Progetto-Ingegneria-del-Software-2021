package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.PopeSpace;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.observerPattern.Observable;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.scenes.PlayerLoginSceneController;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gui extends ClientObservable implements View {

    private final static  String ERROR = "ERROR";
    private final static String START_SCENE = "start_scene.fxml";
    private String nickname;

    @Override
    public void fetchNickname() {
        PlayerLoginSceneController plsc = new PlayerLoginSceneController();
    }

    @Override
    public void fetchResourceType() throws IOException {

    }

    @Override
    public void fetchResourcePlacement() throws IOException {

    }

    @Override
    public void fetchSwapWhite(ResourceType type1, ResourceType type2) throws IOException {

    }

    @Override
    public void displayGenericMessage(String genericMessage) throws IOException {

    }

    @Override
    public void displayLeaderCards(ArrayList<LeaderCard> leaderCards) throws IOException {

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
    public void displayPersonalBoard(PersonalBoard personalBoard) {

    }

    @Override
    public void displayStatus(List<String> players, List<PersonalBoard> personalBoards, String playingPlayer) {

    }

    @Override
    public void displayTable(Table t) {

    }

    @Override
    public void displayEffect(LeaderCard leaderCard) {

    }

    @Override
    public void fetchSlotChoice(List<Slot> slots) {

    }

    @Override
    public void displayPopeSpaceActivation(PopeSpace popeSpace) {

    }

    @Override
    public void displayMarket(MarketTray marketTray) {

    }


    @Override
    public void displayWinningMsg(String win) {

    }
}
