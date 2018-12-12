package u.com.foodie.networking;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import u.com.foodie.utils.AppConstant;

public class RestClient {


//    public static Retrofit retrofitService() {
//
//        return new Retrofit.Builder()
//                .baseUrl(AppConstant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }


    public static Retrofit retrofitService() {


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(AppConstant.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());


        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("user-key", "865716bbf6e36b095d1e584411efb616")
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit;
    }
}