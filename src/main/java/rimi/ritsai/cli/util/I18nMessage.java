package rimi.ritsai.cli.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public class I18nMessage {

	public final static I18nMessage instance(String messageFile, Locale locale) {
		Properties messageSource = new Properties();
		InputStream inputSteam = null;
		try {
			inputSteam = ResourceUtils.loadResource(messageFile, locale);
			messageSource.load(inputSteam);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ResourceUtils.close(inputSteam);
		}
		return new I18nMessage(messageSource);
	}

	public final static I18nMessage instance(String messagePath) {
		return instance(messagePath, Locale.getDefault());
	}

	private Properties properties;

	private I18nMessage(Properties properties) {
		this.properties = properties;
	}

	public String getMessage(String message, Object... args) {
		String messageKey = message.replaceAll("\\s", "_");
		String localizedMessage = properties.getProperty(messageKey, message);
		return String.format(localizedMessage, args);
	}

}
