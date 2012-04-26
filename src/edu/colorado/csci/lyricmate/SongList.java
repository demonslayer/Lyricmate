package edu.colorado.csci.lyricmate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SongList extends ListActivity {
    private static final String MEDIA_PATH = new String("/sdcard");
    private List<String> songs = new ArrayList<String>();
    private MediaPlayer mp = new MediaPlayer();
    private int currentPosition = 0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        updateSongList();
    }

	public void updateSongList() {
		File home = new File(MEDIA_PATH);
		if (home.listFiles(new Mp3Filter()).length > 0) {
			for (File file : home.listFiles(new Mp3Filter())) {
				songs.add(file.getName());
			}
			
			ArrayAdapter<String> songList = new ArrayAdapter<String>(this, R.layout.song, songs);
			setListAdapter(songList);
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		currentPosition = position;
		Intent i = new Intent(this, Song.class);
		String song = songs.get(currentPosition);
		i.putExtra("position", currentPosition);
		i.putExtra("media_path", MEDIA_PATH);
		i.putExtra("song", song);
		startActivity(i);
//		playSong(MEDIA_PATH + "/" + songs.get(position));
	}
}