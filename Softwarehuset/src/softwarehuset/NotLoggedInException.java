package softwarehuset;

public class NotLoggedInException extends Exception{
	
	private String errorOp;
	
	public NotLoggedInException(String errorMsg, String errorOp) {
		super(errorMsg);
		this.errorOp = errorOp;
	}

	public Object getOperation() {
		return errorOp;
	}
	
}
