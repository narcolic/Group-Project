package Menu;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.Optional;

public class MainView extends Application {

    private Stage stage;
    private Button startb;
    private Button options;
    private Button help;
    private Button quit;
    private Button next;
    private Button previous;
    private Button back;

    private int counter;
    private ImageView imageView;
    private String[] textHelp; // array of help text
    Image[] img; // array of images

    //TextArea text = new TextArea(); // output help text associated with help image
    Label text;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Scene scene = menuScene();
        stage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene menuScene() {
        startb = new Button();
        startb.setText("Start");
        startb.setMaxWidth(Double.MAX_VALUE);
        startb.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //PARAMETER_ACTION = "start";
            }
        });

        //create the options button
        options = new Button();
        options.setText("Options");
        options.setMaxWidth(Double.MAX_VALUE);
        options.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stage.setScene(OptionsScene());
            }
        });

        //create the Help button
        help = new Button();
        help.setText("Help");
        help.setMaxWidth(Double.MAX_VALUE);
        help.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stage.setScene(HelpScene());
            }
        });

        //create the Quit button
        quit = new Button();
        quit.setText("Quit");
        quit.setMaxWidth(Double.MAX_VALUE);
        quit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //PARAMETER_ACTION = "quit";
                //System.exit(0);
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Quoridor");
                alert.setHeaderText("Quit Quoridor");
                alert.setContentText("Are you sure you want to quit Quoridor?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // close Quoridor
                    System.exit(0);
                } else {
                    // return to main menu
                }
                // stage.close();
            }
        });


        //set order of buttons
        GridPane root = new GridPane();
        GridPane.setRowIndex(startb, 1);
        GridPane.setRowIndex(options, 2);
        GridPane.setRowIndex(help, 3);
        GridPane.setRowIndex(quit, 4);

        //add buttons to menu
        root.getChildren().add(startb);
        root.getChildren().add(options);
        root.getChildren().add(help);
        root.getChildren().add(quit);
        root.setAlignment(Pos.CENTER);

        stage.setResizable(false);
        return new Scene(root, 800, 600);
    }

    protected Scene HelpScene() {
        text = new Label();
        GridPane root = new GridPane();
        VBox box = new VBox();


        img = new Image[5];
        img[0] = new Image("/Menu/Images/dog.png");
        img[1] = new Image("/Menu/Images/dog1.png");
        imageView = new ImageView(img[0]);
        //  imageView.setFitHeight(100);
        //   imageView.setFitWidth(100);

        textHelp = new String[5];
        textHelp[0] = "asddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd";
        textHelp[1] = "fasfdhar";

        text.setText(textHelp[0]);
        text.setMaxSize(550, 150);
        text.setWrapText(true);
        text.setStyle("-fx-background-color: #FFFFFF;");
        // text.appendText(textHelp[0]);

        next = new Button();
        next.setText(">");
        next.setMaxWidth(Double.MAX_VALUE);
        next.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        next.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (counter >= 4) {
                    counter = 0;
                } else {
                    counter += 1;
                }
                img = new Image[counter];
                imageView = new ImageView(img[counter]);
                text.setText(textHelp[counter]);
            }
        });

        previous = new Button();
        previous.setText("<");
        previous.setMaxWidth(Double.MAX_VALUE);
        previous.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        previous.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (counter >= 1) {
                    counter -= 1;
                } else {
                    counter = 4;
                }
                img = new Image[counter];
                imageView = new ImageView(img[counter]);
                text.setText(textHelp[counter]);
            }
        });

        back = new Button();
        back.setText("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stage.setScene(menuScene());
            }
        });

        box.getChildren().add(imageView);
        box.setAlignment(Pos.CENTER);
        box.setMinSize(300, 400);

        box.setFillWidth(true);
        box.setStyle("-fx-background-color: cadetblue;"
                + "-fx-border-width: 2;"
                + "-fx-border-color: black");

        GridPane.setConstraints(box, 2, 1);
        GridPane.setConstraints(next, 3, 1);
        GridPane.setConstraints(previous, 1, 1);
        GridPane.setConstraints(text, 2, 2);

        //ColumnConstraints cimg = new ColumnConstraints();
        //cimg.setPercentWidth(10);
        //root.getColumnConstraints().add(cimg);

        root.getChildren().addAll(box, next, previous, text, back);
        root.setVgap(10); //sets a vertical gap
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);

        stage.setMinWidth(800);
        stage.setMinHeight(650);
        stage.setResizable(false); // do not allow window to be resized
        return new Scene(root, 800, 600);
    }

    private Scene OptionsScene() {
        stage.setTitle("Options");
        final String SOUND_SLIDER_TOOLTIP = "Slide to increase sound volume.";
        final String MUTE_TOOLTIP = "Click to mute sound.";
        GridPane root;
        root = new GridPane();

        Slider soundSlider = new Slider(0,100,50);
        soundSlider.setShowTickLabels(true);
        soundSlider.setShowTickMarks(true);
        soundSlider.setMajorTickUnit(50);
        soundSlider.setMinorTickCount(5);
        soundSlider.setBlockIncrement(10);
        soundSlider.setMinWidth(400);
        soundSlider.setTooltip(new Tooltip(SOUND_SLIDER_TOOLTIP));

        CheckBox muteBox = new CheckBox();
        muteBox.setSelected(false);
        muteBox.setTooltip(new Tooltip(MUTE_TOOLTIP));

        Label soundVolumeLabel = new Label("Sound Volume: ");
        Label soundValue = new Label(Double.toString(soundSlider.getValue()));
        Label muteLabel = new Label("Mute Sound: ");

        soundVolumeLabel.setTextFill(Color.BLACK);
        soundValue.setTextFill(Color.BLACK);
        muteLabel.setTextFill(Color.BLACK);

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

        soundSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            soundValue.setText(String.format("%.2f", new_val));
        });

        root.setVgap(30);
        root.setHgap(70);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: lightblue; -fx-padding:10; -fx-font-size: 16; -fx-alignment: baseline-left;");

        stage.setResizable(false);
        return new Scene(root, 800, 600);
    }

}
