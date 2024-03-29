package it.polimi.ingsw.view;


import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagessc.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observerPattern.Observer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The virtual view of the game. The methods are already described in CLI or GUI class. Here they use the client
 * handler of the server to send messages accordingly to players' action in the GUI or CLI.
 */
public class VirtualView implements Observer {
    private final ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void fetchResourceType() throws IOException {
        clientHandler.sendMessage(new AskResourceType());
    }

    public void fetchResourcePlacement(Resource resource) throws IOException {
        clientHandler.sendMessage(new AskResourcePlacement(resource));
    }

    public void fetchSwapWhite(ResourceType type1, ResourceType type2) throws IOException {
        clientHandler.sendMessage(new AskSwapWhite(type1, type2));
    }

    public void fetchExtraProd(Resource resource) throws IOException {
        clientHandler.sendMessage(new ExtraProduction(resource));
    }

    public void displayGenericMessage(String genericMessage) throws IOException {
        clientHandler.sendMessage(new GenericMessage(genericMessage));
    }

    public void displayPopup(String message) throws IOException {
        clientHandler.sendMessage(new GenericPopup(message));
    }

    public void fetchPlayerAction() throws IOException {
        clientHandler.sendMessage(new AskAction());
    }

    public void fetchDoneAction(ArrayList<LeaderCard> leaderCards) throws IOException {
        clientHandler.sendMessage(new AskDone(leaderCards));
    }

    public void fetchPlayLeader(ArrayList<LeaderCard> leaderCards, boolean isEndTurn) throws IOException{
        clientHandler.sendMessage(new AskPlayLeader(leaderCards, isEndTurn));
    }

    public void displayMarketTray(MarketTray marketTray) throws IOException{
        clientHandler.sendMessage(new DisplayMarket(marketTray));
    }

    public void displayDeck(DevCard[][] showedDeck)throws IOException{
        clientHandler.sendMessage(new DisplayDevCards(showedDeck));
    }

    public void displayPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse, ArrayList<LeaderCard> activeLeaderCards) throws IOException {
        clientHandler.sendMessage(new DisplayPersonalBoard(faithTrack, slots, warehouse, activeLeaderCards));
    }

    public void sendPersonalBoard(String name, FaithTrack fT, Slot[] slots, SerializableWarehouse wH, ArrayList<LeaderCard> lC) throws IOException {
        clientHandler.sendMessage(new SendOtherPersonalBoard(name, fT, slots, wH, lC));
    }

    public void displayGUIPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse) throws IOException {
        clientHandler.sendMessage(new DisplayGUIPersonalBoard(faithTrack, slots, warehouse));
    }

    public void displayToken(Token token) throws IOException {
        clientHandler.sendMessage(new TurnToken(token));
    }

    public void displayWinningMsg() throws IOException {
        clientHandler.sendMessage(new Message( "client", Content.MATCH_FINISHED));
    }

    public void forcedEnd(String nickname) throws IOException {
        clientHandler.sendMessage(new ForcedEnd(nickname));
    }

    @Override
    public void update(Message message) throws IOException {
        if (clientHandler.getNickname().equals(message.getReceiverNickname())) {
            clientHandler.sendMessage(message);
        }
    }

    public void displayBasicProdPopup(int i, String s) throws IOException{
        clientHandler.sendMessage(new BasicProd(i, s));
    }
}