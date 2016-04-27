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
	
	//Images
	private Image defaultSquareImg;
	private Image clickedSquareImg;
	private Image noVerWallImg;
	private Image verWallPlacedImg;
	private Image noHorWallImg;
	private Image horWallPlacedImg;
	private Image noSquareFenceImg;
	private Image squareFencePlacedImg;
	private Image pawnImg;
	private Image buttonPlaceHorImg;
	private Image buttonPlaceVerImg;
	private Image buttonPlaceRemImg;
	private Image pointerImg;
	private Image pointerEmptyImg;
	private Image playerFenceAvlbImg;
	private Image playerFenceUsedImg;
	
	/**Scene of the Gameview*/
	private Scene GUI;
	
	/**Top level layout pane*/
	private BorderPane layout;
	/**Grid pane that stores the images in a 2D grid*/
	private GridPane board = new GridPane();
	/**Left pane that stores the buttons*/
	private VBox left = new VBox();
	/**Right pane that stores the player and turn info*/
	private VBox right = new VBox();
	
	private int boardCompX;
	private int boardCompY;
	/**2D array containing board grid*/
	private ImageView[][] boardComp;
	/**Array containing pawn graphic objects*/
	private ImageView[] pawns;
	private HBox[] players; 
	
	//menu bar and buttons
	private Menu optionMenu;
	private Menu helpMenu;
	private Menu quitMenu;
	private MenuBar menuBar;
	
	//fence buttons on left
	private ImageView buttonPlaceHor;
	private ImageView buttonPlaceVer;
	private ImageView buttonPlaceRem;
	
	//player information 
	private ImageView pointer;
	private ImageView[][] playerFences;
	
	

	@Override
	public void start(final Stage primaryStage) {
		// TODO: remove this when board is initialised properly
		Board.getInstance().setupBoard(false, true);
		
		// Set up the images ready to be used
		setupImages();
		
		// Board, buttons and info player display
		initialiseContent();
		
		// Centre, left and right panes
		initialisePanes();
		
		GUI = new Scene(layout, 1280, 720);
		primaryStage.setTitle("Quoridor");
		primaryStage.setScene(GUI);
		layout.setLeft(left);
		layout.setCenter(board);
		layout.setRight(right);
		layout.setTop(menuBar);
		primaryStage.show();
	}

	private void initialiseContent() {
		// set up a 2D array to store all the components
		boardCompX = Board.getInstance().getSizeX() * 2 - 1;
		boardCompY = Board.getInstance().getSizeY() * 2 - 1;
		boardComp = new ImageView
				[boardCompX]
				[boardCompY];
		
		// fill boardComp with the board squares
		setupBoardComp();

		// place initial pawn on the board
		pawns = new ImageView[Board.getInstance().getNumberOfPawns()];
		addPawnsToBoard();
		
		setupMenu(); // create menu options and add them to the bar
		
		setupButtons(); // display wall buttons

		//pointer
		pointer = new ImageView();
		pointer.setImage(pointerImg);
		//player information
		players = new HBox[Board.getInstance().getNumberOfPawns()];
		playerFences = new ImageView[players.length][Board.getInstance().getMaxPawnFences()];
		setupPlayerHBoxes();
		players[0].getChildren().set(0, pointer);
	}

	private void initialisePanes() {
		layout = new BorderPane();
		board = new GridPane();
		left = new VBox();
		right = new VBox();

		//add to board pane
		for (int x = 0; x < boardCompX; x++) {
			for (int y = 0; y < boardCompY; y++) {
				ImageView toAdd = boardComp[x][y]; //add boardComp images to the board pane
				board.add(toAdd, x, y);
			}
		}
		//add to left pane
		left.getChildren().addAll(buttonPlaceHor, buttonPlaceVer, buttonPlaceRem);
		//add to right pane
		right.getChildren().addAll(players);


		board.setStyle("-fx-padding:10;");
		left.setStyle("-fx-padding:10;");
		right.setAlignment(Pos.CENTER_RIGHT);
		right.setStyle("-fx-padding:10;");
	}

	private void setupPlayerHBoxes() {
		for(int i = 0; i < players.length; i++) {
			Label playerID = new Label("P" + (i + 1));
			Label playerFenceCount = new Label(Board.getInstance().getMaxPawnFences() + "");
			
			players[i] = new HBox();
			players[i].getChildren().add(createEmptyPointer());
			players[i].getChildren().add(playerID);
			for(int j = 0; j < Board.getInstance().getMaxPawnFences(); j++)
			{
				playerFences[i][j] = new ImageView();
				playerFences[i][j].setImage(playerFenceAvlbImg);
				players[i].getChildren().add(playerFences[i][j]);
			}
			players[i].getChildren().add(playerFenceCount);
		}
	}

	private void setupButtons() {
		buttonPlaceHor = new ImageView();
		buttonPlaceHor.setImage(buttonPlaceHorImg);
		buttonPlaceHor.setOnMouseClicked(e -> {
			eventPlaceFenceHorizontal();
		});
		
		buttonPlaceVer = new ImageView();
		buttonPlaceVer.setImage(buttonPlaceVerImg);
		buttonPlaceVer.setOnMouseClicked(e -> {
			eventPlaceFenceVertical();
		});
		
		buttonPlaceRem = new ImageView();
		buttonPlaceRem.setImage(buttonPlaceRemImg);
		buttonPlaceRem.setOnMouseClicked(e -> {
			eventRemoveFence();
		});
	}

	/**
	 * Adds pawns to the board from positions provided by the Board instance
	 */
	private void addPawnsToBoard() {
		Position[] pawnPositions = Board.getInstance().getPawnPositionsArray();
		for (int i = 0; i < pawnPositions.length; i++) {
			pawns[i] = new ImageView();
			pawns[i].setImage(pawnImg);
			//TODO: tint the pawn to its colour
			boardComp[getBoardSquarePosition(pawnPositions[i].getX())]
					[getBoardSquarePosition(pawnPositions[i].getY())] = pawns[i];
			board.add(pawns[i], 
					getBoardSquarePosition(pawnPositions[i].getX()), 
							getBoardSquarePosition(pawnPositions[i].getY()));
		}
	}

	/**
	 * Removes all pawn images from the board and replaces them with empty tiles
	 */
	private void clearPawnsFromBoard() {
		for (int x = 0; x < boardCompX; x = x + 2) {
			for (int y = 0; y < boardCompY; y = y + 2) {
				if(boardComp[x][y].getImage() == pawnImg)
				{
					ImageView gameSquare = createGameSquare();
					boardComp[x][y] = gameSquare;
					board.add(gameSquare, x, y);
				}
			}
		}
	}
	
	private void addFencesToBoard() {
		Fence[] fences = Board.getInstance().getFencesArray();
		for(Fence f : fences)
		{
			Position fencePos = f.getPosition();
			int baseX = getBoardSquarePosition(fencePos.getX());
			int baseY = getBoardSquarePosition(fencePos.getY());
			int length = f.getLength();
			if(f.getOrientation()) {
				baseX--;
				for(int i = 0; i < length * 2 - 1; i++)
				{
					ImageView image = boardComp[baseX][baseY + i];
					if (i % 2 == 1) {
						image.setImage(squareFencePlacedImg);
					} else{
						image.setImage(verWallPlacedImg);
					}
				}
			} 
			else 
			{
				baseY--;
				for(int i = 0; i < length * 2 - 1; i++)
				{
					ImageView image = boardComp[baseX + i][baseY];
					if (i % 2 == 1) {
						image.setImage(squareFencePlacedImg);
					} else{
						image.setImage(horWallPlacedImg);
					}
				}
			}
		}
	}
	
	private void clearFencesFromBoard() {
		for (int x = 0; x < boardCompX; x++) {
			for (int y = 0; y < boardCompY; y++) {
				if (x % 2 == 1 || y % 2 == 1) {
					if (x % 2 == 0) {
						//boardComp[x][y].setImage(horWallPlacedImg);
						boardComp[x][y].setImage(noHorWallImg);
					}
					else if (y % 2 == 0) {
						//boardComp[x][y].setImage(verWallPlacedImg);
						boardComp[x][y].setImage(noVerWallImg);
					}
					else {
						//boardComp[x][y].setImage(squareFencePlacedImg);
						boardComp[x][y].setImage(noSquareFenceImg);
					}
				}
				
			}
		}
	}
	
	private void setupMenu() {
		optionMenu = new Menu("Options");
		helpMenu = new Menu("Help");
		quitMenu = new Menu("Quit");
		menuBar = new MenuBar();
		menuBar.getMenus().addAll(optionMenu, helpMenu, quitMenu);
		/*
		quit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});
		*/
	}

	private void setupBoardComp() {
		for (int x = 0; x < boardCompX; x = x + 2) {
			for (int y = 0; y < boardCompY; y = y + 2) {
				ImageView gameSquare = createGameSquare();
				boardComp[x][y] = gameSquare;
			}
		}
		// fill boardComp with vertical fences
		for (int x = 1; x < boardCompX; x = x + 2) {
			for (int y = 0; y < boardCompY; y = y + 2) {
				ImageView verFences = createVerticalFence(x, y);
				boardComp[x][y] = verFences;
			}
		}
		// fill boardComp with horizontal fences
		for (int x = 0; x < boardCompX; x = x + 2) {
			for (int y = 1; y < boardCompY; y = y + 2) {

				ImageView fences = createHorizontalFence(x, y);
				boardComp[x][y] = fences;
			}
		}
		// fill boardComp with square fences
		for (int x = 1; x < boardCompX; x = x + 2) {
			for (int y = 1; y < boardCompY; y = y + 2) {

				ImageView fences = createMidFence(x, y);
				boardComp[x][y] = fences;
			}
		}
	}

	private ImageView createMidFence(int x, int y) {
		ImageView fences = new ImageView();
		fences.setImage(noSquareFenceImg);
		fences.setOnMouseClicked(e -> {
			eventFenceClick(x, y);
		});
		return fences;
	}

	private ImageView createHorizontalFence(int x, int y) {
		ImageView fences = new ImageView();
		fences.setImage(noHorWallImg);
		fences.setOnMouseClicked(e -> {
			eventFenceClick(x, y);
		});
		return fences;
	}

	private ImageView createVerticalFence(int x, int y) {
		ImageView verFences = new ImageView();
		verFences.setImage(noVerWallImg);
		verFences.setOnMouseClicked(e -> {
			eventFenceClick(x, y);
		});
		return verFences;
	}

	private ImageView createGameSquare() {
		ImageView gameSquare = new ImageView();
		gameSquare.setImage(defaultSquareImg);
		gameSquare.setOnMouseClicked(e -> {
			gameSquare.setImage(clickedSquareImg);
		});
		return gameSquare;
	}

	private ImageView createEmptyPointer() {
		ImageView pointerEmpty = new ImageView();
		pointerEmpty.setImage(pointerEmptyImg);
		return pointerEmpty;
	}
	
	private void setupImages() {
		defaultSquareImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/a.png"));
		clickedSquareImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/c.png"));
		noVerWallImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/noVerWall.png"));
		verWallPlacedImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/VerwallPlaced.png"));
		noHorWallImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/noHorWall.png"));
		horWallPlacedImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/horWallPlaced.png"));
		noSquareFenceImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/squareFence.png"));
		squareFencePlacedImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/squareFenceClicked.png"));
		pawnImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/pawn.png"));
		buttonPlaceHorImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/buttonPlaceFenceH.png"));
		buttonPlaceVerImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/buttonPlaceFenceV.png"));
		buttonPlaceRemImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/buttonPlaceFenceR.png"));
		pointerImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/pointer.png"));
		pointerEmptyImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/pointer_empty.png"));
		playerFenceAvlbImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/fenceAvailable.png"));
		playerFenceUsedImg = new Image(getClass().getResourceAsStream(
				"BoardComponents/fenceUsed.png"));
	}

	/**
	 * Converts the position from the model to the view
	 * @param boardPos
	 * @return
	 */
	private static int getBoardSquarePosition(int boardPos) {
		return boardPos * 2;
	}
	/**
	 * Converts the position from the view to the model
	 * @param boardPos
	 * @return
	 */
	private static int getBoardModelPosition(int boardPos) {
		return boardPos / 2;
	}
	
	/**Called when the place horizontal fence button is pressed*/
	private void eventPlaceFenceHorizontal()
	{
		//TODO: Demo only, remove this
		Board.getInstance().pawnMove(new Position(1,8));
		updatePawnsOnBoard();
		updatePlayerInfo();
		//TODO: Put proper code in here
	}
	/**Called when the place vertical fence button is pressed*/
	private void eventPlaceFenceVertical()
	{
		//TODO: Demo only, remove this
		Board.getInstance().pawnPlaceFence(new Position(1,1), true);
		updateFencesOnBoard();
		updatePlayerInfo();
		//TODO: Put proper code in here
	}
	/**Called when the remove fence button is pressed*/
	private void eventRemoveFence()
	{
		//TODO: Put proper code in here
	}
	
	private void eventFenceClick(int x, int y)
	{
		//TODO: Demo only, remove this
		System.out.println("Clicked at :" + x + "|" + y);
		//TODO: Put proper code in here
	}
	
	/**
	 * Updates the player info to match the model
	 */
	public void updatePlayerInfo()
	{
		int currentPawnT = Board.getInstance().getPawnTurn();
		int lastPawnT = Board.getInstance().getPreviousPawnTurn();
		//run on last pawn
		for(int i = 0; i < players.length; i++)
		{
			HBox pBox = players[i];
			if(i == lastPawnT) pBox.getChildren().set(0, createEmptyPointer());
		}
		//run on current pawn
		for(int i = 0; i < players.length; i++)
		{
			HBox pBox = players[i];
			if(i == currentPawnT) pBox.getChildren().set(0, pointer);
		}
	}
	
	/**
	 * Updates the pawn's positions on the board.
	 * Clears all pawns from the board, then re-adds them with their current positions.
	 */
	public void updatePawnsOnBoard()
	{
		clearPawnsFromBoard();
		addPawnsToBoard();
	}
	
	/**
	 * Updates the fence's positions on the board.
	 */
	public void updateFencesOnBoard()
	{
		clearFencesFromBoard();
		addFencesToBoard();
	}
	
	
	
	

}