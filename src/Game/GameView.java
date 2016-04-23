package Game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.application.Platform;
//import javafx.scene.control.Alert;

public class GameView extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {

		// Set up the images ready to be used
		Image defaultSquareImg = new Image(getClass().getResourceAsStream("BoardComponents/a.png"));
		Image clickedSquareImg = new Image(getClass().getResourceAsStream("BoardComponents/c.png"));
		Image noVerWallImg = new Image(getClass().getResourceAsStream("BoardComponents/noVerWall.png"));
		Image verwallPlacedImg = new Image(getClass().getResourceAsStream("BoardComponents/VerwallPlaced.png"));
		Image noHorWallImg = new Image(getClass().getResourceAsStream("BoardComponents/noHorWall.png"));
		Image horWallPlacedImg = new Image(getClass().getResourceAsStream("BoardComponents/horWallPlaced.png"));
		Image noSquareFenceImg = new Image(getClass().getResourceAsStream("BoardComponents/squareFence.png"));
		Image squareFencePlacedImg = new Image(getClass().getResourceAsStream("BoardComponents/squareFenceClicked.png"));


		// set up a 2D array to store all the components
		ImageView[][] boardComp = new ImageView[17][17];

		// fill boardComp with the board squares
		for (int x = 0; x <= 16; x = x + 2) {
			for (int y = 0; y <= 16; y = y + 2) {
				ImageView gameSquare = new ImageView();
				gameSquare.setImage(defaultSquareImg);
				gameSquare.setOnMouseClicked(e -> {
					gameSquare.setImage(clickedSquareImg);
				});
				boardComp[x][y] = gameSquare;
			}
		}

		// fill boardComp with vertical fences
		for (int x = 1; x <= 16; x = x + 2) {
			for (int y = 0; y <= 16; y = y + 2) {
				ImageView verFences = new ImageView();
				verFences.setImage(noVerWallImg);
				verFences.setOnMouseClicked(e -> {
					verFences.setImage(verwallPlacedImg);
				});
				boardComp[x][y] = verFences;
			}
		}



		// fill boardComp with horizontal fences
		for (int x = 0; x <= 16; x= x+2) {
			for (int y = 1; y < 16; y = y +2) {

				ImageView fences = new ImageView();
				fences.setImage(noHorWallImg);
				fences.setOnMouseClicked(e -> {
					fences.setImage(horWallPlacedImg);
				});
				boardComp[x][y] = fences;
			}
		}

		// fill boardComp with horizontal fences
		for (int x = 1; x <= 16; x= x+2) {
			for (int y = 1; y < 16; y = y +2) {

				ImageView fences = new ImageView();
				fences.setImage(noSquareFenceImg);
				fences.setOnMouseClicked(e -> {
				fences.setImage(squareFencePlacedImg);
				});
				boardComp[x][y] = fences;
			}
		}

		GridPane board = new GridPane();

		// display the board
		for (int x = 0; x <= 16; x++) {
			for (int y = 0; y <= 16; y++) { 

				ImageView toAdd = boardComp[x][y];
				board.add(toAdd, x, y);
			}
		}

		GridPane left = new GridPane();
		GridPane right = new GridPane();
		BorderPane layout = new BorderPane();

		Scene GUI = new Scene(layout, 800, 800);

		primaryStage.setTitle("Quoridor");
		primaryStage.setScene(GUI);
		layout.setLeft(left);
		layout.setCenter(board);		
		layout.setRight(right);
		primaryStage.show();
	}
}