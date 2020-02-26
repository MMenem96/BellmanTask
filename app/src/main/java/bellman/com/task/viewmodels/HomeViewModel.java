package bellman.com.task.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import bellman.com.task.models.Attraction;
import bellman.com.task.models.HotSpot;
import bellman.com.task.repo.AreaCategoriesRepo;
import bellman.com.task.util.Resource;

public class HomeViewModel extends AndroidViewModel {

    private AreaCategoriesRepo areaCategoriesRepo;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        areaCategoriesRepo = AreaCategoriesRepo.getInstance(application);
    }


    public LiveData<Resource<List<Attraction>>> getAttractions() {
        return areaCategoriesRepo.fetchAreaAttractions();
    }

    public LiveData<Resource<List<HotSpot>>> getHotSpots() {
        return areaCategoriesRepo.fetchAreaHotSpots();
    }
}