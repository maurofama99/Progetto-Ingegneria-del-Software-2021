package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.WaitingRoom;
import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.messagescs.PlayersNumber;
import it.polimi.ingsw.network.messagessc.LoginRequest;
import it.polimi.ingsw.network.messagessc.NumPlayersRequest;
import it.polimi.ingsw.view.VirtualView;

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
    private String nickname;
    private GameController gameController;
    private final WaitingRoom waitingRoom;
    private boolean started = false;

    public ClientHandler(Server server, Socket client, WaitingRoom waitingRoom) {
        this.server = server;
        this.client = client;
        this.waitingRoom = waitingRoom;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setStarted(boolean started) {
        this.started = started;
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
            sendMessage(new LoginRequest());
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }

        try {
            client.close();
        } catch (IOException e) {
            System.out.println("va qui?");
            e.printStackTrace();
        }
    }

    public Server getServer() {
        return server;
    }


    private void handleClientConnection() throws IOException {

        System.out.println("Handling " + client.getInetAddress());

        try {
            //non va bene true dobbiamo impostare la condizione per cui il while termina se il thread termina
            while (true) {
                /* read messages from the client, process them, and send replies */
                Message msg = (Message) input.readObject();
                receiveMessage(msg);
                //se arriva un messaggio login_data significa che un client si è appena connesso
                //se un client si connette c'è bisogno di assegnargli una virtual view per comunicare
                //la virtual view deve essere aggiunta alla mappa delle virtual view
                /*if (msg.getMessageType() == Content.LOGIN_DATA){
                    VirtualView vv = new VirtualView(this);
                    gameController.getVvMap().put(((LoginData)msg).getNickname(),vv);
                    nickname = ((LoginData)msg).getNickname();
                    receiveMessage(msg);
                } else {
                    msg.setSenderUser(nickname);
                    receiveMessage(msg);
                }*/
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
        output.reset();
        output.writeObject(msg);
        output.flush();
    }

    //per risolvere il problema delle vv inutili si può tener conto che il client handler si ricorda del nickname precedente (l'ultimo rifiutato)
    //oppure fai un booleano first messaage e nei messaggi non first cancelli la vv precedentemente creata
    public void receiveMessage(Message msg) throws IOException {
        if (!started){
            VirtualView vv = new VirtualView(this);
            waitingRoom.getVvMap().put(((LoginData)msg).getNickname(),vv);
            nickname = ((LoginData)msg).getNickname();
            waitingRoom.getPlayerClientHandlerHashMap().put(nickname, this);
            waitingRoom.receiveMessage(msg);
        } else gameController.receiveMessage(msg);
    }

}