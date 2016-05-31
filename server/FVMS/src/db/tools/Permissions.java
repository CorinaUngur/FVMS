package db.tools;

public enum Permissions {
	READ("R"),
	EDIT("W"),
	PERMIT("P"), 
	ALL("RWP");
	
	private String val;
	
	private Permissions(String val) {
		this.val = val;
	}
	
	@Override
	public String toString() {
		return val;
	}
}
