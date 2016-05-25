package db.tools;

public enum Config {
	
	KEY_LENGTH(256*8),
	KEY_SALT("1&@&B*(@#$%^3456"),
	ENC_ITERATIONS(20*1000),

	NEWFILE_DEFAULTMESSAGE("new file default message"),
	STATUS_AVAILABLE(1),
	STATUS_MOVEDTOTRASH(2);
	private String string_value = null;
	private int int_value;
	Config(String string_value){
		this.string_value = string_value;
	}
	
	Config(int int_value){
		this.int_value = int_value;
	}
	
	public String getString(){
		return string_value;
	}
	public int getInt(){
		return int_value;
	}
	@Override
	public String toString() {
		return string_value==null ? String.valueOf(int_value) : string_value;
	}
}
