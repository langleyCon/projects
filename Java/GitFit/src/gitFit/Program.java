package gitFit;


public class Program {
	private String programName;
	private int programType;
	private int programId;
	private String username;
	
	
	public Program(String programName, int programType, String username) {
		super();
		this.programName = programName;
		this.programType = programType;
		this.username = username;
		//this.programId = programId;
	}


	public String getProgramName() {
		return programName;
	}


	public void setProgramName(String programName) {
		this.programName = programName;
	}


	public int getProgramType() {
		return programType;
	}


	public void setProgramType(int programType) {
		this.programType = programType;
	}


	public int getProgramId() {
		return programId;
	}


	public void setProgramId(int programId) {
		this.programId = programId;
	}
	
	
	
}
