package app.details.listing.intcore.george.mina.listingdetailsapp.ui.fragments.overviewFragmnet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.details.listing.intcore.george.mina.listingdetailsapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewFragment extends Fragment {
    @BindView(R.id.tv_overview)
    TextView overview;
    private String overviewText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overviewText = getArguments().getString("overview");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        overview.setText(overviewText);
    }
}
