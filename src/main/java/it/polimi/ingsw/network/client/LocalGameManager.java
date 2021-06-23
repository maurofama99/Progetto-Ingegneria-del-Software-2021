package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.WaitingRoom;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagessc.LoginRequest;
import it.polimi.ingsw.network.server.ClientHandler;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Manages messages exchange between view and controller in local game.
 */
public class LocalGameManager implements Runnable {
    private final Client client;
    private final ClientHandler clientHandler;
    private final LinkedBlockingQueue<Message> messageQueueClientHandler = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<Message> messageQueueServerHandler = new LinkedBlockingQueue<>();

    public LocalGameManager(Client client) {
        this.client = client;
        this.clientHandler = new ClientHandler(new WaitingRoom(), this);
        this.clientHandler.setSolo(true);
    }

    public Client getClient() {
        return client;
    }

    /**
     * Receives and processes messages between View and Controller running two threads simultaneously.
     * Uses Linked Blocking Queues to maintain the correct receiving order.
     */
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

    /**
     * Queue the message to be sent to the controller
     * @param msg Message sent to Controller
     */
    public void sendMessageClientHandler(Message msg) {
        try {
            messageQueueClientHandler.put(msg);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Queue the message to be sent to the view
     * @param msg Message sent to View
     */
    public void sendMessageServerHandler(Message msg) {
        try {
            messageQueueServerHandler.put(msg);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
