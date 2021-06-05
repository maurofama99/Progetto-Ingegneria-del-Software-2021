package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.*;
import it.polimi.ingsw.network.messagessc.NoAvailableResources;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.util.*;


//gestisce tutti gli stati che il player può avere, comprese le mosse

public class PlayerController {
    private final GameController gameController;
    private PlayerAction playerAction;
    private ArrayList<Resource> resources = new ArrayList<>();
    private int whiteCounter = 0;
    private int cont = 0;
    private ResourceType typeInput1;
    private ResourceType typeInput2;
    private ResourceType type1, type2; //usati per il dobbio swap

    public PlayerController(GameController gameController) {
        this.gameController = gameController;
        this.playerAction = PlayerAction.WAITING;
    }

    public void setPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }

    public VirtualView playerVV() {
        return gameController.getVvMap().get(gameController.getTable().getCurrentPlayer().getNickname());
    }

    public void receiveMessage(Message msg) throws IOException, CloneNotSupportedException {

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
                playerVV().displayGenericMessage
                        (       "--------------------------------\n" +
                                "|         Please wait...       |\n" +
                                "|    It's not your turn yet    |\n" +
                                "--------------------------------\n");
                playerVV().displayGUIPersonalBoard(getPlayerPB().getFaithTrack(), getPlayerPB().getSlots(), new SerializableWarehouse(getPlayerPB().getWarehouse()));
                playerVV().displayPopup("Please wait...\nIt's not your turn yet");
                if (!gameController.isSinglePlayer()) sendPBToOthers();
                gameController.getTable().nextPlayer();
                gameController.askPlayerAction(playerVV());
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
                    addFaithPointsToOpponents(1);
                    resources.remove(resources.get(resources.size() - 1));
                }
                else if (answer.equalsIgnoreCase("switch")) {
                    try{
                        getPlayerPB().getWarehouse().getDepot().switchFloors(((ResourcePlacement) msg).getSourceFloor(),((ResourcePlacement) msg).getDestFloor());
                    } catch (IllegalArgumentException e){
                        playerVV().displayGenericMessage(e.getMessage() + ". Try Again...\n");
                        playerVV().displayPopup(e.getMessage() + ". Try Again...\n");
                    }
                }
                else if (answer.equalsIgnoreCase("extra")){
                    try{
                        getPlayerPB().getWarehouse().getDepot().addResourceToExtraDepot(resources.get(resources.size() - 1));
                    } catch (IllegalArgumentException e){
                        goAhead = false;
                        playerVV().displayGenericMessage(e.getMessage() + ". Try Again...\n");
                        playerVV().displayPopup(e.getMessage() + ". Try Again...\n");
                    }
                    if (goAhead) {
                        resources.remove(resources.get(resources.size() - 1));
                    }
                }
                else if (Integer.parseInt(answer) <= 3 && Integer.parseInt(answer) >= 1) {
                    //aggiungi la risorsa al deposito
                    try {
                        getPlayerPB().getWarehouse().getDepot().addResourceToDepot(resources.get(resources.size() - 1), Integer.parseInt(((ResourcePlacement) msg).getFloor()));
                    } catch (IllegalArgumentException e) {
                        goAhead = false;
                        playerVV().displayGenericMessage(e.getMessage() + ". Try Again...\n");
                        playerVV().displayPopup(e.getMessage() + ". Try Again...\n");
                    }
                    if (goAhead) {
                        resources.remove(resources.get(resources.size() - 1));
                    }
                }
                if (!resources.isEmpty()) {
                    playerVV().displayGenericMessage(resources.get(resources.size() - 1).toString() +
                                                             "\nIn which floor of the depot do you want to place this resource? " +
                            "\n'DISCARD' to discard this resource and give one faith point to your opponents or " +
                            "\n'SWITCH' to switch two floors");
                    extraDepotAlert();
                    displayPB();
                    playerVV().fetchResourcePlacement(resources.get(resources.size() - 1));
                }
                else {
                    displayPB();
                    playerVV().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
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
                    playerVV().fetchSwapWhite(type1,type2);
                }
                else {
                    whiteCounter=0;
                    playerVV().displayGenericMessage(resources.get(resources.size() - 1).toString() +
                            "\nIn which floor of the depot do you want to place this resource? \n" +
                            "'DISCARD' to discard this resource and give one faith point to your opponents\n" +
                            "'SWITCH' to switch two floors");
                    extraDepotAlert();
                    playerVV().fetchResourcePlacement(resources.get(resources.size() - 1));
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

        if (getPlayerPB().getActiveLeaderCards().size()==2){
            if (getPlayerPB().getActiveLeaderCards().get(0).getLeaderEffect().getEffectType().equals(EffectType.SWAPWHITE) &&
                    getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getEffectType().equals(EffectType.SWAPWHITE)){
                doubleSwap = true;
            }
        }

        //applica effetto swap white se attivo
        if (getPlayerPB().hasEffect(EffectType.SWAPWHITE) && !doubleSwap){
            singleSwapWhite();
        } else if (!doubleSwap){ //altrimenti togli le white resources
            resources.removeIf(e -> e.getType().equals(ResourceType.WHITERESOURCE));
        }

        faithFilter();

        if (!doubleSwap) {
            try {
                playerVV().displayGenericMessage("You chose: " + resources.toString() +
                        "\n" +
                        resources.get(resources.size() - 1).toString()
                        + "\nIn which floor of the depot do you want to place this resource? \n" +
                        "'DISCARD' to discard this resource and give one faith point to your opponents or \n" +
                        "'SWITCH' to switch two floors");
                extraDepotAlert();
                playerVV().fetchResourcePlacement(resources.get(resources.size() - 1));

            } catch (IndexOutOfBoundsException e){
                playerVV().displayGenericMessage("You don't have resource to place");
                playerVV().displayPopup("You don't have resource to place");
                playerVV().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
            }

        } else {
            type1 = ((Resource) getPlayerPB().getActiveLeaderCards().get(0).getLeaderEffect().getObject()).getType();
            type2 = ((Resource) getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getObject()).getType();
            //conta quante biglie bianche ha selezionato
            for (Resource resource : resources){
                if (resource.getType().equals(ResourceType.WHITERESOURCE)) whiteCounter++;
            }
            playerVV().displayGenericMessage("You chose: " + resources.toString());
            resources.removeIf(e -> e.getType().equals(ResourceType.WHITERESOURCE));
            if (whiteCounter > 0){
                playerVV().displayGenericMessage("You selected " + whiteCounter + " white marbles, now choose for each marble which Leader Card you want to use to transform it in a new resource!");
                //devi chiedere whiteCounter volte al player che tipo di risorsa vuole tra type1 o type 2 e poi la aggiungi all'array di risorse resources
                playerVV().fetchSwapWhite(type1,type2);
            } else {
                playerVV().displayGenericMessage(resources.get(resources.size() - 1).toString() +
                        "\nIn which floor of the depot do you want to place this resource? \n" +
                        "'DISCARD' to discard this resource and give one faith point to your opponents or \n" +
                        "'SWITCH' to switch two floors");
                extraDepotAlert();
                playerVV().fetchResourcePlacement(resources.get(resources.size() - 1));
            }
        }
    }

    private void singleSwapWhite(){
        for(LeaderCard leaderCard : getPlayerPB().getActiveLeaderCards()){
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
                getPlayerPB().getFaithTrack().moveForward(gameController.getTable().getCurrentPlayer(), 1);
            }
        }
        resources.removeIf(e -> e.getType().equals(ResourceType.FAITHPOINT));
    }

    public void extraDepotAlert() throws IOException {
        if (getPlayerPB().hasEffect(EffectType.EXTRADEPOT)){
            ResourceType extraDepotResource;
            for(LeaderCard leaderCard : getPlayerPB().getActiveLeaderCards()) {
                if (leaderCard.getLeaderEffect().getEffectType().equals(EffectType.EXTRADEPOT)) {
                    extraDepotResource = (ResourceType) leaderCard.getLeaderEffect().getObject();
                    for (Resource res : resources) {
                        if (res.getType() == extraDepotResource) {
                            playerVV().displayGenericMessage("Type \"extra\" to place this resource in the extra depot");
                        }
                    }
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
            displayPB();
        }
        catch (IllegalArgumentException e){
            playerVV().displayGenericMessage("You don't have the requirements to activate it");
            playerVV().displayPopup("You don't have the requirements to activate it");
            playerVV().fetchPlayLeader(gameController.getTable().getCurrentPlayer().getLeaderCards(), ((ActivateLeader)msg).isEndTurn());
            trueOrFalse = false;
        }

        if (trueOrFalse) {
            playerVV().fetchPlayLeader(gameController.getTable().getCurrentPlayer().getLeaderCards(), ((ActivateLeader)msg).isEndTurn());
        }
    }

    public void discardLeader(Message msg) throws IOException {
        gameController.getTable().getCurrentPlayer().getLeaderCards().remove(((DiscardOneLeader)msg).getLeaderCard());
        getPlayerPB().getFaithTrack().moveForward(gameController.getTable().getCurrentPlayer(), 1);
        displayPB();
        playerVV().fetchPlayLeader(gameController.getTable().getCurrentPlayer().getLeaderCards(), ((DiscardOneLeader)msg).isEndTurn());

    }


    /**
     * When the player wants to buy a development card. This method removes the ard from the matrix and place it on a player's slot.
     * @param msg It contains row, column of the matrix and a slot number of the player's personal board.
     * @throws IOException
     */

    public void buyDevCard(Message msg) throws IOException, CloneNotSupportedException {
        if (gameController.getTable().getDevCardsDeck().getDevCard(((BuyDevCard)msg).getRow(), ((BuyDevCard)msg).getColumn())==null){
            playerVV().displayGenericMessage("Selected card is not available anymore!\n");
            playerVV().fetchPlayerAction();
        }

        DevCard devCard = gameController.getTable().getDevCardsDeck().getDevCard(((BuyDevCard)msg).getRow(), ((BuyDevCard)msg).getColumn());
        ArrayList<Resource> requirements = new ArrayList<>();
        for (Resource  resource : devCard.getRequirementsDevCard()){
            requirements.add((Resource) resource.clone());
        }

        if (getPlayerPB().hasEffect(EffectType.DISCOUNT)){
            for (int i=0; i<devCard.getRequirementsDevCard().size(); i++){
                for (int j = 0; j<getPlayerPB().getActiveLeaderCards().size(); j++){
                    if (getPlayerPB().getActiveLeaderCards().get(j).getLeaderEffect().getEffectType().equals(EffectType.DISCOUNT)
                            && requirements.get(i).getType().equals(getPlayerPB().getActiveLeaderCards().get(j).getLeaderEffect().getObject()))
                        requirements.get(i).setQnt(requirements.get(i).getQnt() - 1);
                }
            }
        }

        if (gameController.getTable().getDevCardsDeck().getDevCard(((BuyDevCard)msg).getRow(), ((BuyDevCard)msg).getColumn()).checkRequirements(requirements, gameController.getTable().getCurrentPlayer())){
            try {
                gameController.getTable().getCurrentPlayer().buyDevCard(devCard, ((BuyDevCard) msg).getSlot());
                gameController.getTable().getDevCardsDeck().removeAndGetCard(((BuyDevCard)msg).getRow(), ((BuyDevCard)msg).getColumn());
                countDevCards();
                displayPB();
                playerVV().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
            } catch (IllegalAccessException e ){
                playerVV().displayGenericMessage(e.getMessage());
                playerVV().displayPopup(e.getMessage());
                playerVV().fetchPlayerAction();
            }
        }

        else {
            playerVV().update(new NoAvailableResources(gameController.getTable().getCurrentPlayer().getNickname()));
        }



    }

    public void activateProduction(Message msg) throws IOException, CloneNotSupportedException {
        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        switch (msg.getMessageType()){
            case ACTIVATE_PRODUCTION:

                if (((ActivateProduction)msg).getSlot1()==1 ){
                    try {
                        resourcesToAdd.addAll(gameController.getTable().getCurrentPlayer().activateProd(0));
                        playerVV().displayGenericMessage("You activated production in slot 1!\n");
                    } catch (NoSuchElementException e){
                        playerVV().displayGenericMessage(e.getMessage());
                        playerVV().displayPopup(e.getMessage());
                    }
                }
                if (((ActivateProduction)msg).getSlot2()==1){
                    try {
                        resourcesToAdd.addAll(gameController.getTable().getCurrentPlayer().activateProd(1));
                        playerVV().displayGenericMessage("You activated production in slot 2!\n");
                    } catch (NoSuchElementException e){
                        playerVV().displayGenericMessage(e.getMessage());
                        playerVV().displayPopup(e.getMessage());
                    }
                }
                if (((ActivateProduction)msg).getSlot3()==1){
                    try {
                        resourcesToAdd.addAll(gameController.getTable().getCurrentPlayer().activateProd(2));
                        playerVV().displayGenericMessage("You activated production in slot 3!\n");
                    } catch (NoSuchElementException e){
                        playerVV().displayGenericMessage(e.getMessage());
                        playerVV().displayPopup(e.getMessage());
                    }
                }

                if (((ActivateProduction)msg).getBasic()==1){
                    cont=0;
                    playerVV().displayGenericMessage("You can now spend two resources to get one in Strongbox!: \n");
                    playerVV().fetchResourceType(); //fetch basic src 1
                    //playerVV().displayPopup("Basic production activated, you can now spend two resources to get a new one in Strongbox!\nNow choose the first one");
                    playerVV().displayBasicProdPopup(1,"Basic production activated, you can now spend two resources to get a new one in Strongbox!\n Now choose the first one");
                }

                else if (((ActivateProduction)msg).getBasic()==0) {
                    if (getPlayerPB().hasEffect(EffectType.ADDPRODUCTION)){
                        for (int j = 0; j< getPlayerPB().getActiveLeaderCards().size(); j++){
                            if (getPlayerPB().getActiveLeaderCards().get(j).getLeaderEffect().getEffectType().equals(EffectType.ADDPRODUCTION)){
                                playerVV().fetchExtraProd((Resource) getPlayerPB().getActiveLeaderCards().get(j).getLeaderEffect().getObject());
                            }
                        }
                    }
                    else {
                        getPlayerPB().getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);

                        displayPB();

                        playerVV().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
                    }


                }
                break;

            case ACTIVATE_EXTRAPRODUCTION:
                if (((ActivateExtraProd)msg).getType().equals(ResourceType.NULLRESOURCE)){
                    displayPB();
                    playerVV().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
                }
                else {
                    resourcesToAdd.add(new Resource(1, ((ActivateExtraProd) msg).getType()));
                    getPlayerPB().getFaithTrack().moveForward(gameController.getTable().getCurrentPlayer(), 1);
                    playerVV().displayGenericMessage("Extra production activated!\n");
                    getPlayerPB().getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);
                    displayPB();
                    playerVV().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
                }

                break;

            case RESOURCE_TYPE:
                if (cont==0){ //fetch basic src 2
                    typeInput1 =((ResourceTypeChosen) msg).getResourceType();
                    cont ++;
                    playerVV().fetchResourceType();
                    //playerVV().displayPopup("Now choose the second");
                    playerVV().displayBasicProdPopup(2,"Now choose the second");
                }
                else if (cont==1){ //fetch basic dest
                    typeInput2 = ((ResourceTypeChosen) msg).getResourceType();
                    cont ++;
                    playerVV().displayGenericMessage("\nNow you can choose a type of resource you want to place in StrongBox!\n");
                    playerVV().fetchResourceType();
                    //playerVV().displayPopup("\nNow you can choose a type of resource you want to place in StrongBox!\n");
                    playerVV().displayBasicProdPopup(3,"Now you can choose a type of resource you want to place in StrongBox!");
                }
                else {
                    ResourceType typeOut = ((ResourceTypeChosen) msg).getResourceType();
                    try {
                        resourcesToAdd.add(gameController.getTable().getCurrentPlayer().basicProduction(typeInput1, typeInput2, typeOut));
                    } catch (NoSuchElementException e) {
                        playerVV().displayGenericMessage("You don't have the requirements to do this production");
                        playerVV().displayPopup("You don't have the requirements to do this production");
                    }

                    if (getPlayerPB().hasEffect(EffectType.ADDPRODUCTION)){
                        for (int j = 0; j< getPlayerPB().getActiveLeaderCards().size(); j++){
                            if (getPlayerPB().getActiveLeaderCards().get(j).getLeaderEffect().getEffectType().equals(EffectType.ADDPRODUCTION)){
                                playerVV().fetchExtraProd((Resource) getPlayerPB().getActiveLeaderCards().get(j).getLeaderEffect().getObject());
                            }
                        }
                    }
                    else {
                        getPlayerPB().getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);
                        displayPB();
                        playerVV().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
                    }

                }
                break;
        }
    }

    public void addFaithPointsToOpponents(int faithPoints){
        for(Player player : gameController.getTable().getPlayers()){
            if (gameController.isSinglePlayer()){
                gameController.getTable().getLorenzoIlMagnifico().moveBlackCross(player, 1);
            }
            if(!gameController.getTable().getCurrentPlayer().equals(player))
                player.getPersonalBoard().getFaithTrack().moveForward(player, faithPoints);
        }
    }

    public void countDevCards() throws IOException {
        if (gameController.getTable().getCurrentPlayer().getCounterDevCards() == 7){
            gameController.setEndPlayerNumber(gameController.getTable().getCurrentPlayer().getTurnOrder());
            if (gameController.isSinglePlayer()) {
                gameController.endSoloGame(true);
            }
            else gameController.setTableState(TableState.END);
        }
    }

    public void sendPBToOthers() throws IOException {
        for (Player player: gameController.getTable().getPlayers()){
            if (!player.getNickname().equals(gameController.getTable().getCurrentPlayer().getNickname())){
                gameController.getVvMap().get(player.getNickname()).sendPersonalBoard(
                        gameController.getTable().getCurrentPlayer().getNickname(),
                        getPlayerPB().getFaithTrack(),
                        getPlayerPB().getSlots(),
                        new SerializableWarehouse(getPlayerPB().getWarehouse()),
                        getPlayerPB().getActiveLeaderCards());
            }
        }
    }

    public void displayPB () throws IOException {
        playerVV().displayPersonalBoard(getPlayerPB().getFaithTrack(),
                getPlayerPB().getSlots(),
                new SerializableWarehouse(getPlayerPB().getWarehouse()));
    }

    public PersonalBoard getPlayerPB(){
        return gameController.getTable().getCurrentPlayer().getPersonalBoard();
    }

}
