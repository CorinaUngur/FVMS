package db.beans;

import java.util.ArrayList;

public class Project {
	private ArrayList<File> files;
	private String name;

	public Project(String name, ArrayList<File> files) {
		this.name = name;
		this.files = files;
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
}
