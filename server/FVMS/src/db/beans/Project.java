package db.beans;

import java.util.ArrayList;

public class Project {
	private ArrayList<File> files;
	private String name;
	private int pid;

	public Project(int pid, String name, ArrayList<File> files) {
		this.name = name;
		this.files = files;
		this.pid = pid;
	}

	public ArrayList<File> getFiles() {
		return files;
	}

	public String getName() {
		return name;
	}

	public void addFile(File file) {
		files.add(file);
	}
	public int getPid(){
		return pid;
	}
}
