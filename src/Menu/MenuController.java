package Menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MenuController {

    private Options options;
    private Menu menuModel;
    private OptionsView optionsView;
    private MainView menuView;
    private HelpView helpView;
    private StartView startView;

    private static final String ACTION_START = "start";
    private static final String ACTION_OPTION = "option";
    private static final String ACTION_HELP = "help";
    private static final String ACTION_QUIT = "quit";

    private static final String PARAMETER_ACTION = "action";

    public MenuController() {
        menuView = new MainView();
        options = new Options();
        menuModel = new Menu();

        switch (PARAMETER_ACTION) {
            case ACTION_START:
                menuView.start.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Start");
                    }
                });
                startView = new StartView();
                break;
            case ACTION_OPTION:
                menuView.options.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Options");
                    }
                });
                optionsView = new OptionsView();
                break;
            case ACTION_HELP:
                menuView.help.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Help");
                    }
                });
                helpView = new HelpView();
                break;
            case ACTION_QUIT:
                menuView.quit.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        //menuView.primaryStage.close()  ;
                    }
                });
                break;
            default:
                //menuView.start();
        }
    }

    public void setMute() {
        options.volume = 0;
        options.mute = true;
    }


}
