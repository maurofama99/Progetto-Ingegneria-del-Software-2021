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
import it.polimi.ingsw.network.messagessc.NoAvailableResources;
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
    private ArrayList<Resource> resources = new ArrayList<>();

    int cont = 0;
    ResourceType typeInput1, typeInput2, typeOut;

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

    public void receiveMessage(Message msg) throws IOException, IllegalAccessException, CloneNotSupportedException {

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
        switch (msg.getMessageType()) {
            case GOING_MARKET:
                goToMarket(msg);
                break;
            case RESOURCE_PLACEMENT:
                String answer;
                answer = ((ResourcePlacement) msg).getFloor().replaceAll("\\s+", "");
                if (answer.equalsIgnoreCase("discard")) {
                    //scarta la risorsa e aggiungi faith point
                    addFaithPointsToOpponents(1);
                    resources.remove(resources.get(resources.size() - 1));
                } else if (Integer.parseInt(answer) <= 3 && Integer.parseInt(answer) >= 1) {
                    //aggiungi la risorsa al deposito
                    gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().getDepot().addResourceToDepot(resources.get(resources.size() - 1), Integer.parseInt(((ResourcePlacement) msg).getFloor()));
                    resources.remove(resources.get(resources.size() - 1));
                } else {
                    //gestisci input errato
                }

                if (!resources.isEmpty()) {
                    playerVirtualView().displayGenericMessage(resources.get(resources.size() - 1).toString() +
                                                             "\nIn which floor of the depot do you want to place this resource? (Type DISCARD to discard this resource and give one faith point to your opponents)");
                    playerVirtualView().fetchResourcePlacement();
                } else {
                    playerVirtualView().displayGenericMessage(gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().toString());
                    playerVirtualView().fetchDoneAction();
                }
        }
    }




    public void goToMarket(Message msg) throws IOException {
        int index  = ((GoingMarket)msg).getIndex();
        boolean rowOrCol = ((GoingMarket)msg).isRowOrColumn();
        boolean doubleSwap = false;

        if (rowOrCol) { //selezione riga
            resources.addAll(gameController.getTable().getMarketTray().selectRow(index));
        } else { //selezione colonna
            resources.addAll(gameController.getTable().getMarketTray().selectColumn(index));
        }

        //SWAP WHITE
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

        //rimuovi i faithpoint e muovi il faith marker
        for (Resource res : resources){
            if(res.getType().equals(ResourceType.FAITHPOINT)){
                gameController.getTable().getCurrentPlayer().getPersonalBoard().getFaithTrack().moveForward(1);
                res.setType(ResourceType.WHITERESOURCE);
            }
        }
        resources.removeIf(e -> e.getType().equals(ResourceType.WHITERESOURCE));

        //chiedi risorsa per risorsa dove la vuole posizionare
        playerVirtualView().displayGenericMessage("You chose: " + resources.toString() +
                                                  "\n" + resources.get(resources.size() - 1).toString() +
                                                  "\nIn which floor of the depot do you want to place this resource? (Type DISCARD to discard this resource and give one faith point to your opponents)");
        playerVirtualView().fetchResourcePlacement();

        //todo: gestire doppia swap white
        //todo: muovere le risorse nel piano

    }



    //quando il giocatore decide di attivare una delle sue leader card
    //devo mettere nella cli il display delle leader card
    public void activateLeaderCard(Message msg) throws IOException {
        gameController.getTable().getCurrentPlayer().getLeaderCards().remove(((ActivateLeader)msg).getLeaderCard());
        gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().add(((ActivateLeader)msg).getLeaderCard());
        playerVirtualView().displayGenericMessage(gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().toString());
    }


    /**
     * When the player wants to buy a development card. This method removes the ard from the matrix and place it on a player's slot.
     * @param msg It contains row, column of the matrix and a slot number of the player's personal board.
     * @throws IllegalAccessException
     * @throws IOException
     */

    public void buyDevCard(Message msg) throws IllegalAccessException, IOException, CloneNotSupportedException {
        if (!gameController.getTable().getDevCardsDeck().getDevCard(((BuyDevCard)msg).getRow(), ((BuyDevCard)msg).getColumn()).checkRequirements(gameController.getTable().getCurrentPlayer()))
            playerVirtualView().update(new NoAvailableResources(gameController.getTable().getCurrentPlayer().getNickname()));

        else {
            DevCard devCard = gameController.getTable().getDevCardsDeck().removeAndGetCard(((BuyDevCard) msg).getRow(), ((BuyDevCard) msg).getColumn());
            playerVirtualView().displayGenericMessage("You bought this development card: \n" + devCard.toString());
            gameController.getTable().getCurrentPlayer().buyDevCard(devCard, ((BuyDevCard) msg).getSlot());
            playerVirtualView().displayGenericMessage("It's placed here: \n" + Arrays.toString(gameController.getTable().getCurrentPlayer().getPersonalBoard().getSlots()));
            playerVirtualView().fetchDoneAction();
        }
    }

    public void activateProduction(Message msg) throws IOException, CloneNotSupportedException {
        //todo se ha la leader card attiva
        switch (msg.getMessageType()){
            case ACTIVATE_PRODUCTION:

                if (((ActivateProduction)msg).getSlot1()==1){
                    gameController.getTable().getCurrentPlayer().activateProd(0);
                }
                if (((ActivateProduction)msg).getSlot2()==1){
                    gameController.getTable().getCurrentPlayer().activateProd(1);
                }
                if (((ActivateProduction)msg).getSlot3()==1){
                    gameController.getTable().getCurrentPlayer().activateProd(2);
                }

                playerVirtualView().displayGenericMessage("You activated production in slots!\n"
                        + gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().toString());

                if (((ActivateProduction)msg).getBasic()==1){
                    playerVirtualView().displayGenericMessage("You can now spend two resources from floors to get one resource in Strongbox!: \n :");
                    playerVirtualView().fetchResourceType();
                }
                break;

            case RESOURCE_TYPE:
                if (cont==0){
                    typeInput1 =((ResourceTypeChosen) msg).getResourceType();
                    cont ++;
                    playerVirtualView().fetchResourceType();
                }
                if (cont==1){
                    typeInput2 = ((ResourceTypeChosen) msg).getResourceType();
                    cont ++;
                    playerVirtualView().displayGenericMessage("\nNow you can choose a type of resource you want to place in StrongBox!!\n");
                    playerVirtualView().fetchResourceType();
                }
                else {
                    typeOut = ((ResourceTypeChosen) msg).getResourceType();
                    cont=0;
                    gameController.getTable().getCurrentPlayer().getPersonalBoard().basicProduction(typeInput1, typeInput2, typeOut);
                    playerVirtualView().fetchDoneAction();
                }


        }
    }

    public void discardLeader(Message msg){

    }

    public void addFaithPointsToOpponents(int faithpoints){
        for(Player player : gameController.getTable().getPlayers()){
            player.getPersonalBoard().getFaithTrack().moveForward(faithpoints);
        }
    }


}
