package app.details.listing.intcore.george.mina.listingdetailsapp.rest;

import app.details.listing.intcore.george.mina.listingdetailsapp.rest.moviesModel.Movie;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
public interface MoviesClient {

    @GET("popular")
    Observable<Movie> getPopularMovies(@Query("page") Integer page);

    @GET("top_rated")
    Observable<Movie> getTopRatedMovies(@Query("page") Integer page);

}
