package versioning;

import init.InitializationClass;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import utils.Tools;
import versioning.tools.Messages;
import config.Settings;
import db.FileSystemDB;
import db.ProjectsDB;
import db.UsersDB;
import db.tools.Config;

public class FilesOperation {
	FileSystem fs = null;
	FileSystemDB fsdb = null;
	ProjectsDB pdb = null;
	byte[] standard_content = null;
	String userEmail = null;
	String fileName = null;
	String message = null;
	String user = null;
	int pid = 0;

	@Before
	public void setUp() {
		InitializationClass.initialization();
		fs = new FileSystem();
		fsdb = FileSystemDB.getInstance();
		pdb = ProjectsDB.getInstance();
		
		standard_content = "some content".getBytes();
		userEmail = "files@test.user";
		user = "filesTestUser";
		fileName = "testFile.txt";
		message = "test message";

		pdb.removeAllFilesAndProjects();
		fsdb.removeAllFiles();
		fs.removeFilesFromRoot();
		fs.emptyTrash();
		
		UsersDB.getInstance().insertUser(user , "parola", userEmail);
		pdb.addNewProject("test_project", user);
		pid = pdb.getProjectID("test_project");
	}

	@Test
	public void addNewFile_validInput() {
		String result = fs.add_newFile(standard_content, fileName, user,pid);
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
	public void addNewFile_sameContentAsAnotherone(){
		
		fs.add_newFile(standard_content, fileName, user, 1);
		String result = fs.add_newFile(standard_content, fileName + "changed", userEmail,pid);
		
		Assert.assertEquals(Messages.File_boundToProject.toString(), result);

	}

	@Test
	public void addChange_validInput() {
		fs.add_newFile(standard_content, fileName, user,pid);
		int id = fsdb.getChangeID(Tools.hashContent(standard_content));
		byte[] changed_content = "changed content".getBytes();
		String result = fs.addChange(id,pid, user, changed_content, fileName,
				message);

		Assert.assertEquals(Messages.File_added.toString(), result);

		String path = fsdb.getFilePath(Tools.hashContent(changed_content));
		File file = new File(path);
		Assert.assertTrue(file.exists());
	}

	@Test
	public void moveToTrash_validFile() {
		fs.add_newFile(standard_content, fileName, user,pid);
		int id = fsdb.getChangeID(Tools.hashContent(standard_content));
		byte[] changed_content = "changed content".getBytes();
		fs.addChange(id, pid, user, changed_content, fileName, "test change");

		String result = fs.moveFileToTrash(id);
		Assert.assertEquals(Messages.File_removed.toString(), result);

		File folder = new File(Settings.FS_ROOTFOLDER + id);
		Assert.assertFalse(folder.exists());

		File trashFolder = new File(Settings.TRASH_FOLDER + id);
		Assert.assertTrue(trashFolder.exists());
		
		int currentStatus = fsdb.getStatus(id);
		Assert.assertEquals(Config.STATUS_MOVEDTOTRASH.getInt(), currentStatus);
	}
	@Test
	public void emptyTrash_2VersionsFile(){
		fs.add_newFile(standard_content, fileName, user,pid);
		int id = fsdb.getChangeID(Tools.hashContent(standard_content));
		byte[] changed_content = "changed content".getBytes();
		fs.addChange(id, pid, user, changed_content, fileName, "test change");

		String result = fs.emptyTrash();
		Assert.assertEquals(Messages.Trash_empty.toString(), result);

		File trashFolder = new File(Settings.TRASH_FOLDER);
		Assert.assertTrue(trashFolder.list().length == 0);
		
		int remainedFilesInDB = fsdb.removeTrashFiles();
		Assert.assertEquals(0, remainedFilesInDB);
	}
}
