package pack;

public class Register {
	
	String name;
	String value;
	
	
	public Register(String rname) {
		
		
		name = "R" + rname;
		value = "0000000000000000";
		
		
	}
	
	
	
	public int getHexValue() {
		
		int hex = Integer.parseInt(value, 16);
	
		return hex;
		
	}

}
