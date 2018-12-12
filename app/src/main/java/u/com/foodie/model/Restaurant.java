package u.com.foodie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import u.com.foodie.R;



public class Restaurant {

    @SerializedName("restaurant")
    @Expose
    private Restaurant_ restaurant;

    public Restaurant_ getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant_ restaurant) {
        this.restaurant = restaurant;
    }

}


