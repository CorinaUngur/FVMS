package versioning;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import config.Tools;
import db.FileSystemDB;
import db.UsersDB;
import fileSystem.Config;
import fileSystem.FileSystem;
import fileSystem.tools.Messages;

public class FilesOperation {
	FileSystem fs = null;
	FileSystemDB fsdb = null;
	byte[] standard_content = null;
	String userEmail = null;
	String fileName = null;
	String message = null;

	@Before
	public void setUp() {
		fs = new FileSystem();
		fsdb = FileSystemDB.getInstance();
		standard_content = "some content".getBytes();
		userEmail = "files@test.user";
		UsersDB.getInstance().insertUser("filesTestUser", "parola",
				userEmail);
		fileName = "testFile.txt";
		message = "test message";
		fsdb.removeAllFiles();
		Tools.removeAllFiles(Config.ROOT_FOLDER.toString());
	}

	@Test
	public void addNewFile_validInput() {
		String result = fs.add_newFile(standard_content, fileName, userEmail);
		Assert.assertEquals(Messages.File_added.toString(), result);
		
		String hash = Tools.hashContent(standard_content);
		boolean isFile = fsdb.isFileInDB(Tools.hashContent(standard_content));
		Assert.assertTrue(isFile);
		
		String path = fsdb.getFilePath(hash);
		File file = new File(path);
		Assert.assertTrue(file.exists());
		
		String folder_name = "" + fsdb.getOriginalFileID(hash);
		Assert.assertTrue(path.contains("/" + folder_name + "/"));
	}
	
	@Test
	public void addChange_validInput(){
		fs.add_newFile(standard_content, fileName, userEmail);
		int id = fsdb.getChangeID(Tools.hashContent(standard_content));
		byte[] changed_content = "changed content".getBytes();
		String result = fs.addChange(id, userEmail, changed_content, fileName, message);
		
		Assert.assertEquals(Messages.File_added.toString(), result);
		
		String path = fsdb.getFilePath(Tools.hashContent(changed_content));
		File file = new File(path);
		Assert.assertTrue(file.exists());
	}
}
