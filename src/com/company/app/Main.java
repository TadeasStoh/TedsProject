package com.company.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
        while(1<2) {
            for (int i = 0; i < 1000000; i++) {
                System.out.println(i);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader franta = new FXMLLoader(Main.class.getResource("../view/fxml.fxml"));
        Parent ludek=franta.load();
        stage.setTitle("clovece");
        stage.setScene(new Scene(ludek));
        stage.show();
    }
}
