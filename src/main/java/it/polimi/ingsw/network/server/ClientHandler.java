package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagessc.LoginRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * A class that represents the client inside the server.
 */
public class ClientHandler implements Runnable
{
    private Server server;
    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private boolean isConnected;
    private boolean isLogged;

    //get the table associated with this client

    public ClientHandler(Server server, Socket client) {
        this.server = server;
        this.client = client;

        this.isConnected = true;
        this.isLogged = false;
    }


    @Override
    public void run()
    {
        //imposta in e out stream
        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("(Server) could not open connection to " + client.getInetAddress());
            return;
        }

        System.out.println("Connected to " + client.getInetAddress());

        try {
            sendMessage(new LoginRequest("server", Content.LOGIN_REQUEST));
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server getServer() {
        return server;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public boolean isLogged() {
        return isLogged;
    }

    private void handleClientConnection() throws IOException {

        System.out.println("Connected to: " + client.getInetAddress());

        try {
            //non va bene true dobbiamo impostare la condizione per cui il while termina se il thread termina
            while (true) {
                /* read commands from the client, process them, and send replies */
                Message msg = (Message) input.readObject();

                //Procedura inserimento player
               if(msg.getMessageType() == Content.LOGIN && msg.getSenderUser() != null){
                   isLogged = true;
               }
               server.receiveMessage(msg);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            System.out.println("invalid stream from client");
        }

        client.close();
    }

    /**
     * Sends a message to the client.
     * @param msg The message to be sent.
     * @throws IOException If a communication error occurs.
     */
    public void sendMessage(Message msg) throws IOException {
        try {
            output.reset();
            output.writeObject(msg);
            output.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}