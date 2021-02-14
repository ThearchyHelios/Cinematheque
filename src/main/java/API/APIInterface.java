package API;

import Search.SearchMovie;
import retrofit2.Call;
import retrofit2.http.GET;
import Module.Movie;
import retrofit2.http.HEAD;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("3/search/movie")
    Call<SearchMovie> get_movie(
            @Query("api_key") String apiKey,
//            @Query("language") String language,
            @Query("query") String query
//            @Query("page") int page,
//            @Query("include_adult") boolean includeAdult,
//            @Query("region") String region,
//            @Query("year") int year,
//            @Query("primary_release_year") int primaryReleaseYear
    );
    @GET("3/movie")
    Call<Movie> get_movie_by_id(
//            @Path("movie_id") int movieId,
            @Path("movie_id") int  movieID,
            @Query(value = "api_key", encoded = true) String apiKey
    );
}
