package Game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.application.Platform;
import Game.Board;
import Game.Position;
import Game.Fence;

//import javafx.scene.control.Alert;

public class GameView extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {
		// TODO: remove this when board is initliased properly
		Board.getInstance().setupBoard(false, false);

		// Set up the images ready to be used
		Image defaultSquareImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/a.png"));
		Image clickedSquareImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/c.png"));
		Image noVerWallImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/noVerWall.png"));
		Image verwallPlacedImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/VerwallPlaced.png"));
		Image noHorWallImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/noHorWall.png"));
		Image horWallPlacedImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/horWallPlaced.png"));
		Image noSquareFenceImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/squareFence.png"));
		Image squareFencePlacedImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/squareFenceClicked.png"));
		Image pawnImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/pawn.png"));
		Image buttonPlaceHorImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/buttonPlaceFenceH.png"));
		Image buttonPlaceVerImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/buttonPlaceFenceV.png"));
		Image buttonPlaceRemImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/buttonPlaceFenceR.png"));
		Image pointerImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/pointer.png"));
		Image playerFenceAvlbImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/fenceAvailable.png"));
		Image playerFenceUsedImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/fenceUsed.png"));

		// set up a 2D array to store all the components
		ImageView[][] boardComp = new ImageView[Board.getInstance().getSizeX() * 2 - 1][Board
				.getInstance().getSizeY() * 2 - 1];
		ImageView[] pawns = new ImageView[Board.getInstance().numberOfPawns()];

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
		for (int x = 0; x <= 16; x = x + 2) {
			for (int y = 1; y < 16; y = y + 2) {

				ImageView fences = new ImageView();
				fences.setImage(noHorWallImg);
				fences.setOnMouseClicked(e -> {
					fences.setImage(horWallPlacedImg);
				});
				boardComp[x][y] = fences;
			}
		}

		// fill boardComp with square fences
		for (int x = 1; x <= 16; x = x + 2) {
			for (int y = 1; y < 16; y = y + 2) {

				ImageView fences = new ImageView();
				fences.setImage(noSquareFenceImg);
				fences.setOnMouseClicked(e -> {
					fences.setImage(squareFencePlacedImg);
				});
				boardComp[x][y] = fences;
			}
		}

		// fill the board with pawns in there appropriate places
		Position[] pawnPositions = Board.getInstance().getPawnPositionsArray();
		for (Position p : pawnPositions) {
			ImageView pawn = new ImageView();
			pawn.setImage(pawnImg);
			boardComp[getBoardSquarePosition(p.getX())][getBoardSquarePosition(p
					.getY())] = pawn;
		}

		GridPane board = new GridPane();

		// display the board
		for (int x = 0; x <= 16; x++) {
			for (int y = 0; y <= 16; y++) {

				ImageView toAdd = boardComp[x][y];
				board.add(toAdd, x, y);
			}
		}

		// menu
		Menu menu1 = new Menu("File");
		Menu quit = new Menu("Quit");
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(menu1);
		menuBar.getMenus().add(quit);
		quit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});

		// display wall buttons
		ImageView buttonPlaceHor = new ImageView();
		buttonPlaceHor.setImage(buttonPlaceHorImg);
		
		ImageView buttonPlaceVer = new ImageView();
		buttonPlaceVer.setImage(buttonPlaceVerImg);
		
		ImageView buttonPlaceRem = new ImageView();
		buttonPlaceRem.setImage(buttonPlaceRemImg);

		VBox left = new VBox();
		VBox right = new VBox();
		BorderPane layout = new BorderPane();
		
		left.getChildren().addAll(buttonPlaceHor, buttonPlaceVer, buttonPlaceRem);
		left.setStyle("-fx-padding:10;");


		board.setStyle("-fx-padding:10;");

		Scene GUI = new Scene(layout, 800, 800);
		
		//player information
		HBox player = new HBox();
		
		ImageView pointer = new ImageView();
		pointer.setImage(pointerImg);
		Label playerID = new Label("P1");
		ImageView[] playerFences = new ImageView[Board.getInstance().maxPawnFences()];
		for(int i = 0; i < playerFences.length; i++)
		{
			playerFences[i] = new ImageView();
			playerFences[i].setImage(playerFenceAvlbImg);
		}
		Label playerFenceCount = new Label("10");
		
		
		
		player.getChildren().addAll(pointer, playerID);
		player.getChildren().addAll(playerFences);
		player.getChildren().add(playerFenceCount);
		right.getChildren().addAll(player);

		

		primaryStage.setTitle("Quoridor");
		primaryStage.setScene(GUI);
		layout.setLeft(left);
		layout.setCenter(board);
		layout.setRight(right);
		layout.setTop(menuBar);
		primaryStage.show();
	}

	public int getBoardSquarePosition(int boardPos) {
		return boardPos * 2;
	}
}