package it.polimi.ingsw.model.singleplayer;

public class LorenzoIlMagnifico {

    private boolean isPlaying;
    private Token showedToken;
    private BlackCross blackCross = new BlackCross(0);

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public Token getShowedToken() {
        return showedToken;
    }

    public void setShowedToken(Token showedToken) {
        this.showedToken = showedToken;
    }

    public BlackCross getBlackCross() {
        return blackCross;
    }

    public void setBlackCross(BlackCross blackCross) {
        this.blackCross = blackCross;
    }

    public void turnToken(){
        //turn the first token, update showed token and activate its effect
    }
}
