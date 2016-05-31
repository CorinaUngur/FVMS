package db;

import init.InitializationClass;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import versioning.FileSystem;
import db.beans.Project;

public class ProjectsOperations {
	private int uid = 0;
	private ProjectsDB pdb = null;
	private FileSystem fs = null;
	String email = null;
	String user = null;

	@Before
	public void setUP() {
		InitializationClass.initialization();
		pdb = ProjectsDB.getInstance();
		fs = new FileSystem();
		user = "user";
		email = "email@email.com";
		UsersDB.getInstance().insertUser("user", "parola", email);
		uid = UsersDB.getInstance().getUID_byEmail("email@email.com");
		pdb.clearProjects();
		
		pdb.removeAllFilesAndProjects();
		FileSystemDB.getInstance().removeAllFiles();
		fs.removeFilesFromRoot();
		fs.emptyTrash();
	}

	@Test
	public void getProjects_validationTest() {
		String project_name = "newProject";
		pdb.addNewProject(project_name, user);
		fs.add_newFile("some_content".getBytes(), "rpath/file.txt", user,
				pdb.getProjectID(project_name));
		List<Project> projects = pdb.getProjects(uid);
		
		Assert.assertEquals(1, projects.size());
	}
}
