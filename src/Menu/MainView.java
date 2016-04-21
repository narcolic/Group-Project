package Menu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.application.Platform;
//import javafx.scene.control.Alert;

public class MainView extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public Button start;
    public Button options;
    public Button help;
    public Button quit;

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Main Menu");

        //create the start button
        start = new Button();
        start.setText("Start");
        start.setMaxWidth(Double.MAX_VALUE);
        
        //create the options button
        options = new Button();
        options.setText("Options");
        options.setMaxWidth(Double.MAX_VALUE);
        
        //create the Help button
        help = new Button();
        help.setText("Help");
        help.setMaxWidth(Double.MAX_VALUE);
        
        //create the Quit button
        quit = new Button();
        quit.setText("Quit");
        quit.setMaxWidth(Double.MAX_VALUE);

        
        //set order of buttons
        GridPane root = new GridPane();
        GridPane.setRowIndex(start,1);
        GridPane.setRowIndex(options,2);
        GridPane.setRowIndex(help,3);
        GridPane.setRowIndex(quit,4);

        //add buttons to menu
        root.getChildren().add(start);
        root.getChildren().add(options);
        root.getChildren().add(help);
        root.getChildren().add(quit);
        root.setAlignment(Pos.CENTER);
        
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}