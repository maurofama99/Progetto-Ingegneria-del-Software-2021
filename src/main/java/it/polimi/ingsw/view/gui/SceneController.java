package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observerPattern.*;
import it.polimi.ingsw.view.gui.scenes.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class SceneController extends ClientObservable {

    private static Scene onGoingScene;
    private static GenericSceneController activeController;

    public static Scene getOnGoingScene() {
        return onGoingScene;
    }

    public static GenericSceneController getActiveController() {
        return activeController;
    }

    public static <T> T changeRootPane(List<ClientObserver> clientObservers, Scene scene, String fxml){
        T controller = null;

        try{
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController();
            ((ClientObservable) controller).addAllClientObservers(clientObservers);
            activeController = (GenericSceneController) controller;
            onGoingScene = scene;
            onGoingScene.setRoot(root);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return controller;
    }


    public static <T> T changeRootPane(List<ClientObserver> clientObservers, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        return changeRootPane(clientObservers, scene, fxml);
    }

    public static <T> T changeRootPane(List<ClientObserver> clientObservers, String fxml) {
        return changeRootPane(clientObservers, onGoingScene, fxml);
    }

    public static void changeRootPane(GenericSceneController controller, Scene scene, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));

            loader.setController(controller);
            activeController = controller;
            Parent root = loader.load();

            onGoingScene = scene;
            onGoingScene.setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void changeRootPane(GenericSceneController controller, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        changeRootPane(controller, scene, fxml);
    }

    public static void changeRootPane(GenericSceneController controller, String fxml) {
        changeRootPane(controller, onGoingScene, fxml);
    }

    public static void showPopup(GenericPopupController controller, String fxml){

        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));

            loader.setController(controller);
            Parent parent = loader.load();
            Scene popupScene = new Scene(parent);
            controller.setScene(popupScene);
            controller.showPopUp();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


}
