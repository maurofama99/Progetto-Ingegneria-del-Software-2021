package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.messagescs.GoingMarket;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MarketPopupSceneController extends ClientObservable implements GenericPopupController {
    private final Stage stage;

    private ModelView modelView;
    private boolean interactive;

    private double x_Offset = 0;
    private double y_Offset = 0;
    @FXML
    private StackPane rootPane;
    @FXML
    private GridPane marketTray;
    @FXML
    private ImageView slideMrbl;
    @FXML
    private Button backBtn = new Button();
    @FXML
    private Button column1Btn = new Button();
    @FXML
    private Button column2Btn = new Button();
    @FXML
    private Button column3Btn= new Button();
    @FXML
    private Button column4Btn= new Button();
    @FXML
    private Button row1Btn= new Button();
    @FXML
    private Button row2Btn= new Button();
    @FXML
    private Button row3Btn= new Button();

    public MarketPopupSceneController(ModelView modelView, boolean interactive){
        stage = new Stage();
        this.modelView = modelView;
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
        this.interactive = interactive;
    }


    public void initialize(){

        if (interactive) backBtn.setDisable(true);
        else disable(true);

        setMarbles();

        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);
        column1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenColumn1BtnClicked);
        column2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenColumn2BtnClicked);
        column3Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenColumn3BtnClicked);
        column4Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenColumn4BtnClicked);
        row1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenRow1BtnClicked);
        row2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenRow2BtnClicked);
        row3Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenRow3BtnClicked);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenBackBtnClicked);

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

    private void whenBackBtnClicked(MouseEvent event){
        stage.close();
    }

    private void whenColumn1BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(1, false));
        disable(true);
        stage.close();
    }

    private void whenColumn2BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(2, false));
        disable(true);
        stage.close();
    }

    private void whenColumn3BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(3, false));
        disable(true);
        stage.close();
    }

    private void whenColumn4BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(4, false));
        disable(true);
        stage.close();
    }

    private void whenRow1BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(1, true));
        disable(true);
        stage.close();
    }

    private void whenRow2BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(2, true));
        disable(true);
        stage.close();
    }

    private void whenRow3BtnClicked(MouseEvent event){
        notifyObservers(new GoingMarket(3, true));
        disable(true);
        stage.close();
    }


    public void disable(boolean disable) {
        column1Btn.setDisable(disable);
        column3Btn.setDisable(disable);
        column2Btn.setDisable(disable);
        row1Btn.setDisable(disable);
        row2Btn.setDisable(disable);
        row3Btn.setDisable(disable);
        column4Btn.setDisable(disable);
    }



    public ImageView setMarble(ResourceType resource){

        ImageView imageView = new ImageView();

        switch(resource){
            case SHIELD :
                imageView.setImage(new Image("/punchboard/marbles/cyan.png"));
                break;
            case SERVANT :
                imageView.setImage(new Image("/punchboard/marbles/purple.png"));
                break;
            case STONE :
                imageView.setImage(new Image("/punchboard/marbles/grey.png"));
                break;
            case COIN:
                imageView.setImage(new Image("/punchboard/marbles/yellow.png"));
                break;
            case WHITERESOURCE :
                imageView.setImage(new Image("/punchboard/marbles/white.png"));
                break;
            case FAITHPOINT:
                imageView.setImage(new Image("/punchboard/marbles/red.png"));
                break;
        }

        imageView.setPreserveRatio(false);
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
        return imageView;
    }

    public void setMarbles(){

       switch (modelView.getMarketTray().getSlide().getType()){
           case SHIELD :
               slideMrbl.setImage(new Image("/punchboard/marbles/cyan.png"));
               break;
           case SERVANT :
               slideMrbl.setImage(new Image("/punchboard/marbles/purple.png"));
               break;
           case STONE :
               slideMrbl.setImage(new Image("/punchboard/marbles/grey.png"));
               break;
           case COIN:
               slideMrbl.setImage(new Image("/punchboard/marbles/yellow.png"));
               break;
           case WHITERESOURCE :
               slideMrbl.setImage(new Image("/punchboard/marbles/white.png"));
               break;
           case FAITHPOINT:
               slideMrbl.setImage(new Image("/punchboard/marbles/red.png"));
               break;
       }

        for(int j=0; j<3;j++){
            for(int k=0; k<4; k++){
                marketTray.add(setMarble(modelView.getMarketTray().getTray()[j][k].getType()), k,j);
            }
        }

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
