package db.tools;

public enum Columns {

	USERS_username("user_name"), USERS_password("pass"), USERS_email("email"), TEAMS_name(
			"team_name"), USERS_Id("uid"), TEAMS_Id("tid"), Changes_Hash("hash"), Changes_Path(
			"path"), Changes_ID("cid"), Changes_FID("fid"), Projects_ID("pid"), Projects_Name("name"), ProjectFiles_PID(
			"pid"), ProjectFiles_CID("cid"), Projects_Path("relative_path"), FPermissions_user(
			"uid"), FPermissions_file("fid"), FPermissions_rights("rights"), PPermissions_user(
			"uid"), PPermissions_project("pid"), PPermissions_rights("rights"), ProjectFiles_RPath(
			"relative_path"), FileStatus_status("status"), FileStatus_FID("fid");

	public String column_name = "";

	Columns(String column) {
		this.column_name = column;
	}

	@Override
	public String toString() {
		return column_name;
	}

}
