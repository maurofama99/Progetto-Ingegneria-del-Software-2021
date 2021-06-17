package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This a Popup Controller interface, which manages the Action Popup. It asks the player the action he wants to
 * do at the start of the turn.
 */
public class ActionPopupController extends ClientObservable implements GenericPopupController {

    private final Stage stage;
    private ModelView modelView;

    private double x_Offset;
    private double y_Offset;

    @FXML
    private BorderPane rootPane;
    @FXML
    private Button marketBtn;
    @FXML
    private Button buyBtn;
    @FXML
    private Button productionBtn;

    /**
     * Constructor of the class
     * @param modelView the current ModelView of the table
     */
    public ActionPopupController(ModelView modelView){
        stage = new Stage();
        this.modelView = modelView;
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.NONE);
        //stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
    }

    @FXML
    public void initialize(){
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);
        marketBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenMarketButtonClicked);
        buyBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenBuyButtonClicked);
        productionBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenProductionButtonClicked);
    }

    /**
     * These two methods manages the tracking process of the window.
     * @param event the mouse event selected in the initialize method
     */
    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getSceneX();
        y_Offset = stage.getY() - event.getSceneY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() - x_Offset);
        stage.setY(event.getScreenY() - y_Offset);
    }


    /**
     * Takes the player to the market
     * @param event the mouse event selected in the initialize method
     */
    private void whenMarketButtonClicked(MouseEvent event){
        Platform.runLater(() -> {
            MarketPopupSceneController mpsc = new MarketPopupSceneController(modelView, true);
            mpsc.addAllClientObservers(clientObservers);
            SceneController.showPopup(mpsc, "market_tray_display.fxml");
        });
        stage.close();
    }

    /**
     * Takes the player to the dev cards buy window
     * @param event the mouse event selected in the initialize method
     */
    private void whenBuyButtonClicked(MouseEvent event){
        Platform.runLater(() -> {
            DevcardPopupDisplayScene dpds = new DevcardPopupDisplayScene(modelView, true);
            dpds.addAllClientObservers(clientObservers);
            SceneController.showPopup(dpds, "display_devcards.fxml");
        });
        stage.close();
    }

    /**
     * Takes the player to the production popup to let him choose the cards
     * @param event the mouse event selected in the initialize method
     */
    private void whenProductionButtonClicked(MouseEvent event){
        Platform.runLater(() -> {
            ProductionPopupSceneController ppsc = new ProductionPopupSceneController(modelView);
            ppsc.addAllClientObservers(clientObservers);
            SceneController.showPopup(ppsc, "production_popup.fxml");
        });
        stage.close();
    }

    /**
     * These two methods are called to manage the popup scene
     */
    public void showPopUp(){
        stage.showAndWait();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }

}

