package core;

import core.EV3Types.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 *
 * @author Jordan
 */
public class Command
{

    private ByteBuffer data;
    private CommandType commandType;

    /**
     * Create a new command
     *
     * @param commandType CommandType The type of the request
     */
    public Command(CommandType commandType)
    {
        this.commandType = commandType;
        initialize();
    }

    private void initialize()
    {

        data = ByteBuffer.allocateDirect(200);

        // Two first byte for the command length (gets filled in later)
        data.put((byte) 0);
        data.put((byte) 0);

        // Two byte for the request identification
        // TODO : Use response class and manage a id        
        data.put((byte) 0);
        data.put((byte) 0);

        // One byte for the type of command
        data.put((byte) commandType.get());

        if (commandType == CommandType.DirectReply || commandType == CommandType.DirectNoReply) {
            data.put((byte) 0);
            data.put((byte) 0);
        }
    }

    private void addOpcode(Opcode opcode)
    {

        // Write on two bytes if the command is to large
        if (opcode.get() > Opcode.Tst.get())
            data.put((byte) (opcode.get() >> 8));

        data.put((byte) opcode.get());
    }

    private void addOpcode(SystemOpcode opcode)
    {

        data.put((byte) opcode.get());
    }

    /**
     * Add a string parameter to the message
     *
     * @param parameter
     */
    private void addParameter(String parameter)
    {
        data.put((byte) ArgumentSize.String.get());

        byte[] stringBytes = parameter.getBytes(Charset.defaultCharset());

        for (byte b : stringBytes)
            data.put(b);

        // End of string
        data.put((byte) 0x00);
    }

    /**
     * Add a paramter to the message. This function cut up the int in byte array
     *
     * @param parameter
     */
    private void addParameter(int parameter)
    {
        data.put((byte) ArgumentSize.Int.get());
        data.putInt(parameter);
    }

    private void addParameter(byte parameter)
    {
        data.put((byte) ArgumentSize.Byte.get());
        data.put(parameter);
    }

    private void addParameter(short parameter)
    {
        data.put((byte) ArgumentSize.Short.get());
        data.putShort(parameter);
    }

    private void addRawParameter(int parameter)
    {
    }

    /**
     * Makes the motor turn at the specified power
     *
     * @param ports
     * @param power Power of the motor between -100 and 100%
     * @throws core.ArgumentException
     */
    public void turnMotorAtPower(OutputPort ports, int power) throws ArgumentException
    {
        if (power < -100 || power > 100)
            throw new ArgumentException("Power must be between -100 and 100 inclusive.", "power");

        addOpcode(Opcode.OutputPower);
        addParameter((byte) 0); // layer
        addParameter((byte) ports.get());	// ports
        addParameter((byte) power);	// power
    }
    
    /**
     * Makes the motor turn at the specified speed
     *
     * @param ports
     * @param speed Power of the motor between -100 and 100%
     * @throws core.ArgumentException
     */
    public void turnMotorAtSpeed(OutputPort ports, int speed) throws ArgumentException
    {
        if (speed < -100 || speed > 100)
            throw new ArgumentException("Speed must be between -100 and 100 inclusive.", "speed");

        addOpcode(Opcode.OutputSpeed);
        addParameter((byte) 0); // layer
        addParameter((byte) ports.get());	// ports
        addParameter((byte) speed);	// power
    }

    
    /**
     * Makes the motor stop
     *
     * @param ports
     * @param brake Apply power to stop the rotation
     */
    public void stopMotor(OutputPort ports, boolean brake)
    {
        addOpcode(Opcode.OutputStop);
        addParameter((byte) 0); // layer
        addParameter((byte) ports.get()); // ports
        addParameter((byte)(brake ? 1 : 0));
    }
    
    /**
     * Makes the motor start
     *
     * @param ports
     */
    public void startMotor(OutputPort ports)
    {
        addOpcode(Opcode.OutputStart);
        addParameter((byte) 0); // layer
        addParameter((byte) ports.get()); // ports
    }

    /**
     * Return all the bytes of the command in byte[] The two first bytes are
     * filled in with the length of the command
     *
     * @return the array byte
     */
    public byte[] toBytes()
    {

        int bytesCount = data.position() - 2;

        // little-endian
        data.put(0, (byte) bytesCount);
        data.put(1, (byte) (bytesCount >> 8));

        byte[] ret = new byte[bytesCount + 2];

        for (int i = 0; i < bytesCount + 2; i++)
            ret[i] = data.get(i);

        return ret;
    }

    @Override
    public String toString()
    {
        String ret = "";
        byte[] bytes = toBytes();

        for (byte b : bytes)
            ret += ((b & 0xff)) + "  ";

        ret += "\n";

        // Represent the unsigned representation of bytes
        for (byte b : bytes)
            ret += Integer.toBinaryString((b & 0xff)) + "  ";

        return ret;
    }
}
