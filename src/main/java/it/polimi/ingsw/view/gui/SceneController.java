package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observerPattern.Observable;
import it.polimi.ingsw.observerPattern.Observer;
import it.polimi.ingsw.view.gui.scenes.GenericSceneController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

public class SceneController extends Observable {

    private static Scene onGoingScene;
    private static GenericSceneController activeController;

    public static Scene getOnGoingScene() {
        return onGoingScene;
    }

    public static GenericSceneController getActiveController() {
        return activeController;
    }

    public static <T> T changeRootPane(List<Observer> observerList, Scene scene, String fxml){
        T controller = null;

        try{
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml" + fxml));
            Parent root = loader.load();
            controller = loader.getController();
            ((Observable) controller).addAllObservers(observerList);

            activeController = (GenericSceneController) controller;
            onGoingScene = scene;
            onGoingScene.setRoot(root);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return controller;
    }


    public static <T> T changeRootPane(List<Observer> observerList, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        return changeRootPane(observerList, scene, fxml);
    }

    public static <T> T changeRootPane(List<Observer> observerList, String fxml) {
        return changeRootPane(observerList, onGoingScene, fxml);
    }

    public static void changeRootPane(GenericSceneController controller, Scene scene, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));

            // Setting the controller BEFORE the load() method.
            loader.setController(controller);
            activeController = controller;
            Parent root = loader.load();

            onGoingScene = scene;
            onGoingScene.setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
