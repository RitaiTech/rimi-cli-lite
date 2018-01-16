package rimi.ritsai.cli.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentParser {

	private static List<String> extractArguments(String line) {
		List<String> argumentList = new ArrayList<String>();
		Pattern pattern = Pattern.compile("('(.*?)'|\"(.*?)\"|'(.*?)'|\\S+)");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String text = null;
			for (int i = matcher.groupCount(); i > 0; i--) {
				text = matcher.group(i);
				if (text != null)
					break;
			}
			argumentList.add(text);
		}
		return Collections.unmodifiableList(argumentList);
	}

	public static String[] parse(String line) {
		return extractArguments(line).toArray(new String[0]);
	}
}
