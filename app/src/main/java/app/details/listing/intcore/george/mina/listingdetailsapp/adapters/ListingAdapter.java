package app.details.listing.intcore.george.mina.listingdetailsapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import app.details.listing.intcore.george.mina.listingdetailsapp.R;
import app.details.listing.intcore.george.mina.listingdetailsapp.models.ViewModel;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.moviesModel.Result;
import app.details.listing.intcore.george.mina.listingdetailsapp.ui.listingActivity.ListingActivity;
import app.details.listing.intcore.george.mina.listingdetailsapp.ui.listingActivity.ListingActivityVP;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.MyViewHolder>implements View.OnClickListener{

    private ArrayList<Result> results = new ArrayList<>();
    private ViewModel viewModel;
    private Context mContext;
    private ListingActivityVP.View activityView;
    private GridLayoutManager gridLayoutManager = null;

    public ListingAdapter(Context mContext) {
        this.mContext = mContext;
        this.activityView = (ListingActivity)mContext;
    }

    @NonNull
    @Override
    public ListingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ListingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListingAdapter.MyViewHolder holder, int position) {
        Result result = results.get(position);
        int span = gridLayoutManager.getSpanCount();
        if(span>1){

            holder.recLayout.setVisibility(View.GONE);
            holder.squLayout.setVisibility(View.VISIBLE);
            holder.titleSqu.setText(result.getTitle());
            holder.dateSqu.setText(result.getReleaseDate());
            holder.ratingBarSqu.setText(result.getVoteAverage() +"/10");

            try{
                Glide.with(mContext)
                        .load("http://image.tmdb.org/t/p/w342"+result.getPosterPath())
                        .into(holder.imageSqu);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else {

            holder.recLayout.setVisibility(View.VISIBLE);
            holder.squLayout.setVisibility(View.GONE);
            holder.titleRec.setText(result.getTitle());
            holder.dateRec.setText(result.getReleaseDate());
            holder.ratingBarRec.setText(result.getVoteAverage() +"/10");

            try{
                Glide.with(mContext)
                        .load("http://image.tmdb.org/t/p/w342"+result.getPosterPath())
                        .into(holder.imageRec);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }


        holder.itemView.setTag(position);


    }

    public void setLayoutManager(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }


    @Override
    public int getItemCount() {
        return results.size();
    }

    public void swapData(ViewModel viewModel){
        this.viewModel = viewModel;
        this.results = viewModel.getMoviesResults();
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rec_id)
        CardView recLayout;
        @BindView(R.id.squ_id)
        CardView squLayout;
        @BindView(R.id.tv_name_rec)
        TextView titleRec;
        @BindView(R.id.tv_date_rec)
        TextView dateRec;
        @BindView(R.id.tv_rating_rec)
        TextView ratingBarRec;
        @BindView(R.id.img_movie_rec)
        ImageView imageRec;

        @BindView(R.id.tv_name_squ)
        TextView titleSqu;
        @BindView(R.id.tv_date_squ)
        TextView dateSqu;
        @BindView(R.id.tv_rating_squ)
        TextView ratingBarSqu;
        @BindView(R.id.img_movie_squ)
        ImageView imageSqu;

        MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
            v.setOnClickListener(ListingAdapter.this);
        }
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        activityView.openDetailsActivity(pos,viewModel);
    }
}
