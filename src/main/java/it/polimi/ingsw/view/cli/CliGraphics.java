package it.polimi.ingsw.view.cli;


import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.model.singleplayer.Token;

import java.util.ArrayList;

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
                s.append("▕   ").append(leaderCard.getLeaderEffect().getObject()).append("  ⇨   ？ + ").append(CliColor.ANSI_RED.escape()).append("† ").append(CliColor.RESET +"  ▏    ");
                break;
            case DISCOUNT:
                Resource res = new Resource(1, (ResourceType) leaderCard.getLeaderEffect().getObject());
                s.append("▕         ").append(printRes(res)).append("-1 ").append("       ▏    ");
                break;
            case SWAPWHITE:
                s.append("▕    ⓿    ➡︎   ").append(printMarbles((Resource)leaderCard.getLeaderEffect().getObject())).append("    ▏    ");
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

    public void printPersonalBoard(SerializableWarehouse wH, Slot[] slots, FaithTrack ft){
        StringBuilder s = new StringBuilder();
        int i=0;
        String [] wareHouse = ("                                \n" + printWarehouse(wH)).split("\n");
        String [] slot = printSlots(slots).split("\n");

        s.append(printFaithTrack(ft));
        while(i<8) {
            s.append(wareHouse[i]).append("          ").append(slot[i]).append("\n");
            i++;
        }

        System.out.println(s);

    }


    public String printWarehouse(SerializableWarehouse warehouse){
        return printDepot(warehouse.getFloors()) + printStrongBox(warehouse.getStrongbox());
    }

    public String printStrongBox(Resource[] strongbox){


        return "▕▔▔▔▔▔▔▏▕▔▔▔▔▔▔▏▕▔▔▔▔▔▔▏▕▔▔▔▔▔▔▏\n" +
                "▕  " +strongbox[0].getQnt()+ printRes(strongbox[0]) +" ▏" +
                "▕  "+ strongbox[1].getQnt()+ printRes(strongbox[1]) +" ▏" +
                "▕  "+ strongbox[2].getQnt()+ printRes(strongbox[2]) +" ▏" +
                "▕  "+ strongbox[3].getQnt()+ printRes(strongbox[3]) +" ▏\n" +
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
                "\n         2  " + secondFloor + "             \n         3  "+thirdFloor + "             \n                                \n";

        return s;
    }


    public String printDevCard(DevCard devCard){
        String s;
        String vp;

        if (devCard==null){
            s =     "▕▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▏\n" +
                    "▕                     ▏\n" +
                    "▕                     ▏\n" +
                    "▕                     ▏\n" +
                    "▕                     ▏\n" +
                    "▕                     ▏\n" +
                    "▕                     ▏\n" +
                    "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n";
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
        StringBuilder slot = new StringBuilder();


        if (slots[0].getCards().size()>0){
            card0 = printDevCard(slots[0].getShowedCard()).split("\n");
        }
        else
            card0 = ("▕▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n").split("\n");


        if (slots[1].getCards().size()>0){
            card1 = printDevCard(slots[1].getShowedCard()).split("\n");
        }
        else
            card1 = ("▕▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n").split("\n");

        if (slots[2].getCards().size()>0){
            card2 = printDevCard(slots[2].getShowedCard()).split("\n");
        }
        else
            card2 = ("▕▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕                     ▏\n" +
                     "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n").split("\n");

        while (j< card1.length){
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
                s.append(CliColor.ANSI_YELLOW.escape()).append("$ ").append(CliColor.RESET);
                break;
            case SHIELD:
                s.append(CliColor.ANSI_BLUE.escape()).append("◆︎︎ ").append(CliColor.RESET);
                break;
            case STONE:
                s.append(CliColor.ANSI_GRAY.escape()).append("︎◭︎ ").append(CliColor.RESET);
                break;
            case FAITHPOINT:
                s.append(CliColor.ANSI_RED.escape()).append("† ").append(CliColor.RESET);
                break;
            case SERVANT:
                s.append(CliColor.ANSI_PURPLE.escape()).append("∎︎ ").append(CliColor.RESET);
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
        if (faithTrack.isFirstFavorTile()) first = "2" + CliColor.ANSI_YELLOW.escape() + "✷" + CliColor.RESET;
        else first = "x ";

        if (faithTrack.isSecondFavorTile()) second = "3" + CliColor.ANSI_YELLOW.escape() + "✷" + CliColor.RESET;
        else second = "x ";

        if (faithTrack.isThirdFavorTile()) third = "4" + CliColor.ANSI_YELLOW.escape() + "✷" + CliColor.RESET;
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




    public void printLorenzo(Token token){
        System.out.println("888                                                              d8b 888      888b     d888                            d8b  .d888 d8b                       \n" +
                "888                                                              Y8P 888      8888b   d8888                            Y8P d88P\"  Y8P                       \n" +
                "888                                                                  888      88888b.d88888                                888                              \n" +
                "888      .d88b.  888d888 .d88b.  88888b.  88888888  .d88b.       888 888      888Y88888P888  8888b.   .d88b.  88888b.  888 888888 888  .d8888b .d88b.       \n" +
                "888     d88\"\"88b 888P\"  d8P  Y8b 888 \"88b    d88P  d88\"\"88b      888 888      888 Y888P 888     \"88b d88P\"88b 888 \"88b 888 888    888 d88P\"   d88\"\"88b      \n" +
                "888     888  888 888    88888888 888  888   d88P   888  888      888 888      888  Y8P  888 .d888888 888  888 888  888 888 888    888 888     888  888      \n" +
                "888     Y88..88P 888    Y8b.     888  888  d88P    Y88..88P      888 888      888   \"   888 888  888 Y88b 888 888  888 888 888    888 Y88b.   Y88..88P      \n" +
                "88888888 \"Y88P\"  888     \"Y8888  888  888 88888888  \"Y88P\"       888 888      888       888 \"Y888888  \"Y88888 888  888 888 888    888  \"Y8888P \"Y88P\"       \n" +
                "                                                                                                          888                                               \n" +
                "                                                                                                     Y8b d88P                                               \n" +
                "                                                                                                      \"Y88P\"                                                \n" +
                "                                    888                                            888                                                                      \n" +
                "                                    888                                            888                                                                      \n" +
                "                                    888                                            888                                                                      \n" +
                "                                    888888 888  888 888d888 88888b.   .d88b.   .d88888           d8b                                                        \n" +
                "                                    888    888  888 888P\"   888 \"88b d8P  Y8b d88\" 888           Y8P                                                        \n" +
                "                                    888    888  888 888     888  888 88888888 888  888                                                                      \n" +
                "                                    Y88b.  Y88b 888 888     888  888 Y8b.     Y88b 888           d8b                                                        \n" +
                "                                     \"Y888  \"Y88888 888     888  888  \"Y8888   \"Y88888           Y8P                                                        \n" +
                "                                                                                                                                                            \n" +
                "                                                                                                                 \n" +
                "                                    " + token.getTokenAction().toString());




    }
}
