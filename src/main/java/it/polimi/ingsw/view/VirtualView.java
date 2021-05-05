package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.PopeSpace;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagessc.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observerPattern.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VirtualView implements View, Observer {
    private ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void fetchNickname() throws IOException {
        clientHandler.sendMessage(new LoginRequest());
    }

    @Override
    public void fetchPlayersNumber() throws IOException {
       clientHandler.sendMessage(new NumPlayersRequest());
    }

    @Override
    public void fetchResourceType() throws IOException {
        clientHandler.sendMessage(new AskResourceType());
    }

    @Override
    public void fetchResourcePlacement() throws IOException {
        clientHandler.sendMessage(new AskResourcePlacement());
    }


    @Override
    public void displayGenericMessage(String genericMessage) throws IOException {
        clientHandler.sendMessage(new GenericMessage(genericMessage));
    }

    @Override
    public void displayLeaderCards(ArrayList<LeaderCard> leaderCards) throws IOException {

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

    @Override
    public void update(Message message) throws IOException {
        if (clientHandler.getNickname().equals(message.getReceiverNickname())) {
            clientHandler.sendMessage(message);
        }
    }
}