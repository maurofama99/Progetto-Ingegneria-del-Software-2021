package it.polimi.ingsw.network.server;
import it.polimi.ingsw.controller.WaitingRoom;

public class ServerManager {

    public static void main(String[] args) {
            Server server = new Server(new WaitingRoom());
            server.run();
    }

}

