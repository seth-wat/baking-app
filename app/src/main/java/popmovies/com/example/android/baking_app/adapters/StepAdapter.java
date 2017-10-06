package popmovies.com.example.android.baking_app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import popmovies.com.example.android.baking_app.R;
import popmovies.com.example.android.baking_app.data.Step;
import popmovies.com.example.android.baking_app.fragments.RecipeFragment;

/**
 * Adapter to populate the RecyclerView with recipe steps in RecipeFragment
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private List<Step> steps;
    private Context context;
    private RecipeFragment.onStepSelectedListener listener;
    private ArrayList<View> clearViews;
    private boolean tabletMode = false;

    /*
    lastPosition represents the position passed in via the constructor.
    lastPositionToSend represents the position that will be returned by getLastPosition.
     */
    private int lastPosition;
    private int lastPositionToSend;

    private ViewHolder viewHolder;

    public StepAdapter(Context context, List<Step> steps, RecipeFragment.onStepSelectedListener listener, boolean tabletMode, int lastPosition) {
        this.context = context;
        this.steps = steps;
        this.listener = listener;
        clearViews = new ArrayList<>();
        if (tabletMode) {
            this.tabletMode = tabletMode;
        }
        this.lastPosition = lastPosition;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_step, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TextView stepTextView = (TextView) holder.itemView.findViewById(R.id.step_text_view);
        stepTextView.setText( (position) + ". " + steps.get(position).getShortDescription());

        /*
        If we are at position 0 and using a tablet, then the item view should be highlighted because
        that step will be displayed by default in StepFragment.
         */
        if (position == 0 && tabletMode && lastPosition == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            clearViews.add(holder.itemView);
        }
        if (lastPosition != 0 && lastPosition == position) {
            /*
            If the lastPosition is not 0 and lastPosition matches the current position
            we need to restore the background color change from a previous state.
             */
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            clearViews.add(holder.itemView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                By calling the listener.onStepSelected we can pass the Step data back to
                the Fragments host Activity.
                */
                listener.onStepSelected(steps, position);
                lastPositionToSend = position;
                /*
                When a view is selected highlight it, if a view has been selected before
                return the color to normal.
                 */
                if (!clearViews.isEmpty()) {
                    ArrayList<View> clearFromLoop = new ArrayList<View>();
                    for (View view : clearViews) {
                        view.setBackgroundColor(ContextCompat.getColor(context, R.color.defaultBackground));
                        clearFromLoop.add(view);
                    }
                    clearViews.removeAll(clearFromLoop);
                }
                v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                clearViews.add(v);
            }
        });

    }
    public int getLastPosition() {
        /*
        This should be called to return the last position that was selected before the instance
        is destroyed, or the last selected position from previous states
        if nothing was selected during this instance.
         */
        if (lastPositionToSend == 0) lastPositionToSend = lastPosition;
        return lastPositionToSend;
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
