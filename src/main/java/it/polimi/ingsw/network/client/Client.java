package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.observerPattern.ClientObservable;
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

    public Client(View view) {
        this.view = view;
    }

    //sarà spostato in ClientApp
    public static void main(String[] args) {
        /* Instantiate a new Client. The main thread will become the
         * thread where user interaction is handled. */
        boolean cli = false;

        for (String el : args){
            if (el.equals("-c")) {
                cli = true;
                break;
            }
        }

        if (cli){
            Cli view = new Cli();
            Client client = new Client(view);
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
            server = new Socket(ip, Server.SOCKET_PORT);
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

    //secondo me questo metodo non serve perchè è già nel serverHandler
    public void sendMessage(Message msg) {
        try {
            serverHandler.getOutput().writeObject(msg);
            serverHandler.getOutput().flush();
        } catch (IOException e) {
            System.out.println("Communication error");
        }
    }

    public void receiveMessage(Message msg) throws IOException {
        switch (msg.getMessageType()){
            case LOGIN_REQUEST:
                //se il messaggio ricevuto è di LOGIN REQUEST allora invia i tuoi login data
                view.fetchNickname(); //chiede al player il nickname e lo invia al server
                break;
            case NUM_PLAYERS_REQUEST:
                //view.fetchPlayersNumber();
            case LOGIN_SUCCESSFUL:
                //view.waitFor();
                break;
            case LOGIN_FAIL:
                //view.fetchNickname();
                break;

        }
    }

    @Override
    public void update(Message message) {
        serverHandler.sendMessage(message);
    }
}