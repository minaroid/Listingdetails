package app.details.listing.intcore.george.mina.listingdetailsapp.ui.fragments.discussionFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.details.listing.intcore.george.mina.listingdetailsapp.R;
import app.details.listing.intcore.george.mina.listingdetailsapp.adapters.PostsAdapter;
import app.details.listing.intcore.george.mina.listingdetailsapp.models.ViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscussionFragment  extends Fragment{
    @BindView(R.id.rv_posts_list)
    RecyclerView recyclerView;
    private ViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = (ViewModel) getArguments().getSerializable("viewModel");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discussion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new PostsAdapter(viewModel,getContext()));
    }
}
