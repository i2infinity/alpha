package amrish.ravidas.com.alpha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.root_layout)
    ConstraintLayout mRootConstraintLayout;

    private TicTacToeFragment mTicTacToeFragment;
//
//    @BindView(R.id.tic_tac_toe_fragment)
//    Fragment mFragmentTicTacToe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        int fullScreenVisibility =
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE;
        getWindow().getDecorView().setSystemUiVisibility(fullScreenVisibility);
        final AnimationDrawable animationDrawable = (AnimationDrawable) mRootConstraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(8000);
        animationDrawable.start();
        mTicTacToeFragment = (TicTacToeFragment)getSupportFragmentManager().findFragmentById(R.id.tic_tac_toe_fragment);
    }

    @OnClick(R.id.resetLayout)
    public void onResetClick(View view) {
        mTicTacToeFragment.resetGame();
    }
}
