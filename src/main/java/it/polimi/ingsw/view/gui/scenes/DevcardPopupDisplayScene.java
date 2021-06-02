package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.devcard.Deck;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.network.messagescs.BuyDevCard;
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

public class DevcardPopupDisplayScene extends ClientObservable implements GenericPopupController{

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

    @FXML
    private Button firstRowFirstColumnBtn, firstRowSecondColumnBtn, firstRowThirdColumnBtn, firstRowFourthColumnBtn;
    @FXML
    private Button secondRowFirstColumnBtn, secondRowSecondColumnBtn, secondRowThirdColumnBtn, secondRowFourthColumnBtn;
    @FXML
    private Button thirdRowFirstColumnBtn, thirdRowSecondColumnBtn, thirdRowThirdColumnBtn, thirdRowFourthColumnBtn;

    public DevcardPopupDisplayScene(ModelView modelView){
        stage = new Stage();
        this.modelView = modelView;
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.NONE);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
    }

    public void initialize(){
        setDevCardImages();

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
        stage.close();
    }

    private void whenFirstRowFirstColumnBtnClicked(MouseEvent event){
        //todo popup per slot
        disableAll();
        notifyObservers(new BuyDevCard(1, 1, 1));
        stage.close();
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

    public void disableAll(){
        firstRowFirstColumnBtn.setDisable(true);
        firstRowSecondColumnBtn.setDisable(true);
        firstRowThirdColumnBtn.setDisable(true);
        firstRowFourthColumnBtn.setDisable(true);
        secondRowFirstColumnBtn.setDisable(true);
        secondRowSecondColumnBtn.setDisable(true);
        secondRowThirdColumnBtn.setDisable(true);
        thirdRowFirstColumnBtn.setDisable(true);
        thirdRowSecondColumnBtn.setDisable(true);
        thirdRowThirdColumnBtn.setDisable(true);
        thirdRowFourthColumnBtn.setDisable(true);
    }

    public void showPopUp(){
        stage.show();
    }

    public void setScene(Scene scene){
        stage.setScene(scene);
    }

    public ImageView setDevCardImage(DevCard devCard){
        ImageView devImage = new ImageView();
        Image image = new Image("/front/devcard_" + "color-" + devCard.getCardColor() + "_level-" + devCard.getLevel() + "_vp-" + devCard.getVictoryPointsDevCard() + ".png");
        devImage.setImage(image);
        devImage.setPreserveRatio(false);

        return devImage;
    }

    public void setDevCardImages(){

        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 4 ; j++) {
                devCardGrid.add(setDevCardImage(modelView.getShowedDeck()[i][j]),j,i);
            }
        }
    }

}
