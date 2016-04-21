package Menu;

/**
 * Created by narco on 20-Apr-16.
 */
public class HelpView {
}

package Menu;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Help extends Application{

	/*public static void main(String[] args) {
        launch(args);
    }*/
	Image[] img;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane root = new GridPane();
		img = new Image[5];
		img[0]=new Image("/Images/dog.png");
		img[1]=new Image("/Images/cat.png");
		
	   // Image image = new Image("/Images/dog.png"); // image to be used

	   // ImageView imageView = new ImageView(image);
		ImageView imageView = new ImageView(img);
	    imageView.setFitHeight(100);
	    imageView.setFitWidth(100);
	   
	    //	    root.getChildren().add(imageView);

	    root.getChildren().add(new ImageView(img));
	    root.setAlignment(Pos.CENTER);
	    
	    primaryStage.setScene(new Scene(root, 300, 250));
	    primaryStage.show();
	}

	public static void main(String[] args) {
	  //  Application.launch(args);
	    launch(args);
	}

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
