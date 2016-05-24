package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.logging.Level;

import config.Settings;

public class Logger {
	private static Writer writer = System.console().writer();

	public static void initializeLogger() {
		String path = Settings.LOGGER_WRITER;
		if (!path.equals("console")) {
			File logFile = new File(path);
			if (logFile.exists()) {
				try {
					writer = new FileWriter(logFile);
				} catch (IOException e) {
				System.out.println("Log writer creation failed: " + e.getMessage());
				}
			} else {
				System.out.println("Log file not found");
			}
		}

	}

	public static void log(Level level, Exception e, String message) {
		String log_msq = level.toString() + ": " + message
				+ "\n Error message:" + e.getMessage();
		try {
			writer.append(log_msq);
			writer.flush();
		} catch (IOException ex) {
			System.out.println("Logger exeception:" + ex.getMessage());
		}

	}

	public static void logERROR(Exception e, String message) {
		write("ERROR" + "\t" + message + "\nException message: "
				+ e.getMessage());
	}

	public static void logERROR(Exception e) {
		write("ERROR" + "\t" + "\nException message: " + e.getMessage());
	}

	public static void logINFO(String messString) {
		write("INFO" + "\t" + messString);
	}

	public static void logWARNING(String messString) {
		write("WARNING" + "\t" + messString);
	}

	private static void write(String log_msq) {
		try {
			writer.append(LocalDateTime.now() + "\t" + log_msq);
			writer.flush();
		} catch (IOException ex) {
			System.out.println("Logger exeception:" + ex.getMessage());
		}
	}
}
