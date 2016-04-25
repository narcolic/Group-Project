package Menu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HelpView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public Button next;
    public Button previous;

    private String[] textHelp; // array of help text 
    Image[] img; // array of images

    //TextArea text = new TextArea(); // output help text associated with help image
    Label text = new Label();
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        VBox box = new VBox();

        img = new Image[5];
        img[0] = new Image("/Menu/Images/dog1.png");
        ImageView imageView = new ImageView(img[0]);

        textHelp = new String[5];
        textHelp[0] = "asddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd";
        text.setText(textHelp[0]);
        text.setMaxSize(550, 150);
        text.setWrapText(true);
        text.setStyle("-fx-background-color: #FFFFFF;");
       
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

        root.getChildren().addAll(box, next, previous, text);
        root.setVgap(10); //sets a vertical gap
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(650);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }
}
