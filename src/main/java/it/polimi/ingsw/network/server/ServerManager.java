package it.polimi.ingsw.network.server;
import it.polimi.ingsw.controller.WaitingRoom;

public class ServerManager {

    public static void main(String[] args) {
        int i = 0;
        String arg;
        int SOCKET_PORT = -1;

        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];

            if ("-port".equals(arg)) {
                if (i < args.length)
                    try {
                        SOCKET_PORT = Integer.parseInt(args[i++]);
                    } catch (NumberFormatException e) {
                        System.err.println("-port requires a port number\nUsage: Server -port portNumber");
                    }
                else
                    System.err.println("-port requires a port number\nUsage: Server -port portNumber");
            }
        }
        if (i == 0 || SOCKET_PORT==-1){
            System.err.println("Usage: Server -port portNumber");
            return;
        }

        Server server = new Server(new WaitingRoom(), SOCKET_PORT);
        server.run();
    }

}

