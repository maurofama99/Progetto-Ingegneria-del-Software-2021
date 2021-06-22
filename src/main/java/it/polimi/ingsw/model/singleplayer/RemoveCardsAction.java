package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.view.cli.CliColor;
import it.polimi.ingsw.view.cli.CliGraphics;

import java.io.Serializable;

/**
 * Action of the token that removes dev cards from the shop
 */
public class RemoveCardsAction implements TokenAction, Serializable {

    private Color devCardColor;

    public RemoveCardsAction(Color devCardColor) {
        this.devCardColor = devCardColor;
    }

    public Color getDevCardColor() {
        return devCardColor;
    }


    //Lorenzo's takes the cards removed, he can't play them of course. Visually easier to implement and
    //it's better to see.

    /**
     * Override of token action, here it removes the cards. Always two cards of level 1 of the color
     * selected, if absent, the level increases.
     * @param t table where we are playing
     */
    @Override
    public void doAction(Table t) {
        int i=0;

        switch (devCardColor) {
            case GREEN:
                while (i < 2)
                    if (t.getDevCardsDeck().getDevCard(1, 2) != null) {
                        t.getDevCardsDeck().removeAndGetCard(1, 2);
                        i++;
                    } else if (t.getDevCardsDeck().getDevCard(2, 2) != null) {
                        t.getDevCardsDeck().removeAndGetCard(2, 2);
                        i++;
                    } else if (t.getDevCardsDeck().getDevCard(3, 2) != null) {
                        t.getDevCardsDeck().removeAndGetCard(3, 2);
                        i++;
                    }
                break;
            case BLUE:
                while (i < 2)
                    if (t.getDevCardsDeck().getDevCard(1, 3) != null) {
                        t.getDevCardsDeck().removeAndGetCard(1, 3);
                        i++;
                    } else if (t.getDevCardsDeck().getDevCard(2, 3) != null) {
                        t.getDevCardsDeck().removeAndGetCard(2, 3);
                        i++;
                    } else if (t.getDevCardsDeck().getDevCard(3, 3) != null) {
                        t.getDevCardsDeck().removeAndGetCard(3, 3);
                        i++;
                    }
                break;
            case YELLOW:
                while (i < 2)
                    if (t.getDevCardsDeck().getDevCard(1, 1) != null) {
                        t.getDevCardsDeck().removeAndGetCard(1, 1);
                        i++;
                    } else if (t.getDevCardsDeck().getDevCard(2, 1) != null) {
                        t.getDevCardsDeck().removeAndGetCard(2, 1);
                        i++;
                    } else if (t.getDevCardsDeck().getDevCard(3, 1) != null) {
                        t.getDevCardsDeck().removeAndGetCard(3, 1);
                        i++;
                    }
                break;
            case PURPLE :
                while (i < 2)
                    if (t.getDevCardsDeck().getDevCard(1, 4) != null) {
                        t.getDevCardsDeck().removeAndGetCard(1, 4);
                        i++;
                    } else if (t.getDevCardsDeck().getDevCard(2, 4) != null) {
                        t.getDevCardsDeck().removeAndGetCard(2, 4);
                        i++;
                    } else if (t.getDevCardsDeck().getDevCard(3, 4) != null) {
                        t.getDevCardsDeck().removeAndGetCard(3, 4);
                        i++;
                    }
                break;
        }
    }

    @Override
    public String toString() {
        String color = "";
        switch (devCardColor){
            case YELLOW:
                color = CliColor.ANSI_YELLOW.escape();
                break;
            case BLUE:
                color = CliColor.ANSI_BLUE.escape();
                break;
            case GREEN:
                color = CliColor.ANSI_GREEN.escape();
                break;
            case PURPLE:
                color = CliColor.ANSI_PURPLE.escape();
                break;
        }

        return  "-------------------------------\n                                    "+
                "|                             |\n                                    "+
                "|              "+color+"▉▉▉▉"+CliColor.RESET + "           |\n                                    "+
                "|          -2  "+color+"▉▉▉▉"+CliColor.RESET + " ︎           |\n                                    "+
                "|              "+color+"▉▉▉▉"+CliColor.RESET + "           |\n                                    "+
                "|                             |\n                                    "+
                "-------------------------------\n";
    }

    /**
     * Used in the matching system between images and attributes in the GUI
     * @return a string (that should match a file name in the resources folder)
     */
    @Override
    public String toStringGui(){
        switch (devCardColor){
            case YELLOW:
                return  "yellow";
            case BLUE:
                return  "blue";
            case GREEN:
                return  "green";
            case PURPLE:
                return  "purple";
            default:
                throw new IllegalStateException("Unexpected value: " + devCardColor);
        }
    }
}
