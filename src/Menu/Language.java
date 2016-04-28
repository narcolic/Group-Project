package Menu;

import java.util.HashMap;
import java.util.Objects;

class Language {

    String[] languageList; // list for the different languages
    private String language; // language selected
    private HashMap<String, String> english = new HashMap<>();
    private HashMap<String, String> french = new HashMap<>();
    private HashMap<String, String> german = new HashMap<>();

    /**
     * Constructor 
     */
    Language() {
        languageList = new String[3];
        language = "English"; // default language is English
        fillLanguageListArray();
        fillEnglishMap();
        fillGermanMap();
        fillFrenchMap();
    }

    /**
     * Set the preferred language for the game
     * @param lang New language
     */
    void setLanguage(String lang) {
        this.language = lang;
    }

    /**
     * Get the language current used within the game
     * @return language Language currently selected
     */
    String getLanguage() {
        return this.language;
    }

    /**
     * Get the languages within the game
     * @return english, french, german Language depending on what the user has selected
     */
    HashMap getCurrentLanguage() {
        if (Objects.equals(language, "English")) {
            return english;
        } else if (Objects.equals(language, "French")) {
            return french;
        } else {
            return german;
        }
    }

    /*
     * Convert the default English commands into different languages 
     */
    
    /**
     * Set the English commands for the game
     */
    private void fillEnglishMap() {
        english.put("Start", "Start");
        english.put("Options", "Options");
        english.put("Help", "Help");
        english.put("Quit", "Quit");
        english.put("Back", "Back");
        english.put("Sound Volume", "  Sound Volume:  ");
        english.put("Mute Sound", "  Mute Sound:  ");
        english.put("Language", "  Language:  ");
        english.put("Sound Settings", "Sound Settings:");
        english.put("Language Settings", "Language Settings:");
    }

    /**
     * Map the French equivalent commands to the default English commands
     */
    private void fillFrenchMap() {
        french.put("Start", "Entamer");
        french.put("Options", "Paramètres");
        french.put("Help", "Aidez-moi");
        french.put("Quit", "Quitter");
        french.put("Back", "Arrière");
        french.put("Sound Volume", "  Volume Sonore:  ");
        french.put("Mute Sound", "  Muet:  ");
        french.put("Language", "  Langue:  ");
        french.put("Sound Settings", "Paramètres du son:");
        french.put("Language Settings", "Paramètres de langue: ");
    }

    /**
     * Map the German equivalent commands to the default English commands
     */
    private void fillGermanMap() {
        german.put("Start", "Anfang");
        german.put("Options", "Optionen");
        german.put("Help", "Hilfe");
        german.put("Quit", "Ausgang");
        german.put("Back", "Zurück");
        german.put("Sound Volume", "  Lautstärke:  ");
        german.put("Mute Sound", "  Stumm:  ");
        german.put("Language", "  Sprache:  ");
        german.put("Sound Settings", "Audioeinstellungen:");
        german.put("Language Settings", "Spracheinstellungen: ");
    }

    /**
     * Set the different languages to the list of languages
     */
    private void fillLanguageListArray() {
        languageList = new String[]{"English", "French", "German"};
    }
}
