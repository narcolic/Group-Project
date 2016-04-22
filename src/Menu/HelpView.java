package Menu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelpView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public Button next;
    public Button previous;

    private String[] textHelp; // array of help text 
    Image[] img; // array of images

    TextArea text = new TextArea(); // output help text associated with help image

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        img = new Image[5];
        img[0] = new Image("/Images/dog.png");


        //img[1]=new Image("/Images/cat.png");

        //Image image = new Image("/Images/dog.jpg"); // image to be used

        //ImageView imageView = new ImageView(image);
        ImageView imageView = new ImageView(img[0]);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        
        //	    root.getChildren().add(imageView);
        textHelp = new String[5];
        textHelp[0] = "asd";
        text.appendText(textHelp[0]);

        next = new Button();
        next.setText("Next");

        previous = new Button();
        previous.setText("Previous");

       /* root.getChildren().add(new ImageView(img[0]));
        root.getChildren().add(next);
        root.getChildren().add(previous);
        root.getChildren().add(text);
*/
        root.getChildren().addAll(new ImageView(img[0]),next,previous,text);
        
        GridPane.setRowIndex(imageView,1);
        GridPane.setColumnIndex(next,2);
        GridPane.setColumnIndex(previous,0);
        GridPane.setRowIndex(text,2);
        root.setVgap(10); //sets a vertical gap
        root.setAlignment(Pos.CENTER);
        
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }

	/*public static void main(String[] args) {
        //Application.launch(args);
	    launch(args);
	}*/

	/*
	private String[] textHelp;
	private String[] imageHelp;
	private int slideNumber;*/

    /**
     * Constructor
     */
	/*public Help() {

	}
	
	public void nextSlide(){

	}

	public void previousSlide(){
		
	}*/

}
