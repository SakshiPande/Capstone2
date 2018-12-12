package u.com.foodie.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import u.com.foodie.dao.FoodieDao;
import u.com.foodie.model.RestaurantDB;


@Database(entities = {RestaurantDB.class}, version = 1, exportSchema = false)
public abstract class FoodieDb extends RoomDatabase {

    private static FoodieDb foodieDB;

    public static FoodieDb getInstance(Context context) {
        if (foodieDB == null) {
            foodieDB = Room.databaseBuilder(context.getApplicationContext(), FoodieDb.class, "Foodie_database")
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return foodieDB;
    }

    public abstract FoodieDao getFoodieDao();


}