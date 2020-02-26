package bellman.com.task.presistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import bellman.com.task.models.Attraction;
import bellman.com.task.models.HotSpot;

@Dao
public interface AreaCategoriesDao {

    //Insert HotSpots with replacing existing ones
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAreaHotSpot(List<HotSpot> hotSpots);

//    //Insert Events with replacing existing ones
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertAreaEvents(List<Any> events )

    //Insert Attractions with replacing existing ones
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAreaAttractions(List<Attraction> attractions);


    //Get Hotspot
    @Query("SELECT * FROM hotpots")
    LiveData<List<HotSpot>> getHotSpots();

    //Get Attractions
    @Query("SELECT * FROM attractions")
    LiveData<List<Attraction>> getAttraction();

}