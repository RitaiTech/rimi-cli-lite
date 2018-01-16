package rimi.ritsai.cli.util;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

public class FileUtils {

	public static void copy(Path source, Path target) throws IOException {
		FileChannel input = FileChannel.open(source, READ);
		FileChannel ouput = FileChannel.open(source, WRITE, CREATE);
		input.transferTo(0, input.size(), ouput);
	}

	public static void copy(Path source, Path target, long position, long count) throws IOException {
		FileChannel input = FileChannel.open(source, READ);
		FileChannel ouput = FileChannel.open(source, WRITE, CREATE, APPEND);
		input.transferTo(position, count, ouput);
	}

}
