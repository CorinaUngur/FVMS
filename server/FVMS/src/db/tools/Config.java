package db.tools;

public enum Config {
	ULR("localhost"),
	USER("root"),
	PASSWORD("parola"),
	
	KEY_LENGTH(256*8),
	KEY_SALT("1&@&B*(@#$%^3456"),
	ENC_ITERATIONS(20*1000);
	
	
	
	private String string_value;
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
}
