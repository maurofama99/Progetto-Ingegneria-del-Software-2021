package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.SinglePlayerController;
import it.polimi.ingsw.controller.WaitingRoom;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.client.LocalGameManager;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.messagessc.LoginRequest;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A class that represents the client inside the server.
 */
public class ClientHandler implements Runnable {
    private Server server;
    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String nickname;
    private GameController gameController;
    private SinglePlayerController singlePlayerController;
    private final WaitingRoom waitingRoom;
    private boolean started = false;
    private boolean singlePlayer = false;
    private boolean solo = false;
    private LocalGameManager localGameManager;

    public ClientHandler(Server server, Socket client, WaitingRoom waitingRoom) {
        this.server = server;
        this.client = client;
        this.waitingRoom = waitingRoom;
    }

    public ClientHandler(WaitingRoom waitingRoom, LocalGameManager localGameManager) {
        this.waitingRoom = waitingRoom;
        this.localGameManager = localGameManager;
    }

    public void setSolo(boolean solo) {
        this.solo = solo;
    }

    public String getNickname() {
        return nickname;
    }

    public Server getServer() {
        return server;
    }

    public void setSinglePlayerController(SinglePlayerController singlePlayerController) {
        this.singlePlayerController = singlePlayerController;
    }

    public void setSinglePlayer(boolean singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    @Override
    public void run() {
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
        System.out.println("Handling " + client.getInetAddress());
        sendMessage(new LoginRequest());

        try {
            //non va bene true dobbiamo impostare la condizione per cui il while termina se il thread termina
            while (true) {
                /* read messages from the client, process them, and send replies */
                Message msg = (Message) input.readObject();
                receiveMessage(msg);
            }
        } catch (ClassNotFoundException | ClassCastException | IllegalAccessException | CloneNotSupportedException e) {
            e.printStackTrace();
            System.out.println("invalid stream from client");
        }
        client.close();
    }

    /**
     * Sends a message to the client.
     *
     * @param msg The message to be sent.
     * @throws IOException If a communication error occurs.
     */
    public void sendMessage(Message msg) throws IOException {
        if (solo){
            //localGameManager.getClient().receiveMessage(msg);
            localGameManager.sendMessageServerHandler(msg);
        } else {
            output.reset();
            output.writeObject(msg);
            output.flush();
        }
    }

    //sfrutta il fatto che le vv sono tutte salvate e accoppiate al loro nickname per la resilienza alle disconnessioni:
    //quando un player si riconnette e ha un nickname già associato alla virtual view, è la sua
    //per risolvere il problema delle vv inutili si può tener conto che il client handler si ricorda del nickname precedente (l'ultimo rifiutato)
    public void receiveMessage(Message msg) throws IOException, IllegalAccessException, CloneNotSupportedException {
        if (msg.getMessageType() == Content.LOGIN_DATA) {
            if (waitingRoom.nicknameAlreadyPresent(((LoginData)msg).getNickname()) || ((((LoginData)msg).getNumPlayers() > 4 && ((LoginData)msg).getNumPlayers()<1))) {
                sendMessage(new LoginRequest());
            } else {
            VirtualView vv = new VirtualView(this);
            waitingRoom.getVvMap().put(((LoginData) msg).getNickname(), vv);
            nickname = ((LoginData) msg).getNickname();
            waitingRoom.getPlayerClientHandlerHashMap().put(nickname, this);
            waitingRoom.receiveMessage(msg);
            }
        }
        else if (singlePlayer) {
            singlePlayerController.receiveSPMessage(msg);
            System.out.println("mandato");
        }
        else gameController.receiveMessage(msg);
    }

}