package amrish.ravidas.com.alpha;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import amrish.ravidas.com.alpha.databinding.TicTacToeGameBinding;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TicTacToeFragment extends Fragment {
    private Unbinder unbinder;
    private TicTacToeGameViewModel viewModel;
    @BindView(R.id.detailsText) TextView detailsText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeViewModel();
        TicTacToeGameBinding binding = DataBindingUtil.inflate(inflater, R.layout.tic_tac_toe_game, container, false);
        binding.setViewModel(viewModel);
        final View view = binding.getRoot();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initializeViewModel() {
        viewModel = new TicTacToeGameViewModel();
        viewModel.init(1);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
