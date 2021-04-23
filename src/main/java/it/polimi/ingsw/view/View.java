package it.polimi.ingsw.view;

public interface View {

    //reperisci il nickname del player
    void fetchNickname();

    //reperisci il numero di giocatori con i quali vuole giocare il player
    void fetchPlayersNumber();

    //qui ci vanno tutti i metodi che reperiscono dall'utente i comandi
}
