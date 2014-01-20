package core;

/**
 *
 * @author Jordan
 */
public class ArgumentException extends Exception
{
    public ArgumentException(String msg, String arg)
    {
      super("Argument \"" + arg + "\" is invalid : " + msg);
    }
}
