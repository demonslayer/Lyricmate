package edu.colorado.csci.lyricmate;

import java.io.IOException;

import android.media.MediaMetadataRetriever;

public class SearchHelper {

	public static String findLyrics(String song, String mediaPath) {
		MediaMetadataRetriever meta = new MediaMetadataRetriever();
		meta.setDataSource(mediaPath + "/" + song);
		
		String title = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
		String artist = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
		
		LyricGetter lyrGet = new LyricGetter();
		try {
			String lyrics = lyrGet.getLyrics(title, artist);
			return lyrics;
		} catch (IOException e) {
			return "Sorry, no lyrics are available for the song " + title + " by " + artist;
		}
	}

	public static String findBios(String song, String mediaPath) {
		MediaMetadataRetriever meta = new MediaMetadataRetriever();
		meta.setDataSource(mediaPath + "/" + song);
		
		String artist = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
		
		ArtistGetter arGet = new ArtistGetter();
		try {
			String bio = arGet.getBio(artist);
			return bio;
		} catch (IOException e) {
			return "Sorry, no bio is available for the artist " + artist;
		}
	}

}
