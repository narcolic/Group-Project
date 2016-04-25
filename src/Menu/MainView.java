package Menu;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private Stage stage;
    private Button backB;

    private AudioClip audio;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Scene scene = menuScene();
        stage.setTitle("Main Menu");

        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
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
        Thread thread = new Thread(task);
        thread.start();

    }

    private Scene menuScene() {

        stage.setTitle("Quoridor");
        Button startB = new Button();
        startB.setText("Start");
        startB.setMaxWidth(Double.MAX_VALUE);
        startB.setOnAction(event -> stage.setScene(StartScene()));

        Button optionsB = new Button();
        optionsB.setText("Options");
        optionsB.setMaxWidth(Double.MAX_VALUE);
        optionsB.setOnAction(event -> stage.setScene(OptionsScene(optionsModel)));

        Button helpB = new Button();
        helpB.setText("Help");
        helpB.setMaxWidth(Double.MAX_VALUE);
        helpB.setOnAction(event -> stage.setScene(HelpScene(helpModel)));

        Button quitB = new Button();
        quitB.setText("Quit");
        quitB.setMaxWidth(Double.MAX_VALUE);
        quitB.setOnAction(event -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Quoridor");
            alert.setHeaderText("Quit Quoridor");
            alert.setContentText("Are you sure you want to quit Quoridor?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // close Quoridor
                System.exit(0);
            }
        });

        GridPane root = new GridPane();
        GridPane.setRowIndex(startB, 1);
        GridPane.setRowIndex(optionsB, 2);
        GridPane.setRowIndex(helpB, 3);
        GridPane.setRowIndex(quitB, 4);

        root.getChildren().add(startB);
        root.getChildren().add(optionsB);
        root.getChildren().add(helpB);
        root.getChildren().add(quitB);
        root.setAlignment(Pos.CENTER);
        root.setVgap(20);

        root.setStyle(
                "-fx-background-image: url(" +
                        "/Menu/Images/menuBG.jpg" +
                        "); " +
                        "-fx-background-size: cover,auto;"
        );

        stage.setResizable(false);
        return new Scene(root, 800, 600);
    }

    private Scene HelpScene(Help help) {

        stage.setTitle("Help");
        help.fillTextHelpArray();
        help.fillImageLocArray();
        Label textLabel = new Label();
        GridPane root = new GridPane();
        VBox box = new VBox();

        ImageView imageView = new ImageView(new Image(help.getCurrentImageLocation()));

        textLabel.setText(help.getCurrentText());
        textLabel.setMaxSize(550, 150);
        textLabel.setWrapText(true);
        textLabel.setStyle("-fx-background-color: #FFFFFF;");

        Button nextB = new Button();
        nextB.setMaxWidth(Double.MAX_VALUE);
        nextB.setStyle("-fx-background-image: url(" +
                "/Menu/Images/nextBTN.png" +
                "); " +
                "-fx-background-size: cover, auto;"
                + "-fx-background-radius: 5em; " +
                "-fx-min-width: 50px; " +
                "-fx-min-height: 50px; "
        );
        nextB.setOnAction(event -> {
            if (help.isNextAvailable()) {
                help.nextSlide();
                stage.setScene(HelpScene(helpModel));
            } else {
                stage.setScene(HelpScene(helpModel));
            }
        });

        Button previousB = new Button();
        previousB.setMaxWidth(Double.MAX_VALUE);
        previousB.setStyle("-fx-background-image: url(" +
                "/Menu/Images/previousBTN.png" +
                "); " +
                "-fx-background-size: cover, auto;"
                + "-fx-background-radius: 5em; " +
                "-fx-min-width: 50px; " +
                "-fx-min-height: 50px; "
        );
        previousB.setOnAction(event -> {
            if (help.isPreviousAvailable()) {
                help.previousSlide();
                stage.setScene(HelpScene(helpModel));
            } else {
                stage.setScene(HelpScene(helpModel));
            }
        });

        backB = new Button();
        backB.setMaxWidth(Double.MAX_VALUE);
        backB.setStyle("-fx-background-image: url(" +
                "/Menu/Images/backBTN.png" +
                "); " +
                "-fx-background-size: cover, auto;"
                + "-fx-background-radius: 5em; " +
                "-fx-min-width: 50px; " +
                "-fx-min-height: 50px; "
        );
        backB.setOnAction(event -> stage.setScene(menuScene()));

        box.getChildren().add(imageView);
        box.setAlignment(Pos.CENTER);
        box.setMinSize(300, 400);

        box.setFillWidth(true);
        box.setStyle("-fx-background-color: cadetblue;"
                + "-fx-border-width: 2;"
                + "-fx-border-color: black");

        GridPane.setConstraints(box, 2, 1);
        GridPane.setConstraints(nextB, 3, 1);
        GridPane.setConstraints(previousB, 1, 1);
        GridPane.setConstraints(textLabel, 2, 2);

        root.getChildren().addAll(box, nextB, previousB, textLabel, backB);
        root.setVgap(10); //sets a vertical gap
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);

        root.setStyle(
                "-fx-background-image: url(" +
                        "/Menu/Images/menuBG.jpg" +
                        "); " +
                        "-fx-background-size: cover,auto;"
        );

        // stage.setMinWidth(800);
        // stage.setMinHeight(650);
        stage.setResizable(false); // do not allow window to be resized
        return new Scene(root, 800, 600);
    }

    private Scene OptionsScene(Options option) {

        stage.setTitle("Options");
        final String SOUND_SLIDER_TOOLTIP = "Slide to increase sound volume.";
        final String MUTE_TOOLTIP = "Click to mute sound.";
        GridPane root;
        root = new GridPane();

        Slider soundSlider = new Slider(0, 100, option.getVolume());
        soundSlider.setShowTickLabels(true);
        soundSlider.setShowTickMarks(true);
        soundSlider.setMajorTickUnit(50);
        soundSlider.setMinorTickCount(5);
        soundSlider.setBlockIncrement(10);
        soundSlider.setMinWidth(400);
        soundSlider.setTooltip(new Tooltip(SOUND_SLIDER_TOOLTIP));
        soundSlider.setStyle("-fx-font-size: 16; -fx-text-fill: #FFFFFF; -fx-sroke: white;");

        CheckBox muteBox = new CheckBox();
        muteBox.setSelected(option.isMute());
        muteBox.setTooltip(new Tooltip(MUTE_TOOLTIP));

        Label soundVolumeLabel = new Label("Sound Volume: ");
        Label soundValue = new Label(Double.toString(soundSlider.getValue()));
        Label muteLabel = new Label("Mute Sound: ");

        soundVolumeLabel.setTextFill(Color.WHITE);
        soundValue.setTextFill(Color.WHITE);
        muteLabel.setTextFill(Color.WHITE);

        backB = new Button();
        backB.setMaxWidth(Double.MAX_VALUE);
        backB.setStyle("-fx-background-image: url(" +
                "/Menu/Images/backBTN.png" +
                "); " +
                "-fx-background-size: cover, auto;"
                + "-fx-background-radius: 5em; " +
                "-fx-min-width: 50px; " +
                "-fx-min-height: 50px; " +
                "-fx-max-width: 50px; " +
                "-fx-max-height: 50px;"
        );
        backB.setOnAction(event -> stage.setScene(menuScene()));

        GridPane.setConstraints(soundVolumeLabel, 0, 1);
        GridPane.setConstraints(soundSlider, 1, 1);
        GridPane.setConstraints(soundValue, 2, 1);
        GridPane.setConstraints(muteLabel, 0, 2);
        GridPane.setConstraints(muteBox, 1, 2);

        root.getChildren().add(soundVolumeLabel);
        root.getChildren().add(soundSlider);
        root.getChildren().add(soundValue);
        root.getChildren().add(muteLabel);
        root.getChildren().add(muteBox);
        root.getChildren().add(backB);

        root.setStyle("-fx-background-image: url(" +
                "/Menu/Images/menuBG.jpg" +
                "); " +
                "-fx-background-size: cover,auto;" +
                "-fx-padding:10;" +
                "-fx-font-size: 16;" +
                "-fx-alignment: baseline-left;"
        );

        soundSlider.valueProperty().addListener(observable -> {
            soundValue.setText(String.format("%.1f", soundSlider.getValue()));
            option.setVolume(soundSlider.getValue());
            audio.stop();
            audio.setVolume(option.getVolumePercent());
            audio.play();
        });

        muteBox.setOnAction((event -> {if (muteBox.isSelected()) {
            audio.stop();
            option.changeMuteState();
        } else {
            audio.stop();
            audio.setVolume(option.getVolumePercent());
            audio.play();
            option.changeMuteState();
        }}));
/*        if (muteBox.isSelected()) {
            audio.stop();
        } else {
            audio.stop();
            audio.setVolume(option.getVolumePercent());
            audio.play();
        }*/

        root.setVgap(30);
        root.setHgap(70);
        root.setAlignment(Pos.CENTER);

        stage.setResizable(false);
        return new

                Scene(root, 800, 600);

    }

    private Scene StartScene() {

        stage.setTitle("Quoridor");
        GridPane root;
        root = new GridPane();

        backB = new Button();
        backB.setOnAction(event -> stage.setScene(menuScene()));
        backB.setStyle("-fx-background-image: url(" +
                "/Menu/Images/backBTN.png" +
                "); " +
                "-fx-background-size: cover, auto;"
                + "-fx-background-radius: 5em; " +
                "-fx-min-width: 50px; " +
                "-fx-min-height: 50px; " +
                "-fx-max-width: 50px; " +
                "-fx-max-height: 50px;"
        );

        VBox singlePlayer = new VBox();
        VBox multiPlayer = new VBox();
        VBox practise = new VBox();

        singlePlayer.setAlignment(Pos.CENTER_LEFT);
        singlePlayer.setStyle("-fx-background-image: url(/Menu/Images/1p.png);"
                + "-fx-background-repeat: no-repeat;"
                + "-fx-background-position: center;"
                + "-fx-background-color: cadetblue;"
                + "-fx-border-width: 2;"
                + "-fx-border-color: black");
        singlePlayer.setMinSize(250, 350);
        singlePlayer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(SinglePlayerScreen()));

        multiPlayer.setAlignment(Pos.CENTER);
        multiPlayer.setStyle("-fx-background-image: url(/Menu/Images/2p.png);"
                + "-fx-background-repeat: no-repeat;"
                + "-fx-background-position: center;"
                + "-fx-background-color: indianred;"
                + "-fx-border-width: 2;"
                + "-fx-border-color: black");
        multiPlayer.setMinSize(250, 350);
        multiPlayer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(MultiPlayerScreen()));

        practise.setAlignment(Pos.CENTER_RIGHT);
        practise.setStyle("-fx-background-image: url(/Menu/Images/pc.png);"
                + "-fx-background-repeat: no-repeat;"
                + "-fx-background-position: center;"
                + "-fx-background-color: lightgreen;"
                + "-fx-border-width: 2;"
                + "-fx-border-color: black");
        practise.setMinSize(250, 350);
        practise.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(PracticeScreen()));

        GridPane.setConstraints(singlePlayer, 0, 1);
        GridPane.setConstraints(multiPlayer, 1, 1);
        GridPane.setConstraints(practise, 2, 1);

        root.setStyle(
                "-fx-background-image: url(" +
                        "/Menu/Images/menuBG.jpg" +
                        "); " +
                        "-fx-background-size: cover,auto;" +
                        "-fx-padding:10;" +
                        "-fx-font-size: 16;"
        );

        root.getChildren().addAll(singlePlayer, multiPlayer, practise, backB);
        root.setVgap(10);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);

        stage.setResizable(false);
        return new Scene(root, 800, 600);
    }

    private Scene SinglePlayerScreen() {
        GridPane root;
        root = new GridPane();

        Label singleP = new Label("Single Player");
        singleP.setTextFill(Color.WHITE);

        backB = new Button();
        backB.setOnAction(event -> stage.setScene(StartScene()));
        backB.setStyle("-fx-background-image: url(" +
                "/Menu/Images/backBTN.png" +
                "); " +
                "-fx-background-size: cover, auto;"
                + "-fx-background-radius: 5em; " +
                "-fx-min-width: 50px; " +
                "-fx-min-height: 50px; " +
                "-fx-max-width: 50px; " +
                "-fx-max-height: 50px;"
        );

        root.setStyle(
                "-fx-background-image: url(" +
                        "/Menu/Images/menuBG.jpg" +
                        "); " +
                        "-fx-background-size: cover,auto;" +
                        "-fx-padding:10;" +
                        "-fx-font-size: 16;" +
                        "-fx-alignment: baseline-left;"
        );

        GridPane.setConstraints(singleP, 0, 1);
        root.getChildren().addAll(backB, singleP);

        stage.setResizable(false);
        return new Scene(root, 800, 600);
    }

    private Scene MultiPlayerScreen() {
        GridPane root;
        root = new GridPane();

        Label multiP = new Label("Multi Player");
        multiP.setTextFill(Color.WHITE);

        backB = new Button();
        backB.setOnAction(event -> stage.setScene(StartScene()));
        backB.setStyle("-fx-background-image: url(" +
                "/Menu/Images/backBTN.png" +
                "); " +
                "-fx-background-size: cover, auto;"
                + "-fx-background-radius: 5em; " +
                "-fx-min-width: 50px; " +
                "-fx-min-height: 50px; " +
                "-fx-max-width: 50px; " +
                "-fx-max-height: 50px;"
        );

        root.setStyle(
                "-fx-background-image: url(" +
                        "/Menu/Images/menuBG.jpg" +
                        "); " +
                        "-fx-background-size: cover,auto;" +
                        "-fx-padding:10;" +
                        "-fx-font-size: 16;" +
                        "-fx-alignment: baseline-left;"
        );

        GridPane.setConstraints(multiP, 0, 1);
        root.getChildren().addAll(backB, multiP);

        stage.setResizable(false);
        return new Scene(root, 800, 600);
    }

    private Scene PracticeScreen() {
        GridPane root;
        root = new GridPane();

        Label practise = new Label("Practise Screen");
        practise.setTextFill(Color.WHITE);

        backB = new Button();
        backB.setOnAction(event -> stage.setScene(StartScene()));
        backB.setStyle("-fx-background-image: url(" +
                "/Menu/Images/backBTN.png" +
                "); " +
                "-fx-background-size: cover, auto;"
                + "-fx-background-radius: 5em; " +
                "-fx-min-width: 50px; " +
                "-fx-min-height: 50px; " +
                "-fx-max-width: 50px; " +
                "-fx-max-height: 50px;"
        );

        root.setStyle(
                "-fx-background-image: url(" +
                        "/Menu/Images/menuBG.jpg" +
                        "); " +
                        "-fx-background-size: cover,auto;" +
                        "-fx-padding:10;" +
                        "-fx-font-size: 16;" +
                        "-fx-alignment: baseline-left;"
        );

        GridPane.setConstraints(practise, 0, 1);
        root.getChildren().addAll(backB, practise);

        stage.setResizable(false);
        return new Scene(root, 800, 600);
    }

}
