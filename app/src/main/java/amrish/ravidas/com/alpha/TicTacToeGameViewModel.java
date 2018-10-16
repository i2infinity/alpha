package amrish.ravidas.com.alpha;

import android.arch.lifecycle.ViewModel;

/**
 * This view model represents the state of a Tic-Tac-Toe game in memory
 * Architecture Reference - https://developer.android.com/jetpack/docs/guide#recommended-app-arch
 */
public class TicTacToeGameViewModel extends ViewModel {
    private int gameId;

    void init(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}
