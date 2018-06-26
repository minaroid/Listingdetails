package app.details.listing.intcore.george.mina.listingdetailsapp.ui.detailsActivity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.details.listing.intcore.george.mina.listingdetailsapp.R;
import app.details.listing.intcore.george.mina.listingdetailsapp.adapters.DetailsPagerAdapter;
import app.details.listing.intcore.george.mina.listingdetailsapp.models.ViewModel;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.moviesModel.Result;
import app.details.listing.intcore.george.mina.listingdetailsapp.ui.fragments.discussionFragment.DiscussionFragment;
import app.details.listing.intcore.george.mina.listingdetailsapp.ui.fragments.overviewFragmnet.OverviewFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.img_cover)
    ImageView coverImage;

    private ViewModel viewModel;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getIntent()!=null){
            this.viewModel = (ViewModel) getIntent().getExtras().getSerializable("viewModel");
            this.result = viewModel.getMoviesResults().get(getIntent().getIntExtra("pos",0));
        }

        initViewPager();
        initToolbar();
    }

    private void initToolbar() {
        ratingBar.setRating((float) 4.5);
        try{
            Glide.with(this)
                    .load("http://image.tmdb.org/t/p/w342"+result.getBackdropPath())
                    .into(coverImage);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private void initViewPager() {
        DetailsPagerAdapter adapter = new DetailsPagerAdapter(getSupportFragmentManager());

        DiscussionFragment discussionFragment = new DiscussionFragment();
        OverviewFragment overviewFragment = new OverviewFragment();
        Bundle bundle  = new Bundle();
        bundle.putSerializable("viewModel",viewModel);
        bundle.putString("overview",result.getOverview());
        discussionFragment.setArguments(bundle);
        overviewFragment.setArguments(bundle);

        adapter.addFragment(overviewFragment);
        adapter.addFragment(discussionFragment);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        View view1 = getLayoutInflater().inflate(R.layout.item_tab_layout, null);
        TextView t1 =  view1.findViewById(R.id.tv_tab);
        t1.setText(R.string.tab_overview);
        t1.setBackground(getResources().getDrawable(R.drawable.cornered_textview));
        t1.setTextColor(getResources().getColor(R.color.white));
        tabLayout.getTabAt(0).setCustomView(view1);

        View view2 = getLayoutInflater().inflate(R.layout.item_tab_layout, null);
        TextView t2 =  view2.findViewById(R.id.tv_tab);
        t2.setText(R.string.tab_discussion);
        t2.setBackgroundColor(getResources().getColor(R.color.white));
        t2.setTextColor(getResources().getColor(R.color.gray));
        tabLayout.getTabAt(1).setCustomView(view2);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View v = tab.getCustomView();
                TextView t = v.findViewById(R.id.tv_tab);
                t.setBackground(getResources().getDrawable(R.drawable.cornered_textview));
                t.setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View v = tab.getCustomView();
                TextView t=  v.findViewById(R.id.tv_tab);
                t.setBackgroundColor(getResources().getColor(R.color.white));
                t.setTextColor(getResources().getColor(R.color.gray));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setCurrentItem(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }
}
