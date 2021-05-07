package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.WaitingRoom;
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
    private final int SOCKET_PORT;
    private final WaitingRoom waitingRoom;

    public Server(WaitingRoom waitingRoom, int SOCKET_PORT) {
        this.waitingRoom = waitingRoom;
        this.SOCKET_PORT = SOCKET_PORT;
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
                ClientHandler clientHandler = new ClientHandler(this, client, this.waitingRoom);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("Connection dropped");
            }
        }
    }

}

