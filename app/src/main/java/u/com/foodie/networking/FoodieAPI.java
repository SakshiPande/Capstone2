package u.com.foodie.networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import u.com.foodie.model.CityIdBase;
import u.com.foodie.model.Establishments;
import u.com.foodie.model.PopularBaseResponse;

public interface FoodieAPI {

    @GET("api/v2.1/geocode")
    Call<CityIdBase>getCityID(@Query("lat") Double Latitude, @Query("lon") Double Longitude);

    @GET("api/v2.1/establishments")
    Call<Establishments> getEstablishment(@Query("city_id") String cityID);

    @GET("api/v2.1/search")
    Call<PopularBaseResponse> getPopularRestaurants(@Query("lat") Double Latitude, @Query("lon") Double Longitude,@Query("count") int count,@Query("sort") String sort_type
                                                    ,@Query("order") String order );



}
