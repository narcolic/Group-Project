package Game;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Statistics {

    private final SimpleStringProperty playerName;
    private final SimpleIntegerProperty moves;
    private final SimpleIntegerProperty fences;

    Statistics(String playerName, Integer moves, Integer fences) {
        this.playerName = new SimpleStringProperty(playerName);
        this.moves = new SimpleIntegerProperty(moves);
        this.fences = new SimpleIntegerProperty(fences);
    }

    public String getPlayerName() {
        return playerName.get();
    }

    public void setPlayerName(String player) {
        playerName.set(player);
    }

    public Integer getMoves() {
        return moves.get();
    }

    public void setMoves(Integer move) {
        moves.set(move);
    }

    public int getFences() {
        return fences.get();
    }

    public void setFences(Integer fence) {
        fences.set(fence);
    }
}

