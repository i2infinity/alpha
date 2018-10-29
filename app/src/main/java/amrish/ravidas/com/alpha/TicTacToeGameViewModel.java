package amrish.ravidas.com.alpha;

import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

/**
 * This view model represents the state of a Tic-Tac-Toe game in memory
 * Architecture Reference - https://developer.android.com/jetpack/docs/guide#recommended-app-arch
 */
public class TicTacToeGameViewModel extends ViewModel {
    private int mGameId;

    // TODO - Make NonNull
    @Nullable
    private GameState mCurrentState;

    public int getGameId() {
        return mGameId;
    }

    boolean canPlay(GameState.CellPosition cellPosition) {
        if (mCurrentState == null) {
            throw new IllegalStateException("Game not initialized");
        }
        if (mCurrentState.getStatus().equals(GameState.GameStatus.HasWinner)) {
            return false;
        }
        return mCurrentState.getBlockTypeAtPosition(cellPosition).equals(ViewTicTacToeCell.CellType.NONE);
    }

    @NonNull
    GameState onGridClicked(GameState.CellPosition cellPosition) {
        if (!canPlay(cellPosition)) {
            throw new IllegalStateException("Player cannot access provided block position");
        }

        mCurrentState.moveNext(cellPosition);
        return mCurrentState;
    }

    @Nullable
    GameState getCurrentState() {
        return mCurrentState;
    }

    void init(Player player1, Player player2) {
        if (player1.getPlayerBlockType().equals(player2.getPlayerBlockType())) {
            throw new IllegalArgumentException("Player 1 and Player 2 cannot share same block type");
        }
        mGameId += 1;
        mCurrentState = new GameState(player1, player2);
    }
}
