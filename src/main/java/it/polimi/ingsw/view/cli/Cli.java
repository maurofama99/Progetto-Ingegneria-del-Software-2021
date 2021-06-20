package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.network.messagescs.*;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.View;
import javafx.event.Event;

import java.io.IOException;
import java.util.*;

/**
 * The CLI class contains every method used during a match to do an action and respond to it. Methods notify the
 * observer and act accordingly.
 */
public class Cli extends ClientObservable implements View {

    private CliGraphics cliGraphics = new CliGraphics();
    private String nickname;
    private boolean solo = false;
    private final ModelView modelView;
    private boolean firstPlayer;

    public Cli() {
        this.modelView = new ModelView(this);
    }

    public void setSolo(boolean solo) {
        this.solo = solo;
    }

    @Override
    public void setFirstPlayer(boolean var) {this.firstPlayer=var;}

    /**
     * Asks for nickname and number of players to set the lobby of the match.
     */
    @Override
    public void fetchNickname() {
        System.out.print("What's your nickname?\n>");
        Scanner scanner = new Scanner(System.in);
        String nickname = scanner.nextLine();
        nickname = nickname.replaceAll("\\s+","");
        this.nickname = nickname;
        System.out.print("How many players do you want to play with? (Max 4 players. Type 1 for single player)\n>");
        int numPlayers = -1;
        while (numPlayers < 1 || numPlayers > 4) {
            try {
                numPlayers = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("This is not a number, try again!\n>");
                scanner.nextLine();
            }
        }
        notifyObservers(new LoginData(nickname, numPlayers));
    }

    /**
     * Same for the fetchNickname, but for a local game
     * @param event the event that caused the game to be local
     */
    @Override
    public void localFetchNickname(Event event) {
        System.out.print("What's your nickname?\n>");
        Scanner scanner = new Scanner(System.in);
        String nickname = scanner.nextLine();
        nickname = nickname.replaceAll("\\s+","");
        this.nickname = nickname;
        notifyObservers(new LoginData(nickname, 1));
    }

    /**
     * Asks the player for the resource he wants, the player chooses using an integer from 0 to 3.
     */
    @Override
    public void fetchResourceType() {
        System.out.println(cliGraphics.printDepot(modelView.getWarehouse().getFloors()));

        System.out.print(" - 0 --> SHIELD" + cliGraphics.printRes(new Resource(1, ResourceType.SHIELD)) + "\n" +
                " - 1 --> SERVANT" + cliGraphics.printRes(new Resource(1, ResourceType.SERVANT)) + "\n" +
                " - 2 --> COIN" + cliGraphics.printRes(new Resource(1, ResourceType.COIN)) + "\n" +
                " - 3 --> STONE" + cliGraphics.printRes(new Resource(1, ResourceType.STONE)) + "\n\n>");

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

    /**
     * The player provides a floor and the resource is placed if possible. Also gives the possibility to the player
     * to choose the switch action, the discard action or put the resource in an extra depot if present
     * @param resource the resource to place
     * @throws IOException
     */
    @Override
    public void fetchResourcePlacement(Resource resource) throws IOException {
        System.out.println(cliGraphics.printDepot(modelView.getWarehouse().getFloors()));
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
            System.out.println("\nInvalid Input, try again!\n>");
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
                    System.out.println("This is not a number..\n>");
                    scanner.nextLine();
                }
            }
            System.out.print("Destination floor (1,2,3):  >");
            int destFloor = -1;
            while(destFloor < 1 || destFloor > 3){
                try {
                    destFloor = scanner.nextInt();
                } catch (InputMismatchException e){
                    System.out.println("This is not a number..\n>");
                    scanner.nextLine();
                }
            }
            notifyObservers(new ResourcePlacement(nickname, floor, sourceFloor, destFloor));
        }
        else notifyObservers(new ResourcePlacement(nickname, floor));
    }

    /**
     * If a double swap white effect is active, it asks the player which one it wants for that market session
     * @param type1 first swap white
     * @param type2 second swap white
     * @throws IOException
     */
    @Override
    public void fetchSwapWhite(ResourceType type1, ResourceType type2) throws IOException{
        System.out.println("Do you want a " + type1.getResourceName() + " or a " + type2.getResourceName() + "?");
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine().replaceAll("\\s+","");
        while (!(type.equalsIgnoreCase(type1.getResourceName())) && !(type.equalsIgnoreCase(type2.getResourceName()))){
            System.out.println("Invalid input\nDo you want a " + type1 + " or a " + type2 + "?");
            type = scanner.nextLine().replaceAll("\\s+","");
        }
        notifyObservers(new SwappedResource(nickname, type));
    }

    /**
     * If an extra production is active from a leader card, the player can activate it from here
     * @param resource the resource that has to be provided
     * @throws IOException
     */
    @Override
    public void fetchExtraProd(Resource resource) throws IOException {
        StringBuilder s = new StringBuilder();
        System.out.println("You can also activate the production on your leader card: \n" +
                s.append(cliGraphics.printRes(resource)).append("   ➡︎  ？ + ").append(CliColor.ANSI_RED.escape()).append("† ").append(CliColor.RESET) +
                "Do you want to spend " + resource.getType() + "for a faith point and a resource you can chose?\n" +
                "-type yes/no");
        Scanner scanner = new Scanner(System.in);
        String y_n = scanner.nextLine();
        y_n = y_n.replaceAll("\\s+","");
        while (!(y_n.equalsIgnoreCase("yes")) && !(y_n.equalsIgnoreCase("no"))){
            System.out.println("\nInvalid input\n-type yes/no");
            y_n = scanner.nextLine().replaceAll("\\s+","");
        }
        if (y_n.equalsIgnoreCase("no"))
            notifyObservers(new ActivateExtraProd(-1));
        else {
            System.out.print("You can choose a type of resource you want:\n" +
                    " - 0 --> SHIELD" + cliGraphics.printRes(new Resource(1, ResourceType.SHIELD)) + "\n" +
                    " - 1 --> SERVANT" + cliGraphics.printRes(new Resource(1, ResourceType.SERVANT)) + "\n" +
                    " - 2 --> COIN" + cliGraphics.printRes(new Resource(1, ResourceType.COIN)) + "\n" +
                    " - 3 --> STONE" + cliGraphics.printRes(new Resource(1, ResourceType.STONE)) + "\n\n>");
            int resourceType = -1;
            while (resourceType < 0 || resourceType > 3) {
                try {
                    resourceType = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.print("This is not a number, try again!\n>");
                    scanner.nextLine();
                }
            }
            notifyObservers(new ActivateExtraProd(resourceType));
        }

    }

    /**
     * A simple generic message
     * @param genericMessage the message displayed
     * @throws IOException
     */
    @Override
    public void displayGenericMessage(String genericMessage) throws IOException {
        System.out.println(genericMessage);
    }

    /**
     * Displays the initial four leader cards and let the player discard two
     * @param leaderCards the arraylist of leader cards after the shuffle
     * @throws IOException
     */
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
                System.out.println("This is not a number, try again!\n>");
                scanner.nextLine();
            }
        }
        int index2 = -1;
        while(index2 < 1 || index2 > 4 || index2==index){
            try {
                System.out.print(">");
                index2 = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("This is not a number, try again!\n>");
                scanner.nextLine();
            }
        }
        notifyObservers(new DiscardLeader(nickname, (index-1), (index2-1)));
        if (!solo) {
            ArrayList<Integer> indexes = new ArrayList<>();
            indexes.add(index - 1);
            indexes.add(index2 - 1);
            indexes.sort(Collections.reverseOrder());
            for (int ind : indexes) {
                leaderCards.remove(ind);
            }
            System.out.println("\n\nNow you own these two leader cards.\nYou can activate them at the beginning or at the end of your turn.\n");
            cliGraphics.showLeaderCards(leaderCards);
        }
    }

    /**
     * Displays the current market tray of the model view
     * @param marketTray the current market tray
     */
    @Override
    public void displayMarket(MarketTray marketTray) {
        System.out.println(cliGraphics.showMarketTray(marketTray));
        modelView.setMarketTray(marketTray);
    }


    @Override
    public void displayPopup(String message) {
        //used only in GUI mode
    }

    /**
     * Fetches the player action at the start of the turn. In case of Market it displays the market and asks for the line the player wants,
     * in case of a buy action it lets the player choose a card and if a production action is called it makes
     * the player choose which productions he wants to do.
     * @param message the action of the player
     */
    @Override
    public void fetchPlayerAction(String message){
        cliGraphics.printPersonalBoard(modelView.getWarehouse(), modelView.getSlots(), modelView.getFaithTrack(), modelView.getActiveLeaderCards());
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
                            System.out.println("Invalid choice, try again!\n>");
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
                System.out.println("Type 1 if you want to activate basic production, 0 if you don't.");
                int yesORnoB = -1;
                while (yesORnoB!=0 && yesORnoB!=1){
                    try {
                        yesORnoB = scanner.nextInt();
                    } catch (InputMismatchException e){
                        System.out.println("This is not a number..");
                        scanner.nextLine();
                    }
                }
                System.out.println("\nType 1 if you want to activate the production, 0 if you don't.");
                System.out.print("SLOT 1:  >");
                int yesORno1 = -1;
                while (yesORno1!=0 && yesORno1!=1){
                    try {
                        yesORno1 = scanner.nextInt();
                    } catch (InputMismatchException e){
                        System.out.print("This is not a number..");
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

    /**
     * If a player wants to pass his turn without doing anything else after the main action, this method notifies the
     * observers and pass the turn. Otherwise, the player can try to activate a leader card if it has one and the
     * requirements are met
     * @param message the done or leader action
     * @param leaderCards the arraylist of leader cards of the player
     * @throws IOException
     */
    @Override
    public void fetchDoneAction(String message, ArrayList<LeaderCard> leaderCards) throws IOException {
        Scanner scanner = new Scanner(System.in);
        if (leaderCards.size()>0) {
            System.out.print("Type DONE if you are done or LEADER if you want to play a leader card." + "\n>");
            String answer = scanner.nextLine().replaceAll("\\s+", "");
            while (!answer.equalsIgnoreCase("done") && !answer.equalsIgnoreCase("leader")) {
                System.out.println("\nInvalid input");
                answer = scanner.nextLine().replaceAll("\\s+", "");
            }
            if (answer.equalsIgnoreCase("DONE")) {
                notifyObservers(new DoneAction());
            } else if (answer.equalsIgnoreCase("LEADER")) {
                fetchPlayLeader(leaderCards, true);
            }
        }
        else {
            System.out.print("Type DONE" + "\n>");
            String done = scanner.nextLine().replaceAll("\\s+", "");
            while (!done.equalsIgnoreCase("done") && !done.equalsIgnoreCase("leader")) {
                System.out.println("\nInvalid input");
                done = scanner.nextLine().replaceAll("\\s+", "");
            }

            if (done.equalsIgnoreCase("DONE"))
                notifyObservers(new DoneAction());
        }

    }

    /**
     * At the start of the turn we ask the player before the main action if he wants to plah a leader card
     * @param leaderCards the player leader cards
     * @param isEndTurn boolean value for knowing if this is being asked at the start or end of the turn
     * @throws IOException
     */
    @Override
    public void fetchPlayLeader(ArrayList<LeaderCard> leaderCards, boolean isEndTurn) throws IOException {
        modelView.setLeaderCards(leaderCards);
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
            notifyObservers(new ActivateLeader(index - 1, isEndTurn));
        } else if (action.equalsIgnoreCase("DISCARD")){
            System.out.print("Choose the leader card you want to discard (insert index)\n>");
            int index = chooseLeader(leaderCards, scanner);
            notifyObservers(new DiscardOneLeader(index - 1, isEndTurn));
        } else if (action.equalsIgnoreCase("NO")){
            afterLeaderAction(isEndTurn, leaderCards);
        }

    }

    /**
     * Let the player choose a leader card from the list provided
     * @param leaderCards the leader cards of the player
     * @param scanner
     * @return
     */
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

    /**
     * After a leader is played, this method makes the turn advance or ends it based on the boolean
     * @param trueOrFalse used to determine if the turn should end or if the main action has not yet be done
     * @param leaderCards leader cards of the player
     * @throws IOException
     */
    public void afterLeaderAction(boolean trueOrFalse, ArrayList<LeaderCard> leaderCards) throws IOException {
        if (!trueOrFalse)
            fetchPlayerAction("\nWhat do you want to do now? (Type MARKET, PRODUCTION, BUY)\n");
        else
            fetchDoneAction("Type DONE", leaderCards);
    }

    /**
     * Displays a disconnection message
     * @param nicknameWhoDisconnected nickname of the player who dropped the connection
     * @param text string of the message
     */
    @Override
    public void displayDisconnectedMsg(String nicknameWhoDisconnected, String text) {

    }

    /**
     * Displays a winning message at the end of the game and ends it
     */
    @Override
    public void displayWinningMsg() {
        System.exit(0);
    }

    /**
     * Displays the dev card shop in the CLI
     * @param devCards the dev cards at the top at that moment
     * @throws IOException
     */
    @Override
    public void displayDevCards(DevCard[][] devCards) throws IOException {
        cliGraphics.printMatrixDevCards(devCards);
        modelView.setShowedDeck(devCards);
    }

    /**
     * Displays the personal board of the player
     * @param faithTrack player's faithtrack
     * @param slots player's slots
     * @param warehouse player's depot
     * @param activeLeaderCards player's active leader cards
     */
    @Override
    public void displayPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse, ArrayList<LeaderCard> activeLeaderCards) {
        modelView.setSlots(slots);
        modelView.setWarehouse(warehouse);
        modelView.setFaithTrack(faithTrack);
        modelView.setActiveLeaderCards(activeLeaderCards);
    }

    /**
     * Updates the personal board of the other players in the game
     * @param name nickname of the player
     * @param fT his faithtrack
     * @param slots his slots
     * @param wH his depot
     * @param lC his leader cards that are active
     */
    @Override
    public void updateOtherPersonalBoard(String name, FaithTrack fT, Slot[] slots, SerializableWarehouse wH, ArrayList<LeaderCard> lC) {
        modelView.updateOthersPB(name, fT, slots, wH, lC);
        System.out.println("Player " +name+ " finished his turn!" +
                "\nThis is its updated personal board :)\n");
        cliGraphics.printPersonalBoard(wH, slots, fT, lC);
    }

    @Override
    public void displayGUIPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse) {
        //used only in GUI mode
    }

    /**
     * Displays the token played by lorenzo
     * @param token the token played
     */
    @Override
    public void displayToken(Token token) {
        cliGraphics.printLorenzo(token);
    }

    /**
     * Forced end of a game
     * @param nickname the player who disconnected
     */
    @Override
    public void forcedEnd(String nickname) {
        System.out.println(nickname + " left the game. The match ends now.");
        System.exit(0);
    }

    @Override
    public void displayBasicProdPopup(int arrow, String message) {
        //used onli in GUI mode
    }

}
