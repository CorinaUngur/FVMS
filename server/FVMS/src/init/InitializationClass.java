package init;

import java.io.File;

import utils.Logger;
import config.Settings;
import connection.Connector;
import db.UsersDB;

public class InitializationClass {
	public static void initialization() {

		Logger.initializeLogger();
		Settings.loadProperties("../config.properties");
		createRootAndTrashFolders();
		initializeDatabase();

	}

	private static void initializeDatabase() {
		UsersDB.getInstance().insertUser(Settings.DB_ADMIN_USERNMANE,
				"default", Settings.DB_ADMIN_EMAIL);
	}

	private static void createRootAndTrashFolders() {
		File rootFolder = new File(Settings.FS_ROOTFOLDER);
		createFolder(rootFolder);
		File trashFolder = new File(Settings.TRASH_FOLDER);
		createFolder(trashFolder);
		File tempFolder = new File(Settings.TEMP_PATH);
		createFolder(tempFolder);
	}

	private static void createFolder(File rootFolder) {
		if (!rootFolder.exists()) {
			boolean rootFolderCreated = rootFolder.mkdir();
			if (rootFolderCreated) {
				Logger.logINFO("Root folder created to path:"
						+ rootFolder.getPath());
			} else {
				Logger.logINFO("Root folder was not created");
			}
		} else {

			Logger.logINFO("Root folder already exists");
		}
	}

	public static void main(String args[]) {
		initialization();
		Connector conn = Connector.getInstance();
		conn.startMainLoop();
	}
}
