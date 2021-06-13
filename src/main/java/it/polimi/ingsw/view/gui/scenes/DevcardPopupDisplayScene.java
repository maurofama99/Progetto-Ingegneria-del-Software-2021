package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.devcard.Deck;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.network.messagescs.BuyDevCard;
import it.polimi.ingsw.observerPattern.ClientObservable;
import it.polimi.ingsw.view.cli.ModelView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
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

/**
 * The controller of the dev card market. Shows and updates the cards, letting the player choosing the card he wants
 * giving it to him only after checking if he has the resources
 */
public class DevcardPopupDisplayScene extends ClientObservable implements GenericPopupController{

    private final Stage stage;
    private ModelView modelView;
    private boolean interactive;


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

    /**
     * Simple constructor of the class
     * @param modelView the current modelView that helps keeping the images updated.
     */
    public DevcardPopupDisplayScene(ModelView modelView, boolean interactive){
        stage = new Stage();
        this.modelView = modelView;
        stage.initOwner(SceneController.getOnGoingScene().getWindow());
        stage.initModality(Modality.NONE);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        x_Offset = 0;
        y_Offset = 0;
        this.interactive = interactive;
    }

    public void initialize(){
        setDevCardImages();
        //if (interactive) backBtn.setDisable(true);
        //else disableAll();

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


    private void whenBackBtnClicked(MouseEvent event){
        Platform.runLater(() -> {
            ActionPopupController apc = new ActionPopupController(modelView);
            apc.addAllClientObservers(clientObservers);
            SceneController.showPopup(apc, "action_popup.fxml");
        });
        stage.close();
    }

    /**
     * Twelve methods to manage the click on the cards. They are used to select a card.
     * @param event the mouseevent we provide in the initialize method
     */
    private void whenFirstRowFirstColumnBtnClicked(MouseEvent event){
        disableAll();

        Platform.runLater(() -> {
            SlotSelectPopupController sspc = new SlotSelectPopupController(modelView, 1, 1);
            sspc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sspc, "slot_popup.fxml");
        });

        stage.close();
    }

    private void whenFirstRowSecondColumnBtnClicked(MouseEvent event){
        disableAll();

        Platform.runLater(() -> {
            SlotSelectPopupController sspc = new SlotSelectPopupController(modelView, 1, 2);
            sspc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sspc, "slot_popup.fxml");
        });

        stage.close();
    }

    private void whenFirstRowThirdColumnBtnClicked(MouseEvent event){
        disableAll();

        Platform.runLater(() -> {
            SlotSelectPopupController sspc = new SlotSelectPopupController(modelView, 1, 3);
            sspc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sspc, "slot_popup.fxml");
        });

        stage.close();
    }

    private void whenFirstRowFourthColumnBtnClicked(MouseEvent event){
        disableAll();

        Platform.runLater(() -> {
            SlotSelectPopupController sspc = new SlotSelectPopupController(modelView, 1, 4);
            sspc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sspc, "slot_popup.fxml");
        });

        stage.close();
    }

    private void whenSecondRowFirstColumnBtnClicked(MouseEvent event){
        disableAll();

        Platform.runLater(() -> {
            SlotSelectPopupController sspc = new SlotSelectPopupController(modelView, 2, 1);
            sspc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sspc, "slot_popup.fxml");
        });

        stage.close();
    }

    private void whenSecondRowSecondColumnBtnClicked(MouseEvent event){
        disableAll();

        Platform.runLater(() -> {
            SlotSelectPopupController sspc = new SlotSelectPopupController(modelView, 2, 2);
            sspc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sspc, "slot_popup.fxml");
        });

        stage.close();
    }

    private void whenSecondRowThirdColumnBtnClicked(MouseEvent event){
        disableAll();
        Platform.runLater(() -> {
            SlotSelectPopupController sspc = new SlotSelectPopupController(modelView, 2, 3);
            sspc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sspc, "slot_popup.fxml");
        });
        stage.close();
    }

    private void whenSecondRowFourthColumnBtnClicked(MouseEvent event){
        disableAll();
        Platform.runLater(() -> {
            SlotSelectPopupController sspc = new SlotSelectPopupController(modelView, 2, 4);
            sspc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sspc, "slot_popup.fxml");
        });
        stage.close();
    }

    private void whenThirdRowFirstColumnBtnClicked(MouseEvent event){
        disableAll();
        Platform.runLater(() -> {
            SlotSelectPopupController sspc = new SlotSelectPopupController(modelView, 3, 1);
            sspc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sspc, "slot_popup.fxml");
        });
        stage.close();
    }

    private void whenThirdRowSecondColumnBtnClicked(MouseEvent event){
        disableAll();
        Platform.runLater(() -> {
            SlotSelectPopupController sspc = new SlotSelectPopupController(modelView, 3, 2);
            sspc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sspc, "slot_popup.fxml");
        });
        stage.close();
    }

    private void whenThirdRowThirdColumnBtnClicked(MouseEvent event){
        disableAll();
        Platform.runLater(() -> {
            SlotSelectPopupController sspc = new SlotSelectPopupController(modelView, 3, 3);
            sspc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sspc, "slot_popup.fxml");
        });
        stage.close();
    }

    private void whenThirdRowFourthColumnBtnClicked(MouseEvent event) {
        disableAll();
        Platform.runLater(() -> {
            SlotSelectPopupController sspc = new SlotSelectPopupController(modelView, 3, 4);
            sspc.addAllClientObservers(clientObservers);
            SceneController.showPopup(sspc, "slot_popup.fxml");
        });
        stage.close();
    }

    /**
     * Simple disables all the buttons to prevent multiple clicks
     */
    public void disableAll(){
        firstRowFirstColumnBtn.setDisable(true);
        firstRowSecondColumnBtn.setDisable(true);
        firstRowThirdColumnBtn.setDisable(true);
        firstRowFourthColumnBtn.setDisable(true);
        secondRowFirstColumnBtn.setDisable(true);
        secondRowSecondColumnBtn.setDisable(true);
        secondRowThirdColumnBtn.setDisable(true);
        secondRowFourthColumnBtn.setDisable(true);
        thirdRowFirstColumnBtn.setDisable(true);
        thirdRowSecondColumnBtn.setDisable(true);
        thirdRowThirdColumnBtn.setDisable(true);
        thirdRowFourthColumnBtn.setDisable(true);
    }

    /**
     * Sets the image of a DevCard.
     * @param devCard the DevCard that we use in the matching system. We take some attributes with getters
     * and we named the file in a way it matches it.
     * @return an ImageView that will be added to the Grid
     */
    public ImageView setDevCardImage(DevCard devCard){
        ImageView devImage = new ImageView();
        Image image = new Image("/front/devcard_" + "color-"
                + devCard.getCardColor() + "_level-"
                + devCard.getLevel() + "_vp-"
                + devCard.getVictoryPointsDevCard() + ".png");
        devImage.setImage(image);
        devImage.setPreserveRatio(false);
        devImage.setFitHeight(205);
        devImage.setFitWidth(159);

        return devImage;
    }

    /**
     * Adds one by one the images to the Grid and shows them
     */
    public void setDevCardImages(){

        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 4 ; j++) {
                if(modelView.getShowedDeck()[i][j] != null){
                    devCardGrid.add(setDevCardImage(modelView.getShowedDeck()[i][j]),j,i);
                } else if(modelView.getShowedDeck()[i][j] == null){

                    ImageView backImage = new ImageView();
                    Image image = new Image("/back/devcard-back-"+ i + "-" + j + ".png");
                    backImage.setImage(image);
                    backImage.setPreserveRatio(false);
                    backImage.setFitWidth(159);
                    backImage.setFitHeight(205);
                    devCardGrid.add(backImage, j, i);
                }

            }
        }

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

}
