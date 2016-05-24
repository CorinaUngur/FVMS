package fileSystem.tools;

public enum Messages {
	Update_failed("Required update failed due to tehnical reasons"),
	File_added("New file added to the server"), FolderAlreadyExists("Folder already exists."), Folder_creationFailed("Folder could not be created"), FileAlreadyExists("File already exists"), File_addingFailed("File creation failed");
	
	private String message = "";

	Messages(String message) {
		this.message = message + "; ";
	}

	@Override
	public String toString() {
		return message;
	}

}
