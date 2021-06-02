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

    public ActionPopupController(ModelView modelView){
        stage = new Stage();
        this.modelView = modelView;
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
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

    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getScreenX();
        y_Offset = stage.getY() - event.getScreenY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() +x_Offset);
        stage.setY(event.getSceneY()+y_Offset);
    }

    private void whenMarketButtonClicked(MouseEvent event){
        Platform.runLater(() -> {
            MarketPopupSceneController mpsc = new MarketPopupSceneController(modelView);
            mpsc.addAllClientObservers(clientObservers);
            mpsc.disable(false);
            SceneController.showPopup(mpsc, "market_tray_display.fxml");
        });
        stage.close();
    }

    private void whenBuyButtonClicked(MouseEvent event){
        Platform.runLater(() -> {
            DevcardPopupDisplayScene dpds = new DevcardPopupDisplayScene(modelView);
            dpds.addAllClientObservers(clientObservers);
            SceneController.showPopup(dpds, "display_devcards.fxml");
        });
        stage.close();
    }

    private void whenProductionButtonClicked(MouseEvent event){
        Platform.runLater(() -> {
            ProductionPopupSceneController ppsc = new ProductionPopupSceneController(modelView);
            ppsc.addAllClientObservers(clientObservers);
            SceneController.showPopup(ppsc, "production_popup.fxml");
        });
        stage.close();
    }

    public void showPopUp(){
        stage.showAndWait();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }

}

