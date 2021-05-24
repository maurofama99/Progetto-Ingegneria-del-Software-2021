package it.polimi.ingsw.view.cli;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.faithtrack.PopeSpace;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.network.messagescs.*;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.*;

public class Cli extends ClientObservable implements View {

    private CliGraphics cliGraphics = new CliGraphics();
    private String nickname;
    private ModelView modelView;

    public Cli() {
        this.modelView = new ModelView(this);
    }

    @Override
    public void fetchNickname() {
        System.out.print("What's your nickname?\n>");
        Scanner scanner = new Scanner(System.in);
        String nickname = scanner.nextLine();
        nickname = nickname.replaceAll("\\s+","");
        this.nickname = nickname;
        System.out.print("How many players do you want to play with? (Max 4 players. Type 1 for single player)\n>");
        int numPlayers = -1;
        while(numPlayers<1 || numPlayers>4){
            try {
                numPlayers = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.print("This is not a number, try again!\n>");
                scanner.nextLine();
            }
        }
        notifyObservers(new LoginData(nickname, numPlayers));
    }

    @Override
    public void fetchResourceType() throws IOException {
        System.out.print("You can choose a type of resource you want:\n---------------------\n" +
                        "Type '0' for a SHIELD\n" +
                        "Type '1' for a SERVANT\n" +
                        "Type '2' for a COIN\n" +
                        "Type '3' for a STONE\n" + "---------------------\n>");
        Scanner scanner = new Scanner(System.in);
        int resourceType = -1;
        while (resourceType < 0 || resourceType > 3){
            try {
                resourceType = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.print("This is not a number, try again!\n>");
                scanner.nextLine();
            }
        }
        notifyObservers(new ResourceTypeChosen(nickname, resourceType));
    }

    @Override
    public void fetchResourcePlacement() throws IOException {
        int floorInt;
        Scanner scanner = new Scanner(System.in);
        System.out.print(">");
        String floor = scanner.nextLine();
        floor = floor.replaceAll("\\s+","");

        try{
            floorInt = Integer.parseInt(floor);
        } catch (NumberFormatException e){
            floorInt = -1;
        }

        while(!floor.equalsIgnoreCase("switch")
                && !floor.equalsIgnoreCase("discard")
                && !floor.equalsIgnoreCase("extra")
                && (floorInt > 3 || floorInt < 1)) {
            System.out.println("\nInvalid Input, retry");
            floor = scanner.nextLine();
            floor = floor.replaceAll("\\s+","");
            try{
                floorInt = Integer.parseInt(floor);
            } catch (NumberFormatException e){
                floorInt = -1;
            }
        }

        if (floor.equalsIgnoreCase("switch")) {
            System.out.print("Which floors do you want to switch?\nSource floor (1,2,3): >");
            int sourceFloor = -1;
            while(sourceFloor < 1 || sourceFloor > 3){
                try {
                    sourceFloor = scanner.nextInt();
                } catch (InputMismatchException e){
                    System.out.println("This is not a number..");
                    scanner.nextLine();
                }
            }
            System.out.print("Destination floor (1,2,3):  >");
            int destFloor = -1;
            while(destFloor < 1 || destFloor > 3){
                try {
                    destFloor = scanner.nextInt();
                } catch (InputMismatchException e){
                    System.out.println("This is not a number..");
                    scanner.nextLine();
                }
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
        System.out.print("Choose two LeaderCard you want to discard (Insert index, press enter): ");
        Scanner scanner = new Scanner(System.in);
        int index = -1;
        while(index < 1 || index > 4){
            try {
                System.out.print("\n>");
                index = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("This is not a number..");
                scanner.nextLine();
            }
        }
        int index2 = -1;
        while(index2 < 1 || index2 > 4 || index2==index){
            try {
                System.out.print(">");
                index2 = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("This is not a number..");
                scanner.nextLine();
            }
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
        modelView.setLeaderCards(leaderCards);
    }

    @Override
    public void displayMarket(MarketTray marketTray) {
        System.out.println(cliGraphics.showMarketTray(marketTray));
        modelView.setMarketTray(marketTray);
    }

    @Override
    public void displayPopup(String message) {
        //used only in GUI mode
    }

    @Override
    public void displayDevCards(DevCard[][] devCards) throws IOException {
        cliGraphics.printMatrixDevCards(devCards);
        modelView.setShowedDeck(devCards);
    }

    @Override
    public void displayPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse) {
        modelView.setSlots(slots);
        modelView.setWarehouse(warehouse);
        modelView.setFaithTrack(faithTrack);
        System.out.println(faithTrack.toString());
        cliGraphics.printPersonalBoard(warehouse, slots);
    }

    @Override
    public void fetchPlayerAction(String message) throws IOException {
        boolean rowOrCol = false;
        int index = 0;
        int row, col, slot;
        System.out.print(message + ">");
        Scanner scanner = new Scanner(System.in);
        String action = scanner.nextLine();
        action = action.replaceAll("\\s+","");
        while (!(action.equalsIgnoreCase("market")) && !(action.equalsIgnoreCase("buy")) && !(action.equalsIgnoreCase("production"))){
            System.out.print("Invalid input\n" + message + ">");
            action = scanner.nextLine();
            action = action.replaceAll("\\s+","");
        }
        action = action.toUpperCase();

        switch (action) {
            case "MARKET":
                cliGraphics.printMarketDepot(modelView.getMarketTray(), modelView.getWarehouse());
                System.out.print("Do you want to pick a row or a column of the tray? (row/col)\n>");
                String decision = scanner.nextLine();
                decision = decision.replaceAll("\\s+","");
                while (!decision.equalsIgnoreCase("row") && !decision.equalsIgnoreCase("col")){
                    System.out.print("Invalid input, try again!\n>");
                    decision = scanner.nextLine();
                    decision = decision.replaceAll("\\s+","");
                }
                if (decision.equalsIgnoreCase("row")) {
                    rowOrCol = true;
                    System.out.print("Which row do you want to pick? (1,2,3)\n>");
                    index = -1;
                    while (index<1 || index>3){
                        try {
                            index = scanner.nextInt();
                        } catch (InputMismatchException e){
                            System.out.println("This is not a number..");
                            scanner.nextLine();
                        }
                    }
                } else if (decision.equalsIgnoreCase("col")) {
                    System.out.print("Which column do you want to pick? (1,2,3,4)\n>");
                    index = -1;
                    while (index<1 || index>4){
                        try {
                            index = scanner.nextInt();
                        } catch (InputMismatchException e){
                            System.out.println("This is not a number..");
                            scanner.nextLine();
                        }
                    }
                }
                notifyObservers(new GoingMarket(index, rowOrCol));
                break;

            case "BUY":
                System.out.print("Select row of the devCard you want to buy: (1,2,3)\n>");
                row = -1;
                while (row<1 || row>3){
                    try {
                        row = scanner.nextInt();
                    } catch (InputMismatchException e){
                        System.out.println("This is not a number..");
                        scanner.nextLine();
                    }
                }
                System.out.print("Select column of the devCard you want to buy: (1,2,3,4)\n>");
                col = -1;
                while (col<1 || col>4){
                    try {
                        col = scanner.nextInt();
                    } catch (InputMismatchException e){
                        System.out.println("This is not a number..");
                        scanner.nextLine();
                    }
                }
                System.out.print("Where do you want to place it: (Insert the number of the slot: 1,2,3)\n>");
                slot = -1;
                while (slot<1 || slot>3){
                    try {
                        slot = scanner.nextInt();
                    } catch (InputMismatchException e){
                        System.out.println("This is not a number..");
                        scanner.nextLine();
                    }
                }
                notifyObservers(new BuyDevCard(row, col, slot));
                break;

            case "PRODUCTION":
                System.out.println("Type 1 if you want to activate basic production, 0 if you don't");
                int yesORnoB = -1;
                while (yesORnoB!=0 && yesORnoB!=1){
                    try {
                        yesORnoB = scanner.nextInt();
                    } catch (InputMismatchException e){
                        System.out.println("This is not a number..");
                        scanner.nextLine();
                    }
                }
                System.out.println("Type 1 if you want to activate the production, 0 if you don't:\n");
                System.out.print("SLOT 1:  >");
                int yesORno1 = -1;
                while (yesORno1!=0 && yesORno1!=1){
                    try {
                        yesORno1 = scanner.nextInt();
                    } catch (InputMismatchException e){
                        System.out.println("This is not a number..");
                        scanner.nextLine();
                    }
                }
                System.out.print("SLOT 2:  >");
                int yesORno2 = -1;
                while (yesORno2!=0 && yesORno2!=1){
                    try {
                        yesORno2 = scanner.nextInt();
                    } catch (InputMismatchException e){
                        System.out.println("This is not a number..");
                        scanner.nextLine();
                    }
                }
                System.out.print("SLOT 3:  >");
                int yesORno3 = -1;
                while (yesORno3!=0 && yesORno3!=1){
                    try {
                        yesORno3 = scanner.nextInt();
                    } catch (InputMismatchException e){
                        System.out.println("This is not a number..");
                        scanner.nextLine();
                    }
                }
                notifyObservers(new ActivateProduction(yesORnoB, yesORno1, yesORno2, yesORno3));
                break;

        }
    }

    @Override
    public void fetchDoneAction(String message, ArrayList<LeaderCard> leaderCards) throws IOException {
        System.out.print(message + "\n>");
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
        System.out.print("\nDo you want to activate or discard a leader card? (Type ACTIVATE or DISCARD or NO)\n>");
        Scanner scanner = new Scanner(System.in);
        String action = scanner.nextLine();
        action = action.replaceAll("\\s+","");
        while (!action.equalsIgnoreCase("activate") && !action.equalsIgnoreCase("discard") && !action.equalsIgnoreCase("no")){
            System.out.print("Invalid input, try again!\n>");
            action = scanner.nextLine();
            action = action.replaceAll("\\s+","");
        }

        if (action.equalsIgnoreCase("ACTIVATE")) {
            System.out.print("Choose the leader card you want to activate (insert index)\n>");
            int index = chooseLeader(leaderCards, scanner);
            notifyObservers(new ActivateLeader(leaderCards.get(index - 1)));
        } else if (action.equalsIgnoreCase("DISCARD")){
            System.out.print("Choose the leader card you want to discard (insert index)\n>");
            int index = chooseLeader(leaderCards, scanner);
            notifyObservers(new DiscardOneLeader(index - 1));
        } else if (action.equalsIgnoreCase("NO")){
            afterLeaderAction(isEndTurn, leaderCards);
        }

    }

    private int chooseLeader(ArrayList<LeaderCard> leaderCards, Scanner scanner) {
        cliGraphics.showLeaderCards(leaderCards);
        int index = -1;
        while (index > leaderCards.size() || index < 1){
            try {
                index = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("This is not a number..");
                scanner.nextLine();
            }
        }
        return index;
    }

    public void afterLeaderAction(boolean trueOrFalse, ArrayList<LeaderCard> leaderCards) throws IOException {
        if (!trueOrFalse)
            fetchPlayerAction("\nWhat do you wanna do now? (Type MARKET, PRODUCTION, BUY)\n");
        else
            fetchDoneAction("Type DONE (attenzione: potrebbe scrivere anche leader, è giusto?", leaderCards);
    }



    @Override
    public void displayDisconnectedMsg(String nicknameWhoDisconnected, String text) {

    }


    @Override
    public void displayWinningMsg(String win) {

    }

    @Override
    public void displayToken(Token token) {
        cliGraphics.printLorenzo(token);
    }


}
