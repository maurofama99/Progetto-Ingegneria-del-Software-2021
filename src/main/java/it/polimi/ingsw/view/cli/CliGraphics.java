package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.devcard.Deck;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.leadercards.LeaderEffect;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import javafx.scene.control.Tab;

import java.util.ArrayList;

public class CliGraphics {
    private CliColor cliColor;

    public static void main(String[] args) {
        CliGraphics cliGraphics = new CliGraphics();
        Table table = new Table();

        //cliGraphics.showDevCardsDeck(table.getDevCardsDeck().showedCards());

        cliGraphics.printMatrixDevCards(table.getDevCardsDeck().showedCards());
    }



    public void showLeaderCards(ArrayList<LeaderCard> leaderCards){
        StringBuilder s = new StringBuilder();

        s.append("| ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅̅ ̅̅ ̅̅ ̅ ̅|    " .repeat(leaderCards.size()));
        s.append("\n");
        for (LeaderCard card : leaderCards) {
            s.append("|" + card.getLeaderEffect().toString() + "|    ");
        }
        s.append("\n");

        for (LeaderCard leaderCard : leaderCards) {
            s.append("|     COST: ").append(printRequirements(leaderCard));
        }
        s.append("\n");

        s.append("|                       |    " .repeat(leaderCards.size()));
        s.append("\n");

        for (LeaderCard leaderCard : leaderCards) {
            s.append(printEffect(leaderCard));
        }
        s.append("\n");
        s.append("|                       |    " .repeat(leaderCards.size()));
        s.append("\n");
        s.append("|                       |    " .repeat(leaderCards.size()));
        s.append("\n");

        for (LeaderCard leaderCard : leaderCards) {
            s.append("|        PV: " + leaderCard.getVictoryPoints() + CliColor.ANSI_YELLOW.escape() + "✷ " + CliColor.RESET + "        |    ");
        }
        s.append("\n");

        s.append("|_______________________|    " .repeat(leaderCards.size()));
        s.append("\n");

        System.out.println(s.toString());
    }

    public StringBuilder printEffect(LeaderCard leaderCard){
        StringBuilder s =new StringBuilder();

        switch (leaderCard.getLeaderEffect().getEffectType()) {

            case ADDPRODUCTION:
                s.append("|    ").append(leaderCard.getLeaderEffect().getObject()).append("   ➡︎  ？ + ").append(CliColor.ANSI_RED.escape() +"† ").append(CliColor.RESET +"   |    ");
                break;
            case DISCOUNT:
                s.append("|       ").append(leaderCard.getLeaderEffect().getObject()).append(" -1!! ").append("     |    ");
                break;
            case SWAPWHITE:
                s.append("|      ⚪️    ➡︎   ").append(leaderCard.getLeaderEffect().getObject()).append("     |    ");
                break;
            case EXTRADEPOT:
                Resource res = new Resource(1,(ResourceType) leaderCard.getLeaderEffect().getObject());
                s.append("|     | ").append(printRes(res)).append("|   | ").append(printRes(res)).append("|     |    ");
                break;

        }
        return s;
    }

    public StringBuilder printRequirements (LeaderCard leaderCard){
        StringBuilder s = new StringBuilder();
        switch (leaderCard.getLeaderEffect().getEffectType()){
            case ADDPRODUCTION:
                if (leaderCard.getLeaderEffect().getRequirements().equals(Color.GREEN))
                    s.append(CliColor.ANSI_GREEN.escape()).append("🀫 ").append(CliColor.RESET).append("level 2").append("   |    ");;
                if (leaderCard.getLeaderEffect().getRequirements().equals(Color.BLUE))
                    s.append(CliColor.ANSI_BLUE.escape()).append("🀫 ").append(CliColor.RESET).append("level 2").append("   |    ");;
                if (leaderCard.getLeaderEffect().getRequirements().equals(Color.YELLOW))
                    s.append(CliColor.ANSI_YELLOW.escape()).append("🀫 ").append(CliColor.RESET).append("level 2").append("   |    ");;
                if (leaderCard.getLeaderEffect().getRequirements().equals(Color.PURPLE))
                    s.append(CliColor.ANSI_PURPLE.escape()).append("🀫 ").append(CliColor.RESET).append("level 2").append("   |    ");;
                break;

            case DISCOUNT:
            case SWAPWHITE:
                printRequirementCards(leaderCard, s);
                break;

            case EXTRADEPOT:
                Resource res = new Resource(1, (ResourceType) leaderCard.getLeaderEffect().getRequirements());
                s.append("   5 ").append(printRes(res)).append("      |    ");;
                break;
        }
        return s;
    }

    private void printRequirementCards(LeaderCard leaderCard, StringBuilder s) {
        int cont=0;
        for (Color col : (ArrayList<Color>) leaderCard.getLeaderEffect().getRequirements()) {
            if (col.equals(Color.GREEN)) {
                s.append(CliColor.ANSI_GREEN.escape()).append("🀫 ").append(CliColor.RESET);
            }
            if (col.equals(Color.PURPLE)) {
                s.append(CliColor.ANSI_PURPLE.escape()).append("🀫 ").append(CliColor.RESET);
            }
            if (col.equals(Color.YELLOW)){
                s.append(CliColor.ANSI_YELLOW.escape()).append("🀫 ").append(CliColor.RESET);
            }
            if (col.equals(Color.BLUE)){
                s.append(CliColor.ANSI_BLUE.escape()).append("🀫 ").append(CliColor.RESET);
            }
            cont++;
        }
        if (cont==2)
            s.append("       |    ");
        if (cont==3)
            s.append("     |    ");
    }

    public void showMarketTray(MarketTray marketTray){
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

        System.out.println(s);
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


    public String printDevCard(DevCard devCard){
        String s = "";

        s = getDevColor(devCard).escape() +     "▕▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▏\n" +
                getDevColor(devCard).escape() + "▕      LEVEL: " + devCard.getLevel() + "         ▏\n" +
                getDevColor(devCard).escape() + "▕" + CliColor.RESET + "    COST: " + printResources(devCard.getRequirementsDevCard()) + getDevColor(devCard).escape() + "    ▏\n" +
                getDevColor(devCard).escape() + "▕                       ▏\n" +
                getDevColor(devCard).escape() + "▕" + CliColor.RESET + printResources(devCard.getProduction().getInput()) + " ➡︎ " + printResources(devCard.getProduction().getOutput()) + getDevColor(devCard).escape() + "▏\n" +
                getDevColor(devCard).escape() + "▕        VP: " + devCard.getVictoryPointsDevCard() + CliColor.ANSI_YELLOW.escape() + "✷ " + getDevColor(devCard).escape() + "        ▏\n" +
                getDevColor(devCard).escape() + "▕▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▏\n" + CliColor.RESET;

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
            s = s.append(resource.getQnt());
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
                s.append(CliColor.ANSI_BLUE.escape()).append("♦ ︎ ").append(CliColor.RESET);
                break;
            case STONE:
                s.append(CliColor.ANSI_GRAY.escape()).append("︎▲ ").append(CliColor.RESET);
                break;
            case FAITHPOINT:
                s.append(CliColor.ANSI_RED.escape()).append("† ").append(CliColor.RESET);
                break;
            case SERVANT:
                s.append(CliColor.ANSI_PURPLE.escape()).append("◼︎ ").append(CliColor.RESET);
                break;
        }
    }

    public StringBuilder printRes(Resource res){
        StringBuilder s = new StringBuilder();

        printSymbol(res, s);

        return s;
    }

}
