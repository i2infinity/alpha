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

    @BindView(R.id.block_1_1) ViewTicTacToeBlock block_1_1;
    @BindView(R.id.block_1_2) ViewTicTacToeBlock block_1_2;
    @BindView(R.id.block_1_3) ViewTicTacToeBlock block_1_3;
    @BindView(R.id.block_2_1) ViewTicTacToeBlock block_2_1;
    @BindView(R.id.block_2_2) ViewTicTacToeBlock block_2_2;
    @BindView(R.id.block_2_3) ViewTicTacToeBlock block_2_3;
    @BindView(R.id.block_3_1) ViewTicTacToeBlock block_3_1;
    @BindView(R.id.block_3_2) ViewTicTacToeBlock block_3_2;
    @BindView(R.id.block_3_3) ViewTicTacToeBlock block_3_3;
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

    @OnClick(R.id.gameGrid)
    public void onGridClick(View view) {
        float x = mLastTouchDownXY[0];
        float y = mLastTouchDownXY[1];
        int gridPosition = gameGrid.getNonantPosition(x, y);
        if (gridPosition >= 0) {
            final ViewTicTacToeBlock block = getBlock(gridPosition);
            if (block != null) {
                block.setType(ViewTicTacToeBlock.BlockType.CROSS);
                block.startAnimator();
            }
        }
    }

    @Nullable
    private ViewTicTacToeBlock getBlock(int index) {
        switch (index) {
            case 0:
                return block_1_1;
            case 1:
                return block_1_2;
            case 2:
                return block_1_3;
            case 3:
                return block_2_1;
            case 4:
                return block_2_2;
            case 5:
                return block_2_3;
            case 6:
                return block_3_1;
            case 7:
                return block_3_2;
            case 8:
                return block_3_3;
        }
        return null;
    }
}
