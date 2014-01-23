package core;

/**
 *
 * @author Jordan
 */
public abstract class Communication
{
    abstract boolean open();
    abstract void close();
    abstract boolean write(byte[] data);
}
