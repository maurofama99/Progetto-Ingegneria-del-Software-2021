/*
 * Copyright (c) 2021.  Mauro Famà, Valeria Detomas, Christian Fabio Grazian.
 */

package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagessc.*;
import it.polimi.ingsw.observerPattern.ClientObserver;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.CliColor;
import it.polimi.ingsw.view.gui.JavaFX;
import javafx.event.Event;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Client for the Masters of Renaissance game.
 */
public class Client implements Runnable, ClientObserver {
    private ServerHandler serverHandler;
    private final View view;
    private int SOCKET_PORT;
    private boolean connected = false;
    private Socket server;
    private String ip;
    private boolean cli = false;
    private boolean gui = false;
    private boolean solo = false;
    private String nickname;
    private LocalGameManager localGameManager;
    private final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
    private Event event;

    public Client(View view, int SOCKET_PORT) {
        this.view = view;
        this.SOCKET_PORT = SOCKET_PORT;
        this.cli = true;
    }

    public Client(View view) {
        this.view = view;
        this.gui = true;
    }

    public void setGui(boolean gui) {
        this.gui = gui;
    }

    public void setSolo(boolean solo) {
        this.solo = solo;
    }

    public void setCli(boolean cli) {
        this.cli = cli;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ScheduledExecutorService getSes() {
        return ses;
    }

    /**
     * This main executes the client. We have a precise usage to decide how and on where you wanna play your game
     *
     * @param args the parameters that choose if you want to use the cli, a GUI, play a local game...
     */
    public static void main(String[] args) {
        int SOCKET_PORT = -1;
        boolean cli = false;
        boolean gui = false;
        boolean solo = false;
        String usage = "Usage: java -jar client.jar [-cli -port portNumber | -gui | -local [-cli | -gui]]";

        if (args.length == 0) {
            System.err.println(usage);
        } else {

            switch (args[0]) {
                case "-cli":
                    try {
                        if (args[1].equals("-port") && args.length <= 3) {
                            try {
                                cli = true;
                                solo = false;
                                SOCKET_PORT = Integer.parseInt(args[2]);
                            } catch (NumberFormatException e) {
                                System.err.println("-port requires a port number\n" + usage);
                                break;
                            }
                        } else {
                            System.err.println(usage);
                            break;
                        }
                    } catch (IndexOutOfBoundsException e){
                        System.err.println(usage);
                        break;
                    }
                    break;
                case "-gui":
                    if (args.length <= 1) {
                        gui = true;
                        solo = false;
                    } else {
                        System.err.println(usage);
                        break;
                    }
                    break;
                case "-local":
                    if (args[1].equals("-gui") && args.length <= 2) {
                        solo = true;
                        gui = true;
                    } else if (args[1].equals("-cli") && args.length <= 2){
                        solo = true;
                        cli = true;
                    } else {
                        System.err.println(usage);
                        break;
                    }
                    break;
                default:
                    System.err.println(usage);
                    break;
            }

            if (cli && !solo) {
                Cli view = new Cli();
                Client client = new Client(view, SOCKET_PORT);
                view.addClientObserver(client);
                client.run();
            }
            else if (solo && cli) {
                Cli view = new Cli();
                view.setSolo(true);
                Client client = new Client(view, -1);
                view.addClientObserver(client);
                client.solo = true;
                client.cli = false;
                client.run();
            }
            else if (solo) {
                JavaFX.main(args);
            }
            else if (gui) {
                JavaFX.main(args);
            }
            else {
                System.err.println(usage);
            }

        }

    }

    /**
     * Run method for the CLI game. Asks for the IP of the server (Port is already specified). Also starts the local CLI game.
     */
    @Override
    public void run() {
        if(cli) {

            System.out.println("\n" +
                    CliColor.ANSI_YELLOW.escape() + "_  _ ____ ____ ___ ____ ____ ____  " + CliColor.RESET + "  ____ ____  " + CliColor.ANSI_BLUE.escape() + "  ____ ____ _  _ ____ _ ____ ____ ____ _  _ ____ ____ \n" + CliColor.RESET +
                    CliColor.ANSI_YELLOW.escape() + "|\\/| |__| [__   |  |___ |__/ [__   " + CliColor.RESET + "  |  | |___  " + CliColor.ANSI_BLUE.escape() + "  |__/ |___ |\\ | |__| | [__  [__  |__| |\\ | |    |___ \n" + CliColor.RESET +
                    CliColor.ANSI_YELLOW.escape() + "|  | |  | ___]  |  |___ |  \\ ___]  " + CliColor.RESET + "  |__| |     " + CliColor.ANSI_BLUE.escape() + "  |  \\ |___ | \\| |  | | ___] ___] |  | | \\| |___ |___ \n" + CliColor.RESET +
                    "                                                                                                      \n");


                while (!connected) {
                    System.out.println("Insert the IP address of the server:");
                    Scanner scanner = new Scanner(System.in);
                    ip = scanner.nextLine();
                    tryConnection(ip, SOCKET_PORT);
                }
                System.out.println("Connected");
                serverHandler = new ServerHandler(server, this, ip, cli);
                Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
                serverHandlerThread.start();

        }

        if (solo){
            localGameManager = new LocalGameManager(this);
            localGameManager.run();
        }

    }

    /**
     * Handles the received message and acts accordingly to the switch
     * @param msg the received message (with its type)
     * @throws IOException  If virtual view fails to send message.
     */
    public void receiveMessage(Message msg) throws IOException {
        switch (msg.getMessageType()){
            case LOGIN_REQUEST:
                if (solo) view.localFetchNickname(event);
                else view.fetchNickname();
                break;
            case GENERIC_MESSAGE:
                view.displayGenericMessage(msg.toString());
                break;
            case GENERIC_POPUP:
                view.displayPopup(msg.toString());
                break;
            case ASKTYPERESOURCE:
                view.fetchResourceType();
                break;
            case ASK_RESOURCE_PLACEMENT:
                view.fetchResourcePlacement(((AskResourcePlacement)msg).getResource());
                break;
            case DISPLAY_LEADERCARDS:
                view.displayLeaderCards(((DisplayLeaderCards)msg).getLeaderCards());
                break;
            case DISPLAY_MARKET:
                view.displayMarket(((DisplayMarket)msg).getMarketTray());
                break;
            case DISPLAY_DEVCARDS:
                view.displayDevCards(((DisplayDevCards)msg).getShowedCards());
                break;
            case DISPLAY_PERSONALBOARD:
                view.displayPersonalBoard(((DisplayPersonalBoard)msg).getFaithTrack(), ((DisplayPersonalBoard)msg).getSlots(), ((DisplayPersonalBoard)msg).getSerializableWarehouse(), ((DisplayPersonalBoard)msg).getActiveLeaderCards());
                break;
            case DISPLAY_GUI_PERSONALBOARD:
                view.displayGUIPersonalBoard(((DisplayGUIPersonalBoard)msg).getFaithTrack(), ((DisplayGUIPersonalBoard)msg).getSlots(), ((DisplayGUIPersonalBoard)msg).getSerializableWarehouse());
                break;
            case ASK_ACTION:
                view.fetchPlayerAction(((AskAction)msg).getQuestion());
                break;
            case ASK_DONE:
                view.fetchDoneAction(((AskDone) msg).getQuestion(), ((AskDone) msg).getLeaderCards());
                break;
            case NORESOURCE_AVAILABLE:
                view.fetchPlayerAction("\nYou don't have the requirements to do your action!!" +
                        "\nWhat do you want to do now? (Type MARKET, PRODUCTION, BUY)\n");
                break;
            case ASK_SWAP_WHITE:
                view.fetchSwapWhite(((AskSwapWhite)msg).getType1(), ((AskSwapWhite)msg).getType2());
                break;
            case ASK_PLAYLEADER:
                view.fetchPlayLeader(((AskPlayLeader)msg).getLeaderCardsNotActivated(), ((AskPlayLeader)msg).isEndTurn());
                break;
            case TURN_TOKEN:
                view.displayToken(((TurnToken)msg).getToken());
                break;
            case EXTRA_PRODUCTION:
                view.fetchExtraProd(((ExtraProduction)msg).getResource());
                break;
            case SEND_PERSONALBOARD:
                view.updateOtherPersonalBoard(((SendOtherPersonalBoard)msg).getName(),
                        ((SendOtherPersonalBoard)msg).getFaithTrack(),
                        ((SendOtherPersonalBoard)msg).getSlot(),
                        ((SendOtherPersonalBoard)msg).getSerializableWarehouse(),
                        ((SendOtherPersonalBoard)msg).getLeaderCards());
                break;
            case FORCEDEND:
                view.forcedEnd(((ForcedEnd) msg).getNickname());
                break;
            case MATCH_FINISHED:
                view.displayWinningMsg();
                break;
            case BASIC_PROD:
                view.displayBasicProdPopup(((BasicProd)msg).getArrow(), ((BasicProd)msg).getMessage());
                break;
            case FIRST_PLAYER:
                view.setFirstPlayer(true);
                break;
        }
    }

    /**
     * Tries to connect to the desired server.
     * @param ip ip of the server
     * @param SOCKET_PORT port of the server
     */
    public void tryConnection(String ip, int SOCKET_PORT){
            try {
                server = new Socket(ip, SOCKET_PORT);
                connected = true;
            } catch (IOException e) {
                System.out.println("Server unreachable, try with another address.");
                connected = false;
            }
    }

    /**
     * Connects the gui to the server, using the parameters specified in the connection scene
     * @param ip ip written in the connection scene
     * @param server port in the connection scene
     */
    public void startGuiGame(String ip, Socket server){
        serverHandler = new ServerHandler(server, this, ip, false);
        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
        serverHandlerThread.start();
    }

    /**
     * Updates the game using a message.
     * @param message the message sent
     */
    @Override
    public void update(Message message) {
        if (message.getMessageType() == Content.LOGIN_DATA){
            this.nickname = message.getSenderUser();
        } else {
            message.setSenderUser(nickname);
        }
        if (solo) {
            localGameManager.sendMessageClientHandler(message);
        } else serverHandler.sendMessage(message);
    }

}


