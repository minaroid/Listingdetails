package app.details.listing.intcore.george.mina.listingdetailsapp.models;

import java.io.Serializable;
import java.util.ArrayList;

import app.details.listing.intcore.george.mina.listingdetailsapp.rest.discussionModel.Comment;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.discussionModel.Post;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.discussionModel.User;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.moviesModel.Result;

public class ViewModel implements Serializable{

    private ArrayList<Post> posts;
    private ArrayList<Comment> comments;
    private ArrayList<User> users;
    private ArrayList<Result> moviesResults;


    public ViewModel(ArrayList<Result> moviesResults,ArrayList<Post> posts,
                     ArrayList<Comment> comments,ArrayList<User> users){
        this.comments = comments;
        this.moviesResults = moviesResults;
        this.posts = posts;
        this.users = users;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Result> getMoviesResults() {
        return moviesResults;
    }

    public void setMoviesResults(ArrayList<Result> moviesResults) {
        moviesResults = moviesResults;
    }
}
