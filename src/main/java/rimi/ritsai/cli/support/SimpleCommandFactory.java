package rimi.ritsai.cli.support;

import static java.util.stream.Collectors.groupingBy;
import static rimi.ritsai.cli.util.CommandUtil.getCommandName;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import rimi.ritsai.cli.core.CommandFactory;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.CommandNotFoundException;
import rimi.ritsai.cli.util.CommandUtil;

@Component
public class SimpleCommandFactory implements CommandFactory {

	private static Map<String, List<ConsoleCommand>> groupedCommands = null;

	@Autowired
	public void setAllCommands(List<ConsoleCommand> allCommands) {
		if (ObjectUtils.isEmpty(groupedCommands)) {
			groupedCommands = allCommands.stream().filter(cmd -> null != cmd).collect(groupingBy(CommandUtil::getCommandGroup));
		}
	}

	@Override
	public ConsoleCommand findCommonad(String... arguments) throws CommandNotFoundException {
		final String groupName = arguments.length < 2 ? "" : arguments[0];
		List<ConsoleCommand> commands = groupedCommands.get(groupName);
		if (ObjectUtils.isEmpty(commands)) {
			commands = groupedCommands.get("");
		}

		final String commdName = getCommandNameArgument(arguments);
		String searchName = commdName;
		Optional<ConsoleCommand> result = commands.stream().filter(cmd -> getCommandName(cmd).equals(commdName)).findFirst();
		if (!result.isPresent()) {
			searchName = groupName;
			result = groupedCommands.get("").stream().filter(cmd -> getCommandName(cmd).equals(groupName)).findFirst();
		}

		if (!result.isPresent()) {
			throw new CommandNotFoundException(searchName);
		}
		return result.get();
	}

	private String getCommandNameArgument(String... arguments) {
		String groupName = arguments.length < 2 ? "" : arguments[0];
		String commdName = arguments.length < 2 ? groupName : String.format("%s %s", arguments[0], arguments[1]);
		if (arguments.length == 1) {
			commdName = arguments[0];
		}
		return commdName;
	}

}
