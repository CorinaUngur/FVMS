package connection.tools;

public enum Config {
	
	MessageExpirationTime(60*1000),
	FileBlockSize(60000);
	//(1);
	
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
