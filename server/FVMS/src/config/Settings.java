package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import utils.Logger;

public class Settings {
	private static Properties properties = null;

	public static void loadProperties(String load_file) {
		try {
			FileInputStream fis = new FileInputStream(load_file);
			properties = new Properties();
			properties.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			Logger.logERROR(e);
		} catch (IOException e) {
			Logger.logERROR(e);
		}
		DB_URL = properties.getProperty("db_url");
		DB_NAME = properties.getProperty("db_name");
		DB_USER=properties.getProperty("db_user");
		DB_ADMIN_USERNMANE=properties.getProperty("db_admin_user");
		DB_ADMIN_EMAIL=properties.getProperty("db_admin_email");
		
		DB_PASSWORD=properties.getProperty("db_password");
		TRASH_ENABLED = Boolean.parseBoolean(properties.getProperty("trash_enabled"));
		TRASH_EXPTIME = Integer.parseInt(properties.getProperty("trash_expirationTime"));
		TRASH_MAXSIZE = Integer.parseInt(properties.getProperty("trash_limit"));
		TRASH_FOLDER = properties.getProperty("trash_folder");
		FS_ROOTFOLDER = properties.getProperty("fileSystem_rootFolder");
		CONN_QLOGIN = properties.getProperty("conn_loginQueue");
		CONN_HOST = properties.getProperty("conn_host");
		LOGGER_WRITER = properties.getProperty("logger_writer");
		
	}
	
	public static String DB_URL="localhost";
	public static String DB_NAME="fvms";
	public static String DB_USER="root";
	public static String DB_PASSWORD="parola";
	public static String DB_ADMIN_USERNMANE="default";
	public static String DB_ADMIN_EMAIL="default";
	public static boolean TRASH_ENABLED = true;
	public static int TRASH_EXPTIME = 10;
	public static int TRASH_MAXSIZE = 1024;
	public static String TRASH_FOLDER="./TRASH";
	public static String FS_ROOTFOLDER = "./";
	public static String CONN_QLOGIN="QLogin";
	public static String CONN_HOST="localost";
	public static String LOGGER_WRITER="console";
}
