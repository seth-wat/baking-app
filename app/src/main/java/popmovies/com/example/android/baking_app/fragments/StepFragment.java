package popmovies.com.example.android.baking_app.fragments;

import android.content.res.Configuration;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import popmovies.com.example.android.baking_app.utils.NetworkUtils;

/**

 */
public class StepFragment extends Fragment {

    private Step step;
    private Unbinder unbinder;
    SimpleExoPlayer simpleExoPlayer;
    private boolean isTablet = false;
    public static final String OUTSTATE_STEP = "outstate_step";
    public static final String OUTSTATE_IS_TABLET = "outstate_is_tablet";
    public static final String OUTSTATE_EXO_POSITION = "outstate_exo_position";
    private long exoPosition = 0;

    @BindView(R.id.step_detail_text_view)
    TextView stepDetailTextView;
    @BindView(R.id.step_undetail_text_view)
    TextView unDetailTextView;
    View exoPlayerNotFoundContainer;
    TextView exoPlayerNotFoundTextView;
    SimpleExoPlayerView simpleExoPlayerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.exo_player_view);

        if (savedInstanceState != null) {
            /*
            Restore state.
             */
            if (savedInstanceState.containsKey(OUTSTATE_STEP)) {
                step = Parcels.unwrap((Parcelable) savedInstanceState.get(OUTSTATE_STEP));
                isTablet = (Boolean) savedInstanceState.get(OUTSTATE_IS_TABLET);
                exoPosition = (Long) savedInstanceState.get(OUTSTATE_EXO_POSITION);
            }
        }

        exoPlayerNotFoundContainer = rootView.findViewById(R.id.exo_player_not_found_view);
        exoPlayerNotFoundTextView = (TextView) rootView.findViewById(R.id.exo_player_not_found_text_view);
        return rootView;
    }

    public void setStep(Step step) {
        this.step = step;
    }


    @Override
    public void onPause() {
        if (simpleExoPlayer != null) {
            exoPosition = simpleExoPlayer.getCurrentPosition();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        /*
        Handle initializing and attaching ExoPlayer here in order to
        restore video state.
         */
        if (!step.getDisplayVideoUrlString().isEmpty() && NetworkUtils.hasInternet(getContext())) {
            simpleExoPlayer = initExoPlayer();
            //Attach the simpleExoPlayer to its view
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
        } else if (step.getDisplayVideoUrlString().isEmpty() && NetworkUtils.hasInternet(getContext())) {
            simpleExoPlayerView.setVisibility(View.GONE);
            exoPlayerNotFoundContainer.setVisibility(View.VISIBLE);
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
            if (exoPlayerNotFoundTextView != null) {
                exoPlayerNotFoundTextView.setText(getString(R.string.error_message));
            }
            exoPlayerNotFoundContainer.setVisibility(View.VISIBLE);
        }
        unDetailTextView.setText(step.getShortDescription());
        stepDetailTextView.setText(step.getLongDescription());
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(OUTSTATE_STEP, Parcels.wrap(step));
        outState.putBoolean(OUTSTATE_IS_TABLET, isTablet);
        outState.putLong(OUTSTATE_EXO_POSITION, simpleExoPlayer.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }

    private SimpleExoPlayer initExoPlayer() {
        /*
        Code below handles creating and preparing the SimpleExoPlayer
        No adaptive track selection. Simplifies trackSelector creation.
        */
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        exoPlayerNotFoundContainer.setVisibility(View.GONE);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        Uri mediaUri = Uri.parse(step.getDisplayVideoUrlString());
        String userAgent = Util.getUserAgent(getContext(), "baking_app");
        MediaSource mediaSource = new ExtractorMediaSource(
                mediaUri,
                new DefaultDataSourceFactory(getContext(), userAgent),
                new DefaultExtractorsFactory(), null, null
        );
        if (exoPosition != 0) {
            simpleExoPlayer.seekTo(exoPosition);
            simpleExoPlayer.setPlayWhenReady(true);
        } else {
            simpleExoPlayer.setPlayWhenReady(false);
        }
        simpleExoPlayer.prepare(mediaSource);

        return simpleExoPlayer;
    }

    public void setIsTablet(boolean bool) {
        isTablet = bool;
    }
}
