package edu.colorado.csci.lyricmate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class LyricGetter {
	
	public String getLyrics(String title, String artist) throws IOException {
		title = title.replaceAll(" ", "");
		title = title.toLowerCase();
		title = title.replaceAll("'", "");
		artist = artist.replaceAll(" ", "");
		artist = artist.toLowerCase();

		String lyric = new String();

		URL url = new URL("http://www.azlyrics.com/lyrics/"+ artist + "/" + title + ".html");
		System.out.println(url);

		BufferedReader reader;
		
		boolean use;
		use = false;
		reader = new BufferedReader(
				new InputStreamReader(url.openStream()));
		String s = new String();

		while((s = reader.readLine()) != null) {
			if (s.startsWith("<!-- start of lyrics -->")) {
				use = true;
			} else if (s.startsWith("<!-- end of lyrics -->")) {
				use = false;
			} else if (use == true) {
				s = s.replace("<br>", "\n");
				lyric += s;
			}
		}

		return lyric;

	}

}
