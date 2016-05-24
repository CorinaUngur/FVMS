package db.tools;

public class StmtOrRsNotClosedBefore extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Statement and ResultSet instance must be closed right after use. They have not been closed before last use.";
	}
} 
