package com.securedcommunications.securedcomm.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.example.lenovo.discountgali.R;


public class VideoPlayActivity extends Activity {
    private Context context;
    private String videoUrl;
    private VideoView videoView;
    private ProgressDialog dialog;
    //private Toolbar mToolbar;
    private String title;
    private int dur = 0;
    //private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //if (savedInstanceState == null)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        context = getApplicationContext();
        bind();
        //toolbarSetup();
        Intent intent = getIntent();


        //VideoModel objVideoModel = (VideoModel) intent.getSerializableExtra("VideoModel");
        //Log.d("==videoUrl==", objVideoModel.getVideoUrl());
        videoUrl = intent.getStringExtra("video_link");
        title = intent.getStringExtra("news_title");
        //mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setUpToolbar(title, R.mipmap.signup_back_icon);
        try {
            android.widget.MediaController mediaController = new android.widget.MediaController(this);
            mediaController.setAnchorView(videoView);

            Uri videoUri = Uri.parse(videoUrl);
            //Uri videoUri = Uri.parse("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp");
            //Uri videoUri = Uri.parse("http://s1133.photobucket.com/albums/m590/Anniebabycupcakez/?action=view&amp;%20current=1376992942447_242.mp4");
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(videoUri);

            dialog = new ProgressDialog(VideoPlayActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();


        } catch (Exception e) {
            Log.e("video error", e.getMessage() + "heelo");
            dialog.dismiss();
            finish();
            e.printStackTrace();

        }
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.requestFocus();
                videoView.seekTo(dur);
                videoView.start();

                dialog.dismiss();
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dur = videoView.getDuration();
    }

    @Override
    protected void onPause() {
        if (videoView != null)
            dur = videoView.getDuration();
        super.onPause();
    }

    private void bind() {
        videoView = (VideoView) findViewById(R.id.VideoView);
    }



    /*private void setUpToolbar(String title, int navId) {

        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        mToolbar.setNavigationIcon(navId);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }*/


    @Override
    public void onBackPressed() {
        finish();
        videoView = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView = null;
    }
}
