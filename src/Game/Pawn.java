package Game;

import java.awt.Color;


public class Pawn {

    private int pawnID;
    private Position position;
    private Color pawnColor;

    /**
     * Constructor
     */
    public Pawn(int newID) {
        pawnID = newID;
        setDefaultPosition();
        setDefaultColor();
    }

    public void updateCurrentPosition(Position newPosition) {
        this.position = newPosition;
    }

    public Position getPosition() {
        return position;
    }

/*    public void setNewPawnID(int newID) {
        this.pawnID = newID;
    }*/

    public void setDefaultPosition() {
        switch (pawnID) {
            case 1:
                this.position = null;
                break;
            case 2:
                this.position = null;
                break;
            case 3:
                this.position = null;
                break;
            case 4:
                this.position = null;
                break;
        }
    }

    public void setDefaultColor() {
        switch (pawnID) {
            case 1:
                this.pawnColor = Color.RED;
                break;
            case 2:
                this.pawnColor = Color.BLUE;
                break;
            case 3:
                this.pawnColor = Color.GREEN;
                break;
            case 4:
                this.pawnColor = Color.YELLOW;
                break;
        }
    }
}
