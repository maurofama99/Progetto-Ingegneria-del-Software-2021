package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.Message;

import javax.security.auth.login.Configuration;
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
    public final static int SOCKET_PORT = 1269;
    private GameController gameController;
    private HashMap<String, ClientHandler> playerClientHandlerHashMap;

    public Server(GameController gameController) {
        this.gameController = gameController;
        playerClientHandlerHashMap = new HashMap<>();
    }

    //questo sarà spostato in una classe ServerApp
    public static void main(String[] args) {
        GameController gameController = new GameController();
        Server server = new Server(gameController);
        server.run();
    }

    //non può essere un main perchè deve essere passato al ClientHandler
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
                //server timeout?
                ClientHandler clientHandler = new ClientHandler(this, client);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("Connection dropped");
                System.out.println("Connection dropped");
            }
        }
    }

    public void receiveMessage(Message msg) {
        gameController.receiveMessage(msg);
    }

}

