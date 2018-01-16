package rimi.ritsai.cli.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class ResourceUtils {

	public static InputStream loadResource(String path, Locale locale) throws IOException {
		String namePattern = "(.*?)(\\.)(\\w?)";
		String valuePattern = "$1_%s\\.$3";
		List<String> resourcePath = new ArrayList<String>();
		resourcePath.add(path.replaceAll(namePattern, String.format(valuePattern, locale.toString())));
		resourcePath.add(path.replaceAll(namePattern, String.format(valuePattern, locale.getLanguage())));
		resourcePath.add(path);

		InputStream inputStream = null;
		for (String res : resourcePath) {
			Path filePath = Paths.get(res);
			if (Files.exists(filePath)) {
				inputStream = Files.newInputStream(filePath);
				break;
			}
			inputStream = ClassLoader.getSystemResourceAsStream(res);
			if (null != inputStream) {
				break;
			}
		}
		return inputStream;
	}

	public static String loadUtf8Text(String path, String defaultValue) {
		try {
			InputStream inputStream = loadResource(path, Locale.getDefault());
			return loadUtf8Text(inputStream);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String loadUtf8Text(InputStream inputStream) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString("UTF-8");
	}

	public static Object readObject(InputStream inputStream) throws IOException, ClassNotFoundException {
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(inputStream);
			return objectInputStream.readObject();
		} finally {
			close(objectInputStream);
		}
	}

	public static void writeObject(OutputStream outputSream, Serializable object) throws IOException {
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(outputSream);
			objectOutputStream.writeObject(object);
		} finally {
			close(objectOutputStream);
		}
	}

	public static void close(Closeable stream) {
		if (null != stream) {
			try {
				stream.close();
			} catch (IOException e) {
			}
		}
	}

}
