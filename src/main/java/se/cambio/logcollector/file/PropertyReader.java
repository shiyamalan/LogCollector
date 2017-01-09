package se.cambio.logcollector.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	public static final String PROPERTY_FILE_KEYS[] = { "source-location", "target-location", "is-delete-source",
			"start-time", "end-time","file-copy-rule", "copying-file-size-more-than","file-travesing-stop-point"};

	public final static String PROPERTY_FILE_LOCATON = System.getProperty("user.dir");

	public final static String PROPERTY_FILE_NAME = "log-collector.properties";
	


	public static Properties properties;

	static
	{
		try {
			properties = readPropertyFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static Properties readPropertyFile() throws IOException {
		Properties properties = new Properties();
		InputStream input;

		File file = new File(getFileDir());
		if (!file.exists())
			throw new FileNotFoundException(
					"The Property File " + PROPERTY_FILE_NAME + " is not in " + PROPERTY_FILE_LOCATON);

		input = new FileInputStream(file);
		properties.load(input);
		return properties;
	}

	public static String getFileDir() {
		return PROPERTY_FILE_LOCATON + File.separator + PROPERTY_FILE_NAME;
	}

	public static String getPorpsValue(int i) {
		return properties.get(PROPERTY_FILE_KEYS[i]).toString();
	}
}
