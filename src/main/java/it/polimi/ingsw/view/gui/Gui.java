package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.PopeSpace;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.observerPattern.Observable;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gui extends Observable implements View {
    @Override
    public void fetchNickname() {

    }

    @Override
    public void fetchResourceType() throws IOException {

    }

    @Override
    public void fetchResourcePlacement() throws IOException {

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
    public void fetchDoneAction(String message) throws IOException {

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
