package app.details.listing.intcore.george.mina.listingdetailsapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.details.listing.intcore.george.mina.listingdetailsapp.R;
import app.details.listing.intcore.george.mina.listingdetailsapp.models.ViewModel;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.discussionModel.Comment;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.discussionModel.Post;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.discussionModel.User;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    private ViewModel viewModel ;
    private ArrayList<Comment> comments;
    private ArrayList<User> users;
    private Context context;
    public PostsAdapter(ViewModel model, Context c){
        this.viewModel = model;
        this.comments = model.getComments();
        this.users = model.getUsers();
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = viewModel.getPosts().get(position);
        holder.postTitle.setText(post.getBody());
        for(Comment c : comments){
            if(c.getPostId() == post.getId()){
                holder.postComment.setText(c.getBody());
                holder.postCommentAuth.setText(c.getName());
                break;
            }
        }

        for(User u : users){
            if(u.getId()==post.getUserId()){
                holder.postAuth.setText(u.getName());
                break;
            }
        }

        try{
            Glide.with(context)
                    .load("http://image.tmdb.org/t/p/w342"+ viewModel.getMoviesResults().get(position)
                            .getBackdropPath())
                    .into(holder.postImage);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getPosts().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_post_title)
        TextView postTitle;
        @BindView(R.id.tv_first_comment)
        TextView postComment;
        @BindView(R.id.tv_first_comment_auth)
        TextView postCommentAuth;
        @BindView(R.id.tv_auth_name)
        TextView postAuth;
        @BindView(R.id.img_post)
        ImageView postImage;
        MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}
