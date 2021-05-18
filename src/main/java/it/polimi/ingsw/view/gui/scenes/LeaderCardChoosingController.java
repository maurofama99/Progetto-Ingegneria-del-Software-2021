package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class LeaderCardChoosingController implements GenericSceneController {

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

    }

    private void whenSecondClicked(MouseEvent event){

    }

    private void whenThirdClicked(MouseEvent event){

    }

    private void whenFourthClicked(MouseEvent event){

    }
}
