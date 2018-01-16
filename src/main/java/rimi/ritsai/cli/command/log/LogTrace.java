package rimi.ritsai.cli.command.log;

import rimi.ritsai.cli.core.Command;
import rimi.ritsai.cli.core.Console;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.SystemException;

@Command(name = "log trace", group = "log", usage = "log trace [keywords]", description = "Search key words in all logging files and sort them with execute order")
public class LogTrace implements ConsoleCommand {

	@Override
	public void run(Console console, String... arguments) throws IllegalArgumentException, SystemException {

	}

}
