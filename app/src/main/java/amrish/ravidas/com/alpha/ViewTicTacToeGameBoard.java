package amrish.ravidas.com.alpha;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class ViewTicTacToeGameBoard extends FrameLayout {

    public ViewTicTacToeGameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.game_board, this, true);
    }
}
