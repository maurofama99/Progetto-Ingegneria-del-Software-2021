package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.devcard.Deck;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.leadercards.LeaderEffect;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import javafx.scene.control.Tab;

import java.util.ArrayList;

public class CliGraphics {
    private CliColor cliColor;



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


    public void showDevCardsDeck(Deck devCardDeck){
        DevCard[][] showedCard = devCardDeck.showedCards();

        StringBuilder s = new StringBuilder();

        for (int i = 0; i<3; i++) {
            s.append(CliColor.ANSI_GREEN.escape()).append("| ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅̅ ̅̅ ̅̅ ̅ ̅|  ").append(CliColor.ANSI_BLUE.escape()).append("   | ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅̅ ̅̅ ̅̅ ̅ ̅|  ").append(CliColor.ANSI_YELLOW.escape()).append("   | ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅̅ ̅̅ ̅̅ ̅ ̅|  ").append(CliColor.ANSI_PURPLE.escape()).append("   | ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅ ̅̅ ̅̅ ̅̅ ̅ ̅|\n");

            s.append(CliColor.ANSI_GREEN.escape()).append        ("|       LEVEL: ").append(showedCard[i][0].getLevel()).append("         |     ")
                    .append(CliColor.ANSI_BLUE.escape()).append  ("|       LEVEL: ").append(showedCard[i][1].getLevel()).append("         |     ")
                    .append(CliColor.ANSI_YELLOW.escape()).append("|       LEVEL: ").append(showedCard[i][2].getLevel()).append("         |     ")
                    .append(CliColor.ANSI_PURPLE.escape()).append("|       LEVEL: ").append(showedCard[i][3].getLevel()).append("         |\n");

            s.append(CliColor.ANSI_GREEN.escape()).append("|").append(CliColor.RESET).append(" COST:  ").append(printResources(showedCard[i][0].getRequirementsDevCard())).append(CliColor.ANSI_GREEN.escape()).append("       |     ")
                    .append(CliColor.ANSI_BLUE.escape()).append("|").append(CliColor.RESET).append(" COST:  ").append(printResources(showedCard[i][1].getRequirementsDevCard())).append(CliColor.ANSI_BLUE.escape()).append("       |     ")
                    .append(CliColor.ANSI_YELLOW.escape()).append("|").append(CliColor.RESET).append(" COST:  ").append(printResources(showedCard[i][2].getRequirementsDevCard())).append(CliColor.ANSI_YELLOW.escape()).append("      |     ")
                    .append(CliColor.ANSI_PURPLE.escape()).append("|").append(CliColor.RESET).append(" COST:  ").append(printResources(showedCard[i][3].getRequirementsDevCard())).append(CliColor.ANSI_PURPLE.escape()).append("      |\n");

            s.append(CliColor.ANSI_GREEN.escape()).append("|                        |  ").append(CliColor.ANSI_BLUE.escape()).append("   |                       |  ").append(CliColor.ANSI_YELLOW.escape()).append("   |                       |  ").append(CliColor.ANSI_PURPLE.escape()).append("   |                       |\n");
            s.append(CliColor.ANSI_GREEN.escape()).append("|   °°°°°°°°°°°°°°°°    |  ").append(CliColor.ANSI_BLUE.escape()).append("   |   °°°°°°°°°°°°°°°°    |  ").append(CliColor.ANSI_YELLOW.escape()).append("   |   °°°°°°°°°°°°°°°°    |  ").append(CliColor.ANSI_PURPLE.escape()).append("   |   °°°°°°°°°°°°°°°°    |\n");

            s.append(CliColor.ANSI_GREEN.escape()).append("|").append(CliColor.RESET).append(" ").append(printResources(showedCard[i][0].getProduction().getInput())).append("  ➡︎ ").append(printResources(showedCard[i][0].getProduction().getOutput())).append(CliColor.ANSI_GREEN.escape()).append("    |     ")
                    .append(CliColor.ANSI_BLUE.escape()).append("|").append(CliColor.RESET).append("  ").append(printResources(showedCard[i][1].getProduction().getInput())).append("  ➡︎ ").append(printResources(showedCard[i][1].getProduction().getOutput())).
                    append(CliColor.ANSI_BLUE.escape()).append("    |     ")
                    .append(CliColor.ANSI_YELLOW.escape()).append("|").append(CliColor.RESET).append("  ").append(printResources(showedCard[i][2].getProduction().getInput())).append("  ➡︎ ").append(printResources(showedCard[i][2].getProduction().getOutput())).
                    append(CliColor.ANSI_YELLOW.escape()).append("    |     ")
                    .append(CliColor.ANSI_PURPLE.escape()).append("|").append(CliColor.RESET).append("  ").append(printResources(showedCard[i][3].getProduction().getInput())).append("  ➡︎ ").append(printResources(showedCard[i][3].getProduction().getOutput())).
                    append(CliColor.ANSI_PURPLE.escape()).append("    |\n");
            s.append(CliColor.ANSI_GREEN.escape()).append("|   °°°°°°°°°°°°°°°°    |  ").append(CliColor.ANSI_BLUE.escape()).append("   |   °°°°°°°°°°°°°°°°    |  ").append(CliColor.ANSI_YELLOW.escape()).append("   |   °°°°°°°°°°°°°°°°    |  ").append(CliColor.ANSI_PURPLE.escape()).append("   |   °°°°°°°°°°°°°°°°    |\n");

            s.append(CliColor.ANSI_GREEN.escape()).append        ("|   VICTORY POINTS: ").append(showedCard[i][0].getVictoryPointsDevCard()).append("         |     ")
                    .append(CliColor.ANSI_BLUE.escape()).append  ("|   VICTORY POINTS: ").append(showedCard[i][1].getVictoryPointsDevCard()).append("         |     ")
                    .append(CliColor.ANSI_YELLOW.escape()).append("|   VICTORY POINTS: ").append(showedCard[i][2].getVictoryPointsDevCard()).append("         |     ")
                    .append(CliColor.ANSI_PURPLE.escape()).append("|   VICTORY POINTS: ").append(showedCard[i][3].getVictoryPointsDevCard()).append("         |\n");

            s.append(CliColor.ANSI_GREEN.escape()).append("|_______________________|  ").append(CliColor.ANSI_BLUE.escape()).append("   |_______________________|  ").append(CliColor.ANSI_YELLOW.escape()).append("   |_______________________|  ").append(CliColor.ANSI_PURPLE.escape()).append("   |_______________________|\n");


            s.append(CliColor.RESET).append("\n");
            System.out.println(s.toString());
        }

    }

    public StringBuilder printResources(ArrayList<Resource> res) {
        StringBuilder s = new StringBuilder();
        if (res.size() == 2)
            s.append("   ");
        if (res.size() == 1)
            s.append("  ");

        for (Resource resource : res) {
            s = s.append(resource.getQnt());
            printSymbol(resource, s);
        }

        return s;
    }

    private void printSymbol(Resource resource, StringBuilder s) {
        switch (resource.getType()) {
            case COIN:
                s.append(CliColor.ANSI_YELLOW.escape()).append("$ ").append(CliColor.RESET);
                break;
            case SHIELD:
                s.append(CliColor.ANSI_BLUE.escape()).append("♦ ︎").append(CliColor.RESET);
                break;
            case STONE:
                s.append(CliColor.ANSI_GRAY.escape()).append("◼︎ ").append(CliColor.RESET);
                break;
            case FAITHPOINT:
                s.append(CliColor.ANSI_RED.escape()).append("† ").append(CliColor.RESET);
                break;
            case SERVANT:
                s.append(CliColor.ANSI_PURPLE.escape()).append("S︎ ").append(CliColor.RESET);
                break;
        }
    }

    public StringBuilder printRes(Resource res){
        StringBuilder s = new StringBuilder();

        printSymbol(res, s);

        return s;
    }
}
