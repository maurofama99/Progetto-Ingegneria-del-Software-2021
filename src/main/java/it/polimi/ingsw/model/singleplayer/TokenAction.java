package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Table;

/**
 * Interfaces of the token's action
 */
public interface TokenAction{

    void doAction(Table t);

    String toStringGui();

}
