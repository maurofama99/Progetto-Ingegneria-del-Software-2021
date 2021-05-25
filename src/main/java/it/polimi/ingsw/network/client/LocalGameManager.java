package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.WaitingRoom;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagessc.LoginRequest;
import it.polimi.ingsw.network.server.ClientHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class LocalGameManager implements Runnable{
    private Client client;
    private ClientHandler clientHandler;
    private ServerHandler serverHandler;
    private LinkedBlockingQueue<Message> messageQueueClientHandler = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Message> messageQueueServerHandler = new LinkedBlockingQueue<>();

    public LocalGameManager(Client client, ServerHandler serverHandler) {
        this.client = client;
        this.serverHandler = serverHandler;
        this.serverHandler.setSolo(true);
        this.clientHandler = new ClientHandler(new WaitingRoom(), this);
        this.clientHandler.setSolo(true);
    }

    public Client getClient() {
        return client;
    }

    @Override
    public void run() {
        try {
            client.receiveMessage(new LoginRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread( ()-> {
            while (true){
                try {
                    clientHandler.receiveMessage(messageQueueClientHandler.take());
                } catch (IOException | IllegalAccessException | CloneNotSupportedException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        Thread thread2 = new Thread( ()-> {
            while (true){
                try {
                    client.receiveMessage(messageQueueServerHandler.take());
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread2.start();
    }

    public void sendMessageClientHandler(Message msg) {
        try {
            messageQueueClientHandler.put(msg);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void sendMessageServerHandler(Message msg) {
        try {
            messageQueueServerHandler.put(msg);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
