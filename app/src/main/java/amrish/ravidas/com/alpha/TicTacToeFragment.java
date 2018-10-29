package amrish.ravidas.com.alpha;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import amrish.ravidas.com.alpha.databinding.TicTacToeGameBinding;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TicTacToeFragment extends Fragment {
    private Unbinder unbinder;
    private TicTacToeGameViewModel viewModel;
    @BindView(R.id.gameId) TextView detailsText;
    @BindView(R.id.gameBoard) ViewTicTacToeGameBoard gameBoard;
    private ViewTicTacToeGameBoard.TicTacToeGridClickListener listener = new ViewTicTacToeGameBoard.TicTacToeGridClickListener() {
        @Override
        public void onClick(GameState.CellPosition cellPosition) {
            // TODO - Can this leak memory?
            if (viewModel.canPlay(cellPosition)) {
                gameBoard.startAnimation(cellPosition, viewModel.getCurrentState().getCurrentPlayer().getPlayerBlockType());
                final GameState state = viewModel.onGridClicked(cellPosition);
                if (state.getStatus().equals(GameState.GameStatus.HasWinner)) {
                    Toast.makeText(TicTacToeFragment.this.getContext(), "We have a winner", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeViewModel();
        TicTacToeGameBinding binding = DataBindingUtil.inflate(inflater, R.layout.tic_tac_toe_game, container, false);
        binding.setViewModel(viewModel);
        final View view = binding.getRoot();
        unbinder = ButterKnife.bind(this, view);
        gameBoard.setOnGridClickListener(listener);
        return view;
    }

    private void initializeViewModel() {
        viewModel = new TicTacToeGameViewModel(); // TODO Use ViewModelProviders.of(...) factory
        viewModel.init(new HumanPlayer(ViewTicTacToeCell.CellType.CIRCLE),
                new HumanPlayer(ViewTicTacToeCell.CellType.CROSS));
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
