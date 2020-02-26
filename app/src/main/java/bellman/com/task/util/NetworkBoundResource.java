package bellman.com.task.util;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import bellman.com.task.AppExecutors;
import bellman.com.task.requests.responses.ApiResponse;

public abstract class NetworkBoundResource<CacheType, RequestObject> {

    private static final String TAG = "NetworkBoundResource";
    private AppExecutors appExecutors;

    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        init();
    }

    //Data that will be observed in the UI
    private MediatorLiveData<Resource<CacheType>> results = new MediatorLiveData<>();

    private void init() {

        //Update liveData for loading status
        results.setValue(Resource.loading(null));

        //Observe liveData source for local db
        final LiveData<CacheType> dbSource = loadFromDb();

        results.addSource(dbSource, cacheType -> {
            // Remove observer from local db. Need to decide if read local db or network
            results.removeSource(dbSource);

            if (shouldFetch(cacheType)) {
                //Get data from the network
                fetchFromNetwork(dbSource);
            } else {
                // Otherwise read data from local db
                results.addSource(dbSource, cacheType1 ->
                        setValue(Resource.success(cacheType1)));
            }
        });
    }

    /**
     * observe local db
     * if <condition> query network
     * stop observing the local db
     * insert new data into local db
     * begin observing the local db again to see the refreshed data from the network
     */
    private void fetchFromNetwork(LiveData<CacheType> dbSource) {
        Log.d(TAG, "fetchFromNetwork: Called.");

        //Update LiveData for loading status
        results.addSource(dbSource, it -> {
            //View the cached data and load the new data
            setValue(Resource.loading(it));
        });

        final LiveData<ApiResponse<RequestObject>> apiResponse = createCall();

        results.addSource(apiResponse, it -> {
            results.removeSource(dbSource);
            results.removeSource(apiResponse);

            Log.d(TAG, "run: attempting to refresh data from network...");
            /*
            3 Cases:
            1)ApiSuccessResponse
            2)ApiErrorResponse
            3)ApiEmptyResponse
             */

            if (it instanceof ApiResponse.ApiSuccessResponse) {
                //Save the new data into local db
                appExecutors.diskIO().execute(() -> {
                    Log.d(TAG, "onChanged: ApiSuccessResponse");

                    //Save the new data into the local db in the backgroundThread

                    saveCallResult((RequestObject) processResponse((ApiResponse.ApiSuccessResponse) it));

                    // observe local db again since new result from network will have been saved
                    appExecutors.mainThread().execute(() -> results.addSource(loadFromDb(), cacheResponse -> {
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        // as opposed to use the @dbSource variable passed as input
                        setValue(Resource.success(cacheResponse));
                    }));
                });


            } else if (it instanceof ApiResponse.ApiEmptyResponse) {
                Log.d(TAG, "onChanged: ApiEmptyResponse");
                appExecutors.mainThread().execute(() -> results.addSource(loadFromDb(),
                        cachedResponse -> setValue(Resource.success(cachedResponse))));

            } else if (it instanceof ApiResponse.ApiErrorResponse) {
                Log.d(TAG, "onChanged: ApiError");

                results.addSource(dbSource,
                        cachedObject -> setValue(
                                Resource.error(
                                        ((ApiResponse.ApiErrorResponse) it).getErrorMessage(),
                                        cachedObject
                                )
                        ));
            }
        });


    }


    private CacheType processResponse(ApiResponse.ApiSuccessResponse response) {

        return (CacheType) response.getBody();
    }

    private void setValue(Resource<CacheType> newValue) {
        if (results.getValue() != newValue) {
            results.setValue(newValue);
        }
    }

    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract void saveCallResult(RequestObject item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch(CacheType data);

    // Called to get the cached data from the database.
    @MainThread
    protected abstract LiveData<CacheType> loadFromDb();

    // Called to create the API call.
    @MainThread
    protected abstract LiveData<ApiResponse<RequestObject>> createCall();

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    protected void onFetchFailed() {
    }

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
   public LiveData<Resource<CacheType>> asLiveData() {
        return results;
    }
}