package se.cambio.logcollector.file;

import java.io.File;
import java.io.FilenameFilter;

public class Path 
{

	public static String addPathSeparator(String path) {
		final String PATH_SEPARATOR_FORWARD = "/";
		final String PATH_SEPARATOR_BACKWARD = "\\";
		String directives[] = null;

		if (path.contains(PATH_SEPARATOR_FORWARD))
			directives = path.split(PATH_SEPARATOR_FORWARD);
		if (path.contains(PATH_SEPARATOR_BACKWARD))
			directives = path.split("\\\\");

		path = getPath(directives);

		return path;
	}

	public static String getPath(String[] directives) {

		String path_with_separator = "";
		if (directives == null)
			return null;
		for (String dir : directives) {
			path_with_separator += dir + File.separator;
		}
		return path_with_separator;
	}
	
	public static String[] getSubDirctives(File folder) {
		return folder.list(new FilenameFilter() {
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
	}
}
