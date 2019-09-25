package core;


public class TestFrameworkException extends Exception 
{
	private static final long serialVersionUID = -1278328913448228157L;
	private String exceptionType = "";
	private Exception originalException = null;


	public TestFrameworkException()
	{
		super();
	}
	
	public TestFrameworkException(String message)
	{
		super(message);
	}
	
	public TestFrameworkException(String message, Exception e)
	{
		//preserve original exceptioin
		super(message,e);
		this.exceptionType = e.getClass().getName();
		this.originalException = e;
	}
	
	
	public String getExceptionType()
	{
		return exceptionType;
	}
	
	public Exception getOriginalException()
	{
		return originalException;
	}

}
