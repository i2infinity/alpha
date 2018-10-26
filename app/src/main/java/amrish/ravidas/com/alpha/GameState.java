package amrish.ravidas.com.alpha;

import android.support.annotation.NonNull;

class GameState {

    enum GameStatus {
        InProgress,
        HasWinner,
        Draw,
    }

    @NonNull
    Player getCurrentPlayer() {
        return null;
    }

    @NonNull
    Player getNextPlayer() {
        return null;
    }

    @NonNull
    ViewTicTacToeBlock.BlockType[][] getGridState() {
     return null;
    }

    @NonNull
    GameStatus getStatus() {
        return null;
    }
}
