package edu.colorado.csci.lyricmate;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Song extends Activity implements OnClickListener {

	private int position;
	private String mediaPath;
	private String song;
	private MediaPlayer player = new MediaPlayer();
	private boolean paused;
	private Song that = this;
	private Intent goBack;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.songview);

		position = this.getIntent().getIntExtra("position", 0);
		mediaPath = this.getIntent().getStringExtra("media_path");
		song = this.getIntent().getStringExtra("song");

		goBack = new Intent(that, SongList.class);

		MediaMetadataRetriever meta = new MediaMetadataRetriever();
		meta.setDataSource(mediaPath + "/" + song);

		String title = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
		String artist = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

		TextView textTitle = (TextView) findViewById(R.id.songName);
		if (title != null) {
			textTitle.setText(title);
		} else {
			textTitle.setText(song);
		}

		playSong(mediaPath + "/" + song);

		View stopButton = findViewById(R.id.stop);
		stopButton.setOnClickListener(this);

		View playButton = findViewById(R.id.play);
		playButton.setOnClickListener(this);

		View pauseButton = findViewById(R.id.pause);
		pauseButton.setOnClickListener(this);

	}

	private void playSong(String songPath) {
		try {

			player.reset();
			FileInputStream fis = new FileInputStream(songPath);
			player.setDataSource(fis.getFD());
			player.prepare();
			player.start();
			paused = false;

			player.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					paused = false;
					startActivity(goBack);
				}
			});

		} catch (IOException e) {
			Log.v(getString(R.string.app_name), e.getMessage());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.stop:
			player.stop();
			paused = false;
			startActivity(goBack);
			break;
		case R.id.play:
			if (!player.isPlaying() && !paused) {
				playSong(mediaPath + "/" + song);
			} else if (paused) {
				player.start();
				paused = false;
			}
			break;
		case R.id.pause:
			if (paused) {
				player.start();
				paused = false;
			} else {
				player.pause();
				paused = true;
			}
		}
	}

}
