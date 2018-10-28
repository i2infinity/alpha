package amrish.ravidas.com.alpha;

import android.util.SparseArray;

import androidx.annotation.NonNull;

import java.util.Arrays;

class GameState {

    private final ViewTicTacToeCell.CellType[][] mBlocks;
    private Player[] mPlayers;
    private GameStatus mGameStatus;
    private int mCurrentPlayerIndex;

    public GameState(Player player1, Player player2) {
        mBlocks = new ViewTicTacToeCell.CellType[3][3];
        final ViewTicTacToeCell.CellType[] blocks = new ViewTicTacToeCell.CellType[3];
        Arrays.fill(blocks, ViewTicTacToeCell.CellType.NONE);
        Arrays.fill(mBlocks, blocks);
        mGameStatus = GameStatus.InProgress;
        mPlayers = new Player[]{ player1, player2 };
        mCurrentPlayerIndex = 0;
    }

    enum GameStatus {
        InProgress,
        HasWinner,
        Draw,
    }

    enum CellPosition {
        A(0, "0X0"),
        B(1, "0X1"),
        C(2, "0x2"),
        D(3, "1X0"),
        E(4, "1X1"),
        F(5, "1x2"),
        G(6, "2X0"),
        H(7, "2X1"),
        I(8, "2x2");

        private String mPosition;
        private int mIndex;
        private static SparseArray<CellPosition> lookupMap;
        CellPosition(int index, String address) {
            this.mIndex = index;
            this.mPosition = address;
        }

        public String getValue() {
            return mPosition;
        }

        static {
            lookupMap = new SparseArray<>();
            for (CellPosition value : CellPosition.values()) {
                lookupMap.put(value.mIndex, value);
            }
        }

        public static CellPosition valueOf(int index) {
            return lookupMap.get(index);
        }
    }

    @NonNull
    Player getCurrentPlayer() {
        return mPlayers[mCurrentPlayerIndex];
    }

    private int getNextPlayerIndex() {
        return ++mCurrentPlayerIndex % 2;
    }

    @NonNull
    Player getNextPlayer() {
        return mPlayers[getNextPlayerIndex()];
    }

    @NonNull
    ViewTicTacToeCell.CellType[][] getGridState() {
     return mBlocks;
    }

    @NonNull
    GameStatus getStatus() {
        return mGameStatus;
    }

    @NonNull
    ViewTicTacToeCell.CellType getBlockTypeAtPosition(final CellPosition position) {
        switch (position) {
            case A:
                return mBlocks[0][0];
            case B:
                return mBlocks[0][1];
            case C:
                return mBlocks[0][2];
            case D:
                return mBlocks[1][0];
            case E:
                return mBlocks[1][1];
            case F:
                return mBlocks[1][2];
            case G:
                return mBlocks[2][0];
            case H:
                return mBlocks[2][1];
            case I:
                return mBlocks[2][2];
        }
        throw new UnsupportedOperationException("Invalid position value of " + position);
    }

    void moveNext(final CellPosition cellPosition) {
        // TODO - UPDATE THE PROVIDED BLOCK INDEX
        // TODO - CHECK IF THE GAME HAS WINNER/DRAW
        // TODO -   IN PROGRESS - UPDATE NEXT AND CURRENT PLAYER
        // TODO -   WINNER - UPDATE THE WINNER
        // TODO -   DRAW - COMPLETE GAME
        mCurrentPlayerIndex = getNextPlayerIndex();
    }
}
