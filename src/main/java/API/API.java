package API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class API {
    private static final String BaseURL = "https://api.themoviedb.org/";
    private static Retrofit retrofit;

    public static Retrofit getAPI(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BaseURL).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
}
