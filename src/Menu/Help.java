package Menu;

import java.util.HashMap;

public class Help {

    private String[] englishHelp; // stores text help instructions
    private String[] frenchHelp; // stores text help instructions
    private String[] germanHelp; // stores text help instructions
    private HashMap<String, String[]> textHelp1 = new HashMap<>();
    private String[] imageLoc; // stores image help instructions
    private int slideNumber; // index of current instruction
    private String language;

    /**
     * Constructor
     */
    public Help() {
        englishHelp = new String[5];
        frenchHelp = new String[5];
        germanHelp = new String[5];
        imageLoc = new String[5];
        slideNumber = 0;
        language = "English";
    }

    /**
     * Get the current text help instruction
     *
     * @return textHelp The current text help instruction
     */
    String getCurrentText() {
        return textHelp1.get(this.language)[this.slideNumber];

    }

    void setCurrentLanguage(String lang) {
        this.language = lang;
    }

    /**
     * Get the current image help instruction
     *
     * @return imageLoc The location of the image
     */
    String getCurrentImageLocation() {
        return this.imageLoc[this.slideNumber];
    }

    /**
     * Goes to next help instruction
     */
    void nextSlide() {
        if (isNextAvailable()) {
            this.slideNumber++; // increment index
        }
    }

    /**
     * Check to see if there is a next help instruction
     *
     * @return slideNumber Index within the array length
     */
    boolean isNextAvailable() {
        return this.slideNumber < (englishHelp.length - 1);
    }

    /**
     * Check to see if there is a previous help instruction
     *
     * @return slideNumber Index pointing to instruction within array
     */
    boolean isPreviousAvailable() {
        return slideNumber >= 1;
    }

    /**
     * Go to previous help instruction
     */
    void previousSlide() {
        if (isPreviousAvailable()) {
            this.slideNumber--; // decrement index
        }
    }

    /**
     * Initialise the text help instructions
     */
    void fillTextHelpArray() {
        englishHelp = new String[]{"sadasdsadas1", "asdasdasdsad2", "asdasdasdad3", "dsadasdsad4", "asdasdas5"};
        frenchHelp = new String[]{"frenchasdasdasd1", "frenchasdasdasdsad2", "frenchasdasdasdad3", "frenchdsadasdsad4", "frenchasdasdas5"};
        germanHelp = new String[]{"germansadasdsadas1", "germanasdasdasdsad2", "germanasdasdasdad3", "germandsadasdsad4", "germanasdasdas5"};

        textHelp1.put("English", englishHelp);
        textHelp1.put("French", frenchHelp);
        textHelp1.put("German", germanHelp);
    }

    /**
     * Initialise the help image url's
     */
    void fillImageLocArray() {
        imageLoc = new String[]{"/Menu/Images/Help/dog1.png",
                "/Menu/Images/Help/dog.png",
                "/Menu/Images/Help/dog1.png",
                "/Menu/Images/Help/dog.png",
                "/Menu/Images/Help/dog1.png"};
    }
}
