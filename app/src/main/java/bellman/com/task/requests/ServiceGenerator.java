package bellman.com.task.requests;

import java.util.concurrent.TimeUnit;

import bellman.com.task.util.Constants;
import bellman.com.task.util.LiveDataCallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static OkHttpClient client = new OkHttpClient.Builder()

            // establish connection to server
            .connectTimeout(Constants.NETWORK_CONNECTION_TIMEOUT, TimeUnit.SECONDS)

            // time between each byte read from the server
            .readTimeout(Constants.NETWORK_READ_TIMEOUT, TimeUnit.SECONDS)

            // time between each byte sent to server
            .writeTimeout(Constants.NETWORK_WRITE_TIMEOUT, TimeUnit.SECONDS)

            .retryOnConnectionFailure(false)

            .build();


    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static CategoriesApi categoriesApi = retrofit.create(CategoriesApi.class);

    public static CategoriesApi getCategoriesApi() {
        return categoriesApi;
    }
}