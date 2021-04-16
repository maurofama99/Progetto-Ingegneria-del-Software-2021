package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.Color;

public class RemoveCardsAction implements TokenAction {

    private Color devCardColor;

    public RemoveCardsAction(Color devCardColor) {
        this.devCardColor = devCardColor;
    }

    public Color getDevCardColor() {
        return devCardColor;
    }

    public void setDevCardColor(Color devCardColor) {
        this.devCardColor = devCardColor;
    }

    //Lorenzo's takes the cards removed, he can't play them of course. Visually easier to implement and
    //it's better to see.

    /**
     * Override of token action, here it removes the cards. Always two cards of level 1 of the color
     * selected, if absent, the level increases
     * @param t table where we are playing
     */
    @Override
    public void doAction(Table t) {
        if(getDevCardColor()==Color.GREEN){
        //remove two green cards of level 1 and so on
        //t.getDevCardsDeck().removeAndGetCard(1, 1);
        }
        else if(getDevCardColor()==Color.BLUE){
        //remove two blue cards of level 1 and so on
        //t.getDevCardsDeck().removeAndGetCard(1,2);
        }
        else if(getDevCardColor()==Color.YELLOW){
        //remove two yellow cards of level 1 and so on
        //t.getDevCardsDeck().removeAndGetCard(1,3);
        }
        else if(getDevCardColor()==Color.PURPLE){
        //remove two purple cards of level 1 and so on
        //t.getDevCardsDeck().removeAndGetCard(1,4);
        }
        else
            System.out.println("Not legal");
    }


}
