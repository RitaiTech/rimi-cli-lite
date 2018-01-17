package rimi.ritsai.cli.command.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import rimi.ritsai.cli.core.Command;
import rimi.ritsai.cli.core.Console;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.SystemException;
import rimi.ritsai.cli.support.PreferenceRepository;

@Command(name = "log set", group = "log", usage = "log set", description = "Update the settings of log command")
public class LogSet implements ConsoleCommand {

	@Autowired
	private PreferenceRepository preferences;

	@Override
	public void run(Console console, String... arguments) throws IllegalArgumentException, SystemException {
		final String promptFormat = "%s \t %s [%s] : ";
		preferences.findValues(LogPreference.values()).forEach((pref, value) -> {
			String newValue = console.prompt(promptFormat, pref.getDescription(), pref.getKey(), value);
			if (!ObjectUtils.isEmpty(newValue)) {
				preferences.saveValue(pref, newValue);
			}
		});
	}

}
