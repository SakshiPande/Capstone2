package u.com.foodie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Establishments {
    @SerializedName("establishments")
    @Expose
    private List<EstablishmentsBaseResponse> establishments = null;

    public List<EstablishmentsBaseResponse> getEstablishments() {
        return establishments;
    }

    public void setEstablishments(List<EstablishmentsBaseResponse> establishments) {
        this.establishments = establishments;
    }
}
