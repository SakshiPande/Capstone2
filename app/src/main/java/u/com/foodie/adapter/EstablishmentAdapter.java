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
import u.com.foodie.model.Establishment;
import u.com.foodie.model.EstablishmentsBaseResponse;


public class EstablishmentAdapter extends RecyclerView.Adapter<EstablishmentAdapter.EstablishmentViewHolder> {

    private List<EstablishmentsBaseResponse> establishmentList;
    private Context mContext;

    public EstablishmentAdapter(List<EstablishmentsBaseResponse> establishmentList, Context context) {
        this.establishmentList = establishmentList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public EstablishmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.establishment_list_row, parent, false);

        return new EstablishmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EstablishmentViewHolder holder, int position) {

        holder.txtEstablishment.setText(establishmentList.get(position).getEstablishment().getName());

    }

    @Override
    public int getItemCount() {
        return establishmentList.size();
    }

    public class EstablishmentViewHolder extends RecyclerView.ViewHolder {

        private TextView txtEstablishment;

        EstablishmentViewHolder(View view) {
            super(view);
            txtEstablishment=view.findViewById(R.id.txt_establishment);

        }
    }
}