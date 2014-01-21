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
        data.putShort((short) 0);

        // Two byte for the request identification
        data.putShort((short) response.sequence);

        // One byte for the type of command
        data.put((byte) commandType.get());

        if (commandType == CommandType.DirectReply || commandType == CommandType.DirectNoReply) {
            // 2 bytes (llllllgg gggggggg)
            data.put((byte) globalSize); // lower bits of globalSize
            data.put((byte) (byte) ((localSize << 2) | (globalSize >> 8) & 0x03)); // upper bits of globalSize + localSize
        }
    }

    /**
     * Add an opcode to the message
     *
     * @param opcode
     */
    private void addOpcode(Opcode opcode)
    {
        if (opcode.get() <= 255)
            data.put((byte) opcode.get());
        else if (opcode.get() <= 65535)
            data.putShort((short) opcode.get());
        else
            data.putInt((int) opcode.get());
    }

    /**
     * Add a system opcode to the message
     *
     * @param opcode
     */
    private void addOpcode(SystemOpcode opcode)
    {
        if (opcode.get() <= 255)
            data.put((byte) opcode.get());
        else if (opcode.get() <= 65535)
            data.putShort((short) opcode.get());
        else
            data.putInt((int) opcode.get());
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
        data.put((byte) 0);
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
     * Add a parameter on 2 bytes to the message. This function add a byte
     * before the parameter to specify the number of byte where it is coded
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
        data.put((byte) (0xe1));
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
     * Add string parameter and don't get format specifier added prior to the
     * data itself. these are used in system commands (only?)
     *
     * @param parameter
     */
    private void addRawParameter(String parameter)
    {
        byte[] bytes = parameter.getBytes(Charset.defaultCharset());
        data.put(bytes);
        data.put((byte) 0);
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
     * Turn the motor connected to the specified port or ports at the specified power for the specified times.
     * @param ports A specific port or Ports.All.
     * @param power The power at which to turn the motor (-100% to 100%).
     * @param ms Number of milliseconds to run at constant power.
     * @param brake Apply brake to motor at end of routine.
     * @throws ArgumentException 
     */
    public void turnMotorAtPowerForTime(OutputPort ports, int power, int ms, boolean brake) throws ArgumentException {
        turnMotorAtPowerForTime(ports, power, 0, ms, 0, brake);
    }
    
    
    /**
     * Turn the motor connected to the specified port or ports at the specified power for the specified times.
     * @param ports A specific port or Ports.All.
     * @param power The power at which to turn the motor (-100% to 100%).
     * @param msRampUp Number of milliseconds to get up to power.
     * @param msConstant Number of milliseconds to run at constant power.
     * @param msRampDown Number of milliseconds to power down to a stop.
     * @param brake Apply brake to motor at end of routine.
     * @throws ArgumentException 
     */
    public void turnMotorAtPowerForTime(OutputPort ports, int power, int msRampUp, int msConstant, int msRampDown, boolean brake) throws ArgumentException {
        if (power < -100 || power > 100)
            throw new ArgumentException("Power must be between -100 and 100 inclusive.", "power");
        
        addOpcode(Opcode.OutputTimePower);
        addParameter(0x00); //layer
        addParameter((byte)ports.get());
        addParameter((byte)power);
        addParameter(msRampUp);
        addParameter(msConstant);
        addParameter(msRampDown);
        addParameter((byte)(brake ? 0x01 : 0x00)); // brake (0=coast, 1 = brake)
    }
    
    /**
     * Step the motor connected to the specified port or ports at the specified power for the specified number of steps.
     * @param ports A specific port or Ports.All.
     * @param power The power at which to turn the motor (-100% to 100%).
     * @param steps The number of steps to turn the motor.
     * @param brake Apply brake to motor at end of routine.
     * @throws ArgumentException 
     */
    public void stepMotorAtPower(OutputPort ports, int power, int steps, boolean brake) throws ArgumentException {
        stepMotorAtPower(ports, power, 0, steps, 10, brake);
    }
    
    /**
     * Step the motor connected to the specified port or ports at the specified power for the specified number of steps.
     * @param ports A specific port or Ports.All.
     * @param power The power at which to turn the motor (-100% to 100%).
     * @param rampUpSteps 
     * @param constantSteps The number of steps to turn the motor.
     * @param rampDownSteps
     * @param brake Apply brake to motor at end of routine.
     * @throws ArgumentException 
     */
    public void stepMotorAtPower(OutputPort ports, int power, int rampUpSteps, int constantSteps, int rampDownSteps, boolean brake) throws ArgumentException {
        if (power < -100 || power > 100)
            throw new ArgumentException("Power must be between -100 and 100 inclusive.", "power");
        
        addOpcode(Opcode.OutputStepPower);
        addParameter(0x00); //layer
        addParameter((byte)ports.get());
        addParameter((byte)power);
        addParameter(rampUpSteps);
        addParameter(constantSteps);
        addParameter(rampDownSteps);
        addParameter((byte)(brake ? 0x01 : 0x00)); // brake (0=coast, 1 = brake)
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
        addParameter((byte) (brake ? 1 : 0));
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
     *
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
        addParameter((byte) 0);              // type
        addParameter((byte) mode);	 // mode
        addParameter((byte) 1);		 // # values
        addGlobalIndex((byte) index);	 // index for return data
    }

    /**
     * Append the Get Type/Mode command to an existing Command object
     *
     * @param port The port to query
     * @param typeIndex The index to hold the Type value in the global buffer
     * @param modeIndex The index to hold the Type value in the global buffer
     * @throws core.ArgumentException
     */
    public void getTypeMode(InputPort port, int typeIndex, int modeIndex) throws ArgumentException
    {
        if (typeIndex > 1024)
            throw new ArgumentException("Index for Type cannot be greater than 1024", "typeIndex");
        if (modeIndex > 1024)
            throw new ArgumentException("Index for Mode cannot be greater than 1024", "modeIndex");

        addOpcode(Opcode.InputDevice_GetTypeMode);
        addParameter((byte) 0);		 // layer
        addParameter((byte) port.get());
        addGlobalIndex((byte) typeIndex); // index for type
        addGlobalIndex((byte) modeIndex); // index for mode
    }
    
    /**
     * Append the Ready SI command to an existing Command object
     * @param port The port to query
     * @param mode The mode to read the data as
     * @param index The index to hold the return value in the global buffer
     * @throws ArgumentException 
     */
    public void readySI(InputPort port, int mode, int index) throws ArgumentException {
        if(index > 1024)
            throw new ArgumentException("Index cannot be greater than 1024", "index");
        addOpcode(Opcode.InputDevice_ReadySI);
        addParameter(0x00); //layer
        addParameter((byte)port.get()); // port
        addParameter(0x00); // type
        addParameter((byte)mode); // mode
        addParameter(0x01); // values
        addGlobalIndex((byte)index); // index for return data
    }
    
    /**
     * Append the Ready Percent command to an existing Command object
     * @param port The port to query
     * @param mode The mode to query the value as
     * @param index The index in the global buffer to hold the return value
     * @throws ArgumentException 
     */
    public void readyPercent(InputPort port, int mode, int index) throws ArgumentException {
        if(index > 1024)
            throw new ArgumentException("Index cannot be greater than 1024", "index");
        addOpcode(Opcode.InputDevice_ReadyPct);
        addParameter(0x00); // layer
        addParameter((byte)port.get()); // port
        addParameter(0x00); // type
        addParameter((byte)mode); // mode
        addParameter(0x01); // values
        addGlobalIndex((byte)index); // index for return data
    }
    
    /**
     * Append the Get Device Name command to an existing Command object
     * @param port The port to query
     * @param bufferSize Size of the buffer to hold the returned data
     * @param index Index to the position of the returned data in the global buffer
     * @throws ArgumentException 
     */
    public void getDeviceName(InputPort port, int bufferSize, int index) throws ArgumentException {
        if(index > 1024)
            throw new ArgumentException("Index cannot be greater than 1024", "index");
        addOpcode(Opcode.InputDevice_GetDeviceName);
        addParameter(0x00);
        addParameter((byte)port.get());
        addParameter((byte)bufferSize);
        addGlobalIndex((byte)index);
    }
    
    /**
     * Append the Get Mode Name command to an existing Command object
     * @param port The port to query
     * @param mode The mode of the name to get
     * @param bufferSize Size of the buffer to hold the returned data
     * @param index Index to the position of the returned data in the global buffer
     * @throws ArgumentException 
     */
    public void getModeName(InputPort port, int mode, int bufferSize, int index) throws ArgumentException {
        if(index > 1024)
            throw new ArgumentException("Index cannot be greater than 1024", "index");
        addOpcode(Opcode.InputDevice_GetModeName);
        addParameter(0x00);
        addParameter((byte)port.get());
        addParameter((byte)mode);
        addParameter((byte)bufferSize);
        addGlobalIndex((byte)index);
    }
    
    /**
     * Append the Play Tone command to an existing Command object
     * @param volume Volume to play the tone (0-100)
     * @param frequency Frequency of tone in Hertz
     * @param duration Duration of the tone in milliseconds
     * @throws ArgumentException 
     */
    public void playTone(int volume, int frequency, int duration) throws ArgumentException {
        if(volume < 0 || volume > 100)
            throw new ArgumentException("Volume must be between 0 and 100", "volume");
        addOpcode(Opcode.Sound_Tone);
        addParameter((byte)volume); // volume
        addParameter(frequency); // frequency
        addParameter(duration); // duration (ms)
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
