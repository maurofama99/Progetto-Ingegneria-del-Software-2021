package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagessc.*;
import it.polimi.ingsw.observerPattern.ClientObserver;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.CliColor;
import it.polimi.ingsw.view.gui.Gui;
import it.polimi.ingsw.view.gui.JavaFX;

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
    boolean connected = false;
    Socket server;
    String ip;
    private boolean cli = false;
    private boolean gui = false;

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
            }

        }
        if (i == 0 || (!cli && !gui) || SOCKET_PORT==-1) {
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

    }

    @Override
    public void run() {

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


        if(cli) {
            while (!connected) {
                System.out.println("Insert the IP address of the server:");
                Scanner scanner = new Scanner(System.in);
                String ip = scanner.nextLine();
                tryConnection(ip, SOCKET_PORT);
            }
            System.out.println("Connected");
        }

        if(gui) {
            tryConnection(this.ip, SOCKET_PORT);
        }

        serverHandler = new ServerHandler(server, this);
        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
        serverHandlerThread.start();

    }

    /**
     * The handler object responsible for communicating with the server.
     * @return The server handler.
     */
    public ServerHandler getServerHandler() {
        return serverHandler;
    }

    public void receiveMessage(Message msg) throws IOException {
        switch (msg.getMessageType()){
            case LOGIN_REQUEST:
                view.fetchNickname(); 
                break;
            case GENERIC_MESSAGE:
                view.displayGenericMessage(msg.toString());
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
            case DISPLAY_PERSONALBOARD:
                view.displayPersonalBoard(((DisplayPersonalBoard)msg).getPersonalBoard());
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

    @Override
    public void update(Message message) {
        serverHandler.sendMessage(message);
    }
}
