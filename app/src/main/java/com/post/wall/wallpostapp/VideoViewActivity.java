package com.post.wall.wallpostapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.post.wall.wallpostapp.model.Post;

import java.net.URI;
import java.net.URL;

public class VideoViewActivity extends ActionBarActivity {

    VideoView videoview;
    int currentPosition;
    AudioManager audioManager;
    Post post;
    Uri url;

//    private Toolbar toolBar;
//    int currentVolume;
//    String VideoURL = "http://192.168.2.118:888/Images/NewsFeed/Videos/10392017043923_So_Sorry-_Kale_Dhan_Ki_Kher_Nahi.3gp";
    ProgressBar progressBar;
    private final MediaPlayer.OnInfoListener onInfoToPlayStateListener = new MediaPlayer.OnInfoListener() {

        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {
                progressBar.setVisibility(View.GONE);
            }

            if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
                progressBar.setVisibility(View.VISIBLE);
            }

            if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
                progressBar.setVisibility(View.GONE);
            }
            return false;
        }
    };
    MediaController mediacontroller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the layout from video_main.xml
        setContentView(R.layout.videoview_main);

        url = Uri.parse(getIntent().getExtras().getString("URL"));

        // Find your VideoView in your video_main.xml layout
        videoview = (VideoView) findViewById(R.id.VideoView);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        getIntentIfAvailable();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getIntentIfAvailable() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            progressBar = (ProgressBar) findViewById(R.id.progressbar);
            try {
                // Start the MediaController
                mediacontroller = new MediaController(VideoViewActivity.this);
                mediacontroller.setAnchorView(videoview);
                videoview.setMediaController(mediacontroller);
                videoview.setVideoURI(url);
                videoview.setMediaController(mediacontroller);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            videoview.requestFocus();
            videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.GONE);
                    videoview.start();
                }
            });
            videoview.setOnInfoListener(onInfoToPlayStateListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
        super.onBackPressed();
    }

}

