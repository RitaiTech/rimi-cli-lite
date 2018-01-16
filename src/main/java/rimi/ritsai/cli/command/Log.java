package rimi.ritsai.cli.command;

import rimi.ritsai.cli.core.Command;
import rimi.ritsai.cli.core.Console;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.SystemException;
import rimi.ritsai.cli.poc.PreferenceAnnotation;
import rimi.ritsai.cli.util.CommandUtil;

@PreferenceAnnotation(name = "log.dir.monitor", description = "Where log files are", required = true)
@PreferenceAnnotation(name = "log.dir.outputs", description = "Where diff output", required = true)
@Command(name = "log", usage = "log [commmand]", description = "The command of tracing application loggings")
public class Log implements ConsoleCommand {

	@Override
	public void run(Console console, String... arguments) throws IllegalArgumentException, SystemException {
		console.println(CommandUtil.getCommandManual(this));
	}

}
