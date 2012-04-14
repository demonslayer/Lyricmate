package edu.colorado.csci.lyricmate;

import java.io.File;
import java.io.FilenameFilter;

public class Mp3Filter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String filename) {
		return filename.endsWith(".mp3");
	}

}
