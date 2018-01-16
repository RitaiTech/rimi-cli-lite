package rimi.ritsai.cli.command.log;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import rimi.ritsai.cli.exception.SystemException;

public final class LogSanpUtils {

	public static Map<Path, Long> snapLogs(Path rootDir) throws SystemException {
		LogFileVistor logFileVistor = new LogFileVistor();
		try {
			Files.walkFileTree(rootDir, logFileVistor);
		} catch (IOException e) {
			throw new SystemException(e);
		}
		return logFileVistor.getIndex();
	}

	static class LogFileVistor implements FileVisitor<Path> {

		private Map<Path, Long> index = new HashMap<Path, Long>();

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			index.put(file, Long.valueOf(attrs.size()));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		public Map<Path, Long> getIndex() {
			return index;
		}
	}
}
