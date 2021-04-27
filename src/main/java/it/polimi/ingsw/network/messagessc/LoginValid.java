package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class LoginValid extends Message {

    public LoginValid() {
        super("server", Content.LOGIN_SUCCESSFUL);
    }

}
