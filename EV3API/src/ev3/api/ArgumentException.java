package ev3.api;

/**
 *
 * @author Jordan Martin & Jonathan Taws
 */
public class ArgumentException extends Exception
{
    public ArgumentException(String msg, String arg)
    {
      super("Argument \"" + arg + "\" is invalid : " + msg);
    }
}
