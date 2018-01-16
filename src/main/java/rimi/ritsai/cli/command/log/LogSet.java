package rimi.ritsai.cli.command.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import rimi.ritsai.cli.command.Log;
import rimi.ritsai.cli.core.Command;
import rimi.ritsai.cli.core.Console;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.SystemException;
import rimi.ritsai.cli.poc.PreferenceAnnotation;
import rimi.ritsai.cli.poc.PreferenceAnnotationRepository;

@Command(name = "log set", group = "log", usage = "log set", description = "Update the settings of log command")
public class LogSet implements ConsoleCommand {

	@Autowired
	private PreferenceAnnotationRepository preference;

	@Override
	public void run(Console console, String... arguments) throws IllegalArgumentException, SystemException {
		final String promptFormat = "%s \t %s [%s] : ";
		preference.findAll(Log.class).entrySet().forEach(entry -> {
			PreferenceAnnotation preferenceDef = entry.getKey();
			String peferenceValue = entry.getValue();
			String newValue = console.prompt(promptFormat, preferenceDef.description(), preferenceDef.name(), peferenceValue);
			if (!ObjectUtils.isEmpty(newValue)) {
				preference.save(preferenceDef, newValue);
			}
		});
	}

}
