package amrish.ravidas.com.alpha;

import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

public class HumanPlayer implements Player {
    static int globalPlayerId = 1;
    private final ViewTicTacToeCell.CellType mCellType;
    private final String mPlayerId;

    public HumanPlayer(ViewTicTacToeCell.CellType cellType) {
        this.mCellType = cellType;
        mPlayerId = String.valueOf(globalPlayerId++);
    }

    @NonNull
    @Override
    public PlayerType getPlayerType() {
        return PlayerType.HUMAN;
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
        throw new UnsupportedOperationException("Not supported for Human player type");
    }
}
