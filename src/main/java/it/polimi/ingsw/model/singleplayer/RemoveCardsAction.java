package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.devcard.Color;

public class RemoveCardsAction implements TokenAction {

    private Color devCardColor;

    public Color getDevCardColor() {
        return devCardColor;
    }

    public void setDevCardColor(Color devCardColor) {
        this.devCardColor = devCardColor;
    }

    @Override
    public void doAction() {
        if(getDevCardColor()==Color.GREEN){
        //remove two green cards of level 1 and so on
        }
        else if(getDevCardColor()==Color.BLUE){
        //remove two blue cards of level 1 and so on
        }
        else if(getDevCardColor()==Color.YELLOW){
        //remove two yellow cards of level 1 and so on
        }
        else if(getDevCardColor()==Color.PURPLE){
        //remove two purple cards of level 1 and so on
        }
        else
            System.out.println("Not legal");
    }


}
