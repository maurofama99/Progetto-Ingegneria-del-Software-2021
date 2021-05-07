package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagessc.DealLeaderCards;
import it.polimi.ingsw.observerPattern.ClientObserver;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.gui.Gui;

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

    public Client(View view, int SOCKET_PORT) {
        this.view = view;
        this.SOCKET_PORT = SOCKET_PORT;
    }

    public static void main(String[] args) {
        boolean cli = false;

        for (String el : args){
            if (el.equals("-c")) {
                cli = true;
                break;
            }
        }

        if (cli){
            Cli view = new Cli();
            Client client = new Client(view, Integer.parseInt(args[0]));
            view.addClientObserver(client);
            client.run();
        } else {
            Gui view = new Gui(); //chiaramente non si inizializza così la GUI, messo solo per chiarezza
            //PlayerController playerController = new PlayerController(view);
            //Client client = new Client(playerController);
            //client.run();
        }
    }

    @Override
    public void run() {

        System.out.println("Insert the IP address of the server:");
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.nextLine();

        /* Open connection to the server and start a thread for handling
         * communication. */
        Socket server;
        try {
            server = new Socket(ip, SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");

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
            case DEAL_LEADERCARDS:
                view.displayLeaderCards(((DealLeaderCards)msg).getLeaderCards());
                break;
        }
    }

    @Override
    public void update(Message message) {
        serverHandler.sendMessage(message);
    }
}