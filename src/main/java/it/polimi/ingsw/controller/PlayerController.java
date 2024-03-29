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
import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
 * Manages all the states a player can have, including his moves.
 */

public class PlayerController {
    private final GameController gameController;
    private PlayerAction playerAction;
    private final ArrayList<Resource> resources = new ArrayList<>();
    private int whiteCounter = 0;
    private int cont = 0;
    private ResourceType typeInput1;
    private ResourceType typeInput2;
    private ResourceType type1, type2; //used for the double swap-white action
    ArrayList<Resource> resourcesToAdd = new ArrayList<>();
    ArrayList<Resource> resourcesToRemove = new ArrayList<>();
    boolean alreadyAskedExtra = false;

    public PlayerController(GameController gameController) {
        this.gameController = gameController;
        this.playerAction = PlayerAction.WAITING;
    }

    public PlayerAction getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }

    public VirtualView playerVV() {
        return gameController.getVvMap().get(gameController.getTable().getCurrentPlayer().getNickname());
    }

    public ArrayList<Resource> getResourcesToAdd() {
        return resourcesToAdd;
    }



    /**
     * Dispatches the message received by the client to the specific method that manages the player action.
     * @param msg the message received by the client
     * @throws IOException if the input is wrong
     * @throws CloneNotSupportedException if clone is not supported
     */
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

    /**
     * Manages the market action. After the player has chosen the line with the goToMarket method, this method
     * manages the resource placement. Players can discard or switch the warehouse to make space for a resource.
     * @param msg the message received by the client
     * @throws IOException S
     */
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
                    //Add resource to warehouse
                    try {
                        getPlayerPB().getWarehouse().getDepot().addResourceToDepot(resources.get(resources.size() - 1), Integer.parseInt(((ResourcePlacement) msg).getFloor()));
                    } catch (IllegalArgumentException e) {
                        goAhead = false;
                        playerVV().displayGenericMessage(e.getMessage() + ". Try Again...\n");
                        playerVV().displayPopup("You can't place this Resource here. Try Again...\n");
                    }
                    if (goAhead) {
                        resources.remove(resources.get(resources.size() - 1));
                    }
                }
                if (!resources.isEmpty()) {
                    playerVV().displayGenericMessage("\n"+resources.get(resources.size() - 1).toString() +
                                                             "\nIn which floor of the depot do you want to place this resource? " +
                            "\n- 'DISCARD' to discard this resource and give one faith point to your opponents or " +
                            "\n- 'SWITCH' to switch two floors");
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
                    playerVV().displayGenericMessage("\n" + resources.get(resources.size() - 1).toString() +
                            "\nIn which floor of the depot do you want to place this resource? \n" +
                            "- 'DISCARD' to discard this resource and give one faith point to your opponents\n" +
                            "- 'SWITCH' to switch two floors");
                    extraDepotAlert();
                    playerVV().fetchResourcePlacement(resources.get(resources.size() - 1));
                }
        }
    }

    /**
     * Manages the choosing of a line in the market
     * @param msg the message by the client
     * @throws IOException If virtual view fails to send message.
     */
    public void goToMarket(Message msg) throws IOException {
        int index  = ((GoingMarket)msg).getIndex();
        boolean rowOrCol = ((GoingMarket)msg).isRowOrColumn();
        boolean doubleSwap = false;

        if (rowOrCol) { //row selected
            resources.addAll(gameController.getTable().getMarketTray().selectRow(index));
        } else { //column selected
            resources.addAll(gameController.getTable().getMarketTray().selectColumn(index));
        }

        if (getPlayerPB().getActiveLeaderCards().size()==2){
            if (getPlayerPB().getActiveLeaderCards().get(0).getLeaderEffect().getEffectType().equals(EffectType.SWAPWHITE) &&
                    getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getEffectType().equals(EffectType.SWAPWHITE)){
                doubleSwap = true;
            }
        }

        //Use swap-white effect if active
        if (getPlayerPB().hasEffect(EffectType.SWAPWHITE) && !doubleSwap){
            singleSwapWhite();
        } else if (!doubleSwap){ //Removes white resources if swap-white is not active
            resources.removeIf(e -> e.getType().equals(ResourceType.WHITERESOURCE));
        }

        faithFilter();

        if (!doubleSwap) {
            try {
                playerVV().displayGenericMessage("You chose: " + resources +
                        "\n" +
                        resources.get(resources.size() - 1).toString()
                        + "\nIn which floor of the depot do you want to place this resource? \n" +
                        "- 'DISCARD' to discard this resource and give one faith point to your opponents or \n" +
                        "- 'SWITCH' to switch two floors");
                extraDepotAlert();
                playerVV().fetchResourcePlacement(resources.get(resources.size() - 1));

            } catch (IndexOutOfBoundsException e){
                playerVV().displayGenericMessage("You don't have resources to place");
                playerVV().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
                playerVV().displayPopup("You don't have resources to place");
            }

        } else {
            type1 = ((Resource) getPlayerPB().getActiveLeaderCards().get(0).getLeaderEffect().getObject()).getType();
            type2 = ((Resource) getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getObject()).getType();
            //Counts how many white resources you have picked up
            for (Resource resource : resources){
                if (resource.getType().equals(ResourceType.WHITERESOURCE)) whiteCounter++;
            }
            playerVV().displayGenericMessage("You chose: " + resources);
            resources.removeIf(e -> e.getType().equals(ResourceType.WHITERESOURCE));
            if (whiteCounter > 0){
                playerVV().displayGenericMessage("You selected " + whiteCounter + " white marbles, now choose for each marble which Leader Card you want to use to transform it in a new resource!");
               //Asks (white counter times) to the player the resource he wants, then adds them to the arraylist of resources picked up
                playerVV().fetchSwapWhite(type1,type2);
            } else {
                playerVV().displayGenericMessage("\n" + resources.get(resources.size() - 1).toString() +
                        "\nIn which floor of the depot do you want to place this resource? \n" +
                        "- 'DISCARD' to discard this resource and give one faith point to your opponents \n" +
                        "- 'SWITCH' to switch two floors");
                extraDepotAlert();
                playerVV().fetchResourcePlacement(resources.get(resources.size() - 1));
            }
        }
    }

    /**
     * Swap white effect is managed by this method
     */
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

    /**
     * Since faith points are not a "real" resource, we added a filter. This method makes the faith cross move
     */
    private void faithFilter() {
        for (Resource res : resources){
            if(res.getType().equals(ResourceType.FAITHPOINT)){
                getPlayerPB().getFaithTrack().moveForward(gameController.getTable().getCurrentPlayer(), 1);
            }
        }
        resources.removeIf(e -> e.getType().equals(ResourceType.FAITHPOINT));
    }

    /**
     * Lets the player put a resource in the extra depots provided by extra-depot effect leader cards
     * @throws IOException If virtual view fails to send message.
     */
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

    /**
     * Activates a leader card (after checking the requirements)
     * @param msg message received by the player
     * @throws IOException If virtual view fails to send message.
     */
    public void activateLeaderCard(Message msg) throws IOException {
        boolean trueOrFalse = true;
        try {
            gameController.getTable().getCurrentPlayer().activateLeaderCard(((ActivateLeader)msg).getLeaderCard());
            displayPB();
        }
        catch (IllegalArgumentException e){
            playerVV().displayGenericMessage("You don't have the requirements to activate it");
            playerVV().fetchPlayLeader(gameController.getTable().getCurrentPlayer().getLeaderCards(), ((ActivateLeader)msg).isEndTurn());
            playerVV().displayPopup("You don't have the requirements to activate it");
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
     * @throws IOException If virtual view fails to send message.
     */

    public void buyDevCard(Message msg) throws IOException, CloneNotSupportedException {
        if (gameController.getTable().getDevCardsDeck().getDevCard(((BuyDevCard) msg).getRow(), ((BuyDevCard) msg).getColumn()) == null) {
            playerVV().displayGenericMessage("Selected card is not available anymore!\n");
            playerVV().fetchPlayerAction();
            playerVV().displayPopup("Selected card is not available anymore!\n");
        }
        else
        {
            DevCard devCard = gameController.getTable().getDevCardsDeck().getDevCard(((BuyDevCard) msg).getRow(), ((BuyDevCard) msg).getColumn());
            ArrayList<Resource> requirements = new ArrayList<>();
            for (Resource resource : devCard.getRequirementsDevCard()) {
                requirements.add((Resource) resource.clone());
            }
            requirements = hasDiscountEffect(requirements);

            try {
                if (!gameController.getTable().getCurrentPlayer().buyDevCard(devCard, requirements, ((BuyDevCard) msg).getSlot())) {
                    playerVV().update(new NoAvailableResources(gameController.getTable().getCurrentPlayer().getNickname()));
                } else {
                    gameController.getTable().getDevCardsDeck().removeAndGetCard(((BuyDevCard) msg).getRow(), ((BuyDevCard) msg).getColumn());
                    countDevCards();
                    displayPB();
                    playerVV().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
                }
            } catch (IllegalAccessException e) {
                playerVV().displayGenericMessage(e.getMessage());
                playerVV().fetchPlayerAction();
                playerVV().displayPopup(e.getMessage());
            }
        }
    }

    /**
     * Manages the production powers of the player. Asks for requirements and check if they are met, then puts
     * the output in the strongbox.
     * @param msg message received by the client
     * @throws IOException If virtual view fails to send message.
     * @throws CloneNotSupportedException If clone fails.
     */
    public void activateProduction(Message msg) throws IOException, CloneNotSupportedException {
        switch (msg.getMessageType()){
            case ACTIVATE_PRODUCTION:
                if (((ActivateProduction)msg).getSlot1()==1 ){
                    try {
                        resourcesToAdd.addAll(gameController.getTable().getCurrentPlayer().activateProd(0));
                    } catch (NoSuchElementException e){
                        playerVV().displayGenericMessage(e.getMessage());
                    }
                }
                if (((ActivateProduction)msg).getSlot2()==1){
                    try {
                        resourcesToAdd.addAll(gameController.getTable().getCurrentPlayer().activateProd(1));
                    } catch (NoSuchElementException e){
                        playerVV().displayGenericMessage(e.getMessage());
                    }
                }
                if (((ActivateProduction)msg).getSlot3()==1){
                    try {
                        resourcesToAdd.addAll(gameController.getTable().getCurrentPlayer().activateProd(2));
                    } catch (NoSuchElementException e){
                        playerVV().displayGenericMessage(e.getMessage());
                    }
                }

                if (((ActivateProduction)msg).getBasic()==1){
                    displayPB();
                    cont=0;
                    playerVV().displayGenericMessage("You can now spend two resources to get one in Strongbox!: \n");
                    playerVV().fetchResourceType();
                    playerVV().displayBasicProdPopup(1,"Basic production activated, you can now spend two resources to get a new one in Strongbox!\n Now choose the first one");
                }

                if (((ActivateProduction)msg).getBasic()==0) {
                    askExtraProduction();
                }
                break;

            case ACTIVATE_EXTRAPRODUCTION:
                if (((ActivateExtraProd)msg).getType().equals(ResourceType.NULLRESOURCE)){
                    if (!hasTwoExtraProduction()) finalizeProduction();
                    else {
                        resourcesToRemove.add((Resource) getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getObject());
                        playerVV().fetchExtraProd((Resource) getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getObject());
                    }

                }
                else {
                    try {
                        getPlayerPB().getWarehouse().removeResources(resourcesToRemove);
                        resourcesToAdd.add(new Resource(1, ((ActivateExtraProd) msg).getType()));
                        resourcesToAdd.add(new Resource(1, ResourceType.FAITHPOINT));
                        if (!hasTwoExtraProduction()) finalizeProduction();
                        else{
                            resourcesToRemove.add((Resource) getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getObject());
                            playerVV().fetchExtraProd((Resource) getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getObject());
                        }

                    }
                    catch (NoSuchElementException e){
                        playerVV().displayGenericMessage(e.getMessage());
                        finalizeProduction();
                    }
                }
                break;

            case RESOURCE_TYPE:
                if (cont==0){ //fetch basic src 2
                    typeInput1 =((ResourceTypeChosen) msg).getResourceType();
                    cont ++;
                    playerVV().fetchResourceType();
                    playerVV().displayBasicProdPopup(2,"Now choose the second");
                }
                else if (cont==1){ //fetch basic dest
                    typeInput2 = ((ResourceTypeChosen) msg).getResourceType();
                    cont ++;
                    playerVV().displayGenericMessage("\nNow you can choose a type of resource you want to place in StrongBox!\n");
                    playerVV().fetchResourceType();
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

                    askExtraProduction();
                }
                break;
        }
    }

    public void askExtraProduction() throws IOException {
        if (getPlayerPB().hasEffect(EffectType.ADDPRODUCTION)) {
            if (getPlayerPB().getActiveLeaderCards().get(0).getLeaderEffect().getEffectType().equals(EffectType.ADDPRODUCTION)) {
                playerVV().fetchExtraProd((Resource) getPlayerPB().getActiveLeaderCards().get(0).getLeaderEffect().getObject());
                resourcesToRemove.add((Resource) getPlayerPB().getActiveLeaderCards().get(0).getLeaderEffect().getObject());
            }
            else if (getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getEffectType().equals(EffectType.ADDPRODUCTION)) {
                playerVV().fetchExtraProd((Resource) getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getObject());
                resourcesToRemove.add((Resource) getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getObject());
            }
        }
        else {
            finalizeProduction();
        }
    }

    /**
     * Player can discard resources but everything has a price. If the player perform that action, all other players get a faith point
     * @param faithPoints how many steps the cross will make
     */
    public void addFaithPointsToOpponents(int faithPoints){
        for(Player player : gameController.getTable().getPlayers()){
            if (gameController.isSinglePlayer()){
                gameController.getTable().getLorenzoIlMagnifico().moveBlackCross(player, 1);
            }
            if(!gameController.getTable().getCurrentPlayer().equals(player))
                player.getPersonalBoard().getFaithTrack().moveForward(player, faithPoints);
        }
    }

    public ArrayList<Resource> hasDiscountEffect(ArrayList<Resource> requirements){
        if (getPlayerPB().hasEffect(EffectType.DISCOUNT)){
            for (Resource requirement : requirements) {
                if (getPlayerPB().getActiveLeaderCards().get(0).getLeaderEffect().getEffectType().equals(EffectType.DISCOUNT) &&
                        requirement.getType().equals(getPlayerPB().getActiveLeaderCards().get(0).getLeaderEffect().getObject()))
                    requirement.setQnt(requirement.getQnt() - 1);
                if (getPlayerPB().getActiveLeaderCards().size() == 2) {
                    if (getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getEffectType().equals(EffectType.DISCOUNT) &&
                            requirement.getType().equals(getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getObject()))
                        requirement.setQnt(requirement.getQnt() - 1);
                }
            }
        }
        return requirements;
    }

    /**
     * Checks if the player has two extra production leader cards, if so it sends another extra production request to client.
     * @throws IOException If virtual view fails to send message.
     */
    public boolean hasTwoExtraProduction() throws IOException {
        if (getPlayerPB().getActiveLeaderCards().size()==1) return false;
        else if (getPlayerPB().getActiveLeaderCards().get(0).getLeaderEffect().getEffectType().equals(EffectType.ADDPRODUCTION) &&
                getPlayerPB().getActiveLeaderCards().get(1).getLeaderEffect().getEffectType().equals(EffectType.ADDPRODUCTION)){
            if (!alreadyAskedExtra){
                alreadyAskedExtra = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Counts the development cards of the player at the end of the game
     * @throws IOException If virtual view fails to send message.
     */
    public void countDevCards() throws IOException {
        if (gameController.getTable().getCurrentPlayer().getCounterDevCards() == 7){
            if (gameController.isSinglePlayer()) {
                gameController.getSinglePlayerController().endSoloGame(true);
            }
            else {
                gameController.setTableState(TableState.END);
                gameController.setEndPlayerNumber(gameController.getTable().getCurrentPlayer().getTurnOrder());
            }
        }
    }


    /**
     * If someone wants to see your personal board, this methods send it to them
     * @throws IOException If virtual view fails to send message.
     */
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

    /**
     * Check if the player that is performing the production action can activate the requested productions and moves the faith marker on the faith track if the productions produce faith points.
     * @throws IOException If virtual view fails to send message
     */
    public void finalizeProduction() throws IOException {
        resourcesToRemove.clear();
        alreadyAskedExtra = false;
        if (resourcesToAdd.isEmpty()){
            displayPB();
            playerVV().displayGenericMessage("You were not able to activate any production");
            playerVV().fetchPlayerAction();
            playerVV().displayPopup("You were not able to activate any production");
        }
        else {
            for (Resource res : resourcesToAdd){
                if (res.getType().equals(ResourceType.FAITHPOINT)){
                    for (int i=0; i<res.getQnt(); i++)
                        getPlayerPB().getFaithTrack().moveForward(gameController.getTable().getCurrentPlayer(), 1);
                }
            }
            resourcesToAdd.removeIf(resource -> resource.getType().equals(ResourceType.FAITHPOINT));
            getPlayerPB().getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);
            displayPB();
            resourcesToAdd.clear();
            playerVV().fetchDoneAction(gameController.getTable().getCurrentPlayer().getLeaderCards());
        }
    }

    /**
     * Update current player's personal board.
     * @throws IOException If virtual view fails to send message
     */
    public void displayPB () throws IOException {
        playerVV().displayPersonalBoard(getPlayerPB().getFaithTrack(),
                getPlayerPB().getSlots(),
                new SerializableWarehouse(getPlayerPB().getWarehouse()), getPlayerPB().getActiveLeaderCards());
    }

    public PersonalBoard getPlayerPB(){
        return gameController.getTable().getCurrentPlayer().getPersonalBoard();
    }

}
