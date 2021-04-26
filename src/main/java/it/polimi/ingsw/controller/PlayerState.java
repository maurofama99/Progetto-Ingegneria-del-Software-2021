package it.polimi.ingsw.controller;

public enum PlayerState {
    IN_WAIT,
    START, //--> discard leader cards and choose resources based on player's order
    PLAYING, //can do playeractions
    END; //
}
