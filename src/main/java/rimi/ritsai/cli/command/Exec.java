package rimi.ritsai.cli.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import rimi.ritsai.cli.core.Command;
import rimi.ritsai.cli.core.Console;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.SystemException;
import rimi.ritsai.cli.util.ResourceUtils;

@Command(name = "exec", usage = "exec command [arguments]", description = "Run a system command or shell script")
public class Exec implements ConsoleCommand {

	@Override
	public void run(Console console, String... arguments) throws IllegalArgumentException, SystemException {
		List<String> command = new ArrayList<String>(Arrays.asList(arguments));
		command.remove(0);

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		Map<String, String> envrionment = processBuilder.environment();
		envrionment.putAll(System.getenv());

		Process process = null;

		File out = createTempFile(".out");
		File err = createTempFile(".err");
		try {
			process = processBuilder.redirectErrorStream(true).redirectError(err).redirectOutput(out).start();
			int returnCode = process.waitFor();
			File output = 0 == returnCode ? out : err;
			String outputMessage = readOutPut(output);
			console.println(outputMessage);
		} catch (IOException | InterruptedException e) {
			throw new SystemException(e);
		} finally {
			if (null != process) {
				process.destroy();
			}
		}

	}

	private File createTempFile(String suffix) throws SystemException {
		File tmpFile;
		try {
			tmpFile = Files.createTempFile("tmp", suffix).toFile();
			tmpFile.deleteOnExit();
		} catch (IOException e) {
			throw new SystemException(e);
		}
		return tmpFile;
	}

	private String readOutPut(File file) throws SystemException {
		String output = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			output = ResourceUtils.loadUtf8Text(inputStream);
		} catch (IOException e) {
			throw new SystemException(e);
		} finally {
			ResourceUtils.close(inputStream);
		}
		return output;
	}

}
