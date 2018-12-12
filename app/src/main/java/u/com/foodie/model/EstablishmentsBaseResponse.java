package u.com.foodie.model;


    import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EstablishmentsBaseResponse {

        @SerializedName("establishment")
        @Expose
        private Establishment establishment;

        public Establishment getEstablishment() {
            return establishment;
        }

        public void setEstablishment(Establishment establishment) {
            this.establishment = establishment;
        }

    }

