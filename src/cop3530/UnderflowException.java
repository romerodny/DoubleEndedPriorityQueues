package cop3530;

/**
 * The error to be thrown when removing/finding data that isn't there 
 * 
 * @author Daivd Romero PID: 3624439
 */
public class UnderflowException extends RuntimeException
{
    public UnderflowException()
    {
    }
    public UnderflowException(String message)
    {
        super(message);
    }
}
