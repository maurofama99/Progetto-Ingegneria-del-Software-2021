package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.PopeSpace;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.client.ServerHandler;
import it.polimi.ingsw.network.messagescs.*;
import it.polimi.ingsw.network.messagessc.AskAction;
import it.polimi.ingsw.network.messagessc.NoAvailableResources;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.*;

public class Cli extends ClientObservable implements View {


    private CliGraphics cliGraphics = new CliGraphics();
    private String nickname;


    @Override
    public void fetchNickname() {
        System.out.println("What's your nickname?");
        Scanner scanner = new Scanner(System.in);
        String nickname = scanner.nextLine();
        nickname = nickname.replaceAll("\\s+","");
        this.nickname = nickname;
        System.out.println("How many players do you want to play with? (Max 4 players. Type 1 for single player)");
        int numPlayers = scanner.nextInt();
        while(numPlayers<1 || numPlayers>4){
            System.out.println("\nInvalid number");
            numPlayers = scanner.nextInt();
        }
        notifyObservers(new LoginData(nickname, numPlayers));
    }

    @Override
    public void fetchResourceType() throws IOException {
        System.out.println("You can choose a type of resource you want:\n---------------------\n" +
                        "Type '0' for a SHIELD\n" +
                        "Type '1' for a SERVANT\n" +
                        "Type '2' for a COIN\n" +
                        "Type '3' for a STONE\n" + "---------------------\n");
        Scanner scanner = new Scanner(System.in);
        int resourceType = scanner.nextInt();
        while (resourceType < 0 || resourceType > 3){
            System.out.println("Invalid input\n\n---------------------\nType '0' for a SHIELD\n" +
                                                "Type '1' for a SERVANT\n" +
                                                "Type '2' for a COIN\n" +
                                                "Type '3' for a STONE\n---------------------\n");
            resourceType = scanner.nextInt();
        }
        notifyObservers(new ResourceTypeChosen(nickname, resourceType));
    }

    @Override
    public void fetchResourcePlacement() throws IOException {
        //out print del depot
        Scanner scanner = new Scanner(System.in);
        String floor = scanner.nextLine();
        floor = floor.replaceAll("\\s+","");

        while(!floor.equalsIgnoreCase("switch")
                && !floor.equalsIgnoreCase("discard")
                && Integer.parseInt(floor) > 3 && Integer.parseInt(floor) < 1) {
            System.out.println("\nInvalid Input, retry");
            floor = scanner.nextLine();
            floor = floor.replaceAll("\\s+","");
        }

        if (floor.equalsIgnoreCase("switch")) {
            System.out.print("Which floors do you want to switch?\nSource floor (1,2,3): ");
            int sourceFloor = scanner.nextInt();
            while(sourceFloor < 1 || sourceFloor > 3){
                System.out.println("\nInvalid Input, retry");
                sourceFloor = scanner.nextInt();
            }
            System.out.print("Destination floor (1,2,3): ");
            int destFloor = scanner.nextInt();
            while(destFloor < 1 || destFloor > 3){
                System.out.println("\nInvalid Input, retry");
                destFloor = scanner.nextInt();
            }
            notifyObservers(new ResourcePlacement(nickname, floor, sourceFloor, destFloor));
        }
        else notifyObservers(new ResourcePlacement(nickname, floor));
    }

    @Override
    public void fetchSwapWhite(ResourceType type1, ResourceType type2) throws IOException{
        System.out.println("Do you want a " + type1 + " or a " + type2 + "?");
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine();
        type = type.replaceAll("\\s+","");
        while (!(type.equalsIgnoreCase("servant")) && !(type.equalsIgnoreCase("shield")) && !(type.equalsIgnoreCase("stone")) && !(type.equalsIgnoreCase("coin")) ){
            System.out.println("\nInvalid input\nDo you want a " + type1 + " or a " + type2 + "?");
            type = scanner.nextLine();
            type = type.replaceAll("\\s+","");
        }
        notifyObservers(new SwappedResource(nickname, type));
    }


    @Override
    public void displayGenericMessage(String genericMessage) throws IOException {
        System.out.println(genericMessage);
    }

    @Override
    public void displayLeaderCards(ArrayList<LeaderCard> leaderCards) throws IOException {
        System.out.println("\nYou can choose two of these leaderCards that you are going to activate during the game!!");
        cliGraphics.showLeaderCards(leaderCards);
        System.out.println("Choose two LeaderCard you want to discard (Insert index, press enter): ");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        while(index < 1 || index > 4){
            System.out.println("\nInvalid Input, retry");
            index = scanner.nextInt();
        }
        int index2 = scanner.nextInt();
        while(index2 < 1 || index2 > 4 || index2==index){
            System.out.println("\nInvalid Input, retry");
            index2 = scanner.nextInt();
        }
        notifyObservers(new DiscardLeader(nickname, (index-1), (index2-1)));
        ArrayList<Integer> indexes = new ArrayList<>();
        indexes.add(index-1);
        indexes.add(index2-1);
        indexes.sort(Collections.reverseOrder());
        for (int ind : indexes){
            leaderCards.remove(ind);
        }
        System.out.println("\n\nNow you own these two leader cards.\nYou can activate them at the beginning or at the end of your turn.\n");
        cliGraphics.showLeaderCards(leaderCards);
    }

    @Override
    public void fetchPlayerAction(String message) throws IOException {
        boolean rowOrCol = false;
        int index = 0;
        int row, col, slot;
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        String action = scanner.nextLine();
        action = action.toUpperCase().replaceAll("\\s+","");
        while (!(action.equalsIgnoreCase("market")) && !(action.equalsIgnoreCase("buy")) && !(action.equalsIgnoreCase("production"))){
            System.out.println("Invalid input\n" + message);
            action = scanner.nextLine();
            action = action.replaceAll("\\s+","");
        }

        switch (action) {
            case "MARKET":
                System.out.println("Do you want to pick a row or a column of the tray? (row/col)");
                String decision = scanner.nextLine();
                decision = decision.replaceAll("\\s+","");
                while (!decision.equalsIgnoreCase("row") && !decision.equalsIgnoreCase("col")){
                    System.out.println("\nInvalid input");
                    decision = scanner.nextLine();
                    decision = decision.replaceAll("\\s+","");
                }
                if (decision.equalsIgnoreCase("row")) {
                    rowOrCol = true;
                    System.out.println("Which row do you want to pick? (1,2,3)");
                    index = scanner.nextInt();
                    while (index<1 || index>3){
                        System.out.println("\nInvalid input");
                        index = scanner.nextInt();
                    }
                } else if (decision.equalsIgnoreCase("col")) {
                    System.out.println("Which column do you want to pick? (1,2,3,4)");
                    index = scanner.nextInt();
                    while (index<1 || index>4){
                        System.out.println("\nInvalid input");
                        index = scanner.nextInt();
                    }
                }
                notifyObservers(new GoingMarket(index, rowOrCol));
                break;

            case "BUY":
                System.out.println("Select row of the devCard you want to buy: (1,2,3)");
                row = scanner.nextInt();
                while (row<1 || row>3){
                    System.out.println("\nInvalid input");
                    row = scanner.nextInt();
                }
                System.out.println("Select column of the devCard you want to buy: (1,2,3,4)");
                col = scanner.nextInt();
                while (col<1 || col>4){
                    System.out.println("\nInvalid input");
                    col = scanner.nextInt();
                }
                System.out.println("Where do you want to place it: (Insert the number of the slot: 1,2,3)");
                slot = scanner.nextInt();
                while (slot<1 || slot>3){
                    System.out.println("\nInvalid input");
                    slot = scanner.nextInt();
                }
                notifyObservers(new BuyDevCard(row, col, slot));
                break;

            case "PRODUCTION":
                System.out.println("Type 1 if you want to activate basic production, 0 if you don't");
                int yesORnoB = scanner.nextInt();
                while (yesORnoB!=0 && yesORnoB!=1){
                    System.out.println("\nInvalid input");
                    yesORnoB = scanner.nextInt();
                }
                System.out.println("Type 1 if you want to activate the production, 0 if you don't:\n");
                System.out.print("SLOT 1:  >");
                int yesORno1 = scanner.nextInt();
                while (yesORno1!=0 && yesORno1!=1){
                    System.out.println("\nInvalid input");
                    yesORno1 = scanner.nextInt();
                }
                System.out.print("  SLOT 2:  >");
                int yesORno2 = scanner.nextInt();
                while (yesORno2!=0 && yesORno2!=1){
                    System.out.println("\nInvalid input");
                    yesORno2 = scanner.nextInt();
                }
                System.out.print("  SLOT 3:  >");
                int yesORno3 = scanner.nextInt();
                while (yesORno3!=0 && yesORno3!=1){
                    System.out.println("\nInvalid input");
                    yesORno3 = scanner.nextInt();
                }
                notifyObservers(new ActivateProduction(yesORnoB, yesORno1, yesORno2, yesORno3));
                break;

        }
    }

    @Override
    public void fetchDoneAction(String message, ArrayList<LeaderCard> leaderCards) throws IOException {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        answer = answer.replaceAll("\\s+","");
        while (!answer.equalsIgnoreCase("done") && !answer.equalsIgnoreCase("leader")){
            System.out.println("\nInvalid input");
            answer = scanner.nextLine();
            answer = answer.replaceAll("\\s+","");
        }

        if (answer.equalsIgnoreCase("DONE")){
            notifyObservers(new DoneAction());
        } else if (answer.equalsIgnoreCase("LEADER")){
            fetchPlayLeader(leaderCards, true);
        }
    }

    @Override
    public void fetchPlayLeader(ArrayList<LeaderCard> leaderCards, boolean isEndTurn) throws IOException {
        cliGraphics.showLeaderCards(leaderCards);
        System.out.println("\nDo you want to activate or discard a leader card? (Type ACTIVATE or DISCARD or NO)");
        Scanner scanner = new Scanner(System.in);
        String action = scanner.nextLine();
        action = action.replaceAll("\\s+","");
        while (!action.equalsIgnoreCase("activate") && !action.equalsIgnoreCase("discard") && !action.equalsIgnoreCase("no")){
            System.out.println("\nInvalid input");
            action = scanner.nextLine();
            action = action.replaceAll("\\s+","");
        }

        if (action.equalsIgnoreCase("ACTIVATE")) {
            System.out.println("Choose the leader card you want to activate (insert index)");
            cliGraphics.showLeaderCards(leaderCards);
            int index = scanner.nextInt();
            while (index > leaderCards.size() || index < 1){
                System.out.println("\nInvalid input");
                index = scanner.nextInt();
            }
            notifyObservers(new ActivateLeader(leaderCards.get(index - 1)));
        } else if (action.equalsIgnoreCase("DISCARD")){
            System.out.println("Choose the leader card you want to discard (insert index)");
            cliGraphics.showLeaderCards(leaderCards);
            int index = scanner.nextInt();
            while (index > leaderCards.size() || index < 1){
                System.out.println("\nInvalid input");
                index = scanner.nextInt();
            }
            notifyObservers(new DiscardOneLeader(index - 1));
        } else if (action.equalsIgnoreCase("NO")){
            afterLeaderAction(isEndTurn, leaderCards);
        }

    }

    public void afterLeaderAction(boolean trueOrFalse, ArrayList<LeaderCard> leaderCards) throws IOException {
        if (!trueOrFalse)
            fetchPlayerAction("\nWhat do you wanna do now? (Type MARKET, PRODUCTION, BUY)\n");
        else
            fetchDoneAction("Type DONE (attenzione: potrebbe scrivere anche leader, è giusto?", leaderCards);
    }

    @Override
    public void displayPersonalBoard(PersonalBoard personalBoard) {
        System.out.println(personalBoard.toString());
    }


    @Override
    public void displayDisconnectedMsg(String nicknameWhoDisconnected, String text) {

    }

    @Override
    public void displayErrorMsg(String errorMsg) {

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
        System.out.println(marketTray);
    }


    @Override
    public void displayWinningMsg(String win) {

    }



}
