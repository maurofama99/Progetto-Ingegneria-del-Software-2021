package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
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

    private int index1, index2;

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

    //N.B. quella cliccata verrà scartata nel gioco
    //le quattro carte leader sono fornite nell'arraylist leaderCards
    //far selezionare all'utente due carte su quattro:
    //le due carte selezionate verranno passate tramite il loro indice

    //esempio: showa arraylist leaderCards = { leader1, leader2, leader3, leader4 }
    //l'utente seleziona leader1 e leader3
    //l'arraylist indexes deve essere composto da 0 e 2
    //fine

    private void whenFirstClicked(MouseEvent event){

    }

    private void whenSecondClicked(MouseEvent event){

    }

    private void whenThirdClicked(MouseEvent event){

    }

    private void whenFourthClicked(MouseEvent event){

    }



}
