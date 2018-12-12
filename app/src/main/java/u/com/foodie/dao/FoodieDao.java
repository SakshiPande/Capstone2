package u.com.foodie.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import u.com.foodie.model.RestaurantDB;

@Dao
public interface FoodieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRestaurant(RestaurantDB restaurant);

    @Query("select * from RestaurantDB")
    LiveData<List<RestaurantDB>> getAllRestaurants();

    @Delete
    void deleteres(RestaurantDB restaurant);
}
