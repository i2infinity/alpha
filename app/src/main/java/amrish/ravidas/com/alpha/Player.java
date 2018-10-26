package amrish.ravidas.com.alpha;

import android.arch.lifecycle.LiveData;

public interface Player {
    enum PlayerType {
        HUMAN,
        AI
    }

    PlayerType getPlayerType();

    ViewTicTacToeBlock.BlockType getPlayerBlockType();

    /**
     * Unique player identifier
     */
    String getPlayerId();

    /**
     * Execute the next step for an in-progress game for a non-human player
     * @throws IllegalStateException when invoked after a game is complete or for a PlayerType
     * that does not support
     */
    LiveData<GameState> playNext() throws IllegalStateException;
}
