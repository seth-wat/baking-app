package popmovies.com.example.android.baking_app.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import popmovies.com.example.android.baking_app.R;
import popmovies.com.example.android.baking_app.data.Step;

/**

 */
public class StepFragment extends Fragment {

    private Step step;
    private Unbinder unbinder;
    SimpleExoPlayer simpleExoPlayer;
    private boolean isTablet = false;
    public static final String OUTSTATE_STEP = "outstate_step";
    public static final String OUTSTATE_IS_TABLET = "outstate_is_tablet";

    @BindView(R.id.step_detail_text_view) TextView stepDetailTextView;
    @BindView(R.id.step_undetail_text_view) TextView unDetailTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        SimpleExoPlayerView simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.exo_player_view);

        if (savedInstanceState != null) {
            /*
            retrieve the Step object out of the outstate.
             */
            if (savedInstanceState.containsKey(OUTSTATE_STEP)) {
                step = Parcels.unwrap((Parcelable) savedInstanceState.get(OUTSTATE_STEP));
                isTablet = (Boolean) savedInstanceState.get(OUTSTATE_IS_TABLET);
            }
        }
        /*
        If the users is on a phone and the device is in landscape mode the ExoPlayer should
        take up the entire view space.
         */
        if (isTablet == false && getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            simpleExoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        FrameLayout exoPlayerNotFoundView = (FrameLayout) rootView.findViewById(R.id.exo_player_not_found_view);

        /*
        Code below handles creating and preparing the SimpleExoPlayer
        No adaptive track selection. Simplifies trackSelector creation.
        */
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        if (!step.getDisplayVideoUrlString().isEmpty()) {
            exoPlayerNotFoundView.setVisibility(View.GONE);
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            Uri mediaUri = Uri.parse(step.getDisplayVideoUrlString());
            String userAgent = Util.getUserAgent(getContext(), "baking_app");
            MediaSource mediaSource = new ExtractorMediaSource(
                    mediaUri,
                    new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(), null, null
            );
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(false);

            //Attach the simpleExoPlayer to its view
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
            exoPlayerNotFoundView.setVisibility(View.VISIBLE);
        }
        unDetailTextView.setText(step.getShortDescription());
        stepDetailTextView.setText(step.getLongDescription());
        return rootView;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    @Override
    public void onStop() {
        super.onStop();
        /*
        If step is empty then simpleExoPlayer was never initialized.
         */
        if (!step.getDisplayVideoUrlString().isEmpty()) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(OUTSTATE_STEP, Parcels.wrap(step));
        outState.putBoolean(OUTSTATE_IS_TABLET, isTablet);
        super.onSaveInstanceState(outState);
    }

    public void setIsTablet(boolean bool) {
        isTablet = bool;
    }
}
