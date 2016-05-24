package config;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class Tools {
	public static String hashString(String content) {
		return hashContent(content.getBytes());
	}

	public static String hashContent(byte[] content) {
		String hashedPass = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(content);
			byte[] bytes = md.digest();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			hashedPass = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			Logger.getGlobal().info("hashPassword: " + e.getMessage());
		}
		return hashedPass;
	}

	public static int removeAllFiles(String folder_path) {
		int removed_files = 0;

		File root_folder = new File(folder_path);

		for (String file_name : root_folder.list()) {
			File file = new File(folder_path + "/" + file_name);
			if (file.isDirectory()) {
				removeAllFiles(file.getPath());
			}
			boolean deleted = file.delete();
			removed_files += deleted ? 1 : 0;
		}

		return removed_files;
	}

	public static String getDateTimeNow() {
		return LocalDateTime.now().format(
				DateTimeFormatter.ofPattern("uuuu-MM-dd-hh-mm-ss-SSS"));
	}
}
