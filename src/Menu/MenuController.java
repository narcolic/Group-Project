package Menu;

public class MenuController {

    public MenuController() {

        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(MainView.class);
            }
        }.start();
    }

}
