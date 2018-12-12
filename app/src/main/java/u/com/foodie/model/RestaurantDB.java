package u.com.foodie.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


import java.util.List;

import u.com.foodie.R;
@Entity
public class RestaurantDB {


    @PrimaryKey
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }
}
