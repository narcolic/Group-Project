package Menu;

public class MenuController {
    private MenuView mview;
    private Options options;
    private Menu menu_model;

    public MenuController(){}


    public void setMute(){
        options.volume=0;
        options.mute=true;
    }
}
