package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.server.ClientHandler;

public class VirtualView implements View{

    private final ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    @Override
    public void fetchNickname() {
        //richiama il metodo sendMessage del ClientHandler
    }

    @Override
    public void fetchPlayersNumber() {

    }
}
