package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.observerPattern.ClientObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class DevcardDisplayScene extends ClientObservable implements GenericSceneController {
    @FXML
    private GridPane devCardGrid;
    @FXML
    private Button backBtn;
    @FXML
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
    private ImageView thirdRowFourthColumn;
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

    public void initialize(){

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

    public void setFirstRowFirstColumn(ImageView firstRowFirstColumn) {
        this.firstRowFirstColumn = firstRowFirstColumn;
    }

    public void setFirstRowSecondColumn(ImageView firstRowSecondColumn) {
        this.firstRowSecondColumn = firstRowSecondColumn;
    }

    public void setFirstRowThirdColumn(ImageView firstRowThirdColumn) {
        this.firstRowThirdColumn = firstRowThirdColumn;
    }

    public void setFirstRowFourthColumn(ImageView firstRowFourthColumn) {
        this.firstRowFourthColumn = firstRowFourthColumn;
    }

    public void setSecondRowFirstColumn(ImageView secondRowFirstColumn) {
        this.secondRowFirstColumn = secondRowFirstColumn;
    }

    public void setSecondRowSecondColumn(ImageView secondRowSecondColumn) {
        this.secondRowSecondColumn = secondRowSecondColumn;
    }

    public void setSecondRowThirdColumn(ImageView secondRowThirdColumn) {
        this.secondRowThirdColumn = secondRowThirdColumn;
    }

    public void setSecondRowFourthColumn(ImageView secondRowFourthColumn) {
        this.secondRowFourthColumn = secondRowFourthColumn;
    }

    public void setThirdRowFirstColumn(ImageView thirdRowFirstColumn) {
        this.thirdRowFirstColumn = thirdRowFirstColumn;
    }

    public void setThirdRowSecondColumn(ImageView thirdRowSecondColumn) {
        this.thirdRowSecondColumn = thirdRowSecondColumn;
    }

    public void setThirdRowThirdColumn(ImageView thirdRowThirdColumn) {
        this.thirdRowThirdColumn = thirdRowThirdColumn;
    }

    public void setThirdRowFourthColumn(ImageView thirdRowFourthColumn) {
        this.thirdRowFourthColumn = thirdRowFourthColumn;
    }
}
