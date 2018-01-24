package rimi.ritsai.cli.command;

import rimi.ritsai.cli.core.Command;
import rimi.ritsai.cli.core.Console;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.SystemException;
import rimi.ritsai.cli.util.CommandUtil;

@Command(name = "log", usage = "log [commmand]", description = "The command of tracing application loggings")
public class Log implements ConsoleCommand {

	@Override
	public void run(Console console, String... arguments) throws IllegalArgumentException, SystemException {
		console.println(CommandUtil.getCommandManual(this));
	}

}
