package it.polimi.ingsw.view.cli;


import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;

/**
 * Class of the graphics of the CLI. Every aspect shown in the terminal during a CLI usage is defined and organized here
 */
public class CliGraphics {

    public void showLeaderCards(ArrayList<LeaderCard> leaderCards){
        StringBuilder s = new StringBuilder();

        for (int i=1; i<=leaderCards.size(); i++){
            s.append("           ").append(i).append("               ");
        }
        s.append("\n");
        s.append("▕▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▏    " .repeat(leaderCards.size()));
        s.append("\n");
        for (LeaderCard leaderCard : leaderCards) {
            s.append("▕   COST: ").append(printRequirements(leaderCard));
        }
        s.append("\n");

        s.append("▕                     ▏    " .repeat(leaderCards.size()));
        s.append("\n");

        for (LeaderCard leaderCard : leaderCards) {
            s.append(printEffect(leaderCard));
        }
        s.append("\n");
        s.append("▕                     ▏    " .repeat(leaderCards.size()));
        s.append("\n");

        for (LeaderCard leaderCard : leaderCards) {
            s.append("▕       PV: ").append(leaderCard.getVictoryPoints()).append(CliColor.ANSI_YELLOW.escape()).append("✷ ").append(CliColor.RESET).append("       ▏    ");
        }
        s.append("\n");

        s.append("▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏    " .repeat(leaderCards.size()));
        s.append("\n");

        System.out.println(s);
    }

    public StringBuilder printEffect(LeaderCard leaderCard){
        StringBuilder s =new StringBuilder();

        switch (leaderCard.getLeaderEffect().getEffectType()) {

            case ADDPRODUCTION:
                s.append("▕    ").append(printRes((Resource)leaderCard.getLeaderEffect().getObject())).append("  ➤   ?  + ").append(CliColor.ANSI_RED.escape()).append(" ✝ ").append(CliColor.RESET +" ▏    ");
                break;
            case DISCOUNT:
                Resource res = new Resource(1, (ResourceType) leaderCard.getLeaderEffect().getObject());
                s.append("▕         ").append(printRes(res)).append("-1 ").append("       ▏    ");
                break;
            case SWAPWHITE:
                s.append("▕     ⓿    ➤   ").append(printMarbles((Resource)leaderCard.getLeaderEffect().getObject())).append("     ▏    ");
                break;
            case EXTRADEPOT:
                Resource resource = new Resource(1,(ResourceType) leaderCard.getLeaderEffect().getObject());
                s.append("▕    | ").append(printRes(resource)).append("|   | ").append(printRes(resource)).append("|    ▏    ");
                break;

        }
        return s;
    }

    public StringBuilder printRequirements (LeaderCard leaderCard){
        StringBuilder s = new StringBuilder();
        switch (leaderCard.getLeaderEffect().getEffectType()){
            case ADDPRODUCTION:
                if (leaderCard.getLeaderEffect().getRequirements().equals(Color.GREEN))
                    s.append(CliColor.ANSI_GREEN.escape()).append("▇ ").append(CliColor.RESET).append("lev. 2").append("    ▏    ");
                if (leaderCard.getLeaderEffect().getRequirements().equals(Color.BLUE))
                    s.append(CliColor.ANSI_BLUE.escape()).append("▇ ").append(CliColor.RESET).append("lev. 2").append("    ▏    ");
                if (leaderCard.getLeaderEffect().getRequirements().equals(Color.YELLOW))
                    s.append(CliColor.ANSI_YELLOW.escape()).append("▇ ").append(CliColor.RESET).append("lev. 2").append("    ▏    ");
                if (leaderCard.getLeaderEffect().getRequirements().equals(Color.PURPLE))
                    s.append(CliColor.ANSI_PURPLE.escape()).append("▇ ").append(CliColor.RESET).append("lev. 2").append("    ▏    ");
                break;

            case DISCOUNT:
            case SWAPWHITE:
                printRequirementCards(leaderCard, s);
                break;

            case EXTRADEPOT:
                Resource res = new Resource(1, (ResourceType) leaderCard.getLeaderEffect().getRequirements());
                s.append("   5 ").append(printRes(res)).append("     ▏    ");
                break;
        }
        return s;
    }

    private void printRequirementCards(LeaderCard leaderCard, StringBuilder s) {
        int cont=0;
        for (Color col : (ArrayList<Color>) leaderCard.getLeaderEffect().getRequirements()) {
            if (col.equals(Color.GREEN)) {
                s.append(CliColor.ANSI_GREEN.escape()).append(" ▇ ").append(CliColor.RESET);
            }
            if (col.equals(Color.PURPLE)) {
                s.append(CliColor.ANSI_PURPLE.escape()).append(" ▇ ").append(CliColor.RESET);
            }
            if (col.equals(Color.YELLOW)){
                s.append(CliColor.ANSI_YELLOW.escape()).append(" ▇ ").append(CliColor.RESET);
            }
            if (col.equals(Color.BLUE)){
                s.append(CliColor.ANSI_BLUE.escape()).append(" ▇ ").append(CliColor.RESET);
            }
            cont++;
        }
        if (cont==2)
            s.append("      ▏    ");
        if (cont==3)
            s.append("   ▏    ");
    }


    public String showMarketTray(MarketTray marketTray){
        StringBuilder s = new StringBuilder();

        s.append("\n      1  2  3  4 ");
        s.append("\n  ").append(printMarbles(marketTray.getSlide())).append("  ▼  ▼  ▼  ▼ ");
        s.append("\n     ----------- \n");
        for (int i=0; i<3; i++){
            s.append(i+1).append("︎ ▶ | ").append(printMarbles(marketTray.getTray()[i][0])).append(" ")
                    .append(printMarbles(marketTray.getTray()[i][1])).append(" ")
                    .append(printMarbles(marketTray.getTray()[i][2])).append(" ")
                    .append(printMarbles(marketTray.getTray()[i][3])).append(" |\n");

        }
        s.append("     ----------- \n");

        return s.toString();
    }


    public StringBuilder printMarbles(Resource res) {
        StringBuilder s = new StringBuilder();

        switch (res.getType()) {
            case SERVANT:
                return s.append(CliColor.ANSI_PURPLE.escape()).append("⓿").append(CliColor.RESET);
            case COIN:
                return s.append(CliColor.ANSI_YELLOW.escape()).append("⓿").append(CliColor.RESET);
            case SHIELD:
                return s.append(CliColor.ANSI_BLUE.escape()).append("⓿").append(CliColor.RESET);
            case FAITHPOINT:
                return s.append(CliColor.ANSI_RED.escape()).append("⓿").append(CliColor.RESET);
            case STONE:
                return s.append(CliColor.ANSI_GRAY.escape()).append("⓿").append(CliColor.RESET);
            case WHITERESOURCE:
                return s.append("⓿");
            default:
                throw new IllegalStateException("Unexpected value: " + res.getType());
        }
    }

    public void printMarketDepot(MarketTray marketTray, SerializableWarehouse wH){
        String [] wareHouse = ("       \n" + printWarehouse(wH)).split("\n");
        String [] market = showMarketTray(marketTray).split("\n");

        StringBuilder s = new StringBuilder();
        int i=0;

        while (i<market.length){
            s.append(wareHouse[i]).append("       ").append(market[i]).append("\n");
            i++;
        }

        System.out.println(s);
    }

    public void printPersonalBoard(SerializableWarehouse wH, Slot[] slots, FaithTrack ft, ArrayList<LeaderCard> lc){
        StringBuilder s = new StringBuilder();
        int i=0;
        String [] wareHouse = ("                                \n" + printWarehouse(wH)).split("\n");
        String [] slot = printSlots(slots).split("\n");


        s.append(printFaithTrack(ft));
        while(i<8) {
            s.append(wareHouse[i]).append("          ").append(slot[i]).append("\n");
            i++;
        }
        while (i>7 && i<10){
            s.append("                                          ").append(slot[i]).append("\n");
            i++;
        }

        if (ft.getBlackCrossPosition()>=0){
            System.out.println(CliColor.ANSI_BLUE.escape() + " BLACK CROSS TOKEN POSITION: " +ft.getBlackCrossPosition() + CliColor.RESET);
        }
        System.out.println(s);
        if (lc.size()==1){
            printExtraLeader(wH, lc, 0);
        }
        else if (lc.size()==2){
            printExtraLeader(wH, lc, 0 );
            printExtraLeader(wH, lc, 1 );
        }
    }

    private void printExtraLeader(SerializableWarehouse wH, ArrayList<LeaderCard> lc, int i) {
        System.out.print(CliColor.ANSI_BRED.escape() + "LEADER EFFECT ACTIVATED: " +CliColor.RESET + printEffect(lc.get(i)));
        if (lc.get(i).getLeaderEffect().getEffectType().equals(EffectType.EXTRADEPOT)){
            System.out.println("You have: " + wH.getExtraFloors().get(i).getQnt() + printRes(wH.getExtraFloors().get(i))+" in this extra depot\n");
        }
        else
            System.out.println("\n");
    }


    public String printWarehouse(SerializableWarehouse warehouse){
        return printDepot(warehouse.getFloors()) + "                                \n" + printStrongBox(warehouse.getStrongbox());
    }

    public String printStrongBox(Resource[] strongbox){
        String coin, servant, shield, stone;
        if (strongbox[0].getQnt() >9) coin = strongbox[0].getQnt()+printRes(strongbox[0]) + "";
        else coin = strongbox[0].getQnt()+printRes(strongbox[0]) + " ";

        if (strongbox[1].getQnt() >9) servant = strongbox[1].getQnt()+printRes(strongbox[1]) + "";
        else servant = strongbox[1].getQnt()+printRes(strongbox[1]) + " ";

        if (strongbox[2].getQnt() >9) shield = strongbox[2].getQnt()+printRes(strongbox[2]) + "";
        else shield = strongbox[2].getQnt()+printRes(strongbox[2]) + " ";

        if (strongbox[3].getQnt() >9) stone = strongbox[3].getQnt()+printRes(strongbox[3]) + "";
        else stone = strongbox[3].getQnt()+printRes(strongbox[3]) + " ";

        return "▕▔▔▔▔▔▔▏▕▔▔▔▔▔▔▏▕▔▔▔▔▔▔▏▕▔▔▔▔▔▔▏\n" +
                "▕  " +coin +"▏" +
                "▕  "+ servant +"▏" +
                "▕  "+ shield +"▏" +
                "▕  "+ stone +"▏\n" +
                   "▕▁▁▁▁▁▁▏▕▁▁▁▁▁▁▏▕▁▁▁▁▁▁▏▕▁▁▁▁▁▁▏\n";
    }


    public String printDepot(ArrayList<Resource> floors){
        String s, firstFloor, secondFloor = "", thirdFloor;

        if (floors.get(0).getType().equals(ResourceType.NULLRESOURCE))
            firstFloor = "   ☐   ";
        else firstFloor = "   " + printRes(floors.get(0)) + "  ";

        if (floors.get(1).getType().equals(ResourceType.NULLRESOURCE))
            secondFloor = "  ☐ ☐  ";
        else if (floors.get(1).getQnt() < 2)  secondFloor = "  " + printRes(floors.get(1)) + "☐  ";
        else if (floors.get(1).getQnt() < 3) secondFloor = "  " + printRes(floors.get(1)) + "" + printRes(floors.get(1)) +" ";

        if (floors.get(2).getType().equals(ResourceType.NULLRESOURCE))
            thirdFloor = " ☐ ☐ ☐ ";
        else if (floors.get(2).getQnt() <2)  thirdFloor = " " +printRes(floors.get(2)) + "☐ ☐ ";
        else if (floors.get(2).getQnt() <3) thirdFloor = " " +printRes(floors.get(2)) + printRes(floors.get(2)) +"☐ ";
        else thirdFloor = " " +printRes(floors.get(2)) + printRes(floors.get(2)) + printRes(floors.get(2));

        s = "         1  " + firstFloor +"             " +
                "\n         2  " + secondFloor + "             \n         3  "+thirdFloor + "             \n";

        return s;
    }


    public String printDevCard(DevCard devCard){
        String s;
        String vp;

        if (devCard==null){
            s =     CliColor.RESET + "▕▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▏\n" +
                    CliColor.RESET + "▕                     ▏\n" +
                    CliColor.RESET + "▕                     ▏\n" +
                    CliColor.RESET + "▕                     ▏\n" +
                    CliColor.RESET + "▕                     ▏\n" +
                    CliColor.RESET + "▕                     ▏\n" +
                    CliColor.RESET + "▕                     ▏\n" +
                    CliColor.RESET + "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n";
        }

        else {

            if(devCard.getVictoryPointsDevCard()<10)
                vp = devCard.getVictoryPointsDevCard() +" ";
            else
                vp = ""+devCard.getVictoryPointsDevCard();

            s = getDevColor(devCard).escape() + "▕▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▏"+ CliColor.RESET +"\n" +
                    getDevColor(devCard).escape() + "▕" + CliColor.RESET + "   COST: " + printResources(devCard.getRequirementsDevCard()) + getDevColor(devCard).escape() + "   ▏"+ CliColor.RESET +"\n" +
                    getDevColor(devCard).escape() + "▕     LEVEL: " + devCard.getLevel() + "        ▏"+ CliColor.RESET +"\n" +

                    getDevColor(devCard).escape() + "▕     " + CliColor.RESET + printResources(devCard.getProduction().getInput()) + getDevColor(devCard).escape()+"       ▏"+ CliColor.RESET +"\n" +

                    getDevColor(devCard).escape() + "▕        " + CliColor.RESET + " ↧  " +getDevColor(devCard).escape() + "         ▏"+ CliColor.RESET +"\n" +

                    getDevColor(devCard).escape() + "▕      "+ CliColor.RESET + printResources(devCard.getProduction().getOutput()) + getDevColor(devCard).escape() + "      ▏"+ CliColor.RESET+ "\n" +
                    getDevColor(devCard).escape() + "▕      VP: " + vp + CliColor.ANSI_YELLOW.escape() + "✷ " + getDevColor(devCard).escape() + "       ▏"+ CliColor.RESET +"\n" +
                    getDevColor(devCard).escape() + "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏" + CliColor.RESET + "\n";
        }
        return s;
    }

    public String printRowDevCards(DevCard[] devCards){
        StringBuilder row = new StringBuilder();
        int i= 0;


        String[] card1 = printDevCard(devCards[0]).split("\n");
        String[] card2 = printDevCard(devCards[1]).split("\n");
        String[] card3 = printDevCard(devCards[2]).split("\n");
        String[] card4 = printDevCard(devCards[3]).split("\n");


        while (i< card1.length){
            row.append(card1[i]).append(card2[i]).append(card3[i]).append(card4[i]).append("\n");
            i++;
        }
        return row.toString();
    }

    public String printSlots(Slot[] slots){
        String[] card0, card1, card2;
        int j=0;
        String vp ="";
        StringBuilder slot = new StringBuilder();



        if (slots[0].getCards().size()>0){
            int points =  slots[0].getCards().stream().mapToInt(DevCard::getVictoryPointsDevCard).sum();
            if (points>9){
                vp = points + CliColor.ANSI_YELLOW.escape() + "✷" + CliColor.RESET;
            }
            else
                vp = "▁" + points + CliColor.ANSI_YELLOW.escape() + "✷" + CliColor.RESET;

            if (slots[0].getCards().size()==1) {
                card0 = (printDevCard(slots[0].getShowedCard()) +
                        CliColor.RESET+"▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n" +
                        CliColor.RESET+"▕▁▁▁▁▁▁▁▁▁"+vp+"▁▁▁▁▁▁▁▁▁▏\n").split("\n");
            }
            else if (slots[0].getCards().size()==2){
                card0 = (printDevCard(slots[0].getShowedCard()) + CliColor.RESET +
                        getDevColor(slots[0].getCards().get(0)).escape()+"▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏"+ CliColor.RESET +"\n" +
                        "▕▁▁▁▁▁▁▁▁▁"+vp+"▁▁▁▁▁▁▁▁▁▏\n").split("\n");
            }

            else {
                card0 = (printDevCard(slots[0].getShowedCard()) +
                        getDevColor(slots[0].getCards().get(1)).escape()+"▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏"+ CliColor.RESET +"\n" +
                        getDevColor(slots[0].getCards().get(0)).escape()+"▕▁▁▁▁▁▁▁▁▁"+CliColor.RESET+vp+getDevColor(slots[0].getCards().get(0)).escape()+"▁▁▁▁▁▁▁▁▁▏\n" + CliColor.RESET).split("\n");
            }


        }
        else
            card0 = ("▕▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n" + CliColor.RESET +
                    "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n" + CliColor.RESET +
                    "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n").split("\n");


        if (slots[1].getCards().size()>0){
            int points =  slots[1].getCards().stream().mapToInt(DevCard::getVictoryPointsDevCard).sum();
            if (points>9){
                vp = points + CliColor.ANSI_YELLOW.escape() + "✷" + CliColor.RESET;
            }
            else
                vp = "▁" + points + CliColor.ANSI_YELLOW.escape() + "✷" + CliColor.RESET;

            if (slots[1].getCards().size()==1) {
                card1 = (printDevCard(slots[1].getShowedCard()) +
                        CliColor.RESET+"▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n" +
                        CliColor.RESET+"▕▁▁▁▁▁▁▁▁▁"+vp+"▁▁▁▁▁▁▁▁▁▏\n").split("\n");
            }
            else if (slots[0].getCards().size()==2){
                card1 = (printDevCard(slots[1].getShowedCard()) +
                        getDevColor(slots[1].getCards().get(0)).escape()+"▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏"+ CliColor.RESET +"\n" +
                        "▕▁▁▁▁▁▁▁▁▁"+vp+"▁▁▁▁▁▁▁▁▁▏\n").split("\n");
            }

            else {
                card1 = (printDevCard(slots[1].getShowedCard()) +
                        getDevColor(slots[1].getCards().get(1)).escape()+"▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏"+ CliColor.RESET +"\n" +
                        getDevColor(slots[1].getCards().get(0)).escape()+"▕▁▁▁▁▁▁▁▁▁"+CliColor.RESET+vp+getDevColor(slots[1].getCards().get(0)).escape()+"▁▁▁▁▁▁▁▁▁▏"+ CliColor.RESET +"\n").split("\n");
            }
        }
        else
            card1 = ("▕▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n" + CliColor.RESET +
                    "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n" + CliColor.RESET +
                    "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n").split("\n");

        if (slots[2].getCards().size()>0){
            int points =  slots[2].getCards().stream().mapToInt(DevCard::getVictoryPointsDevCard).sum();
            if (points>9){
                vp = points + CliColor.ANSI_YELLOW.escape() + "✷" + CliColor.RESET;
            }
            else
                vp = "▁" + points + CliColor.ANSI_YELLOW.escape() + "✷" + CliColor.RESET;

            if (slots[2].getCards().size()==1) {
                card2 = (printDevCard(slots[2].getShowedCard()) +
                        CliColor.RESET+"▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n" +
                        CliColor.RESET+"▕▁▁▁▁▁▁▁▁▁"+vp+"▁▁▁▁▁▁▁▁▁▏\n").split("\n");
            }
            else if (slots[2].getCards().size()==2){
                card2 = (printDevCard(slots[2].getShowedCard()) +
                        getDevColor(slots[2].getCards().get(0)).escape()+"▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏"+ CliColor.RESET +"\n" +
                        "▕▁▁▁▁▁▁▁▁▁"+vp+"▁▁▁▁▁▁▁▁▁▏\n").split("\n");
            }

            else {
                card2 = (printDevCard(slots[2].getShowedCard()) +
                        getDevColor(slots[2].getCards().get(1)).escape()+"▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏"+ CliColor.RESET +"\n" +
                        getDevColor(slots[2].getCards().get(0)).escape()+"▕▁▁▁▁▁▁▁▁▁"+CliColor.RESET + vp+getDevColor(slots[2].getCards().get(0)).escape()+"▁▁▁▁▁▁▁▁▁▏"+ CliColor.RESET + "\n").split("\n");
            }
        }
        else
            card2 = ("▕▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n" + CliColor.RESET +
                     "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n" + CliColor.RESET +
                    "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n").split("\n");

        while (j<card1.length){
            slot.append(card0[j]).append(card1[j]).append(card2[j]).append("\n");
            j++;
        }

        return slot.toString();
    }

    public void printMatrixDevCards(DevCard[][] matrix){
        System.out.println(printRowDevCards(matrix[0]) + printRowDevCards(matrix[1]) +printRowDevCards(matrix[2]));
    }

    public CliColor getDevColor(DevCard devCard){
        switch (devCard.getCardColor()){
            case BLUE:
                return CliColor.ANSI_BLUE;
            case PURPLE:
                return CliColor.ANSI_PURPLE;
            case YELLOW:
                return CliColor.ANSI_YELLOW;
            case GREEN:
                return CliColor.ANSI_GREEN;
            default:
                return CliColor.ANSI_GRAY;
        }
    }

    public StringBuilder printResources(ArrayList<Resource> res) {
        StringBuilder s = new StringBuilder();

        if (res.size() == 1)
            s.append("   ");

        if (res.size() == 2)
            s.append(" ");

        for (Resource resource : res) {
            s.append(resource.getQnt());
            printSymbol(resource, s);
        }

        if (res.size() == 2)
            s.append("  ");
        if (res.size() == 1)
            s.append("   ");

        return s;
    }

    private void printSymbol(Resource resource, StringBuilder s) {
        switch (resource.getType()) {
            case COIN:
                s.append(CliColor.ANSI_YELLOW.escape()).append("● ").append(CliColor.RESET);
                break;
            case SHIELD:
                s.append(CliColor.ANSI_BLUE.escape()).append("● ").append(CliColor.RESET);
                break;
            case STONE:
                s.append(CliColor.ANSI_GRAY.escape()).append("● ").append(CliColor.RESET);
                break;
            case FAITHPOINT:
                s.append(CliColor.ANSI_RED.escape()).append("✝ ").append(CliColor.RESET);
                break;
            case SERVANT:
                s.append(CliColor.ANSI_PURPLE.escape()).append("● ").append(CliColor.RESET);
                break;
        }
    }

    public String printRes(Resource res){
        StringBuilder s = new StringBuilder();

        printSymbol(res, s);

        return s.toString();
    }


    public String printFaithTrack(FaithTrack faithTrack){
        StringBuilder s = new StringBuilder();
        int i=4;
        String first, second, third;
        String pos;
        if (faithTrack.isFirstFavorTile()) first = "2✷";
        else first = "x ";

        if (faithTrack.isSecondFavorTile()) second = "3✷";
        else second = "x ";

        if (faithTrack.isThirdFavorTile()) third = "4✷";
        else third = "x ";

        s.append("            ╔═════╦═════╦══" + CliColor.ANSI_YELLOW.escape()+ "+2"+CliColor.RESET + "═╦═════╦═════╦══" + CliColor.ANSI_YELLOW.escape()+ "+4"+CliColor.RESET + "═╗                       ╔═"+ CliColor.ANSI_YELLOW.escape()+"+12"+CliColor.RESET + "═╦═════╦═════╦═"+ CliColor.ANSI_YELLOW.escape()+"+16"+CliColor.RESET +"═╦═════╦═════╦═"+ CliColor.ANSI_YELLOW.escape()+"+20"+CliColor.RESET +"═╗\n");
        s.append("            ║");
        while (i<10) {
            if (i==faithTrack.getFaithMarkerPosition())
                pos = CliColor.ANSI_RED.escape() + "✝" + CliColor.RESET;
            else if (i==8) pos = CliColor.ANSI_MAGENTA.escape() + "☗" + CliColor.RESET;
            else if (i>4 && i<8) pos = CliColor.ANSI_MAGENTA.escape() + i + CliColor.RESET;
            else pos = i +"";
            s.append("  ").append(pos).append("  ║");
            i++;
        }
        s.append("        "+CliColor.ANSI_BRED.escape()+"╔═════╗"+CliColor.RESET+"        ║");
        i=18;
        while (i<25) {
            if (i==faithTrack.getFaithMarkerPosition())
                pos = CliColor.ANSI_RED.escape() + "✝ " + CliColor.RESET;
            else if (i==24) pos = CliColor.ANSI_MAGENTA.escape() + "☗ " + CliColor.RESET;
            else if (i>18 && i<24) pos = CliColor.ANSI_MAGENTA.escape() + i + CliColor.RESET;
            else pos = i +"";
            s.append("  ").append(pos).append(" ║");
            i++;
        }
        s.append("\n");

        s.append("            ╠═════╬═════╩═════╩═════╩═════╬═════╣        "+CliColor.ANSI_BRED.escape()+"║  ").append(second).append(" ║"+CliColor.RESET+"        ╠═════╬═════╩═════╩═════╩═════╩═════╩═════╝\n");

        i=3;
        if (i== faithTrack.getFaithMarkerPosition())
            pos = CliColor.ANSI_RED.escape() + "✝" + CliColor.RESET;
        else pos = i+"";
        s.append("           " + CliColor.ANSI_YELLOW.escape()+ "+1"+CliColor.RESET + "  "+pos);

        s.append("  ║        " + CliColor.ANSI_YELLOW.escape()+"╔═════╗"+CliColor.RESET+"        ║  ");
        i=10;
        if (i== faithTrack.getFaithMarkerPosition())
            pos = CliColor.ANSI_RED.escape() + "✝ " + CliColor.RESET;
        else pos = i+"";
        s.append(pos +" ║");
        s.append(CliColor.ANSI_BRED.escape()+"        ╚═════╝"+CliColor.RESET+"        ║  ");
        i=17;
        if (i== faithTrack.getFaithMarkerPosition())
            pos = CliColor.ANSI_RED.escape() + "✝ " + CliColor.RESET;
        else pos = i+"";
        s.append(pos+" ║");
        s.append("               ").append(CliColor.ANSI_RED.escape()).append("╔═════╗      \n").append(CliColor.RESET);
        s.append("╔═════╦═════╬═════╣        "+ CliColor.ANSI_YELLOW.escape()+"║  ").append(first).append(" ║"+CliColor.RESET+"        ╠═════╬═════╦═════╦═════╦═════╬═════╣               "+CliColor.ANSI_RED.escape()+"║  ").append(third).append(" ║      \n").append(CliColor.RESET);

        i = 0;
        s.append("║");
        while (i<3){
            if (i==faithTrack.getFaithMarkerPosition())
                pos = CliColor.ANSI_RED.escape() + "✝" + CliColor.RESET;
            else pos = i +"";
            s.append("  "+pos+"  ║");
            i++;
        }
        s.append("        "+ CliColor.ANSI_YELLOW.escape()+"╚═════╝"+CliColor.RESET+"        ║");
        i=11;
        while (i<17){
            if (i==faithTrack.getFaithMarkerPosition())
                pos = CliColor.ANSI_RED.escape() + "✝ " + CliColor.RESET;
            else if (i==16) pos = CliColor.ANSI_MAGENTA.escape() + "☗ " + CliColor.RESET;
            else if (i>11 && i<16) pos = CliColor.ANSI_MAGENTA.escape() + i + CliColor.RESET;
            else pos = i+"";
            s.append("  "+pos+" ║");
            i++;
        }
        s.append("               "+CliColor.ANSI_RED.escape()+"╚═════╝"+CliColor.RESET+"      \n");
        s.append("╚═════╩═════╩═════╝                       ╚═════╩══"+ CliColor.ANSI_YELLOW.escape()+"+6"+CliColor.RESET +"═╩═════╩═════╩══"+ CliColor.ANSI_YELLOW.escape()+"+9"+CliColor.RESET +"═╩═════╝\n");

        return (s.toString());
    }
}
