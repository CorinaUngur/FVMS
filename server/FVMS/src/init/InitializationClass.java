package init;
import java.io.File;

import utils.Logger;
import config.Settings;

public class InitializationClass {
	public static void initialization() {

		Logger.initializeLogger();
		Settings.loadProperties("../config.properties");
		createRootAndTrashFolders();
		
	}

	private static void createRootAndTrashFolders() {
		File rootFolder = new File(Settings.FS_ROOTFOLDER);
		if(!rootFolder.exists()){
			boolean rootFolderCreated = rootFolder.mkdir();
			if(rootFolderCreated){
				Logger.logINFO("Root folder created to path:" + rootFolder.getPath());
			}else {
				Logger.logINFO("Root folder was not created");
			}
		} else {

			Logger.logINFO("Root folder already exists");
		}
		File trashFolder = new File(Settings.TRASH_FOLDER);
		if(!trashFolder.exists()){
			boolean trashFolderCreated = trashFolder.mkdir();
			if(trashFolderCreated){
				Logger.logINFO("Trash folder created to path:" + trashFolder.getPath());
			}else {
				Logger.logINFO("trash folder was not created");
			}
		} else {
			Logger.logINFO("Trash folder exists");
		}
	}

	public static void main(String args[]) {
		initialization();
	}
}
