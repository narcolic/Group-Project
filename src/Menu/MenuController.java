package Menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MenuController {

    private Options optionsModel;
    private Menu menuModel;
    private Start startModel;
    private Help helpModel;
    private OptionsView optionsView;
    private MainView menuView;
    private HelpView helpView;
    private StartView startView;
    private Views view;

    private static final String ACTION_START = "start";
    private static final String ACTION_OPTION = "option";
    private static final String ACTION_HELP = "help";
    private static final String ACTION_QUIT = "quit";

    private String PARAMETER_ACTION = "action";

    public MenuController() {
        menuView = new MainView();
        startView = new StartView();
        optionsView = new OptionsView();
        helpView = new HelpView();
        helpModel = new Help();
        optionsModel = new Options();
        menuModel = new Menu();
        startModel = new Start();

        switch (PARAMETER_ACTION) {
            case ACTION_START:
                menuView.start.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        PARAMETER_ACTION = "start";
                    }
                });

                view = new Views(startView, startModel);
                break;
            case ACTION_OPTION:
                menuView.options.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        PARAMETER_ACTION = "option";
                    }
                });
                view = new Views(optionsView, optionsModel);
                break;
            case ACTION_HELP:
                menuView.help.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        PARAMETER_ACTION = "help";
                    }
                });
                view = new Views(helpView, helpModel);
                break;
            case ACTION_QUIT:
                menuView.quit.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        PARAMETER_ACTION = "quit";
                        //menuView.primaryStage.close()  ;
                    }
                });
                break;
            default:
                view = new Views(menuView, menuModel);
        }
    }

    public void setMute() {
        optionsModel.volume = 0;
        optionsModel.mute = true;
    }


}
