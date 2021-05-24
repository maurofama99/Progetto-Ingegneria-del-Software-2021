package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.controller.WaitingRoom;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagessc.LoginRequest;
import it.polimi.ingsw.network.server.ClientHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;

public class LocalGameManager implements Runnable{
    private Client client;
    private ClientHandler clientHandler;
    private ServerHandler serverHandler;
    private LinkedList<Message> messageQueueClientHandler = new LinkedList<>();
    private LinkedList<Message> messageQueueServerHandler = new LinkedList<>();

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
                    clientHandler.receiveMessage(Objects.requireNonNull(messageQueueClientHandler.removeFirst()));
                } catch (IOException | IllegalAccessException | CloneNotSupportedException e) {
                    e.printStackTrace();
                } catch (NullPointerException | NoSuchElementException ignored){ }
            }
        });

        thread.start();

        Thread thread2 = new Thread( ()-> {
            while (true){
                try {
                    client.receiveMessage(Objects.requireNonNull(messageQueueServerHandler.removeFirst()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException | NoSuchElementException ignored){ }
            }
        });

        thread2.start();
    }

    public synchronized void sendMessageClientHandler(Message msg){
        messageQueueClientHandler.addLast(msg);
    }

    public synchronized void sendMessageServerHandler(Message msg){
        messageQueueServerHandler.addLast(msg);
    }
}
