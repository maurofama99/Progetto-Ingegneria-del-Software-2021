package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.*;
import it.polimi.ingsw.network.messagessc.NoAvailableResources;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;



//gestisce tutti gli stati che il player può avere, comprese le mosse

public class PlayerController {
    private final GameController gameController;
    private PlayerAction playerAction;
    private ArrayList<Resource> resources = new ArrayList<>();
    private int whiteCounter = 0;
    private int cont = 0;
    private ResourceType typeInput1, typeInput2, typeOut;
    private ResourceType type1, type2; //usati per il dobbio swap
    private boolean hasExtra = false;

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
                boolean goAhead = true;
                String answer;
                answer = ((ResourcePlacement) msg).getFloor().replaceAll("\\s+", "");
                if (answer.equalsIgnoreCase("discard")) {
                    //scarta la risorsa e aggiungi faith point
                    addFaithPointsToOpponents(1);
                    resources.remove(resources.get(resources.size() - 1));
                } else if (answer.equalsIgnoreCase("switch")) {
                    try{
                        gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().getDepot().switchFloors(((ResourcePlacement) msg).getSourceFloor(),((ResourcePlacement) msg).getDestFloor());
                    } catch (IllegalArgumentException e){
                        playerVirtualView().displayGenericMessage(e.getMessage() + ". Try Again...\n");
                    }
                } else if (answer.equalsIgnoreCase("extra")){
                    try{
                        gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().getDepot().addResourceToExtraDepot(resources.get(resources.size() - 1));
                    } catch (IllegalArgumentException e){
                        goAhead = false;
                        playerVirtualView().displayGenericMessage(e.getMessage() + ". Try Again...\n");
                    }
                    if (goAhead) {
                        resources.remove(resources.get(resources.size() - 1));
                    }
                } else if (Integer.parseInt(answer) <= 3 && Integer.parseInt(answer) >= 1) {
                    //aggiungi la risorsa al deposito
                    try{
                        gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().getDepot().addResourceToDepot(resources.get(resources.size() - 1), Integer.parseInt(((ResourcePlacement) msg).getFloor()));
                    } catch (IllegalArgumentException e){
                        goAhead = false;
                        playerVirtualView().displayGenericMessage(e.getMessage() + ". Try Again...\n");
                    }
                    if (goAhead) {
                        resources.remove(resources.get(resources.size() - 1));
                    }
                } else { //should not enter here
                    throw new IllegalArgumentException("Wrong Input in market action");
                }

                if (!resources.isEmpty()) {
                    playerVirtualView().displayGenericMessage(resources.get(resources.size() - 1).toString() +
                                                             "\nIn which floor of the depot do you want to place this resource? (Type DISCARD to discard this resource and give one faith point to your opponents or Type SWITCH to switch two floors))");
                    playerVirtualView().fetchResourcePlacement();
                } else {
                    playerVirtualView().displayGenericMessage(gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().toString());
                    playerVirtualView().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
                }
                break;
            case SWAPPED_RESOURCE:
                String type;
                ResourceType resourceType;
                type = ((SwappedResource) msg).getResourceType().replaceAll("\\s+", "");
                switch (type) {
                    case "servant":
                        resourceType = ResourceType.SERVANT;
                        break;
                    case "shield":
                        resourceType = ResourceType.SHIELD;
                        break;
                    case "stone":
                        resourceType = ResourceType.STONE;
                        break;
                    case "coin":
                        resourceType = ResourceType.COIN;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + type);
                }
                resources.add(new Resource(1, resourceType));
                whiteCounter--;
                if (whiteCounter > 0){
                    playerVirtualView().fetchSwapWhite(type1,type2);
                } else {
                    whiteCounter=0;
                    playerVirtualView().displayGenericMessage(resources.get(resources.size() - 1).toString() +
                            "\nIn which floor of the depot do you want to place this resource? (Type DISCARD to discard this resource and give one faith point to your opponents or " +
                            "Type SWITCH to switch two floors)");
                    playerVirtualView().fetchResourcePlacement();
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

        if (gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().size()==2){
            if (gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().get(0).getLeaderEffect().getEffectType().equals(EffectType.SWAPWHITE) &&
                    gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().get(1).getLeaderEffect().getEffectType().equals(EffectType.SWAPWHITE)){
                doubleSwap = true;
            }
        }

        //applica effetto swap white se attivo
        if (gameController.getTable().getCurrentPlayer().getPersonalBoard().hasEffect(EffectType.SWAPWHITE) && !doubleSwap){
            singleSwapWhite();
        } else if (!doubleSwap){ //altrimenti togli le white resources
            resources.removeIf(e -> e.getType().equals(ResourceType.WHITERESOURCE));
        }

        //rimuovi i faithpoint e muovi il faith marker
        faithFilter();

        //chiedi risorsa per risorsa dove la vuole posizionare
        if (!doubleSwap) {
            try {
                playerVirtualView().displayGenericMessage("You chose: " + resources.toString() +
                        "\n" +
                        resources.get(resources.size() - 1).toString()
                        + "\nIn which floor of the depot do you want to place this resource? (Type DISCARD to discard this resource and give one faith point to your opponents or " +
                        "Type SWITCH to switch two floors)");
                //nel caso in cui abbia extradepot attivato
                extraDepotAlert();
            } catch (IndexOutOfBoundsException e){
                playerVirtualView().displayGenericMessage("You don't have resource to place");
                playerVirtualView().displayGenericMessage(gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().toString());
                playerVirtualView().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
            }
            playerVirtualView().fetchResourcePlacement();
        } else {
            type1 = (ResourceType) gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().get(0).getLeaderEffect().getObject();
            type2 = (ResourceType) gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().get(1).getLeaderEffect().getObject();
            //conta quante biglie bianche ha selezionato
            for (Resource resource : resources){
                if (resource.getType().equals(ResourceType.WHITERESOURCE)) whiteCounter++;
            }
            playerVirtualView().displayGenericMessage("You chose: " + resources.toString());
            resources.removeIf(e -> e.getType().equals(ResourceType.WHITERESOURCE));
            if (whiteCounter > 0){
                playerVirtualView().displayGenericMessage("You selected " + whiteCounter + " white marbles, now choose for each marble which Leader Card you want to use to transform it in a new resource!");
                //devi chiedere whiteCounter volte al player che tipo di risorsa vuole tra type1 o type 2 e poi la aggiungi all'array di risorse resources
                playerVirtualView().fetchSwapWhite(type1,type2);
            } else {
                playerVirtualView().displayGenericMessage(resources.get(resources.size() - 1).toString() +
                        "\nIn which floor of the depot do you want to place this resource? (Type DISCARD to discard this resource and give one faith point to your opponents or " +
                        "Type SWITCH to switch two floors)");
                extraDepotAlert();
                playerVirtualView().fetchResourcePlacement();
            }
        }
    }

    private void singleSwapWhite(){
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
    }

    private void faithFilter() {
        for (Resource res : resources){
            if(res.getType().equals(ResourceType.FAITHPOINT)){
                gameController.getTable().getCurrentPlayer().getPersonalBoard().getFaithTrack().moveForward(gameController.getTable().getCurrentPlayer(), 1);
                checkPositionFaith(gameController.getTable().getCurrentPlayer());
            }
        }
        resources.removeIf(e -> e.getType().equals(ResourceType.FAITHPOINT));
    }

    public void extraDepotAlert() throws IOException {
        if (gameController.getTable().getCurrentPlayer().getPersonalBoard().hasEffect(EffectType.EXTRADEPOT)){
            ResourceType extraDepotResource;
            hasExtra = true;
            for(LeaderCard leaderCard : gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards()) {
                if (leaderCard.getLeaderEffect().getEffectType().equals(EffectType.EXTRADEPOT)) {
                    extraDepotResource = (ResourceType) leaderCard.getLeaderEffect().getObject();
                    if (resources.get(resources.size() - 1).getType() == extraDepotResource) playerVirtualView().displayGenericMessage("Type \"extra\" to place this resource in the extra depot");
                }
            }
        }
    }

    //quando il giocatore decide di attivare una delle sue leader card
    //devo mettere nella cli il display delle leader card
    public void activateLeaderCard(Message msg) throws IOException {
        boolean trueOrFalse = true;
        try {
            gameController.getTable().getCurrentPlayer().activateLeaderCard(((ActivateLeader)msg).getLeaderCard());
        }
        catch (IllegalArgumentException e){
            playerVirtualView().displayGenericMessage("You don't have the requirements to activate it");
            playerVirtualView().fetchPlayLeader(gameController.getTable().getCurrentPlayer().getLeaderCards());
            trueOrFalse = false;
        }

        if (trueOrFalse) {
            playerVirtualView().displayGenericMessage("\nYou activated these leader cards!\n" +
                    gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards().toString());

            playerVirtualView().fetchPlayLeader(gameController.getTable().getCurrentPlayer().getPersonalBoard().getActiveLeaderCards());
        }
    }

    public void discardLeader(Message msg) throws IOException {

        gameController.getTable().getCurrentPlayer().getLeaderCards().remove(((DiscardOneLeader)msg).getLeaderCard());
        gameController.getTable().getCurrentPlayer().getPersonalBoard().getFaithTrack().moveForward(gameController.getTable().getCurrentPlayer(), 1);

        playerVirtualView().fetchPlayLeader(gameController.getTable().getCurrentPlayer().getLeaderCards());

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
            playerVirtualView().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
        }
    }

    public void activateProduction(Message msg) throws IOException, CloneNotSupportedException {
        //todo se ha la leader card attiva
        switch (msg.getMessageType()){
            case ACTIVATE_PRODUCTION:

                if (((ActivateProduction)msg).getSlot1()==1){
                    gameController.getTable().getCurrentPlayer().activateProd(0);
                    if (gameController.getTable().getCurrentPlayer().activateProd(0))
                        playerVirtualView().displayGenericMessage("You activated production in slot " + 1 + "\n");
                }
                if (((ActivateProduction)msg).getSlot2()==1){
                    gameController.getTable().getCurrentPlayer().activateProd(1);
                    if (gameController.getTable().getCurrentPlayer().activateProd(1))
                        playerVirtualView().displayGenericMessage("You activated production in slot " + 2 + "\n");
                }
                if (((ActivateProduction)msg).getSlot3()==1){
                    gameController.getTable().getCurrentPlayer().activateProd(2);
                    if (gameController.getTable().getCurrentPlayer().activateProd(2))
                        playerVirtualView().displayGenericMessage("You activated production in slot " + 3 + "\n");
                }


                playerVirtualView().displayGenericMessage(gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().getStrongBox().toString());


                if (((ActivateProduction)msg).getBasic()==1){
                    cont=0;
                    playerVirtualView().displayGenericMessage(gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().getDepot() +
                            "You can now spend two resources from floors to get one resource in Strongbox!: \n");
                    playerVirtualView().fetchResourceType();
                }
                else{
                    playerVirtualView().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
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
                    gameController.getTable().getCurrentPlayer().basicProduction(typeInput1, typeInput2, typeOut);
                    playerVirtualView().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
                }


        }
    }

    public void addFaithPointsToOpponents(int faithPoints){
        for(Player player : gameController.getTable().getPlayers()){
            if (gameController.isSinglePlayer()){
                gameController.getTable().getLorenzoIlMagnifico().moveBlackCross(player, 1);
                checkPositionFaith(player);
            }

            if(!gameController.getTable().getCurrentPlayer().equals(player))
                player.getPersonalBoard().getFaithTrack().moveForward(player, faithPoints);
            checkPositionFaith(player);
        }
    }


    public void checkPositionFaith(Player player){
        if (player.getPersonalBoard().getFaithTrack().getFaithMarkerPosition() %8==0){
                for (Player p : gameController.getTable().getPlayers()){
                    p.getPersonalBoard().getFaithTrack().getTrack().get(p.getPersonalBoard().getFaithTrack().getFaithMarkerPosition())
                            .turnFavorAddPoints(p,p.getPersonalBoard().getFaithTrack().getFaithMarkerPosition());
            }
        }

        if (gameController.isSinglePlayer()){
            Player sP = gameController.getTable().getCurrentPlayer();
            if (gameController.getTable().getCurrentPlayer().getPersonalBoard().getFaithTrack().getBlackCrossPosition()%8==0)
                sP.getPersonalBoard().getFaithTrack().getTrack().get(sP.getPersonalBoard().getFaithTrack().getFaithMarkerPosition())
                        .turnFavorAddPoints(sP,sP.getPersonalBoard().getFaithTrack().getFaithMarkerPosition());
        }
    }


}
