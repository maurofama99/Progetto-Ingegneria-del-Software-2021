package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.PopeSpace;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.network.client.ServerHandler;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.messagescs.PlayersNumber;
import it.polimi.ingsw.network.messagescs.ResourcePlacement;
import it.polimi.ingsw.network.messagescs.ResourceTypeChosen;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Cli extends ClientObservable implements View {

    private ServerHandler serverHandler;
    private String nickname;

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void fetchPlayersNumber() {
        System.out.println("Number of players?");
        Scanner scanner = new Scanner(System.in);
        int numPlayers = scanner.nextInt();
        notifyObservers(new PlayersNumber(numPlayers));
    }

    @Override
    public void fetchNickname() {
        System.out.println("What's your nickname?");
        Scanner scanner = new Scanner(System.in);
        String nickname = scanner.nextLine();
        this.nickname = nickname;
        notifyObservers(new LoginData(nickname));
    }

    @Override
    public void fetchResourceType() throws IOException {
        System.out.println("You can choose a type of resource you want:\n---------------------\n" +
                        "Type '0' for a SHIELD\n" +
                        "Type '1' for a SERVANT\n" +
                        "Type '2' for a COIN\n" +
                        "Type '3' for a STONE\n---------------------\n");
        Scanner scanner = new Scanner(System.in);
        int resourceType = Integer.parseInt(scanner.nextLine());
        notifyObservers(new ResourceTypeChosen(nickname, resourceType));
    }

    @Override
    public void fetchResourcePlacement() throws IOException {
        System.out.println("Where do you want to place this resource? ");
        //out print del depot
        Scanner scanner = new Scanner(System.in);
        int floor = Integer.parseInt(scanner.nextLine());
        notifyObservers(new ResourcePlacement(nickname, floor));
    }


    @Override
    public void displayGenericMessage(String genericMessage) throws IOException {
        System.out.println(genericMessage);
    }

    @Override
    public void displayLoginResult(boolean nicknameIsOk, boolean connectionIsOk, String nickname) {

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
