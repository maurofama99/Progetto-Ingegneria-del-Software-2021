package it.polimi.ingsw.controller;

/**
 * Since the singleplayer game has only one player and the "CPU" we have a state for Lorenzo turn
 */
public enum SinglePlayerTableState {
    SETUP,
    PLAYERS_TURN,
    LORENZOS_TURN,
    END
}
