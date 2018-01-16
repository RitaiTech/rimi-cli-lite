package rimi.ritsai.cli.command.log;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import rimi.ritsai.cli.command.data.Snapshot;
import rimi.ritsai.cli.command.data.SnapshotRepoistory;
import rimi.ritsai.cli.core.Command;
import rimi.ritsai.cli.core.Console;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.SystemException;
import rimi.ritsai.cli.support.PreferenceRepository;

@Command(name = "log snap", group = "log", usage = "log snap [name] [description] [-d logDirectory]", description = "Compare the two stages and export the difference")
public class LogSnap implements ConsoleCommand {

	@Autowired
	private SnapshotRepoistory snapshots;

	@Autowired
	private PreferenceRepository perferences;

	@Override
	public void run(Console console, String... arguments) throws IllegalArgumentException, SystemException {

		Snapshot snapshot = null;
		switch (arguments.length) {
		case 2:
			final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final String messageFormat = "%-20s %-20s %s";
			Collection<Snapshot> allSnaps = snapshots.listAll();
			allSnaps.forEach(Snapshot -> {
				String createdDate = formater.format(Snapshot.getCreatedDate());
				console.println(String.format(messageFormat, createdDate, Snapshot.getName(), Snapshot.getDescription()));
			});
			if (allSnaps.isEmpty()) {
				console.println("No snapshot is created yet");
			}
			break;
		case 3:
			snapshot = new Snapshot(arguments[2]);
			createSnapshot(snapshot, console);
			break;
		case 4:
			snapshot = new Snapshot(arguments[2]);
			snapshot.setDescription(arguments[3]);
			createSnapshot(snapshot, console);
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	private void createSnapshot(Snapshot snapshot, Console console) throws SystemException {
		Path logDirectory = Paths.get(perferences.findValue(LogPreference.LOGS_DIR));
		Map<Path, Long> statusMap = LogSanpUtils.snapLogs(logDirectory);
		snapshot.setStatusMap(statusMap);
		snapshot.setLogDirectory(logDirectory);
		Optional<Snapshot> existingOne = snapshots.save(snapshot);
		String messge = existingOne.isPresent() ? "snapshot %s is replaced" : "snapshot %s is created";
		console.println(messge, snapshot.getName());

	}

}
