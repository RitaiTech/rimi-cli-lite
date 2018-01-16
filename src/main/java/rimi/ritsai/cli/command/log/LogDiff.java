package rimi.ritsai.cli.command.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import rimi.ritsai.cli.command.data.Snapshot;
import rimi.ritsai.cli.command.data.SnapshotRepoistory;
import rimi.ritsai.cli.core.Command;
import rimi.ritsai.cli.core.Console;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.SystemException;
import rimi.ritsai.cli.support.PreferenceRepository;
import rimi.ritsai.cli.util.FileUtils;

@Command(name = "log diff", group = "log", usage = "log diff stageA [stageB]", description = "Compare the two stages and export the difference")
public class LogDiff implements ConsoleCommand {

	@Autowired
	private SnapshotRepoistory snapshots;

	@Autowired
	private PreferenceRepository preferences;

	@Override
	public void run(Console console, String... arguments) throws IllegalArgumentException, SystemException {
		preferences.findValues(LogPreference.values());
		Map<Path, Long> stageA, stageB;

		switch (arguments.length) {
		case 3:
			stageA = findSnapshot(arguments[2]).getStatusMap();
			stageB = LogSanpUtils.snapLogs(getTargetDirectory(LogPreference.LOGS_DIR.getKey()));
			exportDiff(stageA, stageB).forEach(dist -> {
				console.println("Exported the diff to %s", dist);
			});
			break;
		case 4:
			stageA = findSnapshot(arguments[2]).getStatusMap();
			stageB = findSnapshot(arguments[3]).getStatusMap();
			exportDiff(stageA, stageB).forEach(dist -> {
				console.println("Exported the diff to %s", dist);
			});
			;
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	private Snapshot findSnapshot(String name) throws SystemException {
		return snapshots.find(name).orElseThrow(() -> {
			throw new SystemException("Stage %s does not exist", name);
		});
	}

	private Path getTargetDirectory(String key) {
		String value = preferences.findValue(key).orElseThrow(() -> {
			throw new SystemException("Preference %s is not configured yet. Type 'log set' to do it.", key);
		});
		return Paths.get(value);
	}

	private List<Path> exportDiff(Map<Path, Long> stageA, Map<Path, Long> stageB) throws SystemException {
		Path source = getTargetDirectory(LogPreference.LOGS_DIR.getKey());
		Path target = getTargetDirectory(LogPreference.DIFF_DIR.getKey());
		List<Path> diffFiles = new ArrayList<Path>();
		List<Throwable> errors = new ArrayList<Throwable>();

		DateTimeFormatter dateTimeFormater = DateTimeFormatter.ofPattern("uMMDD_HHmmssSSS");
		stageB.forEach((file, size) -> {
			if (!stageA.containsKey(file)) {
				return;
			}

			String timeStamp = LocalDateTime.now().format(dateTimeFormater);
			Path distFilePath = target.resolve(timeStamp).resolve(file.relativize(source));
			Long prevPosition = stageA.get(file);
			long lengthToCopy = size - stageA.get(file);
			if (lengthToCopy < 0) {
				prevPosition = size;
				lengthToCopy = lengthToCopy * -1;
			}
			if (lengthToCopy == 0) {
				return;
			}

			try {
				if (!Files.exists(distFilePath.getParent())) {
					Files.createDirectory(distFilePath.getParent());
				}
				FileUtils.copy(file, distFilePath, prevPosition, lengthToCopy);
				diffFiles.add(distFilePath);
			} catch (IOException e) {
				errors.add(e);
			}
		});
		if (!errors.isEmpty()) {
			throw new SystemException(errors.stream().map(Throwable::getLocalizedMessage).collect(Collectors.joining("\n")));
		}
		if (diffFiles.isEmpty()) {
			throw new SystemException("Not change is found.");
		}
		return diffFiles;
	}

}
