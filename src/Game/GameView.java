package Game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;


public class GameView extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
        
    @Override
    public void start(Stage gameStage) {
    	gameStage.setTitle("Quoridor");
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600, Color.WHITE);
        
        int x = 0; //X coordinate of tile
        int y = 0; //Y coordinate of tile
        int posX = 50; //Position of tile in scene
        int posY = 50; //Position of tile in scene
       
        //loop to create a grid of 9x9 tiles
        while(y < 9){
        	Rectangle grid = new Rectangle();
        	grid.setX(posX);
        	grid.setY(posY);
        	grid.setWidth(50);
        	grid.setHeight(50);
        	grid.setFill(Color.RED);
        	

        root.getChildren().add(grid); 
        	x++;
        	posX = posX+60;
        	
        	//if there are 9 tiles in row, go to next row
        	if(x == 9){
        		posX = 50;
        		posY = posY +60;
        		x = 0;
        		y++;
        		
        	}
        }


        
        gameStage.setScene(scene);
        gameStage.show();
    }
}