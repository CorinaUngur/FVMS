package db.beans;

public class File {
	private int id = 0;
	private int pid = 0;
	private String path = null;
	private String name = null;

	public void setName(String name) {
		this.name = name;
	}

	private String lastModified = null;
	private String lastComment = null;
	private String authName = null;
	private byte[] file_content = null;

	public File(int id, int pid, String path, String lastModified,
			String lastComment, String authName) {
		this.id = id;
		this.pid = pid;
		this.path = path;
		this.lastModified = lastModified;
		this.lastComment = lastComment;
		this.authName = authName;
	}

	public int getId() {
		return id;
	}

	public int getPid() {
		return pid;
	}

	public String getPath() {
		return path;
	}

	public String getLastModified() {
		return lastModified;
	}

	public String getLastComment() {
		return lastComment;
	}

	public String getAuthName() {
		return authName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public void setLastComment(String lastComment) {
		this.lastComment = lastComment;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public void setFile_content(byte[] file_content) {
		this.file_content = file_content;
	}

	public byte[] getContentOfTheFile() {
		return file_content;
	}

}
