package u.com.foodie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityIdBase {
    @SerializedName("location")
    @Expose
    private CityID locality;

    @SerializedName("link")
    @Expose
    private String link;



    public CityID getLocality() {
        return locality;
    }

    public void setLocality(CityID locality) {
        this.locality = locality;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
