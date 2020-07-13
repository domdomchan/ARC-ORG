package pack;

import java.math.BigInteger;
import java.util.ArrayList;

public class DADDU extends Instruction{

	String[] temp = new String[10];
	
	String rt, rtBinary;
	String rs, rsBinary;
	String rd, rdBinary;
	String sa;
	String func;
	
	String OPBinary;
	
	public DADDU(String codeString) {
		
		name = "DADDU";
		
		opCode = "000000";
		
		sa = "00000";
		
		func = "101101";
		
		codeLine = codeString;
		
	}
		
	
	public void createOpcode(String codeString, ArrayList<Instruction> codeList) {
		//Split string, separate rs, rt, immediate
		
		if(codeString.contains(": ")) {
			
			codeString = codeString.substring(codeString.indexOf("R"));
			
			temp = codeString.split("\\, ");
			
			rs = temp[1];		
			rt = temp[2];
			rd = temp[0];

			//Convert from String (R1, 1000, etc..) to Binary String
			rsBinary = convertRs(rs);
			rtBinary = convertRt(rt);
			rdBinary = convertRd(rd);
					
			//Convert to HEX
			OPBinary = convertBINARY(rsBinary, rtBinary, rdBinary, sa, func);

			//Checking only print HEX
			System.out.println();		
			OPHEX = convertHEX(OPBinary);
	//		System.out.println(OPHEX);
						
		}
		
		
		else {
			
			codeString = codeString.substring(codeString.indexOf("R"));
		
			temp = codeString.split("\\, ");
		
			rs = temp[1];		
			rt = temp[2];
			rd = temp[0];

		//Convert from String (R1, 1000, etc..) to Binary String
			rsBinary = convertRs(rs);
			rtBinary = convertRt(rt);
			rdBinary = convertRd(rd);
				
		//Convert to HEX
			OPBinary = convertBINARY(rsBinary, rtBinary, rdBinary, sa, func);

		//Checking only print HEX
			System.out.println();		
			OPHEX = convertHEX(OPBinary);
	//		System.out.println(OPHEX);
		
		}
			
	}		
			
		
				
	
	
	
	//@FUNCTIONS
	
	
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
	
	
	///////Converts the RD to binary string
	public String convertRd(String rd) {
		
		String binary = "";
		String biTemp;
		
		rd = rd.substring(1);
		
		int decimal = Integer.parseInt(rd);
		
		
		biTemp = Integer.toBinaryString(decimal);
		
		
		for(int i = 0; i < 5 - biTemp.length(); i++) {
			
			binary = binary.concat("0");
			
		}
		
		return binary.concat(biTemp);
		
	}
	
	
	///////Combines rs, rt, rd to full Opcode
	public String convertBINARY(String rs, String rt, String rd, String sa, String func) {
		
		String binary = opCode + rs + rt + rd + sa + func;
				
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
	}//////CONVERT
	
	
	
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
	
	
	
	/////EXECUTE DADDU
	public void execute(ArrayList<Register> registers) {
		
		
			/////IF CYCLE, IR NPC
			int aTemp;
				
	//			System.out.println("IR = " + OPHEX);
				aTemp = intAddress + 4;
				address = Integer.toHexString(aTemp);
				address = pad(address);
	//			System.out.println("NPC = " + address + "\n");
				
				/////ID CYCLE, A,B,IMM
				//get value of register(rs, rt, print immediate w/ pad)
				String A = "", B = "", IMM = "", ALUOPT = "", PC = address, cond = "0", Rn = "",
				LMD = "LMD = N/A", range = "range = N/A";
				for(int i = 0; i < registers.size(); i ++) {
					
					if(registers.get(i).name.equals(rs))
						A = registers.get(i).value;	
					if(registers.get(i).name.equals(rt))
						B = registers.get(i).value;
				}//for loop to get rs, rt
		//		System.out.println("A = " +A);
		//		System.out.println("B = " +B);
				
				IMM = OPHEX.substring(4);
				
				IMM = pad(IMM);
		//		System.out.println("IMM = " +IMM);
		
		A = pad(A);
		B = pad(B);
		
		
		
		//rd = rs + rt
		
		//get rs
		String rsTemp = "0000000000000000";
		for(int i = 0; i < registers.size(); i ++) {
			
			if(registers.get(i).name.equals(rs))
				rsTemp = registers.get(i).value;			
		}//for loop to get rs
		
		
		//get rt
		String rtTemp = "0000000000000000";
		for(int i = 0; i < registers.size(); i ++) {
					
			if(registers.get(i).name.equals(rt))
						rtTemp = registers.get(i).value;			
		}//for loop to get rt
		
		
		
		//add rs + rt
		
				BigInteger big1 = new BigInteger(rsTemp, 16);
				BigInteger big2 = new BigInteger(rtTemp, 16);
				BigInteger big = new BigInteger("0000000000000000", 16);
		
				big = big1.add(big2);
				
				String ans = big.toString(16);
				
				
				// rd gets ans
				for(int i = 0; i < registers.size(); i ++) {
					
					if(registers.get(i).name.equals(rd))
						registers.get(i).value = ans;		
				}//for loop to get rt
				
				
				
				
				///EX Cycle: ALUOUTPUT, COND
				ALUOPT = pad(ans);
		/*		System.out.println("ALUOUTPUT = " +ALUOPT);
				System.out.println("COND = " +cond);
				System.out.println("PC = "+ PC);
				System.out.println(LMD);
				System.out.println(range);  */
				Rn = ALUOPT;
				
				
				cycle = cycleString(OPHEX, PC, A, B, IMM, Rn, cond, PC, LMD, range, Rn);
		
	}//execute
	
	
	
}//ENNNNDDDDDD
