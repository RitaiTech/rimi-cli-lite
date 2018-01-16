package rimi.ritsai.cli.util;

import rimi.ritsai.cli.core.Command;
import rimi.ritsai.cli.core.Console;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.SystemException;

@Command(name = "exec", usage = "exec command [arguments]", manual = "META-INF/shell/docs/exit.txt")
public class ExecCommand implements ConsoleCommand {

	@Override
	public void run(Console console, String... arguments) throws SystemException {
		// Nothing to do here
	}

}
