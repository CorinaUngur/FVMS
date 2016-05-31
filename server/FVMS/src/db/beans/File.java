package db.beans;

public class File {
	private int id = 0;
	private String path = null;
	private String lastModified = null;
	private String lastComment = null;
	private String authName = null;
	public File(int id, String path, String lastModified, String lastComment, String authName){
		this.id = id;
		this.path = path;
		this.lastModified = lastModified;
		this.lastComment = lastComment;
		this.authName = authName;
	}
	public int getId() {
		return id;
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
	
	
}
