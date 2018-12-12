package u.com.foodie.activities;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import u.com.foodie.FoodieWidget;
import u.com.foodie.R;
import u.com.foodie.data.FoodieDb;
import u.com.foodie.data.MainViewModel;
import u.com.foodie.model.RestaurantDB;
import u.com.foodie.model.RestaurantsDBList;
import u.com.foodie.utils.AppConstant;

public class RestaurantDetailActivity extends AppCompatActivity {
    private String mNameRes,mPhotoUrl,mCuisines,mUserRating,mAddress,id;
    private int mIsonline,mIsAvailable,mAverageCost,midDb;
    private Boolean isFav = false;
    private FoodieDb foodieDb;
    private MainViewModel mainViewModel;

    @BindView(R.id.tv_name)
    TextView mTvName;

    @BindView(R.id.tv_address)
    TextView mTvAddress;

    @BindView(R.id.iv_res)
    ImageView mImgPhoto;

    @BindView(R.id.tv_cuisine)
    TextView mTvCuisine;

    @BindView(R.id.tv_avg_cost)
    TextView mTvAvgCost;

    @BindView(R.id.tv_user_rating)
    TextView mTvUserRating;

    @BindView(R.id.tv_avg_cost_txt)
    TextView mTvAvgtxt;

    @BindView(R.id.tv_user_rating_txt)
    TextView mTvUsrRatingtxt;

    @BindView(R.id.button_favorite)
    Button btnfav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);
        getData();
        setData();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        getIsFav();
    }

    private void setData() {
        mTvName.setText(mNameRes);
        mTvAddress.setText(mAddress);
        mTvCuisine.setText(mCuisines);
        if(!TextUtils.isEmpty(mPhotoUrl))
            Picasso.with(this).load(mPhotoUrl).into(mImgPhoto);
        else
            mImgPhoto.setImageResource(R.drawable.placeholder);

        if(TextUtils.isEmpty(Integer.toString(mAverageCost))) {
            mTvAvgCost.setVisibility(View.GONE);
            mTvAvgtxt.setVisibility(View.GONE);
        }
        else {
            mTvAvgCost.setText(mAverageCost + "");

        }

        if(TextUtils.isEmpty(mUserRating)) {
            mTvUserRating.setVisibility(View.GONE);
            mTvUsrRatingtxt.setVisibility(View.GONE);
        }
        else {
            mTvUserRating.setText(mUserRating + "");

        }
        midDb=Integer.parseInt(id);


    }

    private void getData() {
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("details");
        mNameRes=bundle.getString("name");
        mPhotoUrl=bundle.getString("photo_url");
        mUserRating=bundle.getString("user_rating");
        mCuisines=bundle.getString("cuisine");
        mAverageCost= bundle.getInt("average_cost");
        mIsAvailable= bundle.getInt("is_delivering");
        mIsonline= bundle.getInt("online_delivery");
        mAddress=bundle.getString("address");
        id=bundle.getString("id");

    }

    @OnClick(R.id.button_favorite)
    public void btnFavClick(){
        if (!isFav)
            addFav();
        else
            removeFav();


    }

    private void removeFav() {
        foodieDb = Room.databaseBuilder(getApplicationContext(),
                FoodieDb.class, AppConstant.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                RestaurantDB restaurant=new RestaurantDB();
                restaurant.setName(mNameRes);
                restaurant.setId(midDb);


                mainViewModel.delete(restaurant);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnfav.setText(R.string.add_to_favorites_button_title);
                        isFav = false;
                        Toast.makeText(RestaurantDetailActivity.this, R.string.remove_from_favorites_button_title, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
    private void getIsFav() {

        mainViewModel.getFavoriteRes().observe(this, new Observer<List<RestaurantDB>>() {
            @Override
            public void onChanged(@Nullable List<RestaurantDB> restaurants) {
                for (int i = 0; i < restaurants.size(); i++) {
                    RestaurantDB restaurantDB = restaurants.get(i);
                    if (mNameRes.equals(restaurantDB.getName())) {
                        isFav = true;
                        break;
                    }
                }
                SharedPreferences sharedPreferences = RestaurantDetailActivity.this.getSharedPreferences(AppConstant.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                Gson gson=new Gson();
                RestaurantsDBList restaurantsDBList=new RestaurantsDBList();
                restaurantsDBList.setRestaurantList(restaurants);
                String json=gson.toJson(restaurantsDBList);
                editor.putString("list",json);
                editor.apply();
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(RestaurantDetailActivity.this);

                Bundle bundle = new Bundle();

                int appWidgetId = bundle.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                FoodieWidget.updateAppWidget(RestaurantDetailActivity.this, appWidgetManager, appWidgetId,restaurants);



                if (isFav)
                    btnfav.setText(R.string.remove_from_favorites_button_title);
                else
                    btnfav.setText(R.string.add_to_favorites_button_title);
            }
        });


    }
    private void addFav() {

        foodieDb = Room.databaseBuilder(getApplicationContext(),
                FoodieDb.class, AppConstant.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final RestaurantDB restaurant=new RestaurantDB();
                restaurant.setName(mNameRes);
                restaurant.setId(midDb);
                mainViewModel.insert(restaurant);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {



                        btnfav.setText(R.string.remove_from_favorites_button_title);
                        isFav = true;
                        Toast.makeText(RestaurantDetailActivity.this, R.string.add_to_favorites_button_title, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();


    }

}
