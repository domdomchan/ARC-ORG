package pack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;




public class MainFrame extends JFrame {
	//models
	public String input;
	String processCode;
	String[] codeLines;
	String[] tempCode;
	ArrayList<String> instructions = new ArrayList<String>();
	
	ArrayList<Instruction> codes = new ArrayList<Instruction>();
	
	ArrayList<Register> registers = new ArrayList<Register>();
	
	ArrayList<DataMemory> memories = new ArrayList<DataMemory>();
	
	
	//View/GUI
	private static final int WIDTH = 1400;
    private static final int HEIGHT = 1000;
    
    private JFrame RegChangeFrame, MemFrame;
    private JPanel mainPanel, txtPanel, opcodePanel, registerPanel, newPanel, cyclePanel, memoryPanel, newPanel2;
	private JSplitPane contentPanel;
	private JTextArea txtArea, opcodeArea, cycleArea, memoryArea;
	private JScrollPane scroll, scroll2, scroll3, scroll4;
	private JButton btnRun, btnReset, btnRegister, ok, cancel, GoTo, okay;
	private Container pane;
	private JTextField R, Rval, memAdd, memVal;
	private JLabel RLabel, RvalLabel;
	
	ArrayList<JLabel> buttonList = new ArrayList<JLabel>();

    
    public MainFrame () {
    	
    		pane = getContentPane();
    		pane.setLayout(null);
		pane.setBackground(Color.WHITE);
    		
		
		mainPanel = new JPanel(null);
		mainPanel.setBounds(0, 0, 1400, 1000);
		mainPanel.setBackground(Color.GRAY);
		
		txtPanel = new JPanel();
		txtPanel.setBackground(Color.white);
		txtPanel.setBounds(20, 20, 250, 370);
		
		opcodePanel = new JPanel();
		opcodePanel.setBackground(Color.white);
		opcodePanel.setBounds(300, 20, 250, 370);
		
		registerPanel = new JPanel();
		registerPanel.setLayout(new GridLayout(8, 4));
		registerPanel.setBounds(550, 440, 750, 370);
		
		cyclePanel =  new JPanel();
		cyclePanel.setBackground(Color.white);
		cyclePanel.setBounds(600, 20, 250, 370);
		
		memoryPanel =  new JPanel();
		memoryPanel.setBackground(Color.white);
		memoryPanel.setBounds(900, 20, 250, 370);
		
		memoryArea = new JTextArea("");	   
		scroll4 = new JScrollPane(memoryArea);
		scroll4.setPreferredSize(new Dimension(250, 370));		
		scroll4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		txtArea = new JTextArea("enter code here");	   
		scroll = new JScrollPane(txtArea);
		scroll.setPreferredSize(new Dimension(250, 370));		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		opcodeArea = new JTextArea();	   
		scroll2 = new JScrollPane(opcodeArea);
		scroll2.setPreferredSize(new Dimension(250, 370));		
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		cycleArea = new JTextArea();	   
		scroll3 = new JScrollPane(cycleArea);
		scroll3.setPreferredSize(new Dimension(250, 370));		
		scroll3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		
		btnRun = new JButton("Compile & Run");
		btnRun.setBounds(20, 400, 150, 20);
		btnRun.addActionListener(new compile_Run());
		
		btnReset = new JButton("Reset");
		btnReset.setBounds(20, 430, 80, 20);
		btnReset.addActionListener(new clear_reset());
		
		btnRegister = new JButton("Register");
		btnRegister.setBounds(600, 400, 80, 20);
		btnRegister.addActionListener(new change_reg());
		
		GoTo = new JButton("GoTo");
		GoTo.setBounds(900, 400, 80, 20);
		GoTo.addActionListener(new go_to());
		  
		
		//Initialize Registers
		for(int i = 0; i < 32; i++) {
			
			
			String rname = Integer.toString(i);
			Register register = new Register(rname);
			registers.add(register);
			
			
		
			JLabel button = new JLabel("R"+ i + " = " + register.value);
		    buttonList.add(button);						
		    registerPanel.add(button);  
		} 	////initialize		
		
		
		int add = 0;
		for(int i = 0; i < 256; i++) {
			add = i + 4;
			DataMemory mem = new DataMemory(add);
			memories.add(mem);
		}
		
		
		add = 0;
		for(int i = 0; i < memories.size(); i++) {
			
			memories.get(i).address = add;
			add+=4;
		}
		
		
		
		
		txtPanel.add(scroll);	
		opcodePanel.add(scroll2);
		cyclePanel.add(scroll3);
		memoryPanel.add(scroll4);
		
		mainPanel.add(memoryPanel);
		mainPanel.add(GoTo);
		mainPanel.add(txtPanel);
		mainPanel.add(opcodePanel);
		mainPanel.add(btnRun);
		mainPanel.add(btnReset);
		mainPanel.add(registerPanel);
		mainPanel.add(btnRegister);
		mainPanel.add(cyclePanel);
		
		pane.add(mainPanel);

		setTitle("MIPS Simulator");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
    }//MainFrame()
    
    
    public String getCode() {
    		
    	input = txtArea.getText();
    	
    		return input;
    }
    
    
    //CREATES INSTRUCTIONS FROM INPUT
    public void createInstruction(String instruction) {
    	
    		if(instruction.contains("LD") && !instruction.contains("SD") && !instruction.contains("DADDIU") && !instruction.contains("DADDU") && !instruction.contains("BC") && !instruction.contains("BNEC")&& !instruction.contains("SLTI")) {
    			
    			LD ld = new LD(instruction);
    			codes.add(ld);
    			
    		}	
    		else if(instruction.contains("SD") && !instruction.contains("LD") && !instruction.contains("DADDIU") && !instruction.contains("DADDU") && !instruction.contains("BC") && !instruction.contains("BNEC")&& !instruction.contains("SLTI")) {
    			
    			SD sd = new SD(instruction);
    			codes.add(sd);   			
    		}    		
    		else if(instruction.contains("DADDIU") && !instruction.contains("SD") && !instruction.contains("LD") && !instruction.contains("DADDU") && !instruction.contains("BC") && !instruction.contains("BNEC")&& !instruction.contains("SLTI")) {
    			
    			DADDIU daddiu = new DADDIU(instruction);
    			codes.add(daddiu);
    			
    		}
    		
    		else if(instruction.contains("DADDU") && !instruction.contains("SD") && !instruction.contains("DADDIU") && !instruction.contains("LD") && !instruction.contains("BC") && !instruction.contains("BNEC")&& !instruction.contains("SLTI")) {
    			
    			DADDU daddu = new DADDU(instruction);
    			codes.add(daddu);
    			
    		}  		
    		else if(instruction.contains("BC") && !instruction.contains("SD") && !instruction.contains("DADDIU") && !instruction.contains("DADDU") && !instruction.contains("LD") && !instruction.contains("BNEC")&& !instruction.contains("SLTI")) {
    			
    			BC bc = new BC(instruction);
    			codes.add(bc);
    			
    		}    		
    		else if(instruction.contains("BNEC") && !instruction.contains("SD") && !instruction.contains("DADDIU") && !instruction.contains("DADDU") && !instruction.contains("BC") && !instruction.contains("LD")&& !instruction.contains("SLTI")) {
    			
    			BNEC bnec = new BNEC(instruction);
    			codes.add(bnec);
    			
    		}    		
    		else if(instruction.contains("SLTI") && !instruction.contains("SD") && !instruction.contains("DADDIU") && !instruction.contains("DADDU") && !instruction.contains("BC") && !instruction.contains("BNEC")&& !instruction.contains("LD")) {
    			
    			SLTI slti = new SLTI(instruction);
    			codes.add(slti);
    			
    		}   	
    		
    		else {
    			cycleArea.setText("Invalid Syntax!");
    		}
    }///////create instructions
    
    
    ///CREATE ADDRESS FOR INSTRUCTIONS
    public void createAddress() {
    	
    		
    		int add = 256;
		for(int i = 0; i < codes.size(); i++) {
			
			codes.get(i).intAddress = add;
			add+=4;
		}
    	    	
    }/////create address
    
    
  ///CREATE OPCODES FOR INSTRUCTIONS
    public void createOPCODES() {
    	
		for(int i = 0; i < codes.size(); i++) {
			
			String instruction = codes.get(i).codeLine;
			
			if(codes.get(i) instanceof LD)
				((LD) codes.get(i)).createOpcode(instruction, codes);
			else if(codes.get(i) instanceof SD)
				((SD) codes.get(i)).createOpcode(instruction, codes);
			else if(codes.get(i) instanceof DADDIU)
				((DADDIU) codes.get(i)).createOpcode(instruction, codes);
			else if(codes.get(i) instanceof DADDU)
				((DADDU) codes.get(i)).createOpcode(instruction, codes);
			else if(codes.get(i) instanceof BC)
				((BC) codes.get(i)).createOpcode(instruction, codes);
			else if(codes.get(i) instanceof BNEC)
				((BNEC) codes.get(i)).createOpcode(instruction, codes);
			else if(codes.get(i) instanceof SLTI)
				((SLTI) codes.get(i)).createOpcode(instruction, codes);

		}
    	
    	
    	
    }/////create opcodes
    
    
    ///UPDATE REGISTER LABELS VIEW
    public void updateREGS() {

		//update register labels
		for(int i = 0; i < registers.size(); i++) {
			
			String regVal = "";
			
			for(int j = 0; j < 16 - registers.get(i).value.length(); j++) {
				regVal = regVal.concat("0");}
			
			regVal = regVal.concat(registers.get(i).value);
			
			buttonList.get(i).setText("R"+ i + " = " + regVal.toUpperCase());
			
		}//update reg
		
		registers.get(0).value = "0000000000000000";
		buttonList.get(0).setText("R0" + " = " + registers.get(0).value);
    }
    
    ///RESET REGISTER VALUES
    public void resetREGS() {

		//update register labels
		for(int i = 0; i < registers.size(); i++) {
			
			registers.get(i).value = "0000000000000000";

			buttonList.get(i).setText("R"+ i + " = " + registers.get(i).value);
		
		}//reset reg
    }
    
    
  ///RESET REGISTER VALUES
    public void changeReg() {
    	
    		RegChangeFrame = new JFrame("Change Register Value");
		RegChangeFrame.setSize(420,110);
		RegChangeFrame.setResizable(false);
		RegChangeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		newPanel = new JPanel();
		RegChangeFrame.setVisible(true);
		RegChangeFrame.add(newPanel);
		
		R = new JTextField("#", 3);
		Rval = new JTextField("16 bytes", 16);
		
		ok = new JButton("Okay");
		ok.addActionListener(new ok_change());
		cancel = new JButton("Cancel");
		cancel.addActionListener(new cancel());
		
		
		R.setBounds(120, 10, 200, 30);
		Rval.setBounds(120, 40, 140, 30);
		
		ok.setBounds(345, 120, 75, 32);
		
		newPanel.add(R);
		newPanel.add(Rval);
		newPanel.add(ok);
		
    }
    
    
    ////SIMULATE THE CYCLE
    public void cycle() {
    	
    		for(int i = 0; i < codes.size(); i++) {
    			
    		}
    	
    	
    	
    	
    	
    }
    //////END OF SIMULATION
    
    ///compile n run
    class compile_Run implements ActionListener
	{
		public void actionPerformed (ActionEvent e) {
			
			input = getCode();
			
			codeLines = input.split("\n");
			
			for(int i = 0; i < codeLines.length; i++) {
				if(codeLines[i].length() > 2) {
					instructions.add(codeLines[i]);	
					createInstruction(codeLines[i]);
				}//////if
				
				
				//set address method
				
				
			}///for loop
			
			
			createAddress();
			//Make opcodes
			createOPCODES();
			//make addresses
			
			
			
			///PRINT OPCODES and address////
			for(int i = 0; i < codes.size(); i++) {
				
				int j = codes.get(i).intAddress;
				String s = "0" + Integer.toHexString(j);
				opcodeArea.append(s.toUpperCase() + "\t");
				
				//opcodeArea.disable();
				opcodeArea.append(codes.get(i).OPHEX.toUpperCase());
				opcodeArea.append("\n");
		
			} /////PRINT OPCODES///
			
			int z;
			//execute codes
			for(int i = 0; i < codes.size(); i++) {
				
				if(codes.get(i) instanceof DADDIU)
					((DADDIU) codes.get(i)).execute(registers);
				if(codes.get(i) instanceof DADDU)
					((DADDU) codes.get(i)).execute(registers);
				if(codes.get(i) instanceof SLTI)
					((SLTI) codes.get(i)).execute(registers);
				
				if(codes.get(i) instanceof BNEC) {
					((BNEC) codes.get(i)).execute(registers);
					if(((BNEC) codes.get(i)).condition.equals("1")) {
						i = ((BNEC) codes.get(i)).branchi - 1;}
				}
				
				if(codes.get(i) instanceof BC) {
					((BC) codes.get(i)).execute(registers);
					if(((BC) codes.get(i)).condition.equals("1")) {
						i = ((BC) codes.get(i)).branchi - 1;}
				}
				
				
				
				
				System.out.println(codes.get(i).cycle);
			//		cycleArea.append(codes.get(i).cycle.toUpperCase());
			//		cycleArea.append("\n\n");
				
			}///execute
			
			
			
	/*		///PRINT OPCODES and address////
			for(int i = 0; i < codes.size(); i++) {
				
				String cycle;
				
				if(codes.get(i).cycle.isEmpty() == false) {
					cycleArea.append(codes.get(i).cycle.toUpperCase());
					cycleArea.append("\n\n");
				}
		
			} /////PRINT OPCODES/// */
			
			
			
			///UPDATE REGISTER LABELS
			updateREGS();
		
			
		}////////////
	}/////////compile_run
    
    
    //////RESET BUTTON
    class clear_reset implements ActionListener
	{
		public void actionPerformed (ActionEvent e) {
		
		opcodeArea.setText("");
		cycleArea.setText("");
		codes.clear();
     	resetREGS();

			
		}
    
	}/////////RESET
    
    
    //////CHANGE REGISTER
    class change_reg implements ActionListener
	{
			public void actionPerformed (ActionEvent e) {			
				changeReg();
		
		}
  
	}/////////CHANGE
    
    
    
    public void goTo() {
    	
	MemFrame = new JFrame("Go To Memory");
	MemFrame.setSize(420,110);
	MemFrame.setResizable(false);
	MemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
	newPanel2 = new JPanel();
	MemFrame.setVisible(true);
	
	
	memAdd = new JTextField("#", 3);
	memVal = new JTextField("16 bytes", 16);
	
	okay = new JButton("Okay");
	okay.addActionListener(new okay_change());
	
	
	
	memAdd.setBounds(120, 10, 200, 30);
	memVal.setBounds(120, 40, 140, 30);
	
	okay.setBounds(345, 120, 75, 32);
	
	newPanel2.add(memAdd);
	newPanel2.add(memVal);
	newPanel2.add(okay);
	
	MemFrame.add(newPanel2);
	
}
    
    
    //////CHANGE REGISTER
    class go_to implements ActionListener
	{
			public void actionPerformed (ActionEvent e) {			
				goTo();
		
		}

	}/////////CHANGE
    
    //////OK
    class ok_change implements ActionListener
	{
			public void actionPerformed (ActionEvent e) {			
				
				String regNum = R.getText();
				String regVal = Rval.getText();
				
				int a = Integer.parseInt(regNum.substring(1));
				
				
					if(regVal.length() == 16 ) {
					
					for(int i = 0; i < registers.size(); i++) {
						
						if(registers.get(i).name.equals(regNum)) {
							registers.get(i).value = regVal;
							cycleArea.setText("");
							}
						
						else if(a > 32) {
							cycleArea.setText("Invalid register");
						}
						
						else {}
							
					}
					updateREGS();
					RegChangeFrame.dispose();
			}
		}
	}/////////OK
    
    
    //////OKAY
    class okay_change implements ActionListener
	{
			public void actionPerformed (ActionEvent e) {			
				
				String addNum = memAdd.getText();
				int z = Integer.parseInt(addNum, 16);
				String addPrint = "";
				addNum = Integer.toHexString(z);
				String addValue = "";
				
				String addVal = memVal.getText();
				
				for(int i = 0; i < memories.size(); i++) {
					String temp = Integer.toHexString(memories.get(i).address);
					addPrint = temp;
					if(temp.equals(addNum)) {
						memories.get(i).value = addVal;
						cycleArea.setText("");}
					
					else if(z > 256) {
						cycleArea.setText("Invalid Memory");
					}
					
					else {}
						
				}
						
						memoryArea.setText("");
						for(int i = 0; i < memories.size(); i++) {
							
							String temp = Integer.toHexString(memories.get(i).address);
							temp = pad(temp);
							
							memoryArea.append(temp.toUpperCase());
							memoryArea.append("\t");
							memoryArea.append(memories.get(i).value);
							memoryArea.append("\n");
							
						}
						
				
						MemFrame.dispose();	
					}
					
			}

  ///padd func
  	public String pad(String address) {
  		
  		String padded = "";
  		for(int i = 0; i < 4 - address.length(); i++) {
  			
  			padded = padded.concat("0");
  			
  		}
  		return padded.concat(address);
  	}
    
    //////CANCEL
    class cancel implements ActionListener
	{
			public void actionPerformed (ActionEvent e) {			
				
		
		}

	}/////////CANCEL
    
    
    
    
    

}//END

