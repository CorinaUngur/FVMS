package db;

import init.InitializationClass;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import db.tools.Messages;

public class UsersOperation {
	private UsersDB udb;
	private String username = "user";
	private String email = "email@email.com";
	private String password = "parola";
	@Before
	public void setUP() {
		InitializationClass.initialization();
		udb = UsersDB.getInstance();
		udb.removeUserFromTeam_byEmail("email@email.com", "team");
		udb.removeUserFromTeam_byUsername("user", "team");
		udb.removeLiderFromTeam_byEmail("email@email.com", "team");
		udb.removeLiderFromTeam_byUsername("user", "team");
		udb.removeUser_byUsername("user");
		udb.removeUser_byEmail("email@email.com");
		udb.removeTeam("team");
	}

	@Test
	public void insertuser_valid() {

		String result = udb.insertUser("user", "parola", "email@email.com");

		Assert.assertEquals(Messages.User_inserted.toString(), result);
	}

	@Test
	public void insertuser_emailused() {
		udb.insertUser("user", "parola", "email@email.com");
		String result = udb.insertUser("other_username", "parola",
				"email@email.com");

		Assert.assertEquals(Messages.Email_invalid.toString(), result);
	}

	@Test
	public void insertuser_emailInvalidFormat_dotMissing() {
		udb.insertUser("user", "parola", "email@email.com");
		String result = udb
				.insertUser("other_username", "parola", "email@email");

		Assert.assertEquals(Messages.Email_invalid.toString(), result);
	}

	@Test
	public void insertuser_emailInvalidFormat_atMissing() {
		udb.insertUser("user", "parola", "email@email.com");
		String result = udb.insertUser("other_username", "parola", "email.com");

		Assert.assertEquals(Messages.Email_invalid.toString(), result);
	}

	@Test
	public void insertuser_usernameused() {
		udb.insertUser("user", "parola", "email@email.com");
		String result = udb
				.insertUser("user", "parola", "other_email@email.com");

		Assert.assertEquals(Messages.Username_used.toString(), result);
	}

	@Test
	public void removeUserByEmail_existentUser() {
		udb.insertUser("user", "parola", "email@email.com");
		String result = udb.removeUser_byEmail("email@email.com");

		Assert.assertEquals(Messages.User_deleted.toString(), result);
	}

	@Test
	public void removeUserByEmail_unexistentUser() {
		String result = udb.removeUser_byEmail("email@email.com");

		Assert.assertEquals(Messages.User_inexistend.toString(), result);
	}

	@Test
	public void removeUserByUsername_existentUser() {
		udb.insertUser("user", "parola", "email@email.com");
		String result = udb.removeUser_byUsername("user");

		Assert.assertEquals(Messages.User_deleted.toString(), result);
	}

	@Test
	public void removeUserByUsername_unexistentUser() {
		String result = udb.removeUser_byUsername("user");

		Assert.assertEquals(Messages.User_inexistend.toString(), result);
	}
	@Test
	public void removeUser_withDependencies(){
		udb.insertTeam("team");
		udb.insertUser("user", "password", "email@email.com");
		udb.addUserToTeam_byEmail("email@email.com", "team");
		String result = udb.removeUser_byEmail("email@email.com");
		
		Assert.assertEquals(Messages.User_deleted.toString(), result);
	}
	@Test
	public void checkUserByUsername_validUserAndPass() {
		udb.insertUser("user", "parola", "email@email.com");

		String result = udb.checkUser_byUsername("user", "parola");
		Assert.assertEquals(Messages.Login_succesfull.toString(), result);
	}

	@Test
	public void checkUserByUsername_invalidPass() {
		udb.insertUser("user", "parola", "email@email.com");

		String result = udb.checkUser_byUsername("user", "wrongparola");
		Assert.assertEquals(Messages.Login_failed.toString(), result);
	}

	@Test
	public void checkUserByUsername_invalidUser() {

		String result = udb.checkUser_byUsername("user", "wrongparola");
		Assert.assertEquals(Messages.User_inexistend.toString(), result);
	}

	@Test
	public void checkUserByEamil_validUserAndPass() {
		udb.insertUser("user", "parola", "email@email.com");

		String result = udb.checkUser_byEmail("email@email.com", "parola");
		Assert.assertEquals(Messages.Login_succesfull.toString(), result);
	}

	@Test
	public void checkUserByEamil_invalidPass() {
		udb.insertUser("user", "parola", "email@email.com");

		String result = udb.checkUser_byEmail("email@email.com", "wrongparola");
		Assert.assertEquals(Messages.Login_failed.toString(), result);
	}

	@Test
	public void checkUserByEamil_invalidEmail() {

		String result = udb.checkUser_byUsername("email@email.com",
				"wrongparola");
		Assert.assertEquals(Messages.User_inexistend.toString(), result);
	}

	@Test
	public void insetTeam_validTeam() {
		String result = udb.insertTeam("team");
		Assert.assertEquals(Messages.Team_inserted.toString(), result);
	}

	@Test
	public void insetTeam_teamExists() {
		udb.insertTeam("team");
		String result = udb.insertTeam("team");
		Assert.assertEquals(Messages.Team_alreadyExists.toString(), result);
	}

	@Test
	public void insetTeam_emptyName() {
		String result = udb.insertTeam("   			");
		Assert.assertEquals(Messages.Value_Empty.toString(), result);
	}

	@Test
	public void removeTeam_existingTeam() {
		udb.insertTeam("team");
		String result = udb.removeTeam("team");
		Assert.assertEquals(Messages.Team_removed.toString(), result);
	}

	@Test
	public void removeTeam_nonExistingTeam() {
		String result = udb.removeTeam("team");
		Assert.assertEquals(Messages.Team_notExists.toString(), result);
	}

	@Test
	public void removeTeam_teamWithDependencies() {
		udb.insertUser("user", "parola", "email@email.com");
		udb.insertTeam("team");

		udb.addUserToTeam_byEmail("email@email.com", "team");
		String result = udb.removeTeam("team");
		Assert.assertEquals(Messages.Team_removed.toString(), result);
	}

	@Test
	public void addUserToTeam_validUserAndTeam() {
		udb.insertTeam("team");
		udb.insertUser("user", "password", "email@email.com");
		String result = udb.addUserToTeam_byUsername("user", "team");

		Assert.assertEquals(Messages.User_addedToTeam.toString(), result);
	}

	@Test
	public void addUserToTeam_validUserUnexistentTeam() {
		udb.insertUser("user", "password", "email@email.com");
		String result = udb.addUserToTeam_byUsername("user", "team");

		Assert.assertEquals(Messages.Team_notExists.toString(), result);
	}

	@Test
	public void addUserToTeam_unexistentUserValidTeam() {
		udb.insertTeam("team");
		String result = udb.addUserToTeam_byUsername("user", "team");

		Assert.assertEquals(Messages.User_inexistend.toString(), result);
	}

	@Test
	public void addUserToTeam_userAlreadyInTeam() {
		udb.insertTeam("team");
		udb.insertUser("user", "password", "email@email.com");
		udb.addUserToTeam_byUsername("user", "team");
		String result = udb.addUserToTeam_byUsername("user", "team");

		Assert.assertEquals(Messages.User_alreadyInTeam.toString(), result);
	}
	@Test
	public void addLiderToTeam_validUserAndTeam() {
		udb.insertTeam("team");
		udb.insertUser("user", "password", "email@email.com");
		String result = udb.addTeamLider_byUsername("user", "team");

		Assert.assertEquals(Messages.TeamLider_Added.toString(), result);
	}

	@Test
	public void addLiderToTeam_validUserUnexistentTeam() {
		udb.insertUser("user", "password", "email@email.com");
		String result = udb.addTeamLider_byUsername("user", "team");

		Assert.assertEquals(Messages.Team_notExists.toString(), result);
	}

	@Test
	public void addLiderToTeam_unexistentUserValidTeam() {
		udb.insertTeam("team");
		String result = udb.addTeamLider_byUsername("user", "team");

		Assert.assertEquals(Messages.User_inexistend.toString(), result);
	}

	@Test
	public void addLiderToTeam_userAlreadyTeamLider() {
		udb.insertTeam("team");
		udb.insertUser("user", "password", "email@email.com");
		udb.addTeamLider_byUsername("user", "team");
		String result = udb.addTeamLider_byUsername("user", "team");

		Assert.assertEquals(Messages.User_alreadyTeamLider.toString(), result);
	}
	
	@Test
	public void removeUserFromTeam_validUserInTeam(){
		udb.insertTeam("team");
		udb.insertUser("user", "password", "email@email.com");
		udb.addUserToTeam_byEmail("email@email.com", "team");
		String result = udb.removeUserFromTeam_byEmail("email@email.com", "team");
		
		Assert.assertEquals(Messages.UserRemovalFromTeam_Succeeded.toString(), result);
	}
	
	@Test 
	public void checkUser_SQLInjection(){
		udb.insertUser(username, password, email);
		String user = "user";
		String result = udb.checkUser_byUsername(user, "\" OR \"1\"=\"1");
		
		Assert.assertEquals(Messages.Login_failed.toString(), result);
	}
	// TODO tests for remove user from team
	// TODO tests for remove lider from team
	// TODO tests for SQL Injections

}
