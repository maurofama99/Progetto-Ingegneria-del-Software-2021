package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.ClientObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;

public class LobbySceneController extends ClientObservable implements GenericSceneController {
    private List<String> nicknames;
    private int maxPlayers;

    @FXML
    private Label playersLbl;
    @FXML
    private Label numbersLbl;
    @FXML
    private Button exitGameButton;

    @FXML
    public void initialize(){
        playersLbl.setText(String.join(",", nicknames));
        numbersLbl.setText(nicknames.size() + "/" + maxPlayers);
    }

    private void onExitGameClick(Event event){
        exitGameButton.setDisable(true);


    }

    public void setNicknames(List<String> nicknames){
        this.nicknames = nicknames;
    }

    public void setMaxPlayers(int maxPlayers){
        this.maxPlayers = maxPlayers;
    }

    public void update(){
        playersLbl.setText(String.join(",", this.nicknames));
        numbersLbl.setText(this.nicknames.size()+"/"+this.maxPlayers);
    }

}
