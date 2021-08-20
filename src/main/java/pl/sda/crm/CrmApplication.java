package pl.sda.crm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CrmApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final var fxmlLoader = new FXMLLoader(CrmApplication.class
                .getClassLoader()
                .getResource("start-view.fxml"));
        final var scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("CRM System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}