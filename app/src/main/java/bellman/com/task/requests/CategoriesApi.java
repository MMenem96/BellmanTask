package bellman.com.task.requests;

import androidx.lifecycle.LiveData;

import bellman.com.task.requests.responses.ApiResponse;
import bellman.com.task.requests.responses.AreaCategoriesResponse;
import retrofit2.http.GET;

public interface CategoriesApi {

    //Get all categories(Hot spots, events, and attractions)
    @GET("api/home")
    LiveData<ApiResponse<AreaCategoriesResponse>> fetchCategories();
}