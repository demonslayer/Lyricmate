package edu.colorado.csci.lyricmate;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Artist extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.artistview);
		
		String artist = this.getIntent().getExtras().getString("artist");
		
		
		String bio = this.getIntent().getStringExtra("bio");

		TextView bioView = (TextView) findViewById(R.id.artistView);
		bioView.setText(bio);
		
		TextView textArtist = (TextView) findViewById(R.id.artistName);
		if (artist != null) {
			textArtist.setText(artist);
		} else {
			textArtist.setText("No Artist");
		}
	}

}
