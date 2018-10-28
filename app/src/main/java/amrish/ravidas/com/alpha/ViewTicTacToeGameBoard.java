package amrish.ravidas.com.alpha;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewTicTacToeGameBoard extends FrameLayout {

    private final float[] mLastTouchDownXY = new float[2];

    @Nullable
    private TicTacToeGridClickListener mOnGridClickListener;

    @BindView(R.id.block_1_1)
    ViewTicTacToeCell block_1_1;
    @BindView(R.id.block_1_2)
    ViewTicTacToeCell block_1_2;
    @BindView(R.id.block_1_3)
    ViewTicTacToeCell block_1_3;
    @BindView(R.id.block_2_1)
    ViewTicTacToeCell block_2_1;
    @BindView(R.id.block_2_2)
    ViewTicTacToeCell block_2_2;
    @BindView(R.id.block_2_3)
    ViewTicTacToeCell block_2_3;
    @BindView(R.id.block_3_1)
    ViewTicTacToeCell block_3_1;
    @BindView(R.id.block_3_2)
    ViewTicTacToeCell block_3_2;
    @BindView(R.id.block_3_3)
    ViewTicTacToeCell block_3_3;
    @BindView(R.id.gameGrid) ViewGameGrid gameGrid;

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            // save the X,Y coordinates
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                mLastTouchDownXY[0] = event.getX();
                mLastTouchDownXY[1] = event.getY();
            }

            // let the touch event pass on to whoever needs it
            return false;
        }
    };

    public ViewTicTacToeGameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.game_board, this, true);
        ButterKnife.bind(this, view);
        gameGrid.setOnTouchListener(touchListener);
    }

    void setOnGridClickListener(TicTacToeGridClickListener listener) {
        mOnGridClickListener = listener;
    }

    @OnClick(R.id.gameGrid)
    public void onGridClick(View view) {
        float x = mLastTouchDownXY[0];
        float y = mLastTouchDownXY[1];
        int gridPosition = gameGrid.getNonantPosition(x, y);
        if (gridPosition >= 0 && gridPosition <= 8) {
            if (mOnGridClickListener != null) {
                mOnGridClickListener.onClick(GameState.CellPosition.valueOf(gridPosition));
            }
        }
    }

    public void startAnimation(GameState.CellPosition cellPosition, ViewTicTacToeCell.CellType type) {
        final ViewTicTacToeCell cell = getBlock(cellPosition);
        if (cell != null) {
            cell.setType(type);
            cell.startAnimator();
        }
    }

    @Nullable
    private ViewTicTacToeCell getBlock(GameState.CellPosition cellPosition) {
        switch (cellPosition) {
            case A:
                return block_1_1;
            case B:
                return block_1_2;
            case C:
                return block_1_3;
            case D:
                return block_2_1;
            case E:
                return block_2_2;
            case F:
                return block_2_3;
            case G:
                return block_3_1;
            case H:
                return block_3_2;
            case I:
                return block_3_3;
        }
        return null;
    }

    interface TicTacToeGridClickListener {
        /**
         * Invoked when user clicks on a tic-tac-toe block
         * @param cellPosition The position of the block in Grid
         */
        void onClick(GameState.CellPosition cellPosition);
    }
}
