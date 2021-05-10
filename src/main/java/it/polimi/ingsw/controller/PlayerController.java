package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.client.ServerHandler;
import it.polimi.ingsw.network.messagescs.*;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;


//gestisce tutti gli stati che il player può avere, comprese le mosse

public class PlayerController {
    private final GameController gameController;
    private PlayerAction playerAction;
    private ArrayList<Resource> resources;

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
                receiveMessageOnMarket(msg);
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
                        (       "--------------------------------\n" +
                                "|         Please wait...       |\n" +
                                "|    It's not your turn yet    |\n" +
                                "--------------------------------\n");
                gameController.getTable().nextPlayer();
                gameController.askPlayerAction(playerVirtualView());
                break;
        }
    }

    public void receiveMessageOnMarket(Message msg) throws IOException {
        switch (msg.getMessageType()){
            case GOING_MARKET:
                goToMarket(msg);
                break;
            case RESOURCE_PLACEMENT:
                gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().getDepot().addResourceToDepot(resources.get(resources.size()-1), ((ResourcePlacement) msg).getFloor());
                resources.remove(resources.get(resources.size()-1));
                playerVirtualView().displayGenericMessage(resources.toString());
                if (!resources.isEmpty()){
                    playerVirtualView().displayGenericMessage(resources.get(resources.size()-1).toString() + "\n");
                    playerVirtualView().fetchResourcePlacement();
                } else {
                    playerVirtualView().displayGenericMessage(""+gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().toString());
                    playerVirtualView().fetchDoneAction();
                }
        }
    }


    public void goToMarket(Message msg) throws IOException {
        int index  = ((GoingMarket)msg).getIndex();
        boolean rowOrCol = ((GoingMarket)msg).isRowOrColumn();
        boolean doubleSwap = false;

        if (rowOrCol) { //selezione riga
            resources = gameController.getTable().getMarketTray().selectRow(index);
        } else { //selezione colonna
            resources = gameController.getTable().getMarketTray().selectColumn(index);
        }

        //SWAP WHITE
        //se ha una sola swap white va bene quello già scritto
        //se ha due leader swap white nel momento dell'assegnazione risorse chiedi in quale la vuole trasformare

        if (gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().size()==2){
            if (gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().get(0).getLeaderEffect().getEffectType().equals(EffectType.SWAPWHITE) &&
                    gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().get(1).getLeaderEffect().getEffectType().equals(EffectType.SWAPWHITE)){
                doubleSwap = true;
            }
        }

        //applica effetto swap white se attivo
        if (gameController.getTable().getCurrentPlayer().getPersonalBoard().hasEffect(EffectType.SWAPWHITE) && !doubleSwap){
            for(LeaderCard leaderCard : gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards()){
                if (leaderCard.getLeaderEffect().getEffectType().equals(EffectType.SWAPWHITE)){
                    Resource newResource = (Resource) leaderCard.getLeaderEffect().getObject();
                    for (Resource res : resources){
                        if (res.getType().equals(ResourceType.WHITERESOURCE)){
                            res.setType(newResource.getType());
                        }
                    }
                }
            }
        } else if (!doubleSwap){ //altrimenti togli le white resources
            resources.removeIf(e -> e.getType().equals(ResourceType.WHITERESOURCE));
        }

        //todo: rimuovi i faithpoint e mouvi il faith marker

        //chiedi risorsa per risorsa dove la vuole posizionare
        playerVirtualView().displayGenericMessage("You chose: " + resources.toString());
        playerVirtualView().displayGenericMessage(resources.get(resources.size()-1).toString());
        playerVirtualView().fetchResourcePlacement();

    }


    //quando il giocatore decide di attivare una delle sue leader card
    //devo mettere nella cli il display delle leader card
    public void activateLeaderCard(Message msg) throws IOException {
        gameController.getTable().getCurrentPlayer().getLeaderCards().remove(((ActivateLeader)msg).getLeaderCard());
        gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().add(((ActivateLeader)msg).getLeaderCard());
        playerVirtualView().displayGenericMessage(gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().toString());
    }

    //quando il giocatore compra una carta, lui seleziona quale (indici matrice) e slot
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
