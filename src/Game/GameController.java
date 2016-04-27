package Game;

public class GameController {
    /**
     * Constructor
     *
     */
    public GameController() 
    {
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(GameView.class);
            }
        }.start();
    }
}
