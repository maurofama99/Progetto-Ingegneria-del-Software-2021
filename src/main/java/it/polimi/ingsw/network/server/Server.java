package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.network.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Server for Master of Renaissance game
 */
public class Server implements Runnable {

    /**
     * The socket port where the server listens to client connections
     */
    public static final int SOCKET_PORT = 1269;
    private GameController gameController;

    public Server(GameController gameController) {
        this.gameController = gameController;
    }

    public GameController getGameController() {
        return gameController;
    }

    @Override
    public void run() {
        ServerSocket socket;
        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("Cannot open server socket");
            System.exit(1);
            return;
        }

        while (true) {
            try {
                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                ClientHandler clientHandler = new ClientHandler(this, client);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("Connection dropped");
            }
        }
    }

    public void receiveMessage(Message msg) throws IOException {
        gameController.receiveMessage(msg);
    }

}

