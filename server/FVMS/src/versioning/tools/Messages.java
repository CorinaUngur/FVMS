package versioning.tools;

public enum Messages {
	Update_failed("Required update failed due to tehnical reasons"), File_added(
			"New file added to the server"), FolderAlreadyExists(
			"Folder already exists."), Folder_creationFailed(
			"Folder could not be created"), FileAlreadyExists(
			"File already exists"), File_addingFailed("File creation failed"), File_removed(
			"File was removed from the server"), File_removingFailed(
			"File removing failed"), Trash_notEmpty("Trash is not empty"), Trash_empty(
			"Trash is empty"), Permission_set("Permission set"), Permission_settingFailed("Setting permission failed"), File_boundToProject("Existent file was bound to project");

	private String message = "";

	Messages(String message) {
		this.message = message + "; ";
	}

	@Override
	public String toString() {
		return message;
	}

}
