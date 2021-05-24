package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.messagescs.DiscardLeader;
import it.polimi.ingsw.observerPattern.ClientObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class LeaderCardChoosingController extends ClientObservable implements GenericSceneController {

    private ArrayList<LeaderCard> leaderCards;

    public void setLeaderCards(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    private int index1;
    private boolean first = true;

    @FXML
    private StackPane rootPane;
    @FXML
    private Button firstBtn;
    @FXML
    private Button secondBtn;
    @FXML
    private Button thirdBtn;
    @FXML
    private Button fourthBtn;
    @FXML
    private ImageView firstLeader;
    @FXML
    private ImageView secondLeader;
    @FXML
    private ImageView thirdLeader;
    @FXML
    private ImageView fourthLeader;

    @FXML
    public void initialize(){
        //Probabilmente qua c'è bisogno di un metodo che porta le immagini di ogni leader card alla leader card che è stata randomizzata.

        firstBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstClicked);
        secondBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSecondClicked);
        thirdBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenThirdClicked);
        fourthBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFourthClicked);
    }

    private void whenFirstClicked(MouseEvent event){
        if (first) {
            index1 = 0;
            first = false;
            firstBtn.setDisable(true);
        } else {
            firstBtn.setDisable(true);
            secondBtn.setDisable(true);
            thirdBtn.setDisable(true);
            fourthBtn.setDisable(true);
            notifyObservers(new DiscardLeader("client", index1, 0));
        }
    }

    private void whenSecondClicked(MouseEvent event){
        if (first) {
            index1 = 1;
            first = false;
            secondBtn.setDisable(true);
        } else {
            firstBtn.setDisable(true);
            secondBtn.setDisable(true);
            thirdBtn.setDisable(true);
            fourthBtn.setDisable(true);
            notifyObservers(new DiscardLeader("client", index1, 1));
        }
    }

    private void whenThirdClicked(MouseEvent event){
        if (first) {
            index1 = 2;
            first = false;
            thirdBtn.setDisable(true);
        } else {
            firstBtn.setDisable(true);
            secondBtn.setDisable(true);
            thirdBtn.setDisable(true);
            fourthBtn.setDisable(true);
            notifyObservers(new DiscardLeader("client", index1, 2));
        }
    }

    private void whenFourthClicked(MouseEvent event){
        if (first) {
            index1 = 3;
            first = false;
            fourthBtn.setDisable(true);
        } else {
            firstBtn.setDisable(true);
            secondBtn.setDisable(true);
            thirdBtn.setDisable(true);
            fourthBtn.setDisable(true);
            notifyObservers(new DiscardLeader("client", index1, 3));
        }

    }



}
