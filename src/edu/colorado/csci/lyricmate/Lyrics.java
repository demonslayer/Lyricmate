package edu.colorado.csci.lyricmate;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Lyrics extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lyricview);
		
		String title = this.getIntent().getExtras().getString("title");
		String artist = this.getIntent().getExtras().getString("artist");
		
		TextView textTitle = (TextView) findViewById(R.id.songName);
		if (title != null) {
			textTitle.setText(title);
		} else {
			// TODO fix this
			textTitle.setText("");
		}
		
		String lyrics;
		try {
			lyrics = new LyricGetter().getLyrics(title, artist);
			TextView lyricView = (TextView) findViewById(R.id.lyricView);
			lyricView.setText(lyrics);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TextView textArtist = (TextView) findViewById(R.id.artist);
		if (artist != null) {
			textArtist.setText(artist);
		} else {
			textArtist.setText("");
		}
	}

}
