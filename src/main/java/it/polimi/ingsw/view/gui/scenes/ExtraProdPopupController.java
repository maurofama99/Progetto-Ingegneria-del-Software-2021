package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.player.leadercards.AddProduction;
import it.polimi.ingsw.model.player.leadercards.EffectType;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.messagescs.ActivateExtraProd;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ExtraProdPopupController extends ClientObservable implements GenericPopupController {

    private final Stage stage;

    private Resource resource;
    private ModelView modelView;

    private double x_Offset;
    private double y_Offset;

    @FXML
    private BorderPane rootPane;
    @FXML
    private Button yesBtn;
    @FXML
    private Button noBtn;
    @FXML
    private ImageView leaderCard;

    public ExtraProdPopupController(Resource resource, ModelView modelView){
        stage = new Stage();
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
        this.resource = resource;
        this.modelView = modelView;
    }

    @FXML
    public void initialize(){
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);
        yesBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::yes);
        noBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::no);
        setLeaderCard();
    }

    private void no (MouseEvent event) {
        notifyObservers(new ActivateExtraProd(-1));
        stage.close();
    }

    private void yes (MouseEvent event) {
        Platform.runLater(()-> {
            ExtraProdResourcePopupController eprpc = new ExtraProdResourcePopupController();
            eprpc.addAllClientObservers(clientObservers);
            SceneController.showPopup(eprpc, "swap_white.fxml");
        });
        Platform.runLater(() -> {
            PopupSceneController psc = new PopupSceneController("You can choose a type of resource you want");
            psc.addAllClientObservers(clientObservers);
            SceneController.showPopup(psc, "popup_scene.fxml");
        });
    }

    /**
     * These two methods manages the tracking process of the window.
     * @param event the mouse event selected in the initialize method
     */
    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getScreenX();
        y_Offset = stage.getY() - event.getScreenY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() +x_Offset);
        stage.setY(event.getSceneY()+y_Offset);
    }

    /**
     * These two methods are called to manage the popup scene
     */
    public void showPopUp(){
        stage.show();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }

    public void setLeaderCard(){
        for (LeaderCard leaderCard : modelView.getLeaderCards()){//todo: è l'arraylist sbagliato, bisogna usare quello delle active leader cards
            if (leaderCard.getLeaderEffect().getEffectType() == EffectType.ADDPRODUCTION ){
                if (((Resource)leaderCard.getLeaderEffect().getObject()).getType().equals(resource.getType())){
                    this.leaderCard.setImage(new Image("/front/leader_"+ leaderCard.getLeaderEffect().toString()+".png"));
                }
            }
        }
    }
}
