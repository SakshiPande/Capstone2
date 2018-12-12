package u.com.foodie.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import u.com.foodie.R;
import u.com.foodie.activities.RestaurantDetailActivity;
import u.com.foodie.model.EstablishmentsBaseResponse;
import u.com.foodie.model.Restaurant;
import u.com.foodie.model.Restaurant_;


public class PopularRestaurantAdapter extends RecyclerView.Adapter<PopularRestaurantAdapter.PopularViewHolder> {

    private List<Restaurant> restaurantList;
    private Context mContext;

    public PopularRestaurantAdapter(List<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_row, parent, false);

        return new PopularViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, final int position) {

        holder.txtResName.setText(restaurantList.get(position).getRestaurant().getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Restaurant restaurant=restaurantList.get(position);
                Intent intent=new Intent(mContext,RestaurantDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("photo_url",restaurant.getRestaurant().getThumb());
                bundle.putString("user_rating",restaurant.getRestaurant().getUserRating().getAggregateRating());
                bundle.putString("cuisine",restaurant.getRestaurant().getCuisines());
                bundle.putString("name",restaurant.getRestaurant().getName());
                bundle.putInt("average_cost",restaurant.getRestaurant().getAverageCostForTwo());
                bundle.putInt("is_delivering",restaurant.getRestaurant().getIsDeliveringNow());
                bundle.putInt("online_delivery",restaurant.getRestaurant().getHasOnlineDelivery());
                bundle.putString("address",restaurant.getRestaurant().getLocation().getAddress());
                bundle.putString("id",restaurant.getRestaurant().getId());
                intent.putExtra("details",bundle);
                mContext.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {

        private TextView txtResName;

        PopularViewHolder(View view) {
            super(view);
            txtResName=view.findViewById(R.id.tv_res_name);

        }
    }
}