package core;

/**
 *
 * @author Jordan
 */
public abstract class Communication
{
    abstract void open();
    abstract void close();
    abstract boolean write(byte[] data);
}
