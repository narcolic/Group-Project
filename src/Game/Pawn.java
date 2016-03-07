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
                this.position.setXY(9,0);
                break;
            case 2:
                this.position.setXY(9,17);
                break;
            case 3:
                this.position.setXY(17,9);
                break;
            case 4:
                this.position.setXY(0,9);
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
