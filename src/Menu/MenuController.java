package Menu;

public class MenuController {
    private MenuView menuView;
    private Options options;
    private Menu menuModel;

    public MenuController(){
        
    }

    public void setMute(){
        options.volume=0;
        options.mute=true;
    }
}
