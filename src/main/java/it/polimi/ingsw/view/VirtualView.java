package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.PopeSpace;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.List;

public class VirtualView implements View{

    private final ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }


    @Override
    public void fetchNickname() {

    }

    @Override
    public void fetchPlayersNumber() {

    }

    @Override
    public void displayLoginResult(boolean nicknameIsOk, boolean connectionIsOk, String nickname) {

    }

    @Override
    public void displayGenericMsg(String genericMessage) {

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
    public void displayWaitingRoom(List<String> nicknames, int numPlayers) {

    }

    @Override
    public void fetchFirstPlayer(List<String> nicknames) {

    }

    @Override
    public void displayWinningMsg(String win) {

    }
}
