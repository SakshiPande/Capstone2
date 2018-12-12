package u.com.foodie;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import java.util.List;

import u.com.foodie.model.RestaurantDB;
import u.com.foodie.model.RestaurantsDBList;
import u.com.foodie.utils.AppConstant;

/**
 * Implementation of App Widget functionality.
 */
public class FoodieWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId,List<RestaurantDB> restaurantDBList) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.foodie_widget);

        views.removeAllViews(R.id.widget_ingredients_container);
        for (RestaurantDB restaurant : restaurantDBList) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.fav_widget_row);
            remoteViews.setTextViewText(R.id.name_text_view,restaurant.getName());
            views.addView(R.id.widget_ingredients_container, remoteViews);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them


       SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
       Gson gson=new Gson();

        String json = sharedPreferences.getString("list", "");
        Log.d("new",json);
RestaurantsDBList restaurantsDBList=gson.fromJson(json,RestaurantsDBList.class);



        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,restaurantsDBList.getRestaurantList());
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

