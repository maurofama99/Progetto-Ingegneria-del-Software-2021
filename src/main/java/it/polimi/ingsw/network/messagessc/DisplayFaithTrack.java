package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class DisplayFaithTrack extends Message {
    private FaithTrack faithTrack;

    public DisplayFaithTrack(FaithTrack faithTrack) {
        super("server", "client", Content.DISPLAY_FAITHTRACK);
        this.faithTrack = faithTrack;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }
}
