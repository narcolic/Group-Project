package Menu;

import java.awt.*;

public class Help {

    private String[] textHelp;
    private String[] imageLoc;
    private int slideNumber;

    /**
     * Constructor
     */
    public Help() {
        textHelp = new String[5];
        imageLoc = new String[5];
        slideNumber = 0;
    }

    public String getCurrentText() {
        return this.textHelp[this.slideNumber];
    }

    public String getCurrentImageLocation() {
        return this.imageLoc[this.slideNumber];
    }

    public int getCurrentSlide() {
        return this.slideNumber;
    }

    public void nextSlide() {
        if (isNextAvailable()) {
            this.slideNumber++;
        }
    }

    public boolean isNextAvailable() {
        if (this.slideNumber < (textHelp.length-1))
            return true;
        else return false;
    }

    public boolean isPreviousAvailable() {
        if (slideNumber >= 1)
            return true;
        else return false;
    }

    public void previousSlide() {
        if (isPreviousAvailable()) {
            this.slideNumber--;
        }
    }

    public void fillTextHelpArray() {
        textHelp = new String[]{"sadasdsadas1",
                "asdasdasdsad2",
                "asdasdasdad3",
                "dsadasdsad4",
                "asdasdas5"};
    }

    public void fillImageLocArray() {
        imageLoc = new String[]{"/Menu/Images/dog1.png",
                "/Menu/Images/dog.png",
                "/Menu/Images/dog1.png",
                "/Menu/Images/dog.png",
                "/Menu/Images/dog1.png"};
    }
}
