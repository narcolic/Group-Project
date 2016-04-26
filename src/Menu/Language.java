package Menu;

import java.util.HashMap;

public class Language {

    public String[] languageList;
    private String language;
    private HashMap<String, String> english = new HashMap<String, String>();
    private HashMap<String, String> french = new HashMap<String, String>();

    public Language() {
        languageList = new String[3];
        language = "English";
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public HashMap getCurrentLanguage() {
        if (language.equals("English")){
            return english;
        }else{
            return french;
        }

    }

    public void fillEnglishMap(){
        english.put("Start", "Start1");
        english.put("Options", "Options1");
        english.put("Help", "Help1");
        english.put("Quit", "Quit1");
        english.put("Sound Settings", "Sound Settings1: ");
        english.put("Language Settings", "Language Settings1: ");
    }

    public void fillFrenchMap(){
        french.put("Start", "blastart1");
        french.put("Options", "blaOptions1");
        french.put("Help", "blaHelp1");
        french.put("Quit", "blaQuit1");
        french.put("Language Settings", "blaLanguage Settings1: ");
    }

    public void setCurrentLanguage(String language) {
        this.language = language;
    }

    public void fillLanguageListArray() {
        languageList = new String[]{"English", "French", "German"};
    }
}
