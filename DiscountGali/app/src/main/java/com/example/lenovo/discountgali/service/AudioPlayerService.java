package com.example.lenovo.discountgali.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class AudioPlayerService extends Service {
    static MediaPlayer mediaPlayer;
    private String str,songPath;
    private Boolean flag=false;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("tag", "hkkkkkkkkkkkkkkkki" );
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
    }


    private void playOnline()
    {
        mediaPlayer=new MediaPlayer();

        try {
            String string="storage/emulated/0/DCIM/AB Tere Bin.mp3";
            Log.d("tagggggggg", ""+string);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(songPath);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        str=intent.getStringExtra("id");
        songPath=str.substring(11,(str.length()-1));
        Log.d("taggggggggg",""+songPath);
            playOnline();
            try {
                mediaPlayer.prepare();
            } catch (IllegalStateException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            }
        mediaPlayer.start();
        return Service.START_STICKY;

    }

    public  void resumeMusic()
    {
        Log.d("dfdf", "dfdfdf");
        try{
            if(flag==true)
            {

                flag=false;
                mediaPlayer.start();
            }
            else {

            }
        }
        catch(Exception e)
        {
            Log.i("jjjj",""+e);
        }

    }
    public void pauseMusic()
    {

        try{

            flag=true;
            mediaPlayer.pause();
        }
        catch(Exception e)
        {
            Log.i("jjjj",""+e);
        }
    }



    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }

}

