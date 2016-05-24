package versioning.tools;

public enum Config {
	
	NEWFILE_DEFAULTMESSAGE("new file default message");
	
	private String value;
	private int intValue;
	
	Config(){
		value = null;
	}
	Config(String value){
		this.value = value;
	}
	Config(int intValue){
		this.intValue = intValue;
	}	
	
	@Override
	public String toString() {
		return value != null ? value : String.valueOf(intValue);
	}
	
	public int getInt(){
		return intValue;
	}
}
