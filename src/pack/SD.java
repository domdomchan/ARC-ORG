package pack;

import java.util.ArrayList;

public class SD extends Instruction{

	String[] temp = new String[10];
	
	String rt, rtBinary;
	String base, baseBinary;
	String offset, offsetBinary;
	
	String OPBinary;
	
	public SD(String codeString) {
		
		name = "SD";
		
		opCode = "111111";
		
		codeLine = codeString;
		
	}
		
		
	public void createOpcode(String codeString, ArrayList<Instruction> codeList) {
	
		if(codeString.contains(": ")) {
			
			//Split string, separate base, rt, offset
			
			codeString = codeString.substring(codeString.indexOf("R"));		
			codeString = codeString.substring(0, codeString.length() - 1);
			
			temp = codeString.split("\\(");
			
			base = temp[1];
					
			temp = codeString.split("\\,");
			rt = temp[0];
			
			temp = codeString.split(" ");
			temp[1] = temp[1].substring(0, 4);
			offset = temp[1];
			
			//Convert from String (R1, 1000, etc..) to Binary String
			baseBinary = convertBase(base);		
			rtBinary = convertRt(rt);
			offsetBinary = convertOffset(offset);

			//Convert to HEX
			OPBinary = convertBINARY(baseBinary, rtBinary, offsetBinary);

			//Checking only print HEX
			System.out.println();
			OPHEX = convertHEX(OPBinary);
			System.out.println(OPHEX);
						
		}
		
		else {
		
		//Split string, separate base, rt, offset
		
		codeString = codeString.substring(codeString.indexOf("R"));		
		codeString = codeString.substring(0, codeString.length() - 1);
		
		temp = codeString.split("\\(");
		
		base = temp[1];
				
		temp = codeString.split("\\,");
		rt = temp[0];
		
		temp = codeString.split(" ");
		temp[1] = temp[1].substring(0, 4);
		offset = temp[1];
		
		//Convert from String (R1, 1000, etc..) to Binary String
		baseBinary = convertBase(base);		
		rtBinary = convertRt(rt);
		offsetBinary = convertOffset(offset);

		//Convert to HEX
		OPBinary = convertBINARY(baseBinary, rtBinary, offsetBinary);

		//Checking only print HEX
		System.out.println();
		OPHEX = convertHEX(OPBinary);
		System.out.println(OPHEX);
		
		}
	}
	
	//@FUNCTIONS
	
	
	///////Converts the BASE to binary string
	public String convertBase(String base) {
		
		String binary = "";
		String biTemp;
		
		base = base.substring(1);
		
		int decimal = Integer.parseInt(base);
		
		
		biTemp = Integer.toBinaryString(decimal);
		
		
		for(int i = 0; i < 5 - biTemp.length(); i++) {
			
			binary = binary.concat("0");
			
		}
				
		return binary.concat(biTemp);
		
	}	
	
	
	///////Converts the RT to binary string
	public String convertRt(String rt) {
		
		String binary = "";
		String biTemp;
		
		rt = rt.substring(1);
		
		int decimal = Integer.parseInt(rt);
		
		
		biTemp = Integer.toBinaryString(decimal);
		
		
		for(int i = 0; i < 5 - biTemp.length(); i++) {
			
			binary = binary.concat("0");
			
		}
			
		return binary.concat(biTemp);
		
	}
	
	
	///////Converts the OFFSET to binary string
	public String convertOffset(String offset) {
		
		String binary = "";
		String biTemp;
		
		
		int hex = Integer.parseInt(offset, 16);
		
		biTemp = Integer.toBinaryString(hex);
		
		for(int i = 0; i < 16 - biTemp.length(); i++) {
			
			binary = binary.concat("0");
			
		}
				
		return binary.concat(biTemp);
		
	}
	
	
	///////Combines rs, rt, offset to full Opcode
	public String convertBINARY(String base, String rt, String offset) {
		
		String binary = opCode + base + rt + offset;
				
		return binary;
	}
	
	
	///////Converts the binary string to HEX string
	public String convertHEX(String binary) {
		
		String hex = "";
		
		
		for(int i = 0; i <= 28; i += 4) {
			
			temp[0] = binary.substring(i, i + 4);		
			int decimal = Integer.parseInt(temp[0],2);
			hex = hex.concat(Integer.toString(decimal,16));
			
		}
		
		
		return hex;
	}
	
	
	
}//ENNNNDDDDDD
