package bellman.com.task.presistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import bellman.com.task.models.Attraction;
import bellman.com.task.models.HotSpot;


@Database(entities = {Attraction.class, HotSpot.class}, version = 1)
public abstract class AreaCategoriesDB extends RoomDatabase {
    private static final String DATABASE_NAME = "area_categories_db";
    private static AreaCategoriesDB instance;

    public static AreaCategoriesDB getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AreaCategoriesDB.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract AreaCategoriesDao getRecipeDao();
}