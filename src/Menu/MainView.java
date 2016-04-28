package Menu;

import Game.Board;
import Game.GameView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.Optional;

import static javafx.scene.media.AudioClip.INDEFINITE;

public class MainView extends Application {

    private Help helpModel = new Help();
    private Options optionsModel = new Options();
    private Language languageModel = new Language();

    private Stage stage;
    private Button backB; // go to previous scene

    private AudioClip audio; // game sound

    @Override
    public void start(Stage primaryStage) {

        stage = primaryStage;
        Scene scene = menuScene(languageModel); // initial scene set to menu scene
        stage.setTitle("Main Menu");
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene); // set scene depending on the window displayed
        primaryStage.show();

        final Task task = new Task() {

            @Override
            protected Object call() throws Exception {
                int s = INDEFINITE;
                audio = new AudioClip(getClass().getResource("/Menu/Sound/song.mp3").toExternalForm());
                audio.setVolume(0.5f);
                audio.setCycleCount(s);
                audio.play();
                return null;
            }
        };
        Thread thread = new Thread(task); // create new thread called task
        thread.start(); // start thread
    }

    /**
     * Main menu presented to the user
     *
     * @return scene Main menu scene
     */
    private Scene menuScene(Language language) {

        stage.setTitle("Quoridor");
        stage.getIcons().add(new Image("/Menu/Images/quoridorIcon.jpg")); // application icon

        /*
         * Menu Option Buttons 
         */

        // Start button
        Button startB = new Button();
        startB.setMaxWidth(Double.MAX_VALUE);
        startB.setOnAction(event -> stage.setScene(StartScene()));
        startB.setStyle("-fx-background-image: url('/Menu/Images/Icons/Start.png')");
        startB.getStyleClass().add("button");
        Label startBLabel = new Label((String) language.getCurrentLanguage().get("Start"));
        startBLabel.getStyleClass().add("menu");
        startBLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(StartScene()));

        // Options button
        Button optionsB = new Button();
        optionsB.setMaxWidth(Double.MAX_VALUE);
        optionsB.setOnAction(event -> stage.setScene(OptionsScene(optionsModel, languageModel)));
        optionsB.setStyle("-fx-background-image: url('/Menu/Images/Icons/options.png')");
        optionsB.getStyleClass().add("button");
        Label optionsLabel = new Label((String) language.getCurrentLanguage().get("Options"));
        optionsLabel.getStyleClass().add("menu");
        optionsLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(OptionsScene(optionsModel, languageModel)));

        // Help button
        Button helpB = new Button();
        helpB.setMaxWidth(Double.MAX_VALUE);
        helpB.setOnAction(event -> stage.setScene(HelpScene(helpModel, languageModel)));
        helpB.setStyle("-fx-background-image: url('/Menu/Images/Icons/help.png')");
        helpB.getStyleClass().add("button");
        Label helpLabel = new Label((String) language.getCurrentLanguage().get("Help"));
        helpLabel.getStyleClass().add("menu");
        helpLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(HelpScene(helpModel, languageModel)));

        // Quit button
        Button quitB = new Button();
        quitB.setMaxWidth(Double.MAX_VALUE);
        quitB.setStyle("-fx-background-image: url('/Menu/Images/Icons/quit.png')");
        quitB.getStyleClass().add("button");
        Label quitLabel = new Label((String) language.getCurrentLanguage().get("Quit"));
        quitLabel.getStyleClass().add("menu");
        quitLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> quitButtonAction());

        quitB.setOnAction(event -> {
            // confirmation dialog before leaving game
            quitButtonAction();
        });

        // specify layouts for all components
        GridPane root = new GridPane();
        GridPane.setConstraints(startB, 0, 2);
        GridPane.setConstraints(startBLabel, 1, 2);
        GridPane.setConstraints(optionsB, 0, 3);
        GridPane.setConstraints(optionsLabel, 1, 3);
        GridPane.setConstraints(helpB, 0, 4);
        GridPane.setConstraints(helpLabel, 1, 4);
        GridPane.setConstraints(quitB, 0, 5);
        GridPane.setConstraints(quitLabel, 1, 5);

        root.getChildren().addAll(startB, startBLabel, optionsB, optionsLabel, helpB, helpLabel, quitB, quitLabel);
        root.setAlignment(Pos.CENTER);
        root.setHgap(40);
        root.setVgap(40);

        root.setStyle("-fx-background-image: url('/Menu/Images/menuBackGround.jpg');\n" +
                "    -fx-background-size: cover, auto;\n" +
                "    -fx-padding: 10 50 10 30;");

        stage.setResizable(false);
        // display components in scene
        Scene scene = new Scene(root, 800, 600); // optimal window size 800x600
        // get external stylesheet
        scene.getStylesheets().add(getClass().getResource("custom-font-styles.css").toExternalForm());
        return scene;
    }

    /**
     * Provides instructions on how to play the game
     *
     * @param help Help model
     * @return scene Change current scene to help scene
     */
    private Scene HelpScene(Help help, Language language) {

        stage.setTitle("Help");
        help.setCurrentLanguage(language.getLanguage());

        final String NEXT_SLIDE_TOOLTIP = "Next instructions,"+"\n"+"RIGHT Arrow Key";
        final String PREVIOUS_SLIDE_TOOLTIP = "Previous instructions,"+"\n"+"LEFT Arrow Key";

        // load help image and text instructions
        help.fillTextHelpArray();
        help.fillImageLocArray();
        Label textLabel = new Label(); // to display text help instructions
        GridPane root = new GridPane();
        VBox box = new VBox();

        // used to display help image
        ImageView imageView = new ImageView(new Image(help.getCurrentImageLocation()));

        // set text according to current help instruction
        textLabel.setText(help.getCurrentText());
        textLabel.setMaxSize(550, 150);
        textLabel.setWrapText(true);
        textLabel.setStyle("-fx-background-color: #FFFFFF;");

        /*
         * Buttons 
         */

        // Next button
        Button nextB = new Button();
        nextB.setMaxWidth(Double.MAX_VALUE);
        nextB.setStyle("-fx-background-image: url('/Menu/Images/Icons/nextBTN.png')");
        nextB.getStyleClass().add("button");
        nextB.setTooltip(new Tooltip(NEXT_SLIDE_TOOLTIP));
        nextB.setOnAction(event -> nextButtonAction(help));

        // show next help instruction when the right arrow key is pressed
        nextB.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.RIGHT) {
                nextButtonAction(help);
            }
        });

        // Previous button
        Button previousB = new Button();
        previousB.setMaxWidth(Double.MAX_VALUE);
        previousB.setStyle("-fx-background-image: url('/Menu/Images/Icons/previousBTN.png')");
        previousB.getStyleClass().add("button");
        previousB.setTooltip(new Tooltip(PREVIOUS_SLIDE_TOOLTIP));
        previousB.setOnAction(event -> previousButtonAction(help));

        // show previous help instruction when the left arrow key is pressed
        previousB.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.LEFT) {
                previousButtonAction(help);
            }
        });
        // Back button
        backB = new Button();
        backB.setMaxWidth(Double.MAX_VALUE);
        backB.setStyle("-fx-background-image: url('/Menu/Images/Icons/backBTN.png')");
        backB.getStyleClass().add("button");
        backB.setOnAction(event -> stage.setScene(menuScene(languageModel))); // back to main menu
        /*Label backBLabel = new Label((String) language.getCurrentLanguage().get("Back"));
        backBLabel.getStyleClass().add("menu");
        backBLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(menuScene(languageModel)));*/

        box.getChildren().add(imageView);
        box.setAlignment(Pos.CENTER);
        box.setMinSize(250, 350);

        box.setFillWidth(true);
        box.setStyle("-fx-background-color: cadetblue;"
                + "-fx-border-width: 2;"
                + "-fx-border-color: black");

        // set layout restrictions
        GridPane.setConstraints(backB, 0, 1);
        //GridPane.setConstraints(backBLabel, 1, 1);
        GridPane.setColumnSpan(box, 2);
        GridPane.setConstraints(box, 1, 2);
        GridPane.setConstraints(nextB, 4, 2);
        GridPane.setConstraints(previousB, 0, 2);
        GridPane.setColumnSpan(textLabel, 2);
        GridPane.setConstraints(textLabel, 1, 3);

        root.getChildren().addAll(box, nextB, previousB, textLabel, backB);
        root.setVgap(10); //sets a vertical gap
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);

        // add background image
        root.getStyleClass().add("background");

        stage.setResizable(false); // do not allow window to be resized
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("custom-font-styles.css").toExternalForm());
        return scene;
    }

    /**
     * Displays the user with sound options
     *
     * @param option Options model
     * @return scene Change current scene to options scene
     */
    private Scene OptionsScene(Options option, Language language) {

        stage.setTitle("Options");

        // help displayed when user hovers over slider or mute checkbox
        final String SOUND_SLIDER_TOOLTIP = "Slide to increase sound volume.";
        final String MUTE_TOOLTIP = "Click to mute sound.";
        final String LANGUAGE_TOOLTIP = "Select your language.";

        GridPane root;
        root = new GridPane();

        ImageView soundIco = new ImageView(new Image("/Menu/Images/Icons/sound.png"));
        ImageView languageIco = new ImageView(new Image("/Menu/Images/Icons/language.png"));


        // slider used to control sound volume
        Slider soundSlider = new Slider(0, 100, option.getVolume());
        soundSlider.setShowTickLabels(true);
        soundSlider.setShowTickMarks(true);
        soundSlider.setMajorTickUnit(50);
        soundSlider.setMinorTickCount(5);
        soundSlider.setBlockIncrement(10);
        soundSlider.setMinWidth(400);
        soundSlider.setTooltip(new Tooltip(SOUND_SLIDER_TOOLTIP));
        soundSlider.setStyle("-fx-font-size: 16; -fx-text-fill: #FFFFFF; -fx-sroke: white;");

        // checkbox for mute option
        CheckBox muteBox = new CheckBox();
        muteBox.setSelected(option.isMute());
        muteBox.setTooltip(new Tooltip(MUTE_TOOLTIP));

        ChoiceBox<String> languageListBox = new ChoiceBox<>();
        languageListBox.setTooltip(new Tooltip(LANGUAGE_TOOLTIP));
        languageListBox.getItems().addAll(language.languageList);
        languageListBox.setValue(language.getLanguage());

        Label soundVolumeLabel = new Label((String) language.getCurrentLanguage().get("Sound Volume"));
        Label soundTitleLabel = new Label((String) language.getCurrentLanguage().get("Sound Settings"));
        Label languageTitleLabel = new Label((String) language.getCurrentLanguage().get("Language Settings"));
        Label soundValue = new Label(Double.toString(soundSlider.getValue()));
        Label muteLabel = new Label((String) language.getCurrentLanguage().get("Mute Sound"));
        Label languageLabel = new Label((String) language.getCurrentLanguage().get("Language"));

        languageLabel.setTextFill(Color.WHITE);
        soundValue.setTextFill(Color.WHITE);
        muteLabel.setTextFill(Color.WHITE);
        soundVolumeLabel.setTextFill(Color.WHITE);
        soundVolumeLabel.getStyleClass().add("optionsLabel");
        languageLabel.getStyleClass().add("optionsLabel");
        muteLabel.getStyleClass().add("optionsLabel");
        soundTitleLabel.getStyleClass().add("settings");
        languageTitleLabel.getStyleClass().add("settings");
        soundTitleLabel.setGraphic(soundIco);
        languageTitleLabel.setGraphic(languageIco);

        // Back button
        backB = new Button();
        backB.setStyle("-fx-background-image: url('/Menu/Images/Icons/backBTN.png')");
        backB.getStyleClass().add("button");
        backB.setOnAction(event -> stage.setScene(menuScene(languageModel))); // go back to main menu
        /*Label backBLabel = new Label((String) language.getCurrentLanguage().get("Back"));
        backBLabel.getStyleClass().add("menu");
        backBLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(menuScene(languageModel)));*/

        // set component layouts
        GridPane.setConstraints(backB, 0, 1);
        //GridPane.setConstraints(backBLabel, 1, 1);
        GridPane.setColumnSpan(soundTitleLabel, 2);
        GridPane.setConstraints(soundTitleLabel, 0, 2);
        GridPane.setConstraints(soundVolumeLabel, 0, 3);
        GridPane.setConstraints(soundSlider, 1, 3);
        GridPane.setConstraints(soundValue, 2, 3);
        GridPane.setConstraints(muteLabel, 0, 4);
        GridPane.setConstraints(muteBox, 1, 4);
        GridPane.setColumnSpan(languageTitleLabel, 2);
        GridPane.setRowIndex(languageTitleLabel, 6);
        GridPane.setConstraints(languageLabel, 0, 7);
        GridPane.setConstraints(languageListBox, 1, 7);

        soundSlider.valueProperty().addListener(observable -> {
            // display current value for volume
            soundValue.setText(String.format("%.1f", soundSlider.getValue()));
            option.setVolume(soundSlider.getValue());
            audio.stop(); // stop sounds
            audio.setVolume(option.getVolumePercent());
            audio.play(); // play sound
        });

        muteBox.setOnAction((event -> {
            if (muteBox.isSelected()) {
                // stop sound
                audio.stop();
                option.changeMuteState();
            } else {
                audio.stop(); // stop sound
                audio.setVolume(option.getVolumePercent());
                audio.play(); // play sound
                option.changeMuteState();
            }
        }));

        languageListBox.valueProperty().addListener(observable -> {
            language.setLanguage(languageListBox.getValue());
        });

        // add components
        root.getChildren().addAll(soundTitleLabel, soundVolumeLabel,
                languageLabel, languageTitleLabel, languageListBox,
                soundSlider, soundValue, muteLabel, muteBox, backB);

        root.setVgap(30);
        root.setHgap(60);
        root.setAlignment(Pos.TOP_LEFT);
        root.getStyleClass().add("background");

        stage.setResizable(false);
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("custom-font-styles.css").toExternalForm());
        return scene;
    }

    /**
     * User can select which game mode they would like to play in
     *
     * @return scene Change current scene to mode select screen
     */
    private Scene StartScene() {

        stage.setTitle("Quoridor");
        GridPane root;
        root = new GridPane();

        // Back button
        backB = new Button();
        backB.setOnAction(event -> stage.setScene(menuScene(languageModel))); // go back to main menu
        backB.setStyle("-fx-background-image: url('/Menu/Images/Icons/backBTN.png')");
        backB.getStyleClass().add("button");

        VBox singlePlayer = new VBox();
        VBox multiPlayer = new VBox();
        VBox practice = new VBox();

        singlePlayer.setAlignment(Pos.CENTER_LEFT);
        singlePlayer.getStyleClass().add("p1MenuBox");
        singlePlayer.setMinSize(250, 350);
        // go to single player mode
        singlePlayer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
    		Board.getInstance().setupBoard(false, false);
            Platform.runLater(() -> new GameView().start(new Stage()));
            stage.hide();
        });

        multiPlayer.setAlignment(Pos.CENTER);
        multiPlayer.getStyleClass().add("p2MenuBox");
        multiPlayer.setMinSize(250, 350);
        // go to multiplayer mode
        multiPlayer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(MultiPlayerScreen()));

        practice.setAlignment(Pos.CENTER_RIGHT);
        practice.getStyleClass().add("pcMenuBox");
        practice.setMinSize(250, 350);
        // go to practice mode
        practice.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(PracticeScreen()));

        // set component layouts
        GridPane.setRowIndex(backB, 1);
        GridPane.setConstraints(singlePlayer, 0, 2);
        GridPane.setConstraints(multiPlayer, 1, 2);
        GridPane.setConstraints(practice, 2, 2);

        root.getStyleClass().add("background");

        // add components
        root.getChildren().addAll(singlePlayer, multiPlayer, practice, backB);
        root.setVgap(10);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);

        stage.setResizable(false);
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("custom-font-styles.css").toExternalForm());
        return scene;
    }

    /**
     * Single player mode
     *
     * @return scene Change scene to single player scene
     */
    /*private Scene SinglePlayerScreen() {
        GridPane root;
        root = new GridPane();

        Label singleP = new Label("Single Player");
        singleP.setTextFill(Color.WHITE);

        // Back button
        backB = new Button();
        backB.setOnAction(event -> stage.setScene(StartScene())); // go back to mode select
        backB.setStyle("-fx-background-image: url('/Menu/Images/Icons/backBTN.png')");
        backB.getStyleClass().add("button");

        root.getStyleClass().add("background");

        // set component layouts and add components 
        GridPane.setConstraints(singleP, 0, 1);
        root.getChildren().addAll(backB, singleP);

        stage.setResizable(false);
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("custom-font-styles.css").toExternalForm());
        return scene;
    }*/

    /**
     * Multiplayer mode where multiple users can play
     *
     * @return scene Change current scene to multiplayer scene
     */
    private Scene MultiPlayerScreen() {
        GridPane root;
        root = new GridPane();

        Label multiP = new Label("Multi Player");
        multiP.setTextFill(Color.WHITE);

        // Back button
        backB = new Button();
        backB.setOnAction(event -> stage.setScene(StartScene())); // go back to mode select
        backB.setStyle("-fx-background-image: url('/Menu/Images/Icons/backBTN.png')");
        backB.getStyleClass().add("button");

        root.getStyleClass().add("background");

        // set component layouts and add components
        GridPane.setConstraints(multiP, 0, 1);
        root.getChildren().addAll(backB, multiP);

        stage.setResizable(false);
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("custom-font-styles.css").toExternalForm());
        return scene;
    }

    /**
     * Practice mode where user can play with a computer
     *
     * @return scene Change current scene to practice scene
     */
    private Scene PracticeScreen() {
        GridPane root;
        root = new GridPane();

        Label practice = new Label("Practice Screen");
        practice.setTextFill(Color.WHITE);

        // Back button
        backB = new Button();
        backB.setOnAction(event -> stage.setScene(StartScene())); // go to mode select
        backB.setStyle("-fx-background-image: url('/Menu/Images/Icons/backBTN.png')");
        backB.getStyleClass().add("button");

        root.getStyleClass().add("background");

        // set component layouts and add components 
        GridPane.setConstraints(practice, 0, 1);
        root.getChildren().addAll(backB, practice);

        stage.setResizable(false);
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("custom-font-styles.css").toExternalForm());
        return scene;
    }

    private void quitButtonAction() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Quoridor");
        alert.setHeaderText("Quit Quoridor");
        alert.setContentText("Are you sure you want to quit Quoridor?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // close Quoridor
            System.exit(0);
        }
    }

    private void nextButtonAction(Help help) {
        if (help.isNextAvailable()) {
            // show next help image
            help.nextSlide();
            stage.setScene(HelpScene(helpModel, languageModel));
        } else {
            stage.setScene(HelpScene(helpModel, languageModel));
        }
    }

    private void previousButtonAction(Help help) {
        if (help.isPreviousAvailable()) {
            // show previous help instruction
            help.previousSlide();
            stage.setScene(HelpScene(helpModel, languageModel));
        } else {
            stage.setScene(HelpScene(helpModel, languageModel));
        }
    }
}
