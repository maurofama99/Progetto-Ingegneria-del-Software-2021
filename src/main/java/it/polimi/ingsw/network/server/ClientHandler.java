package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;

import it.polimi.ingsw.model.player.faithtrack.FaithMarker;
import it.polimi.ingsw.network.messages.AnswerMsg;
import it.polimi.ingsw.network.messages.CommandMsg;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


/**
 * A class that represents the client inside the server.
 */
public class ClientHandler implements Runnable
{
    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    //get the table associated with this client

    /**
     * Initializes a new handler using a specific socket connected to
     * a client.
     * @param client The socket connection to the client.
     */
    ClientHandler(Socket client)
    {
        this.client = client;
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

    private void handleClientConnection() throws IOException {
        try {
            //non va bene true dobbiamo impostare la condizione per cui il while termina se il thread termina
            while (true) {
                /* read commands from the client, process them, and send replies */
                System.out.println("Il client handler sta andando");
                Object next = input.readObject();
                CommandMsg command = (CommandMsg) next;
                command.processMessage(this);

                //chiamare il metodo nel server che (passando come argomento command) chiama il game controller per risolvere command

            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        }
    }

    /**
     * Sends a message to the client.
     * @param answerMsg The message to be sent.
     * @throws IOException If a communication error occurs.
     */
    public void sendAnswerMessage(Message answerMsg) throws IOException
    {
        output.writeObject(answerMsg);
    }


}