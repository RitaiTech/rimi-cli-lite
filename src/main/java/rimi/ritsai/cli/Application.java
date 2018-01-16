package rimi.ritsai.cli;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import rimi.ritsai.cli.core.CommandFactory;
import rimi.ritsai.cli.core.Console;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.SystemError;
import rimi.ritsai.cli.util.ArgumentParser;
import rimi.ritsai.cli.util.I18nMessage;
import rimi.ritsai.cli.util.ResourceUtils;

@Service
public class Application implements Console {

	private static ApplicationContext context;

	private static final I18nMessage I18N = I18nMessage.instance("META-INF/shell/i18n/messages.properties");
	private static final String PROMPT = I18N.getMessage("console > ");
	private static final String BLANK = "";

	private Scanner scanner = new Scanner(System.in);

	@Autowired
	private CommandFactory commandFactory;

	@Override
	public String prompt(String message, Object... args) {
		this.println(message, args);
		return scanner.nextLine();
	}

	@Override
	public void println(String message, Object... args) {
		System.out.println(I18N.getMessage(message, args));
	}

	public void run() {
		System.out.println(ResourceUtils.loadUtf8Text("META-INF/shell/i18n/banner.txt", ""));
		final boolean[] runningStatus = new boolean[] { true };
		while (runningStatus[0]) {
			System.out.print(PROMPT);
			String[] arguments = ArgumentParser.parse(scanner.nextLine());
			try {
				ConsoleCommand command = commandFactory.findCommonad(arguments);
				System.out.println(BLANK);
				command.run(this, arguments);
				System.out.println(BLANK);
			} catch (SystemError err) {
				System.err.println(err.getLocalizedMessage());
				runningStatus[0] = false;
			} catch (Exception exp) {
				System.out.println(exp.getLocalizedMessage());
				System.out.println(BLANK);
			}

			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				runningStatus[0] = false;
			}
		}
	}

	public static void main(String[] args) {
		context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		Application application = context.getBean(Application.class);
		application.run();
	}

}
