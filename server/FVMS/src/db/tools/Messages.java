package db.tools;

public enum Messages {
	Username_used("Username already used"), Email_invalid(
			"Email is in an invalid format or is already used"), User_inserted(
			"New user succesfully added"), User_inexistend(
			"Username does not exist"), Login_succesfull("Login successful"), Login_failed(
			"Login failed"), User_deleted("User successfully deleted"), MessageInWrongFormat(
			"Message received by the server has a wrong format"), Team_alreadyExists(
			"Team name already used. Please choose another name."), Team_inserted(
			"New team succesfully inserted"), Team_insertionFailed(
			"Team insertion failed"), Value_Empty("Value is empty"), Team_notExists(
			"Team with this name does not exist."), Team_removalFailed(
			"Team removal failed"), Team_removed("Team was succesfully removed"), User_addedToTeam(
			"User was added to team."), AddingToTeam_Failed(
			"Adding user to team failed"), TeamLider_Added(
			"Team Lider was added to team."), TeamLider_AddingFailed(
			"Team lider adding failed"), OK("OK"), UserRemovalFromTeam_Succeeded(
			"User was removed from the team."), UserRemovalFromTeam_Failed(
			"User was not removed from the team. Operation failed."), User_alreadyInTeam(
			"User is already in the team"), User_isMemberOfTheTeam(
			"User is member of the team."), User_notMemberOFTheTeam(
			"User is not member of the team"), OperationFailed(
			"Operation has failed."), User_alreadyTeamLider(
			"User is already lider of this team"), User_notLiderOfTheTeam(
			"User is not the lider of the team"), User_isLiderOfTheTeam(
			"User is the lider of the team"), File_added(
			"New file added to database"), File_addingFailed(
			"New file was not added to database"), File_alreadySaved(
			"File exists already in database"), Change_added(
			"Change saved in database"), Change_addingFailed(
			"Change failed to be saved to database"), NoFileFoundWithSpecifiedID(
			"No file was found with specified fid"), File_doesNotExist(
			"File does not exist"), Project_added(
			"New project added to the database"), Project_addingFailed(
			"Project was not added to the database"), Project_removed(
			"Project was removed from the database"), Project_removingFailed(
			"Project removal failed"), Project_nameUpdated(
			"Project has been renamed"), Project_renamingFailed(
			"Project renaming failed"), File_addedToProject(
			"File was added to project"), File_addingToPRojectFailed(
			"Adding file to project failed"), File_removedFromProject(
			"File was removed from project"), File_removingFromProjectFailed(
			"File could not be removed from project"), Project_doesNotExist(
			"Project does not exist"), Project_pathUpdated(
			"Relative path of the project was updated"), Project_pathUpdatingFailed(
			"Relative path of the project was updated");

	private String message = "";

	Messages(String message) {
		this.message = message + "; ";
	}

	@Override
	public String toString() {
		return message;
	}

}
