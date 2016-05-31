package db.tools;

public enum Columns {

	USERS_username("user_name"), 
	USERS_password("pass"), 
	USERS_email("email"), 
	USERS_Id("uid"), 
	
	TEAMS_name("team_name"), 
	TEAMS_Id("tid"),
	
	Changes_Hash("hash"), 
	Changes_Path("path"), 
	Changes_ID("cid"), 
	Changes_FID("fid"), 
	Changes_date("date"), 
	Changes_message("message"), 
	Changes_owner("owner"), 
	
	Projects_ID("pid"), 
	Projects_Name("name"), 
	Projects_OwnerID("author"),
	
	ProjectFiles_PID("pid"), 
	ProjectFiles_CID("cid"), 
	ProjectFiles_RPath("relative_path"), 

	PPermissions_user("uid"), 
	PPermissions_project("pid"), 
	PPermissions_rights("rights"), 

	FileStatus_status("status"), 
	FileStatus_FID("fid"); 

	public String column_name = "";

	Columns(String column) {
		this.column_name = column;
	}

	@Override
	public String toString() {
		return column_name;
	}

}
