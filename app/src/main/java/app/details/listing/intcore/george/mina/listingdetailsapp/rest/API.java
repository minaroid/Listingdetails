package app.details.listing.intcore.george.mina.listingdetailsapp.rest;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class API {

    private static final String THE_MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String THE_DISCUSSION_BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static final String MOVIE_API_KEY= "c23c8f8855a040042f0433b7696f9d65";
    private static Retrofit mRetrofit1 = null;
    private static Retrofit mRetrofit2 = null;

    public static OkHttpClient movieClientProvider(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter("api_key",MOVIE_API_KEY).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();
    }

    public static OkHttpClient discussionClientProvider(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        }).build();
    }

    public static MoviesClient getMovies() {

        if (mRetrofit1 == null) {
            mRetrofit1 = new Retrofit.Builder()
                    .baseUrl(THE_MOVIE_DB_BASE_URL)
                    .client(movieClientProvider())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build( );
        }
        return mRetrofit1.create(MoviesClient.class);
    }

    public static DiscussionClient getDiscussion() {
        if (mRetrofit2 == null) {
            mRetrofit2 = new Retrofit.Builder()
                    .baseUrl(THE_DISCUSSION_BASE_URL)
                    .client(discussionClientProvider())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build( );
        }
        return mRetrofit2.create(DiscussionClient.class);
    }
}
