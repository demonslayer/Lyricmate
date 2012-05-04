package edu.colorado.csci.lyricmate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SongList extends ListActivity implements OnClickListener {
	private static final String MEDIA_PATH = new String("/sdcard");
	private List<String> songs = new ArrayList<String>();
	private Object[] songsObject;
	private String[] songsArray;
	private int currentPosition = 0;
	private ArrayAdapter<String> songList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		updateSongList();

		View shuffleButton = findViewById(R.id.shuffle);
		shuffleButton.setOnClickListener(this);
	}

	public void updateSongList() {
		
		songsObject = this.getIntent().getStringArrayExtra("song_list");

		if (songsArray == null) {
			File home = new File(MEDIA_PATH);
			if (home.listFiles(new Mp3Filter()).length > 0) {
				for (File file : home.listFiles(new Mp3Filter())) {
					songs.add(file.getName());
				}

				songList = new ArrayAdapter<String>(this, R.layout.song, songs);
				setListAdapter(songList);
				songsObject = songs.toArray();
			}
		} else {
			songs = Arrays.asList(songsArray(songsObject));
		}
	}

	private String[] songsArray(Object[] songsObject) {
		String[] songsArray = new String[songsObject.length];

		for (int i = 0; i < songsObject.length; i++) {
			songsArray[i] = songsObject[i].toString();
		}

		return songsArray;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		currentPosition = position;
		Intent i = new Intent(this, Song.class);
		String song = songs.get(currentPosition);
		i.putExtra("position", currentPosition);
		i.putExtra("media_path", MEDIA_PATH);
		i.putExtra("song", song);
		i.putExtra("song_list", songsArray(songsObject));
		startActivity(i);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shuffle:
			System.out.println("songs before: " + songs);
			Collections.shuffle(songs);
			System.out.println("songs after: " + songs);
			songsObject = songs.toArray();
			songList.notifyDataSetChanged();
		}
	}
}