package it.polimi.ingsw.network.client.messages;

public class LoginData implements Message {
    private final int numPlayers;
    private final String ip;
    private final String nickname;

    public LoginData(int numPlayers, String ip, String nickname) {
        this.numPlayers = numPlayers;
        this.ip = ip;
        this.nickname = nickname;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public String getIp() {
        return ip;
    }

    public String getNickname() {
        return nickname;
    }
}
