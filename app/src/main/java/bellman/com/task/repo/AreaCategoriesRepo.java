package bellman.com.task.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import bellman.com.task.AppExecutors;
import bellman.com.task.models.Attraction;
import bellman.com.task.models.HotSpot;
import bellman.com.task.presistence.AreaCategoriesDB;
import bellman.com.task.presistence.AreaCategoriesDao;
import bellman.com.task.requests.ServiceGenerator;
import bellman.com.task.requests.responses.ApiResponse;
import bellman.com.task.requests.responses.AreaCategoriesResponse;
import bellman.com.task.util.Constants;
import bellman.com.task.util.NetworkBoundResource;
import bellman.com.task.util.Resource;

public class AreaCategoriesRepo {
    private static final String TAG = "AreaCategoriesRepo";
    private static AreaCategoriesRepo instance;
    private AreaCategoriesDao areaCategoriesDao;

    public static AreaCategoriesRepo getInstance(Context context) {
        if (instance == null) {
            instance = new AreaCategoriesRepo(context);
        }
        return instance;
    }


    private AreaCategoriesRepo(Context context) {
        areaCategoriesDao = AreaCategoriesDB.getInstance(context).getRecipeDao();
    }

    public LiveData<Resource<List<Attraction>>> fetchAreaAttractions() {
        return new NetworkBoundResource<List<Attraction>, AreaCategoriesResponse>(AppExecutors.getInstance()) {


            @Override
            public void saveCallResult(AreaCategoriesResponse item) {
                if (item != null && item.getData() != null) {
                    for (int i = 0; i < item.getData().getAttractions().size(); i++) {
                        item.getData().getAttractions().get(i).setTimeStamp((int) (System.currentTimeMillis() / 1000));
                        //Get the first photo
                        if (!item.getData().getAttractions().get(i).getPhotos().isEmpty())
                            item.getData().getAttractions().get(i).setPhoto(item.getData().getAttractions().get(i).getPhotos().get(0));
                        //Get the first Category
                        if (!item.getData().getAttractions().get(i).getCategories().isEmpty())
                            item.getData().getAttractions().get(i).setCategoryName(item.getData().getAttractions().get(i).getCategories().get(0).getName());

                    }
                    for (int i = 0; i < item.getData().getHotSpots().size(); i++) {
                        item.getData().getHotSpots().get(i).setTimeStamp((int) (System.currentTimeMillis() / 1000));
                        //Get the first photo
                        if (!item.getData().getHotSpots().get(i).getPhotos().isEmpty())
                            item.getData().getHotSpots().get(i).setPhoto(item.getData().getHotSpots().get(i).getPhotos().get(0));
                        //Get the first Category
                        if (!item.getData().getHotSpots().get(i).getCategories().isEmpty())
                            item.getData().getHotSpots().get(i).setCategoryName(item.getData().getHotSpots().get(i).getCategories().get(0).getName());

                    }


                    //Save attractions into the db
                    areaCategoriesDao.insertAreaAttractions(item.getData().getAttractions());
                    //Save hotSpots into the db
                    areaCategoriesDao.insertAreaHotSpot(item.getData().getHotSpots());
                }
            }


            @Override
            protected boolean shouldFetch(List<Attraction> data) {
                int currentTime = (int) (System.currentTimeMillis() / 1000);
                Log.d(TAG, "shouldFetch: current time: " + currentTime);
                if (data != null && data.size() != 0) {
                    int lastRefresh = data.get(0).getTimeStamp();
                    Log.d(TAG, "shouldFetch: last refresh: " + lastRefresh);
                    Log.d(TAG, "shouldFetch: it's been " + ((currentTime - lastRefresh) / 60 / 60) +
                            " hours since this recipe was refreshed. 24 hours must elapse before refreshing. ");
                    if (currentTime - lastRefresh >= Constants.CATEGORIES_REFRESH_TIME) {
                        Log.d(TAG, "shouldFetch: SHOULD REFRESH RECIPE?! " + true);
                        return true;
                    } else {
                        return false;
                    }
                }
                Log.d(TAG, "shouldFetch: Data is empty to try to fetch");
                return true;
            }

            @Override
            protected LiveData<List<Attraction>> loadFromDb() {
                return areaCategoriesDao.getAttraction();
            }

            @Override
            protected LiveData<ApiResponse<AreaCategoriesResponse>> createCall() {
                return ServiceGenerator.getCategoriesApi().fetchCategories();
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<HotSpot>>> fetchAreaHotSpots() {
        return new NetworkBoundResource<List<HotSpot>, AreaCategoriesResponse>(AppExecutors.getInstance()) {
            @Override
            public void saveCallResult(AreaCategoriesResponse item) {
                //Not need to save the hotSpots, cause we have received the response in the prev call .
            }


            @Override
            protected boolean shouldFetch(List<HotSpot> data) {
                //Not need to fetch hotSpots again, since we have fetched the data from the api .
                return false;
            }

            @Override
            protected LiveData<List<HotSpot>> loadFromDb() {
                return areaCategoriesDao.getHotSpots();
            }

            @Override
            protected LiveData<ApiResponse<AreaCategoriesResponse>> createCall() {
                return ServiceGenerator.getCategoriesApi().fetchCategories();
            }
        }.asLiveData();
    }
}