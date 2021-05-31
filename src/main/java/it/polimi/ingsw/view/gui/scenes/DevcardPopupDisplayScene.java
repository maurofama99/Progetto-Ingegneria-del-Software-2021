package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.devcard.Deck;
import it.polimi.ingsw.model.devcard.DevCard;
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

import java.util.ArrayList;

public class DevcardPopupDisplayScene extends ClientObservable implements GenericSceneController {

    private final Stage stage;
    private ModelView modelView;

    private double x_Offset = 0;
    private double y_Offset = 0;

    @FXML
    private StackPane rootPane;
    @FXML
    private GridPane devCardGrid;
    @FXML
    private Button backBtn;
    /*@FXML
    private ImageView firstRowFirstColumn;
    @FXML
    private ImageView firstRowSecondColumn;
    @FXML
    private ImageView firstRowThirdColumn;
    @FXML
    private ImageView firstRowFourthColumn;
    @FXML
    private ImageView secondRowFirstColumn;
    @FXML
    private ImageView secondRowSecondColumn;
    @FXML
    private ImageView secondRowThirdColumn;
    @FXML
    private ImageView secondRowFourthColumn;
    @FXML
    private ImageView thirdRowFirstColumn;
    @FXML
    private ImageView thirdRowSecondColumn;
    @FXML
    private ImageView thirdRowThirdColumn;
    @FXML
    private ImageView thirdRowFourthColumn;*/
    @FXML
    private Button firstRowFirstColumnBtn;
    @FXML
    private Button firstRowSecondColumnBtn;
    @FXML
    private Button firstRowThirdColumnBtn;
    @FXML
    private Button firstRowFourthColumnBtn;
    @FXML
    private Button secondRowFirstColumnBtn;
    @FXML
    private Button secondRowSecondColumnBtn;
    @FXML
    private Button secondRowThirdColumnBtn;
    @FXML
    private Button secondRowFourthColumnBtn;
    @FXML
    private Button thirdRowFirstColumnBtn;
    @FXML
    private Button thirdRowSecondColumnBtn;
    @FXML
    private Button thirdRowThirdColumnBtn;
    @FXML
    private Button thirdRowFourthColumnBtn;

    private Deck deck;

    public DevcardPopupDisplayScene(ModelView modelView){
        stage = new Stage();
        this.modelView = modelView;
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
    }

    public void initialize(){
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::whenRootPanePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::whenRootPaneDragged);

        //Buttons
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenBackBtnClicked);
        firstRowFirstColumnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstRowFirstColumnBtnClicked);
        firstRowSecondColumnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstRowSecondColumnBtnClicked);
        firstRowThirdColumnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstRowThirdColumnBtnClicked);
        firstRowFourthColumnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenFirstRowFourthColumnBtnClicked);
        secondRowFirstColumnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSecondRowFirstColumnBtnClicked);
        secondRowSecondColumnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSecondRowSecondColumnBtnClicked);
        secondRowThirdColumnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSecondRowThirdColumnBtnClicked);
        secondRowFourthColumnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenSecondRowFourthColumnBtnClicked);
        thirdRowFirstColumnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenThirdRowFirstColumnBtnClicked);
        thirdRowSecondColumnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenThirdRowSecondColumnBtnClicked);
        thirdRowThirdColumnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenThirdRowThirdColumnBtnClicked);
        thirdRowFourthColumnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::whenThirdRowFourthColumnBtnClicked);
    }

    private void whenRootPanePressed(MouseEvent event){
        x_Offset = stage.getX() - event.getScreenX();
        y_Offset = stage.getY() - event.getScreenY();
    }

    private void whenRootPaneDragged(MouseEvent event){
        stage.setX(event.getScreenX() +x_Offset);
        stage.setY(event.getSceneY()+y_Offset);
    }

    private void whenBackBtnClicked(MouseEvent event){

    }

    private void whenFirstRowFirstColumnBtnClicked(MouseEvent event){

    }

    private void whenFirstRowSecondColumnBtnClicked(MouseEvent event){

    }

    private void whenFirstRowThirdColumnBtnClicked(MouseEvent event){

    }

    private void whenFirstRowFourthColumnBtnClicked(MouseEvent event){

    }

    private void whenSecondRowFirstColumnBtnClicked(MouseEvent event){

    }

    private void whenSecondRowSecondColumnBtnClicked(MouseEvent event){

    }

    private void whenSecondRowThirdColumnBtnClicked(MouseEvent event){

    }

    private void whenSecondRowFourthColumnBtnClicked(MouseEvent event){

    }

    private void whenThirdRowFirstColumnBtnClicked(MouseEvent event){

    }

    private void whenThirdRowSecondColumnBtnClicked(MouseEvent event){

    }

    private void whenThirdRowThirdColumnBtnClicked(MouseEvent event){

    }

    private void whenThirdRowFourthColumnBtnClicked(MouseEvent event) {

    }

    public void showPopUp(){
        stage.showAndWait();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public ImageView setDevCardImage(DevCard devCard){
        ImageView devImage = new ImageView();
        Image image = new Image("/front/devcard_" + "color-" + devCard.getCardColor() + "_level-" + devCard.getLevel() + "_vp-" + devCard.getVictoryPointsDevCard() + ".png");
        devImage.setImage(image);

        return devImage;
    }

    public void setDevCardImages(DevCard[][] devCards){


        for (int i = 1; i < 4 ; i++) {
            for (int j = 1; j < 5 ; j++) {
                devCardGrid.add(setDevCardImage(devCards[i][j]),i,j);
            }
        }
    }

}
