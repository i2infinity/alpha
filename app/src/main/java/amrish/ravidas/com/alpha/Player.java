package amrish.ravidas.com.alpha;

import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

public interface Player {
    enum PlayerType {
        HUMAN,
        AI
    }

    @NonNull
    PlayerType getPlayerType();

    @NonNull
    ViewTicTacToeCell.CellType getPlayerBlockType();

    /**
     * Unique player identifier
     */
    @NonNull
    String getPlayerId();

    /**
     * Execute the next step for an in-progress game for a non-human player
     * @throws IllegalStateException when invoked after a game is complete or for a PlayerType
     * that does not support
     */
    @NonNull
    LiveData<GameState> playNext() throws IllegalStateException;
}
