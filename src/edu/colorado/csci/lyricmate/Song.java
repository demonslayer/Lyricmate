package edu.colorado.csci.lyricmate;

import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Song extends Activity {
	
	private int position;
	private String mediaPath;
	private String song;
    private MediaPlayer player = new MediaPlayer();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.songview);
		
		position = this.getIntent().getIntExtra("position", 0);
		mediaPath = this.getIntent().getStringExtra("media_path");
		song = this.getIntent().getStringExtra("song");
		
		TextView textTitle = (TextView) findViewById(R.id.songName);
		textTitle.setText(song);
		
		playSong(mediaPath + "/" + song);
		
	}
	
	private void playSong(String songPath) {
		try {
			
			player.reset();
			FileInputStream fis = new FileInputStream(songPath);
			player.setDataSource(fis.getFD());
			player.prepare();
			player.start();
			
		} catch (IOException e) {
			Log.v(getString(R.string.app_name), e.getMessage());
		}
	}

}
