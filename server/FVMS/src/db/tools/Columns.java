package db.tools;

public enum Columns {

	USERS_username("user_name"), USERS_password("pass"), USERS_email("email"), TEAMS_name(
			"team_name"), USERS_Id("uid"), TEAMS_Id("tid"), Changes_Hash("hash"), Changes_Path("path"), Changes_ID("cid"), Changes_FID("fid"), Changes_Status("status");

	public String column_name = "";

	Columns(String column) {
		this.column_name = column;
	}

	@Override
	public String toString() {
		return column_name;
	}

}
