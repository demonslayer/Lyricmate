package edu.colorado.csci.lyricmate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ArtistGetter {

	public String getBio(String artist) throws IOException {

		artist = artist.replaceAll(" ", "_");
		artist = artist.replaceAll("\'", "");

		String bio = new String();

		URL url = new URL("http://en.wikipedia.org/wiki/"+ artist + "_(band)");

		BufferedReader reader;

		boolean use;
		use = false;
		try {
			reader = new BufferedReader(
					new InputStreamReader(url.openStream()));
		} catch (FileNotFoundException e) {
			url = new URL("http://en.wikipedia.org/wiki/"+ artist);
			try {
			reader = new BufferedReader(
					new InputStreamReader(url.openStream()));
			} catch (FileNotFoundException f) {
				return "Sorry, no information was found for the artist " + artist + ".";
			}
		}
		String s = new String();

		while((s = reader.readLine()) != null) {
			bio += s;
		}

		return bio;

	}

}
