package app.details.listing.intcore.george.mina.listingdetailsapp.rest;

import java.util.ArrayList;

import app.details.listing.intcore.george.mina.listingdetailsapp.rest.discussionModel.Comment;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.discussionModel.Post;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.discussionModel.User;
import retrofit2.http.GET;
import rx.Observable;

public interface DiscussionClient {

    @GET("posts")
    Observable<ArrayList<Post>> getPosts();

    @GET("users")
    Observable<ArrayList<User>> getUsers();

    @GET("comments")
    Observable<ArrayList<Comment>> getComments();
}
