package u.com.foodie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import u.com.foodie.R;
import u.com.foodie.model.RestaurantDB;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private Context context;
    private List<RestaurantDB> restaurantDBList;

    public FavoritesAdapter(List<RestaurantDB> restaurantDBList, Context context){
        this.restaurantDBList=restaurantDBList;
        this.context=context;


    }
    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.favorits_list_row, viewGroup, false);

        return new FavoritesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder favoritesViewHolder, int i) {
        favoritesViewHolder.txtName.setText(restaurantDBList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return restaurantDBList.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txt_fav);

        }
    }
}
