package it.polimi.ingsw.network;

public enum Content {
    //s->c
    LOGIN_REQUEST,
    LOGIN_FAIL,
    NUM_PLAYERS_REQUEST,
    FULL_SERVER,
    INVALID_INPUT,
    ANOTHER_PLAYER_TURN,
    GENERAL_NO_REQUIREMENTS,
    START_GAME,


    //secondo me si devono raggruppare i tipi di messaggio
    //cioè che i messaggi che vengono mostrati ad inizio partita sono tutti di un tipo

    GENERIC_MESSAGE,

    //c->s
    LOGIN,
    LOGIN_DATA,
    PLAYERS_NUMBER,


    LOGIN_SUCCESSFUL,
    INVALID_DATA,

    RESPONSE,
    DISCARD_LEADER,
    ACTIVATE_LEADER,
    LEADER_ACTIVATED,
    GAME_STATE,
    DONE_ACTION,
    GOING_MARKET,
    SELECT_LINE,
    SEND_RESOURCE,
    DISCARD_RES,
    MOVE_RES,
    STORE_RES,
    POSITIVE,
    ACTIVATE_PRODUCTION,
    ASKTYPERESOURCE
}

