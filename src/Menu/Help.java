package Menu;

import java.util.HashMap;

class Help {

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
    Help() {
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
        englishHelp = new String[]{"1.Click on the start button", 
        		"2.Select mode (2 or 4 players)", 
        		"3.Number of walls for each player", 
        		"4.Click on the horizontal or vertical button to place a wall on the grid", 
        		"5.The amount of walls available is in green next to your player number",
        		"6.Tiles you can move to are indicated with a yellow circle", 
        		"7.You cannot block a player", 
        		"8.Get to opposite side to win",
        		"9.Player can only jump over one pawn", 
        		"10.Click the box to enable challenge mode", 
        		"11.In challenge mode, you can remove fences you have placed", 
        		"12.In 4 player mode, each player gets 5 walls", 
        		"13.In challenge mode you start in corners and the goal is the opposite corner", 
        		"14.Click here to start practice mode"};
        frenchHelp = new String[]{"1.Cliquez sur le bouton de démarrage", 
        		"2.Sélectionnez Mode ( 2 ou 4 joueurs)", 
        		"3.Nombre de murs pour chaque joueur", 
        		"4.Cliquez sur le bouton horizontal ou vertical pour placer un mur sur la grille", 
        		"5.La quantité de murs disponibles est en vert à côté de votre numéro de joueur",
        		"6.Tuiles vous pouvez passer à sont indiqués par un cercle jaune", 
        		"7.Vous ne pouvez pas bloquer un joueur", 
        		"8.Apprenez à côté opposé à gagner",
        		"9.Joueur ne peut sauter par-dessus un pion", 
        		"10.Cliquez sur la case pour activer le mode défi", 
        		"11.En mode défi, vous pouvez supprimer les clôtures que vous avez placés", 
        		"12.En mode 4 joueurs, chaque joueur reçoit 5 murs", 
        		"13.En mode défi vous commencez dans les coins et le but est le coin opposé", 
        		"14.Cliquez ici pour commencer le mode de pratique"};
        germanHelp = new String[]{"1.Klicken Sie auf die Start-Taste", 
        		"2.Auswahlmodus (2 oder 4 Spielern)", 
        		"3.Anzahl der Wände für jeden Spieler", 
        		"4.Klicken auf die horizontale oder vertikale Schaltfläche auf dem Gitter eine Wand zu platzieren", 
        		"5.Die Höhe der Wände vorhanden ist in grün neben dem Spielernummer",
        		"6.Fliesen Sie können sich zu bewegen sind mit einem gelben Kreis angezeigt", 
        		"7.Sie können einen Spieler nicht blockieren", 
        		"8.Holen Sie sich gegenüberliegende Seite zu gewinnen",
        		"9.Spieler können nur über einen Pfand springen", 
        		"10.Klicken Sie auf die Box Challenge-Modus zu aktivieren", 
        		"11.Im Challenge-Modus, können Sie entfernen Zäunen Sie gesetzt haben", 
        		"12.In 4-Spieler-Modus, erhält jeder Spieler 5 Wände", 
        		"13.Im Challenge-Modus starten Sie in den Ecken und das Ziel ist, die gegenüberliegende Ecke", 
        		"14.Klicken Sie hier, Praxis-Modus zu starten"};

        textHelp1.put("English", englishHelp);
        textHelp1.put("French", frenchHelp);
        textHelp1.put("German", germanHelp);
    }

    /**
     * Initialise the help image url's
     */
    void fillImageLocArray() {
        imageLoc = new String[]{"/Menu/Images/Help/help1.png",
                "/Menu/Images/Help/help2.png",
                "/Menu/Images/Help/help3.png",
                "/Menu/Images/Help/help4.png",
                "/Menu/Images/Help/help5.png",
                "/Menu/Images/Help/help6.png",
                "/Menu/Images/Help/help7.png",
                "/Menu/Images/Help/help8.png",
                "/Menu/Images/Help/help9.png",
                "/Menu/Images/Help/help10.png",
                "/Menu/Images/Help/help11.png",
                "/Menu/Images/Help/help12.png",
                "/Menu/Images/Help/help13.png",
                "/Menu/Images/Help/help14.png"};
    }
}
