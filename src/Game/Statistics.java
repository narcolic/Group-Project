package Game;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Statistics {

    private final SimpleStringProperty playerName;
    private final SimpleDoubleProperty moves;
    private final SimpleIntegerProperty fences;

    Statistics(String playerName, Double moves, Integer fences) {
        this.playerName = new SimpleStringProperty(playerName);
        this.moves = new SimpleDoubleProperty(moves);
        this.fences = new SimpleIntegerProperty(fences);
    }

    public String getPlayerName() {
        return playerName.get();
    }

    public void setPlayerName(String player) {
        playerName.set(player);
    }

    public Double getMoves() {
        return moves.get();
    }

    public void setMoves(Double move) {
        moves.set(move);
    }

    public int getFences() {
        return fences.get();
    }

    public void setFences(Integer fence) {
        fences.set(fence);
    }
}

