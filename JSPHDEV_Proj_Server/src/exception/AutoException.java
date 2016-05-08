package exception;

import adapter.FixAuto;

/**Class for handing and fixing thrown Customized Exceptions.
 * @author Amrata Kasture
 *
 */

public class AutoException extends Exception implements FixAuto{
	private static final long serialVersionUID = 4664456874499611218L;
	int errNo = 0;
	String fixMe="";
	FixFileLocation ffl = new FixFileLocation();
	FixAutoProperties fap = new FixAutoProperties();
	FixFileFormat fff = new FixFileFormat();
	
	public AutoException(int errNo){
		this.errNo = errNo;
	}
	
	public AutoException(ErrorTracker et){
		this.errNo = et.getCode();
	}
	
	public String wrapFix(int errorNo){
		fix(errNo);
		return fixMe;
	}
	
	public void fix(int errNo){
		switch(errNo){	
		case 1: this.fixMe =  ffl.requestPath(errNo); break;
		case 2: this.fixMe =  ffl.ioFix(errNo);break;
		case 3: this.fixMe =  fap.fixBasePrice(errNo); break;
		case 4: this.fixMe =  fap.fixOptions(errNo); break;
		case 5: this.fixMe =  fff.fixFormat(errNo); break;
		case 6: this.fixMe =  ffl.aceessFix(errNo); break;
		}
	}

}
