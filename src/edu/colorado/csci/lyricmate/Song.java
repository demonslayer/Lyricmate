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
	private String title;
	private String artist;
	private String[] songs;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.songview);

		position = this.getIntent().getIntExtra("position", 0);
		System.out.println("the position is " + position);
		mediaPath = this.getIntent().getStringExtra("media_path");
		song = this.getIntent().getStringExtra("song");
		songs = this.getIntent().getStringArrayExtra("song_list");

		goBack = new Intent(that, SongList.class);

		MediaMetadataRetriever meta = new MediaMetadataRetriever();
		meta.setDataSource(mediaPath + "/" + song);

		title = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
		artist = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

		TextView textTitle = (TextView) findViewById(R.id.songName);
		if (title != null) {
			textTitle.setText(title);
		} else {
			textTitle.setText(song);
		}

		TextView textArtist = (TextView) findViewById(R.id.artist);
		if (artist != null) {
			textArtist.setText(artist);
		} else {
			textArtist.setText("");
		}

		playSong(mediaPath + "/" + song);

		View stopButton = findViewById(R.id.stop);
		stopButton.setOnClickListener(this);

		View playButton = findViewById(R.id.play);
		playButton.setOnClickListener(this);

		View pauseButton = findViewById(R.id.pause);
		pauseButton.setOnClickListener(this);

		View lyricButton = findViewById(R.id.lyrics);
		lyricButton.setOnClickListener(this);

		View artistButton = findViewById(R.id.ArtistInfo);
		artistButton.setOnClickListener(this);

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
					if (position == (songs.length - 1) ) {
						startActivity(goBack);
					} else {
						getIntent().putExtra("song", songs[position + 1]);
						getIntent().putExtra("position", position + 1);
						startActivity(getIntent()); 
						finish();	
					}
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
			break;
		case R.id.lyrics:
			Intent lyric = new Intent(this, Lyrics.class);
			lyric.putExtra("title", title);
			lyric.putExtra("artist", artist);
			lyric.putExtra("lyrics", this.getIntent().getStringArrayExtra("lyric_array")[position]);
			startActivity(lyric);
			break;
		case R.id.ArtistInfo:
			Intent artistIntent = new Intent(this, Artist.class);
			artistIntent.putExtra("artist", artist);
			startActivity(artistIntent);
			break;
		}
	}

}
