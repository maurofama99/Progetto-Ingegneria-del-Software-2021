package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messagescs.ResourceTypeChosen;
import it.polimi.ingsw.observerPattern.ClientObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button coinBtn;
    @FXML
    private Button stoneBtn;
    @FXML
    private Button servantBtn;
    @FXML
    private Button shieldBtn;

    @FXML
    public void initialize(){
        coinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenCoinClicked);
        stoneBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenStoneClicked);
        servantBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenServantClicked);
        shieldBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenShieldClicked);
    }

    //dopo che ne viene cliccato uno devono essere disabilitati tutti
    private void whenShieldClicked(MouseEvent event){
        resourceType = 0;
        notifyObservers(new ResourceTypeChosen("client", resourceType));
    }

    private void whenServantClicked(MouseEvent event){
        resourceType = 1;
        notifyObservers(new ResourceTypeChosen("client", resourceType));
    }

    private void whenCoinClicked(MouseEvent event){
        resourceType = 2;
        notifyObservers(new ResourceTypeChosen("client", resourceType));
    }

    private void whenStoneClicked(MouseEvent event){
        resourceType = 3;
        notifyObservers(new ResourceTypeChosen("client", resourceType));
    }




}
