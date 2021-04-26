package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.net.Socket;

/**
 * Client for the Master of Renaissance game.
 */
public class Client implements Runnable
{
    private ServerHandler serverHandler;
    private boolean shallTerminate;
    private PlayerController playerController;

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    //sarà spostato in ClientApp
    public static void main(String[] args)
    {
        /* Instantiate a new Client. The main thread will become the
         * thread where user interaction is handled. */
        Client client = new Client();
        client.run();
    }


    @Override
    public void run()
    {
        //todo: da inserire in args
        String ip = "127.0.0.1";

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

    public void receiveMessage(Message msg){
        playerController.receiveMessage(msg);
    }

}