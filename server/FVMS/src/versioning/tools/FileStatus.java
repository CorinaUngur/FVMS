package versioning.tools;

public enum FileStatus {
    UPTODATE(0),
    CHANGED(1),
    NEW(2),
    DELETED(3),
    SENDING(4);

    private int val;
    private FileStatus(int val) {
		this.val = val;
	}
	public int get() {
		return val;
	}
}
