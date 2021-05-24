package it.polimi.ingsw.network.client;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.messagessc.*;
import it.polimi.ingsw.observerPattern.ClientObserver;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.CliColor;
import it.polimi.ingsw.view.gui.JavaFX;
import it.polimi.ingsw.view.gui.scenes.ConnectionSceneController;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client for the Master of Renaissance game.
 */
public class Client implements Runnable, ClientObserver {
    private ServerHandler serverHandler;
    private View view;
    private final int SOCKET_PORT;
    private boolean connected = false;
    private Socket server;
    private String ip;
    private boolean cli = false;
    private boolean gui = false;
    private boolean solo = true;
    private String nickname;
    private LocalGameManager localGameManager;

    public Client(View view, int SOCKET_PORT) {
        this.view = view;
        this.SOCKET_PORT = SOCKET_PORT;
        this.cli = true;
    }

    public Client(View view, int SOCKET_PORT, String ip) {
        this.view = view;
        this.SOCKET_PORT = SOCKET_PORT;
        this.ip = ip;
        this.gui=true;
    }

    public static void main(String[] args) {
        int i = 0;
        String arg;
        int SOCKET_PORT = -1;
        boolean cli = false;
        boolean gui = false;
        boolean solo = false;

        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];

            switch (arg) {
                case "-port":
                    if (i < args.length)
                        try {
                            SOCKET_PORT = Integer.parseInt(args[i++]);
                        } catch (NumberFormatException e){
                            System.err.println("-port requires a port number\nUsage: Client -port portNumber [-cli | -gui]");
                        }
                    else
                        System.err.println("-port requires a port number\nUsage: Client -port portNumber [-cli | -gui]");
                    break;
                case "-cli":
                    cli = true;
                    break;
                case "-gui":
                    if (cli) {
                        System.err.println("You can't start the program in both CLI and GUI mode\nUsage: Client -port portNumber [-cli | -gui]");
                        break;
                    }
                    gui = true;
                    break;
                case "-solo":
                    solo = true;
                    break;
            }

        }
        if (i == 0 || (!cli && !gui && !solo) || SOCKET_PORT==-1) {
            System.err.println("Usage: Client -port " + "portNumber" + " [-cli | -gui]");
            return;
        }

        if (cli){
            Cli view = new Cli();
            Client client = new Client(view, SOCKET_PORT);
            view.addClientObserver(client);
            client.run();
        }

        if (gui){
            JavaFX.main(args);
        }

        if (solo){
            Cli view = new Cli();
            Client client = new Client(view, -1);
            view.addClientObserver(client);
            client.solo = true;
            client.cli = false;
            client.run();
        }

    }

    @Override
    public void run() {
        if(cli) {

            System.out.println("\n" +
                    CliColor.ANSI_YELLOW.escape() + "_  _ ____ ____ ___ ____ ____ ____  " + CliColor.RESET + "  ____ ____  " + CliColor.ANSI_BLUE.escape() + "  ____ ____ _  _ ____ _ ____ ____ ____ _  _ ____ ____ \n" + CliColor.RESET +
                    CliColor.ANSI_YELLOW.escape() + "|\\/| |__| [__   |  |___ |__/ [__   " + CliColor.RESET + "  |  | |___  " + CliColor.ANSI_BLUE.escape() + "  |__/ |___ |\\ | |__| | [__  [__  |__| |\\ | |    |___ \n" + CliColor.RESET +
                    CliColor.ANSI_YELLOW.escape() + "|  | |  | ___]  |  |___ |  \\ ___]  " + CliColor.RESET + "  |__| |     " + CliColor.ANSI_BLUE.escape() + "  |  \\ |___ | \\| |  | | ___] ___] |  | | \\| |___ |___ \n" + CliColor.RESET +
                    "                                                                                                      \n");
        /*
            System.out.println(CliColor.ANSI_YELLOW.escape() + "_  _ ____ ____ ___ ____ ____ ____ "+CliColor.RESET+"   ____ ____ \n" +
                               CliColor.ANSI_YELLOW.escape() + "|\\/| |__| [__   |  |___ |__/ [__  " + CliColor.RESET+ "   |  | |___ \n" +
                    CliColor.ANSI_YELLOW.escape() +"|  | |  | ___]  |  |___ |  \\ ___] "+CliColor.RESET+"   |__| |    \n" +
                               "                                               \n" + CliColor.ANSI_BLUE.escape() +
                               "____ ____ _  _ ____ _ ____ ____ ____ _  _ ____ ____ \n" +
                               "|__/ |___ |\\ | |__| | [__  [__  |__| |\\ | |    |___ \n" +
                               "|  \\ |___ | \\| |  | | ___] ___] |  | | \\| |___ |___ " + CliColor.RESET);

        System.out.println("\n" +
                "   ▄▄▄▄███▄▄▄▄      ▄████████    ▄████████     ███        ▄████████    ▄████████    ▄████████       ▄██████▄     ▄████████\n"+
                " ▄██▀▀▀███▀▀▀██▄   ███    ███   ███    ███ ▀█████████▄   ███    ███   ███    ███   ███    ███      ███    ███   ███    ███\n"+
                " ███   ███   ███   ███    ███   ███    █▀     ▀███▀▀██   ███    █▀    ███    ███   ███    █▀       ███    ███   ███    █▀ \n"+
                " ███   ███   ███   ███    ███   ███            ███   ▀  ▄███▄▄▄      ▄███▄▄▄▄██▀   ███             ███    ███  ▄███▄▄▄    \n"+
                " ███   ███   ███ ▀███████████ ▀███████████     ███     ▀▀███▀▀▀     ▀▀███▀▀▀▀▀   ▀███████████      ███    ███ ▀▀███▀▀▀    \n"+
                " ███   ███   ███   ███    ███          ███     ███       ███    █▄  ▀███████████          ███      ███    ███   ███       \n"+
                " ███   ███   ███   ███    ███    ▄█    ███     ███       ███    ███   ███    ███    ▄█    ███      ███    ███   ███       \n"+
                "  ▀█   ███   █▀    ███    █▀   ▄████████▀     ▄████▀     ██████████   ███    ███  ▄████████▀        ▀██████▀    ███       \n"+
                "                                                                      ███    ███                                          \n\n\n"+

                "         ▄████████    ▄████████ ███▄▄▄▄      ▄████████  ▄█     ▄████████    ▄████████    ▄████████ ███▄▄▄▄    ▄████████    ▄████████ \n" +
                "         ███    ███   ███    ███ ███▀▀▀██▄   ███    ███ ███    ███    ███   ███    ███   ███    ███ ███▀▀▀██▄ ███    ███   ███    ███ \n" +
                "         ███    ███   ███    █▀  ███   ███   ███    ███ ███▌   ███    █▀    ███    █▀    ███    ███ ███   ███ ███    █▀    ███    █▀  \n" +
                "         ███    ███   ███    █▀  ███   ███   ███    ███ ███▌   ███    █▀    ███    █▀    ███    ███ ███   ███ ███    █▀    ███    █▀  \n" +
                "        ▄███▄▄▄▄██▀  ▄███▄▄▄     ███   ███   ███    ███ ███▌   ███          ███          ███    ███ ███   ███ ███         ▄███▄▄▄     \n" +
                "       ▀▀███▀▀▀▀▀   ▀▀███▀▀▀     ███   ███ ▀███████████ ███▌ ▀███████████ ▀███████████ ▀███████████ ███   ███ ███        ▀▀███▀▀▀     \n" +
                "       ▀███████████   ███    █▄  ███   ███   ███    ███ ███           ███          ███   ███    ███ ███   ███ ███    █▄    ███    █▄  \n" +
                "         ███    ███   ███    ███ ███   ███   ███    ███ ███     ▄█    ███    ▄█    ███   ███    ███ ███   ███ ███    ███   ███    ███ \n" +
                "         ███    ███   ██████████  ▀█   █▀    ███    █▀  █▀    ▄████████▀   ▄████████▀    ███    █▀   ▀█   █▀  ████████▀    ██████████ \n" +
                "         ███    ███       \n");



             */

                while (!connected) {
                    System.out.println("Insert the IP address of the server:");
                    Scanner scanner = new Scanner(System.in);
                    ip = scanner.nextLine();
                    tryConnection(ip, SOCKET_PORT);
                }
                System.out.println("Connected");
                serverHandler = new ServerHandler(server, this, ip);
                Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
                serverHandlerThread.start();

        }

        if(gui) {
            //while (!connected) {
            tryConnection(this.ip, SOCKET_PORT);
           // }
            serverHandler = new ServerHandler(server, this, ip);
            Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
            serverHandlerThread.start();
        }

        if (solo){
            serverHandler = new ServerHandler(this);
            localGameManager = new LocalGameManager(this, serverHandler);
            localGameManager.run();
        }

    }

    public synchronized void receiveMessage(Message msg) throws IOException {
        switch (msg.getMessageType()){
            case LOGIN_REQUEST:
                view.fetchNickname(); 
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
                view.fetchResourcePlacement();
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
                view.displayPersonalBoard(((DisplayPersonalBoard)msg).getFaithTrack(), ((DisplayPersonalBoard)msg).getSlots(), ((DisplayPersonalBoard)msg).getSerializableWarehouse());
                break;
            case ASK_ACTION:
                view.fetchPlayerAction(((AskAction)msg).getQuestion());
                break;
            case ASK_DONE:
                view.fetchDoneAction(((AskDone) msg).getQuestion(), ((AskDone) msg).getLeaderCards());
                break;
            case NORESOURCE_AVAILABLE:
                view.fetchPlayerAction("\nYou don't have the requirements to do your action!!" +
                        "\nWhat do you wanna do now? (Type MARKET, PRODUCTION, BUY)\n");
                break;
            case ASK_SWAP_WHITE:
                view.fetchSwapWhite(((AskSwapWhite)msg).getType1(), ((AskSwapWhite)msg).getType2());
                break;
            case ASK_PLAYLEADER:
                view.fetchPlayLeader(((AskPlayLeader)msg).getLeaderCardsNotActivated(), false);
                break;
            case TURN_TOKEN:
                view.displayToken(((TurnToken)msg).getToken());
        }
    }

    public void tryConnection(String ip, int SOCKET_PORT){
        try {
            server = new Socket(ip, SOCKET_PORT);
            connected = true;
        } catch (IOException e) {
            System.out.println("Server unreachable, try with another address.");
            connected = false;
        }
    }

    //todo: nel cleanup togliere il nickname dal costruttore dei messaggi

    @Override
    public synchronized void update(Message message) {
        if (message.getMessageType() == Content.LOGIN_DATA){
            this.nickname = message.getSenderUser();
        } else {
            message.setSenderUser(nickname);
        }
        if (solo) {
            //localGameManager.getClientHandler().receiveMessage(message);
            localGameManager.sendMessageClientHandler(message);
        } else serverHandler.sendMessage(message);
    }

}


