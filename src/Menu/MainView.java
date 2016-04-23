package Menu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.application.Platform;

import java.awt.event.ActionListener;
//import javafx.scene.control.Alert;

public class MainView extends Application {

    private Stage stage;
    public Button startb;
    public Button options;
    public Button help;
    public Button quit;
    public Button next;
    public Button previous;

    private String[] textHelp; // array of help text
    Image[] img; // array of images

    //TextArea text = new TextArea(); // output help text associated with help image
    Label text = new Label();

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Scene scene = menuScene();
        stage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();

        //create the start button


        //primaryStage.setScene(new Scene(root, 800, 600));
        //primaryStage.show();
    }

    public Scene menuScene(){
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
                //PARAMETER_ACTION = "option";
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
                stage.close();
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

        return new Scene(root, 800, 600);
    }

    protected Scene HelpScene(){
        GridPane root = new GridPane();
        VBox box = new VBox();


        img = new Image[5];
        img[0] = new Image("/Menu/Images/dog1.png");

        ImageView imageView = new ImageView(img[0]);
        //  imageView.setFitHeight(100);
        //   imageView.setFitWidth(100);

        textHelp = new String[5];
        textHelp[0] = "asddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd";
        text.setText(textHelp[0]);
        text.setMaxSize(550, 150);
        text.setWrapText(true);
        text.setStyle("-fx-background-color: #FFFFFF;");
        // text.appendText(textHelp[0]);

        next = new Button();
        next.setText("Next");

        previous = new Button();
        previous.setText("Previous");

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

        root.getChildren().addAll(box, next, previous, text);
        root.setVgap(10); //sets a vertical gap
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);

        stage.setMinWidth(800);
        stage.setMinHeight(650);
        return new Scene(root, 800, 600);
    }

}