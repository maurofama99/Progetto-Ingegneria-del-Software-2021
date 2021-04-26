package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.client.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * A class that represents the server inside the client.
 */
public class ServerHandler implements Runnable
{
    private Socket serverSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Client owner;
    private AtomicBoolean shouldStop = new AtomicBoolean(false);


    /**
     * Initializes a new handler using a specific socket connected to
     * a server.
     * @param serverSocket The socket connection to the server.
     */
    ServerHandler(Socket serverSocket, Client owner)
    {
        this.serverSocket = serverSocket;
        this.owner = owner;
    }


    /**
     * Connects to the server and runs the event loop.
     */
    @Override
    public void run()
    {
        try {
            output = new ObjectOutputStream(serverSocket.getOutputStream());
            input = new ObjectInputStream(serverSocket.getInputStream());
        } catch (IOException e) {
            System.out.println("could not open connection to " + serverSocket.getInetAddress());
            return;
        }

        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("server " + serverSocket.getInetAddress() + " connection dropped");
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * An event loop that receives messages from the server and processes
     * them in the order they are received.
     * @throws IOException If a communication error occurs.
     */
    private void handleClientConnection() throws IOException
    {
        try {
            boolean stop = false;
            while (!stop) {
                /* read commands from the server and process them */
                try {
                    Object next = input.readObject();
                    Message command = (Message) next;
                    //dobbiamo passare il messaggio alla view, per adesso lo stampiamo
                    System.out.println(command.toString());
                } catch (IOException e) {
                    /* Check if we were interrupted because another thread has asked us to stop */
                    if (shouldStop.get()) {
                        /* Yes, exit the loop gracefully */
                        stop = true;
                    } else {
                        /* No, rethrow the exception */
                        throw e;
                    }
                }
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from server");
        }
    }


    /**
     * The game instance associated with this client.
     * @return The game instance.
     */
    public Client getClient()
    {
        return owner;
    }


    /**
     * Sends a message to the server.
     * @param commandMsg The message to be sent.
     */
    public void sendCommandMessage(Message commandMsg)
    {
        try {
            output.writeObject(commandMsg);
        } catch (IOException e) {
            System.out.println("Communication error");
            //owner.terminate();
        }
    }


    /**
     * Requires the run() method to stop as soon as possible.
     */
    public void stop()
    {
        shouldStop.set(true);
        try {
            serverSocket.shutdownInput();
        } catch (IOException e) { }
    }
}
