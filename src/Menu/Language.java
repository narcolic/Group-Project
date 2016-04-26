package Menu;

import java.util.HashMap;

class Language {

    String[] languageList;
    private String language;
    private HashMap<String, String> english = new HashMap<>();
    private HashMap<String, String> french = new HashMap<>();
    private HashMap<String, String> german = new HashMap<>();
    private int languageNo;

    Language() {
        languageList = new String[3];
        language = "English";
        languageNo = 0;
        fillLanguageListArray();
        fillEnglishMap();
        fillGermanMap();
        fillFrenchMap();
    }

    void setLanguage(String language) {
        this.language = language;
    }

    String getLanguage() {
        return this.language;
    }

    HashMap getCurrentLanguage() {
        if (languageNo == 0) {
            return english;
        } else if (languageNo == 1) {
            return french;
        } else {
            return german;
        }

    }

    private void fillEnglishMap() {
        english.put("Start", "Start");
        english.put("Options", "Options");
        english.put("Help", "Help");
        english.put("Quit", "Quit");
        english.put("Back", "Back");
        english.put("Sound Settings", "Sound Settings: ");
        english.put("Language Settings", "Language Settings: ");
    }

    private void fillFrenchMap() {
        french.put("Start", "blastart1");
        french.put("Options", "blaOptions1");
        french.put("Help", "blaHelp1");
        french.put("Quit", "blaQuit1");
        french.put("Back", "blaBack1");
        french.put("Language Settings", "blaLanguage Settings1: ");
    }

    private void fillGermanMap() {
        german.put("Start", "blastart3");
        german.put("Options", "blaOptions3");
        german.put("Help", "blaHelp3");
        german.put("Quit", "blaQuit3");
        german.put("Back", "blaBack3");
        german.put("Language Settings", "blaLanguage Settings3: ");
    }

    private void fillLanguageListArray() {
        languageList = new String[]{"English", "French", "German"};
    }
}
