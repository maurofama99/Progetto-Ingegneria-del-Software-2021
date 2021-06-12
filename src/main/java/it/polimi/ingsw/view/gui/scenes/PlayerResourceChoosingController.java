package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.ResourceTypeChosen;
import it.polimi.ingsw.observerPattern.ClientObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class PlayerResourceChoosingController extends ClientObservable implements GenericSceneController {

    private int resourceType;

    public int getResourceType() {
        return resourceType;
    }

    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView coin;
    @FXML
    private ImageView stone;
    @FXML
    private ImageView servant;
    @FXML
    private ImageView shield;


    @FXML
    public void initialize(){
        coin.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenCoinClicked);
        stone.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenStoneClicked);
        servant.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenServantClicked);
        shield.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenShieldClicked);
    }

    //dopo che ne viene cliccato uno devono essere disabilitati tutti (done, disable done)
    private void whenShieldClicked(MouseEvent event){
        resourceType = 0;
        notifyObservers(new ResourceTypeChosen("client", resourceType));

        coin.setDisable(true);
        stone.setDisable(true);
        servant.setDisable(true);
        shield.setDisable(true);
    }

    private void whenServantClicked(MouseEvent event){
        resourceType = 1;
        notifyObservers(new ResourceTypeChosen("client", resourceType));

        coin.setDisable(true);
        stone.setDisable(true);
        servant.setDisable(true);
        shield.setDisable(true);
    }

    private void whenCoinClicked(MouseEvent event){
        resourceType = 2;
        notifyObservers(new ResourceTypeChosen("client", resourceType));

        coin.setDisable(true);
        stone.setDisable(true);
        servant.setDisable(true);
        shield.setDisable(true);
    }

    private void whenStoneClicked(MouseEvent event){
        resourceType = 3;
        notifyObservers(new ResourceTypeChosen("client", resourceType));

        coin.setDisable(true);
        stone.setDisable(true);
        servant.setDisable(true);
        shield.setDisable(true);
    }




}
