 package pack;

import java.util.ArrayList;

public class BNEC extends Instruction {
	

	String[] temp = new String[10];
	
	String branchLabel;
	
	String OPBinary;
	
	int branchAdd, branchi;

	String rt, rtBinary;
	String rs, rsBinary;
	String imm;
	String condition;
	
	
	
	public BNEC(String codeString) {
		
		name = "BNEC";
		
		opCode = "011000";
		
		codeLine = codeString;
		
		temp = codeString.split(" ");
		
		branchLabel = temp[3].concat(":");
		
	}
	
	
	public void createOpcode(String codeString, ArrayList<Instruction> codeList) {
		
		codeString = codeString.substring(codeString.indexOf("R"));
		
		temp = codeString.split("\\, ");
		
		rs = temp[0];		
		rt = temp[1];
		
		
		//Convert from String (R1, 1000, etc..) to Binary String
		rsBinary = convertRs(rs);
		rtBinary = convertRs(rt);
		
		////////////offset///////////////////
		
		
		
		int distance = 0;
		
		
		for(int i = 0; i < codeList.size(); i++) {
			
			if(codeList.get(i).codeLine.equals(codeLine)) {
				
				for(int j = 0; j < codeList.size(); j++) {
				
					if(codeList.get(j).codeLine.contains(branchLabel)) {
						distance = j - (i + 1);
						branchAdd = codeList.get(j).intAddress;
						branchi = j;
					//	System.out.println(branchAdd);
					//	System.out.println(codeList.get(j).intAddress);
						}
				
				
				}// j loop
		
			}/// if
			
		}/// i loop
		
		String binary = "";
		String biTemp;
			
		biTemp = Integer.toBinaryString(distance);
		
		for(int i = 0; i < 16 - biTemp.length(); i++) {
			
			binary = binary.concat("0");
			
		}
		
		imm = binary.concat(biTemp);
		
		
		OPBinary = opCode.concat(rsBinary).concat(rtBinary).concat(binary.concat(biTemp));

		String hex = "";
		
		
		for(int i = 0; i <= 28; i += 4) {
			
			temp[0] = OPBinary.substring(i, i + 4);		
			int decimal = Integer.parseInt(temp[0],2);
			hex = hex.concat(Integer.toString(decimal,16));
			
		}
		
		OPHEX = hex;
		
	//	System.out.println("\n"+OPHEX);
		
		
	}/////create opcode END
	
	
///////Converts the RS to binary string
	public String convertRs(String rs) {
		
		String binary = "";
		String biTemp;
		
		rs = rs.substring(1);
		
		int decimal = Integer.parseInt(rs);
		
		
		biTemp = Integer.toBinaryString(decimal);
		
		
		for(int i = 0; i < 5 - biTemp.length(); i++) {
			
			binary = binary.concat("0");
			
		}		
		
		return binary.concat(biTemp);
		
	}///convert
	
	
	
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
	
	
	///EXECUTE BNEC
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
				String A = "", B = "", IMM = "", ALUOPT = "", PC = address, cond = "", Rn = "N/A",
				LMD = "LMD = N/A", range = "range = N/A";
				for(int i = 0; i < registers.size(); i ++) {
					
					if(registers.get(i).name.equals(rs))
						A = registers.get(i).value;	
					if(registers.get(i).name.equals(rt))
						B = registers.get(i).value;
				}//for loop to get rs, rt
				
				A = pad(A);
				B = pad(B);
				
				IMM = OPHEX.substring(4);
				
				IMM = pad(IMM);
				
				
			if(A.equals(B)) {
				cond = "0";
			//	System.out.println("COND 0");
			}
			if(!(A.equals(B))) {
				cond = "1";
			//	System.out.println("COND 1");
				}
			
			
				
				
			condition = cond;
			
			if(cond.equals("0")) {
				ALUOPT = address;
				PC = NPC;
			}
			if(cond.equals("1")) {
			String s = Integer.toHexString(branchAdd);
			s = pad(s);
		    ALUOPT = s;
		    PC = ALUOPT;
		//    System.out.println(branchAdd);
		//	System.out.println(s);
			}
			
			cycle = cycleString(OPHEX, NPC, A, B, IMM, ALUOPT, cond, PC, LMD, range, Rn);
			
			
		}
	
	
	

}///EEENNNNDDDD
