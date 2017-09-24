package popmovies.com.example.android.baking_app.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private int position;
    private Unbinder unbinder;
    SimpleExoPlayer simpleExoPlayer;

    @BindView(R.id.step_detail_text_view) TextView stepDetailTextView;
    @BindView(R.id.step_undetail_text_view) TextView unDetailTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        SimpleExoPlayerView simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.exo_player_view);

        /*
        Code below handles creating and preparing the SimpleExoPlayer
        No adaptive track selection. Simplifies trackSelector creation.
        */
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
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
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
    }
}
