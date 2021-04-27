package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.PopeSpace;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.network.client.ServerHandler;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.view.View;

import java.util.List;
import java.util.Scanner;

public class Cli implements View {

    private ServerHandler serverHandler;

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void fetchNickname() {
        System.out.println("What's your nickname?");
        Scanner scanner = new Scanner(System.in);
        String nickname = scanner.nextLine();
        serverHandler.sendMessage(new LoginData(nickname));
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
