package Menu;

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

    String getCurrentText() {
        return this.textHelp[this.slideNumber];
    }

    String getCurrentImageLocation() {
        return this.imageLoc[this.slideNumber];
    }

    void nextSlide() {
        if (isNextAvailable()) {
            this.slideNumber++;
        }
    }

    boolean isNextAvailable() {
        return this.slideNumber < (textHelp.length - 1);
    }

    boolean isPreviousAvailable() {
        return slideNumber >= 1;
    }

    void previousSlide() {
        if (isPreviousAvailable()) {
            this.slideNumber--;
        }
    }

    void fillTextHelpArray() {
        textHelp = new String[]{"sadasdsadas1",
                "asdasdasdsad2",
                "asdasdasdad3",
                "dsadasdsad4",
                "asdasdas5"};
    }

    void fillImageLocArray() {
        imageLoc = new String[]{"/Menu/Images/dog1.png",
                "/Menu/Images/dog.png",
                "/Menu/Images/dog1.png",
                "/Menu/Images/dog.png",
                "/Menu/Images/dog1.png"};
    }
}
