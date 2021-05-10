package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.client.ServerHandler;
import it.polimi.ingsw.network.messagescs.ActivateLeader;
import it.polimi.ingsw.network.messagescs.BuyDevCard;
import it.polimi.ingsw.network.messagescs.GoingMarket;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


//gestisce tutti gli stati che il player può avere, comprese le mosse

public class PlayerController {
    private GameController gameController;
    private PlayerAction playerAction;

    public PlayerController(GameController gameController) {
        this.gameController = gameController;
        this.playerAction = PlayerAction.WAITING;
    }

    public void setPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }

    public VirtualView playerVirtualView() {
        return gameController.getVvMap().get(gameController.getTable().getCurrentPlayer().getNickname());
    }

    public void receiveMessage(Message msg) throws IOException, IllegalAccessException {

        switch (playerAction){
            case GOTO_MARKET:
                goToMarket(msg);
                break;

            case ACTIVATE_LEADER:
                activateLeaderCard(msg);
                break;

            case BUY_DEVCARD:
                buyDevCard(msg);
                break;

            case ACTIVATE_PRODUCTION:
                activateProduction(msg);
                break;

            case DISCARD_LEADER:
                discardLeader(msg);
                break;

            case WAITING:
                playerVirtualView().displayGenericMessage
                        ("-----------------------\n|    Please wait...    |\n-----------------------\n");
                gameController.getTable().nextPlayer();
                playerVirtualView().fetchPlayerAction();
                break;
        }
    }


    public void goToMarket(Message msg){
        int index  = ((GoingMarket)msg).getIndex();
        boolean rowOrCol = ((GoingMarket)msg).isRowOrColumn();
        ArrayList<Resource> resources;

        if (rowOrCol) { //selezione riga
            resources = gameController.getTable().getMarketTray().selectRow(index);
        } else { //selezione colonna
            resources = gameController.getTable().getMarketTray().selectColumn(index);
        }

        if (gameController.getTable().getCurrentPlayer().getPersonalBoard().hasEffect(EffectType.SWAPWHITE)){
            //sostituisci le white resources con la risorsa del leader effect
            //gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().get(0).getLeaderEffect().getObject()

        } else {
            resources.removeIf(e -> e.getType().equals(ResourceType.WHITERESOURCE));
        }

    }

    //quando il giocatore decide di attivare una delle sue leader card
    //devo mettere nella cli il display delle leader card
    public void activateLeaderCard(Message msg) throws IOException {
        gameController.getTable().getCurrentPlayer().getLeaderCards().remove(((ActivateLeader)msg).getLeaderCard());
        gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().add(((ActivateLeader)msg).getLeaderCard());
        playerVirtualView().displayGenericMessage(gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().toString());
    }

    //quando il giocatore compra una carpa, lui seleziona quale e
    public void buyDevCard(Message msg) throws IllegalAccessException, IOException {
        DevCard devCard = gameController.getTable().getDevCardsDeck().removeAndGetCard(((BuyDevCard)msg).getRow(), ((BuyDevCard)msg).getColumn());
        playerVirtualView().displayGenericMessage("You bought this development card: \n" +devCard.toString());
        gameController.getTable().getCurrentPlayer().buyDevCard(devCard, ((BuyDevCard)msg).getSlot());
        playerVirtualView().displayGenericMessage("It's placed here: \n" + Arrays.toString(gameController.getTable().getCurrentPlayer().getPersonalBoard().getSlots()));
        playerVirtualView().fetchDoneAction();
    }

    public void activateProduction(Message msg){
        //il giocatore scrive stringa di numeri tipo 101 per gli slot, se è 1 la vuole attivare se è 0 no
        //per la produzione base scrive BASIC
    }

    public void discardLeader(Message msg){

    }


}
