package Menu;

import java.util.HashMap;
import java.util.Objects;

class Language {

    String[] languageList;
    private String language;
    private HashMap<String, String> english = new HashMap<>();
    private HashMap<String, String> french = new HashMap<>();
    private HashMap<String, String> german = new HashMap<>();

    Language() {
        languageList = new String[3];
        language = "English";
        fillLanguageListArray();
        fillEnglishMap();
        fillGermanMap();
        fillFrenchMap();
    }

    void setLanguage(String lang) {
        this.language = lang;
    }

    String getLanguage() {
        return this.language;
    }

    HashMap getCurrentLanguage() {
        if (Objects.equals(language, "English")) {
            return english;
        } else if (Objects.equals(language, "French")) {
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
        english.put("Sound Volume", "  Sound Volume:  ");
        english.put("Mute Sound", "  Mute Sound:  ");
        english.put("Language", "  Language:  ");
        english.put("Sound Settings", "Sound Settings:");
        english.put("Language Settings", "Language Settings:");
    }

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

    private void fillLanguageListArray() {
        languageList = new String[]{"English", "French", "German"};
    }
}
