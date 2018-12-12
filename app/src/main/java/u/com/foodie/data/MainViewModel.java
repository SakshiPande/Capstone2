package u.com.foodie.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import u.com.foodie.model.RestaurantDB;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<RestaurantDB>> favoriteRes;
    private FoodieRepository resRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        resRepository = new FoodieRepository(application);
        favoriteRes = resRepository.getAll();
    }


    public LiveData<List<RestaurantDB>> getFavoriteRes() {
        return favoriteRes;
    }


    public void insert(RestaurantDB restaurant) {
        resRepository.insertRes(restaurant);
    }

    public void delete(RestaurantDB restaurant) {
        resRepository.deleteRes(restaurant);
    }

}
