package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.SinglePlayerController;
import it.polimi.ingsw.controller.WaitingRoom;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.client.LocalGameManager;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.messagessc.GenericPopup;
import it.polimi.ingsw.network.messagessc.LoginRequest;
import it.polimi.ingsw.view.VirtualView;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A class that represents the client inside the server.
 */
public class ClientHandler implements Runnable {
    private Server server;
    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String nickname;
    private int numPlayers;
    private GameController gameController;
    private SinglePlayerController singlePlayerController;
    private final WaitingRoom waitingRoom;
    private boolean started = false;
    private boolean singlePlayer = false;
    private boolean solo = false;
    private boolean stop = false;
    private LocalGameManager localGameManager;
    private final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

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

    /**
     * The run method of the ClientHandler, which has a timeout for kicking in case of inactivity or
     * if the connection is interrupted. In that case we say the connection is dropped.
     */
    @Override
    public void run() {

        try {
            client.setSoTimeout(6000);
        } catch (SocketException e) {
            System.out.println(nickname + " is unreachable, connection dropped");
            gameController.forcedEndGame(nickname);
        }

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
        } catch (SocketTimeoutException e ){
            System.out.println(nickname + "kicked out for inactivity");
            gameController.forcedEndGame(nickname);
        }
        catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");

            try {
                gameController.forcedEndGame(nickname);
            }  catch (NullPointerException ignore){}

            switch (numPlayers){
                case 2:
                    Iterator<Player> iter2 = waitingRoom.getTwoPlayersArray().iterator();
                    removeDisconnectedPlayer(iter2);
                    break;
                case 3:
                    Iterator<Player> iter3 = waitingRoom.getThreePlayersArray().iterator();
                    removeDisconnectedPlayer(iter3);
                    break;
                case 4:
                    Iterator<Player> iter4 = waitingRoom.getFourPlayersArray().iterator();
                    removeDisconnectedPlayer(iter4);
                    break;
                default:
                    break;
            }
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

        ses.scheduleAtFixedRate( () -> {
                    try {
                        sendMessage(new Message("client", Content.HEARTBEAT));
                        System.out.println("Sent HeartBeat:" + client.getInetAddress());
                    } catch (IOException e) {
                        System.out.println(nickname + " is unreachable, connection dropped");
                        gameController.forcedEndGame(nickname);
                    }
                },
                0, 5, TimeUnit.SECONDS);

        try {
            while (!stop) {
                Message msg = (Message) input.readObject();
                receiveMessage(msg);
            }
        } catch (ClassNotFoundException | ClassCastException | IllegalAccessException | CloneNotSupportedException e) {
            e.printStackTrace();
            System.out.println("invalid stream from client");
        } finally {
            stop = true;
            client.close();
        }

    }

    /**
     * Sends a message to the client.
     *
     * @param msg The message to be sent.
     * @throws IOException If a communication error occurs.
     */
    public void sendMessage(Message msg) throws IOException{
        if (solo){
            localGameManager.sendMessageServerHandler(msg);
        } else {
            output.writeObject(msg);
            output.flush();
            output.reset();
        }
    }

    public void removeDisconnectedPlayer(Iterator<Player> iter){
        while (iter.hasNext()){
            Player player = iter.next();
            if (player.getNickname().equals(nickname)){
                for (String nick : waitingRoom.getPlayerClientHandlerHashMap().keySet()){
                    if (nick.equals(nickname) && waitingRoom.getPlayerClientHandlerHashMap().get(nickname).equals(this)){
                        iter.remove();
                    }
                }
            }
        }
    }

    /**
     * Receive message method of the client handler. For the resilience we use the fact that the virtual views are saved
     * and matched with nicknames, so if a player reconnects and the nickname matches, he can continue playing
     * @param msg the message being received
     * @throws IOException
     * @throws IllegalAccessException
     * @throws CloneNotSupportedException
     */
    //sfrutta il fatto che le vv sono tutte salvate e accoppiate al loro nickname per la resilienza alle disconnessioni:
    //quando un player si riconnette e ha un nickname già associato alla virtual view, è la sua
    //per risolvere il problema delle vv inutili si può tener conto che il client handler si ricorda del nickname precedente (l'ultimo rifiutato)
    public void receiveMessage(Message msg) throws IOException, IllegalAccessException, CloneNotSupportedException {
        if (msg.getMessageType() == Content.HEARTBEAT){
            System.out.println("Received HeartBeat: " + client.getInetAddress());
        }
        else if (msg.getMessageType() == Content.LOGIN_DATA) {
            if (waitingRoom.nicknameAlreadyPresent(((LoginData)msg).getNickname()) || ((((LoginData)msg).getNumPlayers() > 4 && ((LoginData)msg).getNumPlayers()<1))) {
                sendMessage(new GenericPopup("Nickname already present"));
                sendMessage(new LoginRequest());
            } else {
                VirtualView vv = new VirtualView(this);
                waitingRoom.getVvMap().put(((LoginData) msg).getNickname(), vv);
                nickname = ((LoginData) msg).getNickname();
                numPlayers = ((LoginData) msg).getNumPlayers();
                waitingRoom.getPlayerClientHandlerHashMap().put(nickname, this);
                waitingRoom.receiveMessage(msg);
            }
        }
        else if (singlePlayer) {
            singlePlayerController.receiveSPMessage(msg);
        }
        else gameController.receiveMessage(msg);
    }

}