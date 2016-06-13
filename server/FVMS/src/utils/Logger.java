package utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.logging.Level;

import config.Settings;

public class Logger {
	private static PrintStream writer = null;

	public static void initializeLogger() {
		String path = Settings.LOGGER_WRITER;
		if (!path.equals("console")) {
			File logFile = new File(path);
			if (logFile.exists()) {
				try {
					writer = new PrintStream(logFile);
				} catch (IOException e) {
					System.out.println("Log writer creation failed: "
							+ e.getMessage());
				}
			} else {
				System.out.println("Log file not found");
			}
		} else {
			writer = System.out;
		}

	}

	public static void log(Level level, Throwable e, String message) {
		String log_msq = level.toString() + ": " + message
				+ "\n Error message:" + e.getMessage();
		writer.println(log_msq);

	}

	public static void logERROR(Throwable e, String message) {
		write("ERROR" + "\t" + message + "\nException message: "
				+ e.getMessage());
	}

	public static void logERROR(Throwable e) {
		write("ERROR" + "\t" + "\nException message: " + e.getMessage());
	}

	public static void logINFO(String messString) {
		write("INFO" + "\t" + messString);
	}

	public static void logWARNING(String messString) {
		write("WARNING" + "\t" + messString);
	}

	private static void write(String log_msq) {
		writer.println(LocalDateTime.now() + "\t" + log_msq);
	}
}
