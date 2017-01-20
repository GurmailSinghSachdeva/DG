package com.example.lenovo.discountgali.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class AudioListActivity  {
   /* private Button button;
    final String MEDIA_PATH = Environment.getExternalStorageDirectory()
            .getPath() + "/";
    private ArrayList<HashMap<String, File>> songsList = new ArrayList<HashMap<String, File>>();
    private ArrayList<HashMap<String, File>> songsList1 = new ArrayList<HashMap<String, File>>();
    String[] musicName;
    String[] musicPath;

    //private String mp3Pattern = ".mp3";
    private String aacPattern = ".aac";
    private String three_gpPattern = ".3gp";

    private ArrayAdapter arrayAdapter;
    private ListView listView;
    Intent intent;
    Boolean aBoolean=false;
    HashMap<String, File> songMap;
    AudioPlayerService myService;
    Context context;
    DataCallBack dataCallBack;

    public void selectAudio(Context context,DataCallBack dataCallBack){
        this.context = context;
        this.dataCallBack = dataCallBack;
        songsList1 = getPlayList();
//                    songMap.put("songPath","stop");
//                    songMap.put("songPat","resume");
        musicName = new String[songsList1.size()];
        musicPath = new String[songsList1.size()];
        for(int i=0;i<songsList1.size();i++){
            musicName[i] = songsList1.get(i).get("songPath").getName();
            musicPath[i] = songsList1.get(i).get("songPath").getAbsolutePath();
        }
        startAudioFilesFetching(musicName);
    }

    *//**
     * Function to read all mp3 files and store the details in
     * ArrayList
     *//*
    public ArrayList<HashMap<String, File>> getPlayList() {
        //  Log.i("firstlog", "mediapath=" + MEDIA_PATH);
        if (MEDIA_PATH != null) {
            File home = new File(MEDIA_PATH);

            File[] listFiles = home.listFiles();
            //   Log.i("secondlog", "files=" + listFiles.length);

            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    //  Log.i("thirdlog", "absolutepath=" + file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        // return songs list array
        return songsList;
    }

    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }

                }
            }
        }
    }

    private void addSongToList(File song) {
        if (song.getName().endsWith(three_gpPattern)||song.getName().endsWith(aacPattern)) {
            songMap = new HashMap<String, File>();
//            songMap.put("songTitle",
//                   song.getName().substring(0, (song.getName().length() - 4)));
            songMap.put("songPath", song);
            //      Log.i("kkkkkkkk", "" + song.getName().substring(0, (song.getName().length() - 4)));


            // Adding each song to SongList
            songsList.add(songMap);
        }
    }

    String selectedPath;

    public void startAudioFilesFetching(String[] array ){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle("Select Audio");

        alert.setSingleChoiceItems(array,-1, new
                DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedPath = musicPath[which];

                    }

                });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(selectedPath)){
                    AlertUtils.showToast(context,"Please select audio file");
                }else{
                    dialog.dismiss();
                    Syso.print(">>>>>> selected path :::"+selectedPath);
                    dataCallBack.data(selectedPath);
                }
            }
        });
        alert.show();
    }



//    public static Intent getStartIntent(Context context) {
//        Intent intent = new Intent(context, AudioListActivity.class);
//        return intent;
//    }


//    @Override
//    void inItUi() {
//        button = (Button) findViewById(R.id.button);
//        listView = (ListView) findViewById(R.id.list_item);
//
//    }
//
//    @Override
//    void setListener() {
//        button.setOnClickListener(this);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // When clicked, show a toast with the TextView text
//                Toast.makeText(getApplicationContext(),
//                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
//                if (aBoolean == false) {
//                    aBoolean = true;
//                    intent = new Intent(AudioListActivity.this, AudioPlayerService.class);
//                    intent.putExtra("id", "" + ((TextView) view).getText().toString());
//                    Log.i("kkkkkkk", "" + ((TextView) view).getText().toString() + ".mp3");
//                    startService(intent);
//
//                } else {
//                    if (((TextView) view).getText().toString().equals("{songPath=stop}")) {
//                        myService.pauseMusic();
//                    }
//                    else if (((TextView) view).getText().toString().equals("{songPat=resume}")) {
//                        myService.resumeMusic();
//                    }
//                    else {
//                        stopService(new Intent(AudioListActivity.this, AudioPlayerService.class));
//                        intent = new Intent(AudioListActivity.this, AudioPlayerService.class);
//                        intent.putExtra("id", "" + ((TextView) view).getText().toString());
//                        Log.i("kkkkkkk", "" + ((TextView) view).getText().toString() + ".mp3");
//                        startService(intent);
//                    }
//
//                }
//
//
//            }
//        });
//    }
//
//    @Override
//    void setData() {
//        myService=new AudioPlayerService();
//    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_audio_list);
//
//    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable("places", songsList1);
//    }
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//            if (savedInstanceState != null) {
//                songsList1 =(ArrayList<HashMap<String,File>>) savedInstanceState.getSerializable("places");
//            }
//        }






//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.button:
//                try {
//                    button.setVisibility(View.INVISIBLE);
//                    songsList1 = getPlayList();
////                    songMap.put("songPath","stop");
////                    songMap.put("songPat","resume");
//                    musicName = new String[songsList1.size()];
//                    musicPath = new String[songsList1.size()];
//                    for(int i=0;i<songsList1.size();i++){
//                        musicName[i] = songsList1.get(i).get("songPath").getName();
//                        musicPath[i] = songsList1.get(i).get("songPath").getAbsolutePath();
//                    }
//                    startAudioFilesFetching(musicName);
////                    arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, songsList1);
////                    listView.setAdapter(arrayAdapter);
////                    Log.i("lllll", "" + listView);
//                } catch (Exception e) {
//                    Log.i("llllllllllllll", "" + e);
//                }
//                break;
//
//        }
//
//    }

*/
}