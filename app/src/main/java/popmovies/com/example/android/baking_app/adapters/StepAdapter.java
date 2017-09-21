package popmovies.com.example.android.baking_app.adapters;

import android.content.Context;
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

    List<Step> steps;
    Context context;
    RecipeFragment.onStepSelectedListener listener;

    public StepAdapter(Context context, List<Step> steps, RecipeFragment.onStepSelectedListener listener) {
        this.context = context;
        this.steps = steps;
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TextView stepTextView = (TextView) holder.itemView.findViewById(R.id.step_text_view);
        stepTextView.setText(steps.get(position).getShortDescription());
        /*
        By calling the listener.onStepSelected we can pass the Step data back to
        the Fragments host Activity.
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onStepSelected(steps, position);
            }
        });

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
