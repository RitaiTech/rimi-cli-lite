package rimi.ritsai.cli.command;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import rimi.ritsai.cli.core.Command;
import rimi.ritsai.cli.core.Console;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.SystemException;
import rimi.ritsai.cli.util.CommandUtil;
import rimi.ritsai.cli.util.ResourceUtils;

@Command(name = "help", description = "Display the document of avaiable commands.")
public class Help implements ConsoleCommand {

	private List<ConsoleCommand> allCommands;

	@Autowired
	public void setCommands(List<ConsoleCommand> allCommands) {
		this.allCommands = allCommands;
		Collections.sort(allCommands, CommandUtil::compareForSorting);
	}

	@Override
	public void run(Console console, String... argument) throws IllegalArgumentException, SystemException {
		switch (argument.length) {
		case 1:
			console.println("Available commands : ");
			console.println("");		
			final String format = "%-20s %s";
			allCommands.stream().filter(cmd -> CommandUtil.getCommandGroup(cmd).isEmpty()).forEach(cmd -> {
				Command command = cmd.getClass().getAnnotation(Command.class);
				console.println(String.format(format, command.name(), command.description()));
			});
			break;
		case 2:
			final String name = argument[1];
			allCommands.stream().forEach(cmd -> {
				Command info = cmd.getClass().getAnnotation(Command.class);
				if (info.name().equalsIgnoreCase(name)) {
					String path = String.format("META-INF/shell/docs/%s.txt", info.name());
					console.println(ResourceUtils.loadUtf8Text(path, ""));
				}
			});
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

}
