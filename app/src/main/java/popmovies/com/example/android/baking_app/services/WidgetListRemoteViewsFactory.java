package services;

import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import popmovies.com.example.android.baking_app.R;
import popmovies.com.example.android.baking_app.data.Ingredient;

/**
 *
 */

public class WidgetListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    Cursor cursor;
    List<Ingredient> ingredients;

    public WidgetListRemoteViewsFactory(Context applicationContext, List<Ingredient> ingredients) {
        context = applicationContext;
        this.ingredients = ingredients;
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
        remoteViews.setTextViewText(R.id.ingredient_holder_text_view, ingredients.get(position).getIngredient());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
