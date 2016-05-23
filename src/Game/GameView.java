package Game;

import Menu.MenuView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.util.Optional;

public class GameView extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    //Drag variables
    private int offsetX;
    private int offsetY;

    //Images
    private Image defaultSquareImg;
    private Image highlightSquareImg;
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
    private Image buttonPlaceHorImgOn;
    private Image buttonPlaceVerImgOn;
    private Image buttonPlaceRemImgOn;
    private Image pointerImg;
    private Image pointerEmptyImg;
    private Image playerFenceAvlbImg;
    private Image playerFenceUsedImg;

    private boolean ignoreMouse;
    private boolean stageClosedBoolean = false;

    private Stage stage;

    /**
     * Top level layout pane
     */
    private BorderPane layout;
    /**
     * Grid pane that stores the images in a 2D grid
     */
    private GridPane board = new GridPane();
    /**
     * Left pane that stores the buttons
     */
    private VBox left = new VBox();
    /**
     * Right pane that stores the player and turn info
     */
    private VBox right = new VBox();

    private int boardCompX;
    private int boardCompY;
    /**
     * 2D array containing board grid
     */
    private ImageView[][] boardComp;
    /**
     * Array containing pawn graphic objects
     */
    private ImageView[] pawns;
    private TilePane[] players;

    private ToolBar toolBar;

    //fence buttons on left
    private ImageView buttonPlaceHor;
    private ImageView buttonPlaceVer;
    private ImageView buttonPlaceRem;

    private enum ButtonState {NONE, PLACE_HORIZONTAL, PLACE_VERTICAL, REMOVE_FENCE}

    private ButtonState activeButton;

    //player information
    private ImageView pointer;
    private ImageView[][] playerFences;


    @Override
    public void start(final Stage primaryStage) {

        this.stage = primaryStage;

        stage.initStyle(StageStyle.UNDECORATED);
        //stage.setFullScreen(true);

        // Set up the images ready to be used
        setupImages();

        // Board, buttons and info player display
        initialiseContent();

        // Centre, left and right panes
        initialisePanes();

		/*Scene of the Gameview*/
        Scene GUI = new Scene(layout, 1280, 720);
        GUI.getStylesheets().add(getClass().getResource("custom-game-styles.css").toExternalForm());
        stage.setTitle("Quoridor");
        stage.setScene(GUI);
        layout.setLeft(left);
        layout.setCenter(board);
        layout.setRight(right);
        layout.setTop(toolBar);
        stage.getIcons().add(new Image("/Menu/Images/quoridorIcon.jpg"));
        stage.show();

        ignoreMouse = false;
    }

    private void setupImages() {
        defaultSquareImg = new Image(getClass().getResourceAsStream(
                "BoardComponents/a.png"));
        highlightSquareImg = new Image(getClass().getResourceAsStream(
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
        buttonPlaceHorImgOn = new Image(getClass().getResourceAsStream(
                "BoardComponents/buttonPlaceFenceHActive.png"));
        buttonPlaceVerImgOn = new Image(getClass().getResourceAsStream(
                "BoardComponents/buttonPlaceFenceVActive.png"));
        buttonPlaceRemImgOn = new Image(getClass().getResourceAsStream(
                "BoardComponents/buttonPlaceFenceRActive.png"));
        pointerImg = new Image(getClass().getResourceAsStream(
                "BoardComponents/pointer.png"));
        pointerEmptyImg = new Image(getClass().getResourceAsStream(
                "BoardComponents/pointer_empty.png"));
        playerFenceAvlbImg = new Image(getClass().getResourceAsStream(
                "BoardComponents/fenceAvailable.png"));
        playerFenceUsedImg = new Image(getClass().getResourceAsStream(
                "BoardComponents/fenceUsed.png"));
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
        players = new TilePane[Board.getInstance().getNumberOfPawns()];
        playerFences = new ImageView[players.length][Board.getInstance().getMaxPawnFences()];
        setupPlayerHBoxes();
        players[0].getChildren().set(0, pointer);

        updateAll();
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
        left.setSpacing(20);
        left.getChildren().addAll(buttonPlaceHor, buttonPlaceVer);
        if (Board.getInstance().isChallengeMode()) {
            left.getChildren().addAll(buttonPlaceRem);
        }
        //add to right pane
        right.getChildren().addAll(players);


        board.getStyleClass().add("background");
        left.getStyleClass().add("background");
        right.setAlignment(Pos.CENTER_RIGHT);
        right.getStyleClass().add("background");
    }

    private void setupPlayerHBoxes() {
        for (int i = 0; i < players.length; i++) {
            Label playerID = new Label("Player " + (i + 1));
            playerID.getStyleClass().add("playerID");
            playerID.setTextFill(getPawnColour(i));

            players[i] = new TilePane();
            players[i].getChildren().add(createEmptyPointer());
            players[i].getChildren().add(playerID);

            for (int j = 0; j < Board.getInstance().getMaxPawnFences(); j++) {
                playerFences[i][j] = new ImageView();
                playerFences[i][j].setImage(playerFenceAvlbImg);
                players[i].getChildren().add(playerFences[i][j]);
            }
            players[i].setMinHeight(150);
        }
    }

    private void setupButtons() {
        buttonPlaceHor = new ImageView();
        buttonPlaceHor.setImage(buttonPlaceHorImg);
        buttonPlaceHor.setOnMouseClicked(e -> eventPlaceFenceHorizontal());

        buttonPlaceVer = new ImageView();
        buttonPlaceVer.setImage(buttonPlaceVerImg);
        buttonPlaceVer.setOnMouseClicked(e -> eventPlaceFenceVertical());

        buttonPlaceRem = new ImageView();
        buttonPlaceRem.setImage(buttonPlaceRemImg);
        buttonPlaceRem.setOnMouseClicked(e -> eventRemoveFence());

        activeButton = ButtonState.NONE;
    }

    private void setupMenu() {
        toolBar = new ToolBar();

        Button quitMenu = new Button();
        quitMenu.setMaxWidth(Double.MAX_VALUE);
        quitMenu.setStyle("-fx-background-image: url('/Menu/Images/Icons/quit.png')");
        quitMenu.getStyleClass().add("button");
        quitMenu.setOnAction(event -> quitGameAction());

        Button helpMenu = new Button();
        helpMenu.setMaxWidth(Double.MAX_VALUE);
        helpMenu.setStyle("-fx-background-image: url('/Menu/Images/Icons/help.png')");
        helpMenu.getStyleClass().add("button");
        helpMenu.setOnAction(event -> helpAction());

        Button optionsMenu = new Button();
        optionsMenu.setMaxWidth(Double.MAX_VALUE);
        optionsMenu.setStyle("-fx-background-image: url('/Menu/Images/Icons/options.png')");
        optionsMenu.getStyleClass().add("button");
        optionsMenu.setOnAction(event -> optionsAction());

        toolBar.getItems().addAll(optionsMenu, helpMenu, quitMenu);
        //toolBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        toolBar.setMinHeight(60);
        toolBar.getStyleClass().add("tool-bar:horizontal");
    }

    private void optionsAction() {
        MenuView.setMenuFlag("Options");
        hideGameVIew();
    }

    private void helpAction() {
        MenuView.setMenuFlag("Help");
        hideGameVIew();
    }

    public void showGameView() {
        stage.show();
        stageClosedBoolean = false;
        MenuView.setFlag(false);
    }

    private void hideGameVIew() {
        Platform.setImplicitExit(false);
        stage.hide();
        stageClosedBoolean = true;
        MenuView.setFlag(true);
        Platform.setImplicitExit(true);
    }

    private void quitGameAction() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Quoridor");
        alert.setHeaderText("Quit Quoridor");
        alert.setContentText("Are you sure you want to quit Quoridor?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // close Quoridor
            MenuView.setMenuFlag("Menu");
            closeGameView();
        }
    }

    private void closeGameView() {
        Platform.setImplicitExit(false);
        stage.close();
        stageClosedBoolean = true;
        MenuView.setFlag(true);
        Platform.setImplicitExit(true);
    }

    private void toolBarMousePress(MouseEvent e) {

    }

    private void toolBarMouseDragged(MouseEvent e) {

    }

    private void setupBoardComp() {
        for (int x = 0; x < boardCompX; x = x + 2) {
            for (int y = 0; y < boardCompY; y = y + 2) {
                ImageView gameSquare = createGameSquare(x, y);
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
        fences.setOnMouseClicked(e -> eventFenceClick(x, y));
        return fences;
    }

    private ImageView createHorizontalFence(int x, int y) {
        ImageView fences = new ImageView();
        fences.setImage(noHorWallImg);
        fences.setOnMouseClicked(e -> eventFenceClick(x, y));
        return fences;
    }

    private ImageView createVerticalFence(int x, int y) {
        ImageView verFences = new ImageView();
        verFences.setImage(noVerWallImg);
        verFences.setOnMouseClicked(e -> eventFenceClick(x, y));
        return verFences;
    }

    private ImageView createGameSquare(int x, int y) {
        ImageView gameSquare = new ImageView();
        gameSquare.setImage(defaultSquareImg);
        gameSquare.setOnMouseClicked(e -> eventSquareClick(x, y));
        return gameSquare;
    }

    private ImageView createEmptyPointer() {
        ImageView pointerEmpty = new ImageView();
        pointerEmpty.setImage(pointerEmptyImg);
        return pointerEmpty;
    }

    /**
     * Converts the position from the model to the view
     *
     * @param boardPos
     * @return
     */
    private static int getBoardSquarePosition(int boardPos) {
        return boardPos * 2;
    }

    /**
     * Converts the position from the view to the model
     *
     * @param boardPos
     * @return
     */
    private static int getBoardModelPosition(int boardPos) {
        return boardPos / 2;
    }

    /**
     * Called when the place horizontal fence button is pressed
     */
    private void eventPlaceFenceHorizontal() {
        if (ignoreMouse) return;

        if (activeButton != ButtonState.PLACE_HORIZONTAL) {
            activeButton = ButtonState.PLACE_HORIZONTAL;
        } else {
            activeButton = ButtonState.NONE;
        }
        updateButtons();
    }

    /**
     * Called when the place vertical fence button is pressed
     */
    private void eventPlaceFenceVertical() {
        if (ignoreMouse) return;

        if (activeButton != ButtonState.PLACE_VERTICAL) {
            activeButton = ButtonState.PLACE_VERTICAL;
        } else {
            activeButton = ButtonState.NONE;
        }
        updateButtons();
    }

    /**
     * Called when the remove fence button is pressed
     */
    private void eventRemoveFence() {
        if (ignoreMouse) return;

        if (activeButton != ButtonState.REMOVE_FENCE) {
            activeButton = ButtonState.REMOVE_FENCE;
        } else {
            activeButton = ButtonState.NONE;
        }
        updateButtons();
    }


    private void eventFenceClick(int x, int y) {
        if (ignoreMouse) return;
        int boardX = getBoardModelPosition(x);
        int boardY = getBoardModelPosition(y);

        if (activeButton == ButtonState.PLACE_HORIZONTAL) {
            if (Board.getInstance().pawnPlaceFence(new Position(boardX, boardY + 1), false)) {
                updateAll();
            }
        } else if (activeButton == ButtonState.PLACE_VERTICAL) {
            if (Board.getInstance().pawnPlaceFence(new Position(boardX + 1, boardY), true)) {
                updateAll();
            }
        } else if (activeButton == ButtonState.REMOVE_FENCE) {
            if (Board.getInstance().pawnRemoveFence(boardX, boardY, determineFenceOrientation(x, y))) {
                updateAll();
            }
        }
    }

    private void eventSquareClick(int x, int y) {
        if (ignoreMouse) return;

        int boardX = getBoardModelPosition(x);
        int boardY = getBoardModelPosition(y);

        if (Board.getInstance().pawnMove(new Position(boardX, boardY))) {
            updateAll();
        }
    }


    /**
     * Run all updates in approximate order of importance.
     */
    private void updateAll() {
        updatePawnsOnBoard();
        updateFencesOnBoard();
        updateBoardHighlights();
        updatePlayerInfo();
        activeButton = ButtonState.NONE;
        updateButtons();

        checkPlayerWon();
    }

    /**
     * Updates the player info to match the model
     * Should run after the end of each turn
     */
    private void updatePlayerInfo() {
        int currentPawnT = Board.getInstance().getPawnTurn();
        int lastPawnT = Board.getInstance().getPreviousPawnTurn();
        //Remove pointer from previous pawn
        players[lastPawnT].getChildren().set(0, createEmptyPointer());
        //Add pointer to current pawn
        players[currentPawnT].getChildren().set(0, pointer);

        //update the fences and fenceCount of each player
        int[] fenceCount = Board.getInstance().getPawnFenceCountArray();
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < Board.getInstance().getMaxPawnFences(); j++) {
                ImageView fence = playerFences[i][j];
                if (j < Board.getInstance().getMaxPawnFences() - fenceCount[i]) {
                    fence.setImage(playerFenceAvlbImg);
                } else {
                    fence.setImage(playerFenceUsedImg);
                }
            }
        }
    }

    /**
     * Updates the pawn's positions on the board.
     * Clears all pawns from the board, then re-adds them with their current positions.
     */
    private void updatePawnsOnBoard() {
        clearPawnsFromBoard();
        addPawnsToBoard();
    }

    /**
     * Updates the fence's positions on the board.
     */
    private void updateFencesOnBoard() {
        clearFencesFromBoard();
        addFencesToBoard();
    }

    /**
     * Updates the valid moves of the current pawn as highlights on the board.
     */
    private void updateBoardHighlights() {
        resetSquareImages();
        highlightSquareImages();
    }

    private void updateButtons() {
        buttonPlaceHor.setImage(buttonPlaceHorImg);
        buttonPlaceVer.setImage(buttonPlaceVerImg);
        buttonPlaceRem.setImage(buttonPlaceRemImg);
        switch (activeButton) {
            case PLACE_HORIZONTAL:
                buttonPlaceHor.setImage(buttonPlaceHorImgOn);
                break;
            case PLACE_VERTICAL:
                buttonPlaceVer.setImage(buttonPlaceVerImgOn);
                break;
            case REMOVE_FENCE:
                buttonPlaceRem.setImage(buttonPlaceRemImgOn);
                break;
        }
    }

    /**
     * @param x The board x Position
     * @param y The board y Position
     * @return 0 for Horizontal, 1 for Vertical, 2 for Square (unknown)
     */
    private int determineFenceOrientation(int x, int y) {
        if (x % 2 == 0) return 0; //Only horizontal parts of a fence lie on this position
        if (y % 2 == 0) return 1; //Only vertical parts of a fence lie on this position
        //Past this point, player has clicked on a square part of the fence.
        return 2;
    }

    /**
     * Adds pawns to the board from positions provided by the Board instance
     */
    private void addPawnsToBoard() {
        Position[] pawnPositions = Board.getInstance().getPawnPositionsArray();
        for (int i = 0; i < pawnPositions.length; i++) {
            pawns[i] = new ImageView();
            pawns[i].setImage(pawnImg);

            Blend tint = new Blend(BlendMode.MULTIPLY,
                    new ColorInput(0, 0, pawns[i].getImage().getWidth(), pawns[i].getImage().getHeight(),
                            getPawnColour(i)), null);
            pawns[i].setEffect(tint);

            boardComp[getBoardSquarePosition(pawnPositions[i].getX())]
                    [getBoardSquarePosition(pawnPositions[i].getY())] = pawns[i];
            board.add(pawns[i],
                    getBoardSquarePosition(pawnPositions[i].getX()),
                    getBoardSquarePosition(pawnPositions[i].getY()));
        }
    }

    private Paint getPawnColour(int i) {
        switch (i) {
            case 0:
                return Color.RED;
            case 1:
                return Color.ROYALBLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.GOLD;
        }
        return Color.WHITE;
    }

    /**
     * Removes all pawn images from the board and replaces them with empty tiles
     */
    private void clearPawnsFromBoard() {
        for (int x = 0; x < boardCompX; x = x + 2) {
            for (int y = 0; y < boardCompY; y = y + 2) {
                if (boardComp[x][y].getImage() == pawnImg) {
                    ImageView gameSquare = createGameSquare(x, y);
                    boardComp[x][y] = gameSquare;
                    board.add(gameSquare, x, y);
                }
            }
        }
    }

    private void addFencesToBoard() {
        Fence[] fences = Board.getInstance().getFencesArray();
        for (Fence f : fences) {
            Position fencePos = f.getPosition();
            int baseX = getBoardSquarePosition(fencePos.getX());
            int baseY = getBoardSquarePosition(fencePos.getY());
            int length = f.getLength();
            if (f.getOrientation()) {
                baseX--;
                for (int i = 0; i < length * 2 - 1; i++) {
                    ImageView image = boardComp[baseX][baseY + i];
                    if (i % 2 == 1) {
                        image.setImage(squareFencePlacedImg);
                    } else {
                        image.setImage(verWallPlacedImg);
                    }
                    Blend tint = new Blend(BlendMode.MULTIPLY,
                            new ColorInput(0, 0, image.getImage().getWidth(), image.getImage().getHeight(),
                                    getPawnColour(Board.getInstance().getFenceOwnerID(f))), null);
                    image.setEffect(tint);
                }
            } else {
                baseY--;
                for (int i = 0; i < length * 2 - 1; i++) {
                    ImageView image = boardComp[baseX + i][baseY];
                    if (i % 2 == 1) {
                        image.setImage(squareFencePlacedImg);
                    } else {
                        image.setImage(horWallPlacedImg);
                    }
                    Blend tint = new Blend(BlendMode.MULTIPLY,
                            new ColorInput(0, 0, image.getImage().getWidth(), image.getImage().getHeight(),
                                    getPawnColour(Board.getInstance().getFenceOwnerID(f))), null);
                    image.setEffect(tint);
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
                        boardComp[x][y].setEffect(null);
                    } else if (y % 2 == 0) {
                        //boardComp[x][y].setImage(verWallPlacedImg);
                        boardComp[x][y].setImage(noVerWallImg);
                        boardComp[x][y].setEffect(null);
                    } else {
                        //boardComp[x][y].setImage(squareFencePlacedImg);
                        boardComp[x][y].setImage(noSquareFenceImg);
                        boardComp[x][y].setEffect(null);
                    }
                }

            }
        }
    }

    private void resetSquareImages() {
        for (int x = 0; x < boardCompX; x = x + 2) {
            for (int y = 0; y < boardCompY; y = y + 2) {
                if (boardComp[x][y].getImage() != pawnImg) {
                    boardComp[x][y].setImage(defaultSquareImg);
                }
            }
        }
    }

    private void highlightSquareImages() {
        Position[] highlights = Board.getInstance().getValidPositionArray();
        for (Position square : highlights) {
            boardComp[getBoardSquarePosition(square.getX())]
                    [getBoardSquarePosition(square.getY())]
                    .setImage(highlightSquareImg);
        }
    }

    private void checkPlayerWon() {
        if (Board.getInstance().getCurrentPawn().isOnGoalTile()) {
            ignoreMouse = true;

            Button backB = new Button();
            backB.setMaxWidth(Double.MAX_VALUE);
            backB.setStyle("-fx-background-image: url('/Menu/Images/Icons/backBTN.png')");
            backB.getStyleClass().add("button");
            backB.setMinSize(120, 120);
            backB.setOnAction(event -> closeGameView());

            final Label label = new Label("Player " + (Board.getInstance().getPawnTurn() + 1) + " wins!");
            label.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold; -fx-padding: 0 0 20 0;");

            GridPane glass = new GridPane();
            GridPane.setConstraints(backB, 0, 1);
            GridPane.setConstraints(label, 2, 7);
            GridPane.setColumnSpan(label, 2);
            glass.getChildren().addAll(label, backB);
            glass.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;");


            final StackPane winLayout = new StackPane();
            winLayout.getChildren().addAll(glass);
            winLayout.setStyle("-fx-background-color: silver; -fx-font-size: 20; -fx-padding: 10;");

            stage.setScene(new Scene(winLayout, 800, 600));
            stage.show();
        }
    }

    public boolean isWindowClosed() {
        return this.stageClosedBoolean;
    }

}
