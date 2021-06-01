package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.gui.scenes.StartSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class JavaFX extends Application {

    protected boolean solo = false;

    @Override
    public void start(Stage stage) {
        List<String> args = getParameters().getRaw();

        if (args.get(0).equals("-local")){
            this.solo=true;
        }

        Gui view = new Gui();
        Client client;
        if(!solo) {
            client = new Client(view);
            client.setGui(true);
            view.setClient(client);
            view.addClientObserver(client);
        } else {
            view.setSolo(true);
            client = new Client(view);
            view.addClientObserver(client);
            client.setSolo(true);
            client.setCli(false);
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/start_scene.fxml"));
        Parent rootLayout = null;
        try{
            rootLayout = loader.load();
        } catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

        StartSceneController controller = loader.getController();
        if (solo) {
            controller.setSolo(true);
            controller.setClient(client);
        }
        controller.addClientObserver(client);

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
        stage.setTitle("Masters of Renaissance");
        stage.show();
    }

    public void exit(){
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
