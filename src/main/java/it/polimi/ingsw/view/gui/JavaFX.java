package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.GameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFX extends Application {

    @Override
    public void start(Stage stage) {
        Gui view = new Gui();
        GameController gameController = new GameController();
        //view.addObserver();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/start_scene.fxml"));
        Parent rootLayout = null;
        try{
            rootLayout = loader.load();
        } catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

        //Scene
        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.setWidth(1280d);
        stage.setHeight(720d);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setTitle("Master of Renaissance");
        stage.show();
    }

    public void exit(){
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}
