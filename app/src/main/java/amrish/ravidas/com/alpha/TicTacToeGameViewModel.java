package amrish.ravidas.com.alpha;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

/**
 * This view model represents the state of a Tic-Tac-Toe game in memory
 * Architecture Reference - https://developer.android.com/jetpack/docs/guide#recommended-app-arch
 */
public class TicTacToeGameViewModel extends ViewModel {
    private int gameId;
    private GameState mCurrentState;

    void init(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

    int getSelectedBlock(float xPosition, float yPosition) {
        throw new UnsupportedOperationException("Not implmenented");
    }

    boolean canPlay(int blockPosition) {
        if (blockPosition < 0 || blockPosition > 8) {
            throw new IllegalArgumentException("Value should be from 0-8");
        }
        throw new UnsupportedOperationException("Not implmenented");
    }

    LiveData<GameState> onGridClicked(int blockPosition) {
        if (!canPlay(blockPosition)) {
            throw new IllegalStateException("Player cannot access provided block position");
        }
        return null;
    }

    GameState getCurrentState() {
        return mCurrentState;
    }
}
