package versioning.tools;

public enum Config {
	
	ROOT_FOLDER("E:/fvms/root_folder/"),
	
	TRASH_ENABLED(true),
	
	NEWFILE_DEFAULTMESSAGE("new file default message");
	
	private String value;
	private int intValue;
	private boolean boolValue;
	
	Config(){
		value = null;
	}
	Config(String value){
		this.value = value;
	}
	Config(int intValue){
		this.intValue = intValue;
	}	
	Config(boolean value){
		this.boolValue = value;
	}
	
	@Override
	public String toString() {
		return value != null ? value : String.valueOf(intValue);
	}
	
	public int getInt(){
		return intValue;
	}
}
