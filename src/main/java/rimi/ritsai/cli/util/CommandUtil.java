package rimi.ritsai.cli.util;

import java.text.Collator;

import rimi.ritsai.cli.core.Command;
import rimi.ritsai.cli.core.ConsoleCommand;

public final class CommandUtil {

	private static final String DEFAULT_VALUE = "";

	public static String getCommandName(ConsoleCommand bean) {
		return getCommandMetaData(bean).name();
	}

	public static String getCommandGroup(ConsoleCommand bean) {
		return getCommandMetaData(bean).group();
	}

	public static String getCommandUsage(ConsoleCommand bean) {
		return getCommandMetaData(bean).usage();
	}

	public static String getCommandManual(ConsoleCommand bean) {
		Command info = getCommandMetaData(bean);
		String docPath = info.manual();
		if (DEFAULT_VALUE.equals(docPath)) {
			docPath = String.format("META-INF/shell/docs/%s.txt", info.name());
		}
		return ResourceUtils.loadUtf8Text(docPath, "Document is not found");
	}

	public static Command getCommandMetaData(ConsoleCommand bean) {
		return bean.getClass().getAnnotation(Command.class);
	}

	public static int compareForSorting(ConsoleCommand beanA, ConsoleCommand beanB) {
		String nameA = getCommandName(beanA);
		String nameB = getCommandName(beanB);
		return Collator.getInstance().compare(nameA, nameB);
	}

}
