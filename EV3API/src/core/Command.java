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
    private Response response;
    
    private final int responseSize = 11;
    
    public Command(CommandType commandType) throws ArgumentException
    {        
        initialize(commandType, 0, 0);
    }
    
    public Command(CommandType commandType, int globalSize, int localSize) throws ArgumentException
    {
        initialize(commandType, globalSize, localSize);
    }
    
    /**
     * Start a new command of a speicifc type with a global and/or local buffer
     * on the EV3 brick
     *
     * @param commandType The type of the command to start
     * @param globalSize The size of the global buffer in bytes (maximum of 1024
     * bytes)
     * @param localSize The size of the local buffer in bytes (maximum of 64
     * bytes)
     * @throws core.ArgumentException
     */
    private void initialize(CommandType commandType, int globalSize, int localSize) throws ArgumentException
    {
        
        if (globalSize > 1024)
            throw new ArgumentException("Global buffer must be less than 1024 bytes", "globalSize");
        if (localSize > 64)
            throw new ArgumentException("Local buffer must be less than 64 bytes", "localSize");
        
        this.commandType = commandType;

        data = ByteBuffer.allocateDirect(200);
        response = ResponseManager.createResponse();

        // Two first byte for the command length (gets filled in later)
        data.putShort((short)0);

        // Two byte for the request identification
        data.putShort((short)response.sequence);

        // One byte for the type of command
        data.put((byte) commandType.get());

        if (commandType == CommandType.DirectReply || commandType == CommandType.DirectNoReply) 
        {
            // 2 bytes (llllllgg gggggggg)
            data.put((byte) globalSize); // lower bits of globalSize
            data.put((byte) (byte)((localSize << 2) | (globalSize >> 8) & 0x03)); // upper bits of globalSize + localSize
        }
    }

    /**
     * Add an opcode to the message
     * @param opcode 
     */
    private void addOpcode(Opcode opcode)
    {
        if (opcode.get() <= 255)
            data.put((byte)opcode.get());
        else if(opcode.get() <= 65535)
            data.putShort((short)opcode.get());
        else
            data.putInt((int)opcode.get());
    }

    /**
     * Add a system opcode to the message
     * @param opcode 
     */
    private void addOpcode(SystemOpcode opcode)
    {
        if (opcode.get() <= 255)
            data.put((byte)opcode.get());
        else if(opcode.get() <= 65535)
            data.putShort((short)opcode.get());
        else
            data.putInt((int)opcode.get());
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
        data.put((byte)0);
    }

    /**
     * Add a parameter on 4 bytes to the message. This function add a byte
     * before the parameter to specify the number of byte where it is coded
     *
     * @param parameter
     */
    private void addParameter(int parameter)
    {
        data.put((byte) ArgumentSize.Int.get());
        data.putInt(parameter);
    }

    /**
     * Add a parameter on 1 bytes to the message. This function add a byte
     * before the parameter to specify the number of byte where it is coded
     *
     * @param parameter
     */
    private void addParameter(byte parameter)
    {
        data.put((byte) ArgumentSize.Byte.get());
        data.put(parameter);
    }

    /**
     * Add a parameter on 2 bytes to the message. This function add a byte before
     * the parameter to specify the number of byte where it is coded
     *
     * @param parameter
     */
    private void addParameter(short parameter)
    {
        data.put((byte) ArgumentSize.Short.get());
        data.putShort(parameter);
    }

    /**
     * Add parameter on 1 bytes and don't get format specifier added prior to
     * the data itself. these are used in system commands (only?)
     *
     * @param parameter
     */
    private void addRawParameter(byte parameter)
    {
        data.put(parameter);
    }
    
    /**
     * Add parameter on 2 bytes and don't get format specifier added prior to
     * the data itself. these are used in system commands (only?)
     *
     * @param parameter
     */
    private void addRawParameter(short parameter)
    {
        data.putShort(parameter);
    }

    private void addGlobalIndex(byte index)
    {
        // 0xe1 = global index, long format, 1 byte
        data.put((byte)(0xe1));
        data.put(index);
    }
    
    /**
     * Add parameter on 4 bytes and don't get format specifier added prior to
     * the data itself. these are used in system commands (only?)
     *
     * @param parameter
     */
    private void addRawParameter(int parameter)
    {
        data.putInt(parameter);
    }
    
     /**
     * Add string parameter and don't get format specifier added prior to
     * the data itself. these are used in system commands (only?)
     *
     * @param parameter
     */
    private void addRawParameter(String parameter)
    {
        byte[] bytes = parameter.getBytes(Charset.defaultCharset());
        data.put(bytes);
        data.put((byte)0);
    }
    

    /**
     * Add to the message the command : turn motor at the specified power
     *
     * @param ports Port to define the power
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
     * Add to the message the command : turn motor at the specified speed
     *
     * @param ports Port to define the power
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
     * Add to the message the command : Stop motor
     *
     * @param ports Port to define the power
     * @param brake if true the rotation is stopped by applying power else the
     * motor just stop to power up.
     */
    public void stopMotor(OutputPort ports, boolean brake)
    {
        addOpcode(Opcode.OutputStop);
        addParameter((byte) 0); // layer
        addParameter((byte) ports.get()); // ports
        addParameter((byte)(brake ? 1 : 0));
    }
    
    /**
     * Add to the message the command : Start to power up the motor. Before use
     * this function you must define the parameters. For exemple with
     * turnMotorAtSpeed(...). Else the parameters are the previous known
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
     * Add the message the command : Read raw value of the specified port
     * @param port The port to query
     * @param mode The mode to query the value as
     * @param index The index in the global buffer to hold the return value
     * @throws core.ArgumentException
     */
    public void readRaw(InputPort port, int mode, int index) throws ArgumentException
    {
        if (index > 1024)
            throw new ArgumentException("Index cannot be greater than 1024", "index");

        addOpcode(Opcode.InputDevice_ReadyRaw);
        addParameter((byte) 0);          // layer
        addParameter((byte) port.get()); // port
        addParameter((byte)0);              // type
        addParameter((byte) mode);	 // mode
        addParameter((byte)1);		 // # values
        addGlobalIndex((byte) index);	 // index for return data
    }
    
    /**
     * Append the Get Type/Mode command to an existing Command object
     * @param port The port to query
     * @param typeIndex The index to hold the Type value in the global buffer
     * @param modeIndex The index to hold the Type value in the global buffer
     * @throws core.ArgumentException
     */
    public void getTypeMode(InputPort port, int typeIndex, int modeIndex) throws ArgumentException
    {
        if(typeIndex > 1024)
                throw new ArgumentException("Index for Type cannot be greater than 1024", "typeIndex");
        if(modeIndex > 1024)
                throw new ArgumentException("Index for Mode cannot be greater than 1024", "modeIndex");

        addOpcode(Opcode.InputDevice_GetTypeMode);
        addParameter((byte)0);		 // layer
        addParameter((byte)port.get());	 
        addGlobalIndex((byte)typeIndex); // index for type
        addGlobalIndex((byte)modeIndex); // index for mode
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
            ret += ((b & 0xff)) + " ";

        // Represent the unsigned representation of bytes
//        for (byte b : bytes)
//            ret += Integer.toBinaryString((b & 0xff)) + "  ";

        return ret;
    }
}
