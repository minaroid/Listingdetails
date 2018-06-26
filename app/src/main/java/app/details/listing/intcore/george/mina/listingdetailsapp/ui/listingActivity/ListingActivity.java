package app.details.listing.intcore.george.mina.listingdetailsapp.ui.listingActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import app.details.listing.intcore.george.mina.listingdetailsapp.R;
import app.details.listing.intcore.george.mina.listingdetailsapp.adapters.ListingAdapter;
import app.details.listing.intcore.george.mina.listingdetailsapp.models.ViewModel;
import app.details.listing.intcore.george.mina.listingdetailsapp.ui.detailsActivity.DetailsActivity;
import app.details.listing.intcore.george.mina.listingdetailsapp.utils.CommonUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListingActivity extends AppCompatActivity implements ListingActivityVP.View
,View.OnClickListener{
    @BindView(R.id.rv_movies_list)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_connection_layout)
    LinearLayout noConnectionLayout;
    private ListingActivityVP.Presenter presenter;
    private ListingAdapter listingAdapter;
    private SharedPreferences.Editor preferences;
    private GridLayoutManager layoutManager;
    private CommonUtils utils = new CommonUtils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        preferences  = getSharedPreferences("home",MODE_PRIVATE).edit();

        presenter = new ListingActivityPresenter(this);
        presenter.setView(this);

        listingAdapter = new ListingAdapter(this);
        layoutManager = new GridLayoutManager(this, 2);
        listingAdapter.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listingAdapter);
        if(utils.checkConnection(this)){
        presenter.loadData();}
        else {
            noConnectionLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(utils.checkConnection(this)){
        switch (item.getItemId()){
            case R.id.action_popular:
                preferences.putInt("currentValue",1);
                preferences.commit();
                presenter.loadData();
                break;
            case R.id.action_rate:
                preferences.putInt("currentValue",2);
                preferences.commit();
                presenter.loadData();
                break;
            case R.id.action_span:
                switchLayout();
                switchIcon(item);
                break;
        } } else {
            noConnectionLayout.setVisibility(View.VISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadingFinished(ViewModel model) {
        progressBar.setVisibility(View.GONE);
       listingAdapter.swapData(model);
    }

    @Override
    public void openDetailsActivity(int pos,ViewModel model) {
        Intent i = new Intent(this, DetailsActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("viewModel", model);
        b.putInt("pos",pos);
        i.putExtras(b);
        startActivity(i);

    }

    @Override
    public void showMessage(int x) {
        switch (x){

            case 0:
                Toast.makeText(this, R.string.msg_try_later,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void switchIcon(MenuItem item) {
        if (layoutManager.getSpanCount() >= 2) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_format_align_justify));

        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_view_module));
        }
    }

    private void switchLayout() {
        int currentState = layoutManager.getSpanCount();
        if (currentState == 1) {
            layoutManager.setSpanCount(2);
        } else {
            layoutManager.setSpanCount(1);
        }
        listingAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.rxUnsubscribe();
    }

    @OnClick(R.id.no_connection_layout)
    @Override
    public void onClick(View v) {
        if(utils.checkConnection(this)){
            presenter.loadData();
            noConnectionLayout.setVisibility(View.GONE);
        }
    }
}
