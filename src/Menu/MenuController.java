package Menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.awt.event.ActionListener;

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
        //menuView = new MainView();
        //menu listeners
        //this.menuView = menuView;
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(MainView.class);
            }
        }.start();


        /*startView = new StartView();
        optionsView = new OptionsView();
        helpView = new HelpView();
        helpModel = new Help();
        optionsModel = new Options();
        menuModel = new Menu();
        startModel = new Start();

        switch (PARAMETER_ACTION) {
            case ACTION_START:

                view = new Views(startView, startModel);
                break;
            case ACTION_OPTION:

                view = new Views(optionsView, optionsModel);
                break;
            case ACTION_HELP:

                view = new Views(helpView, helpModel);
                break;
            case ACTION_QUIT:

                break;
            default:
                menuView = new MainView();
        }*/
    }

    public void setMute() {
        optionsModel.volume = 0;
        optionsModel.mute = true;
    }


}
