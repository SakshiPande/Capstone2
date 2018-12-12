package u.com.foodie.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import u.com.foodie.dao.FoodieDao;
import u.com.foodie.model.RestaurantDB;

class FoodieRepository {

    private FoodieDao foodieDao;
    private LiveData<List<RestaurantDB>> restaurantList;


    public FoodieRepository(Application application) {
        FoodieDb resDatabase = FoodieDb.getInstance(application);
        foodieDao = resDatabase.getFoodieDao();
        restaurantList = foodieDao.getAllRestaurants();
    }

    public LiveData<List<RestaurantDB>> getAll() {
        return restaurantList;
    }

    public void insertRes(RestaurantDB restaurant) {
        new InsertResTask(foodieDao).execute(restaurant);
    }

    public void deleteRes(RestaurantDB restaurant) {
        new DeleteResTask(foodieDao).execute(restaurant);
    }


    static class InsertResTask extends AsyncTask<RestaurantDB, Void, Void> {
        private FoodieDao resDao;

        private InsertResTask(FoodieDao resDao) {
            this.resDao = resDao;
        }

        @Override
        protected Void doInBackground(RestaurantDB... restaurant) {
            resDao.insertRestaurant(restaurant[0]);
            return null;
        }

    }

    static class DeleteResTask extends AsyncTask<RestaurantDB, Void, Void> {
        private FoodieDao foodieDao;

        private DeleteResTask(FoodieDao foodieDao) {
            this.foodieDao = foodieDao;
        }

        @Override
        protected Void doInBackground(RestaurantDB... restaurantDB) {
            foodieDao.deleteres(restaurantDB[0]);
            return null;
        }

    }
}
