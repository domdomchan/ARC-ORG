package pack;

import java.util.ArrayList;

public class BC extends Instruction {

	String[] temp = new String[10];
	
	String branchLabel;
	
	String OPBinary;
	
	int branchAdd, branchi;
	
	String condition;
	
	public BC(String codeString) {
		
		name = "BC";
		
		opCode = "110010";
		
		codeLine = codeString;
		
		temp = codeString.split(" ");
		
		branchLabel = temp[1].concat(":");
		
	}
	
	
	
	
	public void createOpcode(String codeString, ArrayList<Instruction> codeList) {
		
		int distance = 0;
		
		
		for(int i = 0; i < codeList.size(); i++) {
			
			if(codeList.get(i).codeLine.equals(codeLine)) {
				
				for(int j = 0; j < codeList.size(); j++) {
				
					if(codeList.get(j).codeLine.contains(branchLabel)) {
						distance = j - (i + 1);
						branchAdd = codeList.get(j).intAddress;
						branchi = j;
						}
				
				
				}// j loop
		
			}/// if
			
		}/// i loop
		
		String binary = "";
		String biTemp;
			
		biTemp = Integer.toBinaryString(distance);
		
		for(int i = 0; i < 26 - biTemp.length(); i++) {
			
			binary = binary.concat("0");
			
		}
		// IMM = binary.concat(biTemp);
		
		OPBinary = opCode.concat(binary.concat(biTemp));
		
		String hex = "";
		
		
		for(int i = 0; i <= 28; i += 4) {
			
			temp[0] = OPBinary.substring(i, i + 4);		
			int decimal = Integer.parseInt(temp[0],2);
			hex = hex.concat(Integer.toString(decimal,16));
			
		}
		
		OPHEX = hex;
		
		System.out.println("\n"+OPHEX);
		
		
		
	}////create Opcode
	
	
	///padd func
	public String pad(String address) {
		
		String padded = "";
		for(int i = 0; i < 16 - address.length(); i++) {
			
			padded = padded.concat("0");
			
		}
		return padded.concat(address);
	}
	
	//////return stringcycle
	public String cycleString(String s1, String s2, String s3, String s4, String s5,
			String s6, String s7, String s8, String s9, String s10, String s11) {
		
		String cycle = s1 + "\n" + s2 + "\n\n" + s3 + "\n" + s4 + "\n" + s5 + "\n\n" +
			s6 + "\n" + s7 + "\n\n" + s8 + "\n" + s9 + "\n" + s10 + "\n\n" + s11;
		
		
		return cycle;
	}
	
	
	///EXECUTE DADDIU
	public void execute(ArrayList<Register> registers) {
		
	/////IF CYCLE, IR NPC
		int aTemp;
			
			aTemp = intAddress + 4;
			address = Integer.toHexString(aTemp);
			address = pad(address);
			
			String IR = OPHEX;
			String NPC = address;
			
			/////ID CYCLE, A,B,IMM
			//get value of register(rs, rt, w/ pad)
			String A = "0000000000000000", B = "0000000000000000", IMM = "", ALUOPT = "", PC = address, cond = "1", Rn = "N/A",
			LMD = "LMD = N/A", range = "range = N/A";
			
			
			
			
		condition = cond;
		
		
		String s = Integer.toHexString(branchAdd);
		s = pad(s);
	    ALUOPT = s;
	    PC = ALUOPT;
	//	System.out.println(s);
		
		
		cycle = cycleString(OPHEX, NPC, A, B, IMM, ALUOPT, cond, PC, LMD, range, Rn);
		
		
	}
	
}///end
