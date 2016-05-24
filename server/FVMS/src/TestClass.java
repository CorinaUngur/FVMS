import config.Settings;
import utils.Logger;

public class TestClass {
	public static void main(String args[]) {
		Logger.initializeLogger();
		Settings.loadProperties("../config.properties");
	}
}
