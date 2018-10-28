package amrish.ravidas.com.alpha;

import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

public class AIPlayer implements Player {
    private final ViewTicTacToeCell.CellType mCellType;
    private final String mPlayerId;

    public AIPlayer(ViewTicTacToeCell.CellType cellType) {
        this.mCellType = cellType;
        mPlayerId = String.valueOf(HumanPlayer.globalPlayerId++);
    }

    @NonNull
    @Override
    public PlayerType getPlayerType() {
        return PlayerType.AI;
    }

    @NonNull
    @Override
    public ViewTicTacToeCell.CellType getPlayerBlockType() {
        return mCellType;
    }

    @NonNull
    @Override
    public String getPlayerId() {
        return mPlayerId;
    }

    @NonNull
    @Override
    public LiveData<GameState> playNext() throws IllegalStateException {
        throw new UnsupportedOperationException("Not Implemented");
    }
}
