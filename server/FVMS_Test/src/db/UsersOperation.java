package db;

import init.InitializationClass;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import db.tools.Messages;

public class UsersOperation {
	private UsersDB db;
	private String username = "user";
	private String email = "email@email.com";
	private String password = "parola";
	@Before
	public void setUP() {
		InitializationClass.initialization();
		db = UsersDB.getInstance();
		db.removeUserFromTeam_byEmail("email@email.com", "team");
		db.removeUserFromTeam_byUsername("user", "team");
		db.removeLiderFromTeam_byEmail("email@email.com", "team");
		db.removeLiderFromTeam_byUsername("user", "team");
		db.removeUser_byUsername("user");
		db.removeUser_byEmail("email@email.com");
		db.removeTeam("team");
	}

	@Test
	public void insertuser_valid() {

		String result = db.insertUser("user", "parola", "email@email.com");

		Assert.assertEquals(Messages.User_inserted.toString(), result);
	}

	@Test
	public void insertuser_emailused() {
		db.insertUser("user", "parola", "email@email.com");
		String result = db.insertUser("other_username", "parola",
				"email@email.com");

		Assert.assertEquals(Messages.Email_invalid.toString(), result);
	}

	@Test
	public void insertuser_emailInvalidFormat_dotMissing() {
		db.insertUser("user", "parola", "email@email.com");
		String result = db
				.insertUser("other_username", "parola", "email@email");

		Assert.assertEquals(Messages.Email_invalid.toString(), result);
	}

	@Test
	public void insertuser_emailInvalidFormat_atMissing() {
		db.insertUser("user", "parola", "email@email.com");
		String result = db.insertUser("other_username", "parola", "email.com");

		Assert.assertEquals(Messages.Email_invalid.toString(), result);
	}

	@Test
	public void insertuser_usernameused() {
		db.insertUser("user", "parola", "email@email.com");
		String result = db
				.insertUser("user", "parola", "other_email@email.com");

		Assert.assertEquals(Messages.Username_used.toString(), result);
	}

	@Test
	public void removeUserByEmail_existentUser() {
		db.insertUser("user", "parola", "email@email.com");
		String result = db.removeUser_byEmail("email@email.com");

		Assert.assertEquals(Messages.User_deleted.toString(), result);
	}

	@Test
	public void removeUserByEmail_unexistentUser() {
		String result = db.removeUser_byEmail("email@email.com");

		Assert.assertEquals(Messages.User_inexistend.toString(), result);
	}

	@Test
	public void removeUserByUsername_existentUser() {
		db.insertUser("user", "parola", "email@email.com");
		String result = db.removeUser_byUsername("user");

		Assert.assertEquals(Messages.User_deleted.toString(), result);
	}

	@Test
	public void removeUserByUsername_unexistentUser() {
		String result = db.removeUser_byUsername("user");

		Assert.assertEquals(Messages.User_inexistend.toString(), result);
	}
	@Test
	public void removeUser_withDependencies(){
		db.insertTeam("team");
		db.insertUser("user", "password", "email@email.com");
		db.addUserToTeam_byEmail("email@email.com", "team");
		String result = db.removeUser_byEmail("email@email.com");
		
		Assert.assertEquals(Messages.User_deleted.toString(), result);
	}
	@Test
	public void checkUserByUsername_validUserAndPass() {
		db.insertUser("user", "parola", "email@email.com");

		String result = db.checkUser_byUsername("user", "parola");
		Assert.assertEquals(Messages.Login_succesfull.toString(), result);
	}

	@Test
	public void checkUserByUsername_invalidPass() {
		db.insertUser("user", "parola", "email@email.com");

		String result = db.checkUser_byUsername("user", "wrongparola");
		Assert.assertEquals(Messages.Login_failed.toString(), result);
	}

	@Test
	public void checkUserByUsername_invalidUser() {

		String result = db.checkUser_byUsername("user", "wrongparola");
		Assert.assertEquals(Messages.User_inexistend.toString(), result);
	}

	@Test
	public void checkUserByEamil_validUserAndPass() {
		db.insertUser("user", "parola", "email@email.com");

		String result = db.checkUser_byEmail("email@email.com", "parola");
		Assert.assertEquals(Messages.Login_succesfull.toString(), result);
	}

	@Test
	public void checkUserByEamil_invalidPass() {
		db.insertUser("user", "parola", "email@email.com");

		String result = db.checkUser_byEmail("email@email.com", "wrongparola");
		Assert.assertEquals(Messages.Login_failed.toString(), result);
	}

	@Test
	public void checkUserByEamil_invalidEmail() {

		String result = db.checkUser_byUsername("email@email.com",
				"wrongparola");
		Assert.assertEquals(Messages.User_inexistend.toString(), result);
	}

	@Test
	public void insetTeam_validTeam() {
		String result = db.insertTeam("team");
		Assert.assertEquals(Messages.Team_inserted.toString(), result);
	}

	@Test
	public void insetTeam_teamExists() {
		db.insertTeam("team");
		String result = db.insertTeam("team");
		Assert.assertEquals(Messages.Team_alreadyExists.toString(), result);
	}

	@Test
	public void insetTeam_emptyName() {
		String result = db.insertTeam("   			");
		Assert.assertEquals(Messages.Value_Empty.toString(), result);
	}

	@Test
	public void removeTeam_existingTeam() {
		db.insertTeam("team");
		String result = db.removeTeam("team");
		Assert.assertEquals(Messages.Team_removed.toString(), result);
	}

	@Test
	public void removeTeam_nonExistingTeam() {
		String result = db.removeTeam("team");
		Assert.assertEquals(Messages.Team_notExists.toString(), result);
	}

	@Test
	public void removeTeam_teamWithDependencies() {
		db.insertUser("user", "parola", "email@email.com");
		db.insertTeam("team");

		db.addUserToTeam_byEmail("email@email.com", "team");
		String result = db.removeTeam("team");
		Assert.assertEquals(Messages.Team_removed.toString(), result);
	}

	@Test
	public void addUserToTeam_validUserAndTeam() {
		db.insertTeam("team");
		db.insertUser("user", "password", "email@email.com");
		String result = db.addUserToTeam_byUsername("user", "team");

		Assert.assertEquals(Messages.User_addedToTeam.toString(), result);
	}

	@Test
	public void addUserToTeam_validUserUnexistentTeam() {
		db.insertUser("user", "password", "email@email.com");
		String result = db.addUserToTeam_byUsername("user", "team");

		Assert.assertEquals(Messages.Team_notExists.toString(), result);
	}

	@Test
	public void addUserToTeam_unexistentUserValidTeam() {
		db.insertTeam("team");
		String result = db.addUserToTeam_byUsername("user", "team");

		Assert.assertEquals(Messages.User_inexistend.toString(), result);
	}

	@Test
	public void addUserToTeam_userAlreadyInTeam() {
		db.insertTeam("team");
		db.insertUser("user", "password", "email@email.com");
		db.addUserToTeam_byUsername("user", "team");
		String result = db.addUserToTeam_byUsername("user", "team");

		Assert.assertEquals(Messages.User_alreadyInTeam.toString(), result);
	}
	@Test
	public void addLiderToTeam_validUserAndTeam() {
		db.insertTeam("team");
		db.insertUser("user", "password", "email@email.com");
		String result = db.addTeamLider_byUsername("user", "team");

		Assert.assertEquals(Messages.TeamLider_Added.toString(), result);
	}

	@Test
	public void addLiderToTeam_validUserUnexistentTeam() {
		db.insertUser("user", "password", "email@email.com");
		String result = db.addTeamLider_byUsername("user", "team");

		Assert.assertEquals(Messages.Team_notExists.toString(), result);
	}

	@Test
	public void addLiderToTeam_unexistentUserValidTeam() {
		db.insertTeam("team");
		String result = db.addTeamLider_byUsername("user", "team");

		Assert.assertEquals(Messages.User_inexistend.toString(), result);
	}

	@Test
	public void addLiderToTeam_userAlreadyTeamLider() {
		db.insertTeam("team");
		db.insertUser("user", "password", "email@email.com");
		db.addTeamLider_byUsername("user", "team");
		String result = db.addTeamLider_byUsername("user", "team");

		Assert.assertEquals(Messages.User_alreadyTeamLider.toString(), result);
	}
	
	@Test
	public void removeUserFromTeam_validUserInTeam(){
		db.insertTeam("team");
		db.insertUser("user", "password", "email@email.com");
		db.addUserToTeam_byEmail("email@email.com", "team");
		String result = db.removeUserFromTeam_byEmail("email@email.com", "team");
		
		Assert.assertEquals(Messages.UserRemovalFromTeam_Succeeded.toString(), result);
	}
	
	@Test 
	public void checkUser_SQLInjection(){
		db.insertUser(username, password, email);
		String user = "user";
		String result = db.checkUser_byUsername(user, "\" OR \"1\"=\"1");
		
		Assert.assertEquals(Messages.Login_failed.toString(), result);
	}
	// TODO tests for remove user from team
	// TODO tests for remove lider from team
	// TODO tests for SQL Injections

}
