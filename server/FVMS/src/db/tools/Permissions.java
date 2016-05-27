package db.tools;

public enum Permissions {
	PERMISSION_READ("R"),
	PERMISSION_EDIT("W"),
	PERMISSION_PERMIT("P");
	
	private String val;
	
	private Permissions(String val) {
		this.val = val;
	}
	
	@Override
	public String toString() {
		return val;
	}
}
