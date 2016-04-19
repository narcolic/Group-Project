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
import javafx.scene.control.Alert;

public class MainMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Menu");

        //create the start button
        Button start = new Button();
        start.setText("Start");
        start.setMaxWidth(Double.MAX_VALUE);
        start.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Start");
            }
        });
        
        
        //create the options button
        Button options = new Button();
        options.setText("Options");
        options.setMaxWidth(Double.MAX_VALUE);
        options.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Options");
            }
        });
        
        //create the Help button
        Button help = new Button();
        help.setText("Help");
        help.setMaxWidth(Double.MAX_VALUE);
        help.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Help");
            }
        });
        
        
        //create the Quit button
        Button quit = new Button();
        quit.setText("Quit");
        quit.setMaxWidth(Double.MAX_VALUE);
        quit.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Quit");
                primaryStage.close()  ;      
                }
        });
        
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