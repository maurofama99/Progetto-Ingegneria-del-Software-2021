package it.polimi.ingsw.network.server;

import it.polimi.ingsw.commons.Listener;
import it.polimi.ingsw.network.Message;










//TODO: QUESTA CLASSE ESISTE GIA' NEL PACKAGE VIEW ED IMPLEMENTA L'INTERFACCIA VIEW











public class VirtualView implements Listener<Message> {

    private Server server;

    public VirtualView(Server server) {
        this.server = server;
    }

    public void handleMessage(Message message){

    }

    @Override
    public void update(Message object) {

    }
}
