package app.details.listing.intcore.george.mina.listingdetailsapp.ui.listingActivity;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import app.details.listing.intcore.george.mina.listingdetailsapp.R;
import java.util.ArrayList;
import app.details.listing.intcore.george.mina.listingdetailsapp.models.ViewModel;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.API;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.discussionModel.Comment;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.discussionModel.Post;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.discussionModel.User;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.moviesModel.Movie;
import app.details.listing.intcore.george.mina.listingdetailsapp.rest.moviesModel.Result;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func4;
import rx.schedulers.Schedulers;


public class ListingActivityPresenter implements ListingActivityVP.Presenter {

    private ListingActivityVP.View view;
    private Subscription subscription = null;
    private SharedPreferences preferences;
    private Context context;
    private ActionBar actionBar;

    public ListingActivityPresenter(Context context){
        this.context =context;
        this.preferences = context.getSharedPreferences("home",Context.MODE_PRIVATE);
        this.actionBar = ((AppCompatActivity) context).getSupportActionBar();
    }

    @Override
    public void setView(ListingActivityVP.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {
        view.startLoading();
        switch (preferences.getInt("currentValue",0)){

            case 1 :
                actionBar.setTitle(R.string.menu_popular_title);
                subscription = getViewModelPopular()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ViewModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                view.showMessage(0);
                            }

                            @Override
                            public void onNext(ViewModel result) {
                                view.loadingFinished(result);
                            }
                        });

                break;
            case 2 :
                actionBar.setTitle(R.string.menu_rate_title);
                subscription = getViewModelTopRated()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ViewModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                view.showMessage(0);
                            }

                            @Override
                            public void onNext(ViewModel result) {
                                view.loadingFinished(result);
                            }
                        });

                break;

            default:
                actionBar.setTitle(R.string.menu_popular_title);
                subscription = getViewModelPopular()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ViewModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                view.showMessage(0);
                            }

                            @Override
                            public void onNext(ViewModel result) {
                                view.loadingFinished(result);
                            }
                        });
        }

    }

    @Override
    public void rxUnsubscribe() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    public Observable<ViewModel> getViewModelTopRated(){
       return Observable.zip(getTopRatedResults(), getPosts(), getComments(), getUsers(), new Func4<ArrayList<Result>, ArrayList<Post>, ArrayList<Comment>, ArrayList<User>, ViewModel>() {
           @Override
           public ViewModel call(ArrayList<Result> result, ArrayList<Post> posts, ArrayList<Comment> comments, ArrayList<User> users) {
               return new ViewModel(result,posts,comments,users);
           }
       });
    }

    public Observable<ViewModel> getViewModelPopular(){
        return Observable.zip(getTopPopularResults(), getPosts(), getComments(), getUsers(), new Func4<ArrayList<Result>, ArrayList<Post>, ArrayList<Comment>, ArrayList<User>, ViewModel>() {
            @Override
            public ViewModel call(ArrayList<Result> result, ArrayList<Post> posts, ArrayList<Comment> comments, ArrayList<User> users) {
                return new ViewModel(result,posts,comments,users);
            }
        });
    }

    public Observable<ArrayList<Result>> getTopRatedResults(){

        Observable<Movie> observable = API.getMovies().getTopRatedMovies(1)
                .concatWith(API.getMovies().getTopRatedMovies(2))
                .concatWith(API.getMovies().getTopRatedMovies(3));

        return observable.concatMap(new Func1<Movie, Observable<ArrayList<Result>>>() {
            @Override
            public Observable<ArrayList<Result>> call(Movie movie) {
              return Observable.just(movie.getResults());
            }
        });
    }

    public Observable<ArrayList<Result>> getTopPopularResults(){


        Observable<Movie> observable = API.getMovies().getPopularMovies(1)
                .concatWith(API.getMovies().getPopularMovies(2))
                .concatWith(API.getMovies().getPopularMovies(3));

        return observable.concatMap(new Func1<Movie, Observable<ArrayList<Result>>>() {
            @Override
            public Observable<ArrayList<Result>> call(Movie movie) {
                return Observable.just(movie.getResults());
            }
        });
    }

    public Observable<ArrayList<Post>> getPosts(){

        return API.getDiscussion().getPosts();

    }

    public Observable<ArrayList<User>> getUsers(){

        return API.getDiscussion().getUsers();
    }

    public Observable<ArrayList<Comment>> getComments(){

        return API.getDiscussion().getComments();
    }
}
