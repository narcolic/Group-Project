package Menu;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelpView extends Application{

	public static void main(String[] args) {
        launch(args);
    }
	Image[] img;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane root = new GridPane();
		img = new Image[5];
		img[0]=new Image("/Images/dog.jpg");
		//img[1]=new Image("/Images/cat.png");
		
	    //Image image = new Image("/Images/dog.jpg"); // image to be used

	    //ImageView imageView = new ImageView(image);
		ImageView imageView = new ImageView(img[0]);
	    imageView.setFitHeight(100);
	    imageView.setFitWidth(100);
	   
	    //	    root.getChildren().add(imageView);

	    root.getChildren().add(new ImageView(img[0]));
	    root.setAlignment(Pos.CENTER);
	    
	    primaryStage.setScene(new Scene(root, 300, 250));
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
