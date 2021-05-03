package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Table;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerManager {

    public static ArrayList<GameController> gameControllers = new ArrayList<>();

    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

            GameController gameController = new GameController();
            Server server = new Server(gameController);
            server.getGameController().setTable(new Table());
            gameControllers.add(gameController);
            executor.submit(server);


    }

}


//quando server manager fa partire il primo server, deve verificare costantemente lo stato del game controller di quel server
//quando lo stato diventa IN_GAME deve far partire un nuovo server
//quando lo stato diventa END può stoppare il thread