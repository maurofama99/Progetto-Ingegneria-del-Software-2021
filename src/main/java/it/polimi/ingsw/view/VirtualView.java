package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.Deck;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.PopeSpace;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagessc.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observerPattern.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VirtualView implements Observer {
    private ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void fetchNickname() throws IOException {
        clientHandler.sendMessage(new LoginRequest());
    }

    public void fetchResourceType() throws IOException {
        clientHandler.sendMessage(new AskResourceType());
    }

    public void fetchResourcePlacement() throws IOException {
        clientHandler.sendMessage(new AskResourcePlacement());
    }

    public void fetchResourcePlacementOrDiscard() throws IOException{
        clientHandler.sendMessage(new AskResourcePlacementOrDiscard());
    }

    public void fetchSwapWhite(ResourceType type1, ResourceType type2) throws IOException {
        clientHandler.sendMessage(new AskSwapWhite(type1, type2));
    }

    public void displayGenericMessage(String genericMessage) throws IOException {
        clientHandler.sendMessage(new GenericMessage(genericMessage));
    }


    public void fetchPlayerAction() throws IOException {
        clientHandler.sendMessage(new AskAction());
    }

    public void fetchDoneAction() throws IOException {
        clientHandler.sendMessage(new AskDone());
    }

    public void displayMarketTray(MarketTray marketTray) throws IOException{
        clientHandler.sendMessage(new DisplayMarket(marketTray));
    }

    public void displayDeck(){
    }


    public void displayPersonalBoard(PersonalBoard personalBoard) throws IOException {
        clientHandler.sendMessage(new DisplayPersonalBoard(personalBoard));
    }

    @Override
    public void update(Message message) throws IOException {
        if (clientHandler.getNickname().equals(message.getReceiverNickname())) {
            clientHandler.sendMessage(message);
        }
    }
}