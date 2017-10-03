package popmovies.com.example.android.baking_app.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import popmovies.com.example.android.baking_app.R;
import popmovies.com.example.android.baking_app.data.Ingredient;
import popmovies.com.example.android.baking_app.utils.JsonUtils;
import popmovies.com.example.android.baking_app.utils.NetworkUtils;

/**
 * Populates the widget list with the correct ingredients.
 */

public class WidgetListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    List<String> ingredients;
    private BroadcastReceiver broadcastReceiver;

    public WidgetListRemoteViewsFactory(Context applicationContext, ArrayList<String> ingredients) {
        context = applicationContext;
        this.ingredients = ingredients;
        initBroadcastReceiver();

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_ingredient);
        /*
        This condition below is used to prevent an IndexOutOfBoundsException in the widget.
         */
        if (position == ingredients.size()) position = ingredients.size() - 1;
        remoteViews.setTextViewText(R.id.ingredient_holder_text_view, (position + 1) + ". " + ingredients.get(position));
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void initBroadcastReceiver() {
        /*
        This method sets up a BroadcastReceiver that updates an instance
        of this class with new data.
         */
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    ingredients = intent.getStringArrayListExtra("ingredientBundle");
                    onDataSetChanged();
                }
            };
            IntentFilter filter = new IntentFilter();
            filter.addAction("update_me");
            context.registerReceiver(broadcastReceiver, filter);
        }
    }

}
