package ev3.api;

import ev3.api.EV3Types.*;

/**
 * This class contains method which can be called directly to control the ev3
 * @author Jordan
 */
public class DirectCommand {
    
    private final Brick brick;

    public DirectCommand(Brick brick)
    {
        this.brick = brick;
    }    
    
    /**
     * Start the rotation of the motor at the specified power
     * @param port The port to start
     * @param power Power between -100 and 100
     * @throws ArgumentException 
     */
    public void startMotorAtPower(OutputPort port, int power) throws ArgumentException
    {
        startMotorAtPower(new OutputPort[]{port}, power);
    }
    
    /**
     * Start the rotation of the motors at the specified power
     * @param ports The port to start
     * @param power Power between -100 and 100
     * @throws ArgumentException 
     */
    public void startMotorAtPower(OutputPort[] ports, int power) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectNoReply);
        c.turnMotorAtPower(ports, power);
        c.startMotor(ports);
        brick.sendCommand(c);
    }
    
    public void startMotorAtPowerForTime(OutputPort port, int power, int ms, boolean brake) throws ArgumentException {
        startMotorAtPowerForTime(new OutputPort[]{port}, power, 0, ms, 0, brake);        
    }
    
    public void startMotorAtPowerForTime(OutputPort[] ports, int power, int ms, boolean brake) throws ArgumentException {
        startMotorAtPowerForTime(ports, power, 0, ms, 0, brake);  
    }
    
    public void startMotorAtPowerForTime(OutputPort port, int power, int msRampUp, int msRampConst, int msRampDown, boolean brake) throws ArgumentException {
        startMotorAtPowerForTime(new OutputPort[]{port}, power, msRampDown, msRampConst, msRampDown, brake);
    }
    
    public void startMotorAtPowerForTime(OutputPort[] ports, int power, int msRampUp, int msRampConst, int msRampDown, boolean brake) throws ArgumentException {
        Command c = new Command(CommandType.DirectNoReply);
        c.turnMotorAtPowerForTime(ports, power, msRampUp, msRampConst, msRampDown, brake);
        c.startMotor(ports);
        brick.sendCommand(c);
    }
    
    public void stepMotorAtPower(OutputPort port, int power, int steps, boolean brake) throws ArgumentException {
        stepMotorAtPower(new OutputPort[]{port}, power, 0, steps, 0, brake);
    }
    
    public void stepMotorAtPower(OutputPort[] ports, int power, int steps, boolean brake) throws ArgumentException {
        stepMotorAtPower(ports, power, 0, steps, 0, brake);
    }
    
    public void stepMotorAtPower(OutputPort port, int power, int rampUpSteps, int constantSteps, int rampDownSteps, boolean brake) throws ArgumentException {
        stepMotorAtPower(new OutputPort[]{port}, power, rampUpSteps, constantSteps, rampDownSteps, brake);
    }
    
    public void stepMotorAtPower(OutputPort[] ports, int power, int rampUpSteps, int constantSteps, int rampDownSteps, boolean brake) throws ArgumentException {
        Command c = new Command(CommandType.DirectNoReply);
        c.resetInternalTachoMotor(ports);
        c.stepMotorAtPower(ports, power, rampUpSteps, constantSteps, rampDownSteps, brake);
        c.startMotor(ports);
        brick.sendCommand(c);
    }
    
    public void stepMotorSync(OutputPort port1, OutputPort port2, int power, int step, boolean brake) throws ArgumentException
    {    
        Command c = new Command(CommandType.DirectNoReply);
        c.resetInternalTachoMotor(new OutputPort[]{port1, port2});
        c.stepMotorSync(port1, port2, power, step, brake);
        brick.sendCommand(c); 
    }

    public void stopMotor(OutputPort port, boolean brake) throws ArgumentException
    {
        stopMotor(new OutputPort[]{port}, brake);
    }
    
    public void stopMotor(OutputPort[] ports, boolean brake) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectNoReply);
        c.stopMotor(ports, brake);
        brick.sendCommand(c); 
    }
    
    public double readUltrasonic(InputPort port) throws ArgumentException, InterruptedException
    {
        int responseSize = 6;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        c.getTypeMode(port, index, index+1);
        c.readUltrasonic(port, UltrasonicMode.Centimeters, index+2);
        brick.sendCommand(c);
        
        return SensorDataConverter.getValue(c.response.data, 0, 5);
    }
    
    public int readGyroscope(InputPort port) throws ArgumentException
    {
        int responseSize = 4;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        c.readGyroscope(port, GyroscopeMode.Angle, index);
        brick.sendCommand(c);     
        
        return SensorDataConverter.toInt(c.response.data, 0, 3);
    }
    
    public int readCompass(InputPort port) throws ArgumentException
    {
        int responseSize = 3;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        c.getTypeMode(port, index, index+1);
        c.readRaw(port, 0, index+2);
        brick.sendCommand(c);  
        
        return SensorDataConverter.toInt(c.response.data, 2, 2)*2;
    }
    
    public int readTachoCount(InputPort port) throws ArgumentException
    {
        int responseSize = 4;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        c.readTachoCount(port, MotorMode.Degrees, index);
        brick.sendCommand(c);
        
        return SensorDataConverter.toInt(c.response.data, 0, 3);
    }
    
    public void resetGyroscope(InputPort port) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectNoReply);
        // Switch mode to calibrate
        c.readRaw(port, GyroscopeMode.Rate.get(), 0);
        c.readRaw(port, GyroscopeMode.Angle.get(), 0);
        brick.sendCommand(c); 
    }
    
    public void resetMotorCount(OutputPort port) throws ArgumentException
    {
        resetMotorCount(new OutputPort[]{port});
    }
    
    public void resetMotorCount(OutputPort[] ports) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectNoReply);
        c.resetTachoMotor(ports);
        brick.sendCommand(c);
    }
    
    public void getTypeMode(InputPort port) throws ArgumentException
    {
        int responseSize = 6;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        c.getTypeMode(port, index, index+1);
        brick.sendCommand(c); 
    }
    
    public void playTone(int volume, int frequency, int duration) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectNoReply);
        c.playTone(volume, frequency, duration);
        brick.sendCommand(c);
    }
    

    public double[] readSensors(InputPort[] ports, int[] modes) throws ArgumentException
    {
        if(ports.length != modes.length)
            return null;
        
        int responseSize = ports.length * 6;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        
        // Request each sensors
        for(int i = 0; i < ports.length; i++)
        {
            c.getTypeMode(ports[i], index, index+1);
            c.readRaw(ports[i], modes[i], index+2);
            index += 6;
        }
        
        index = 0;
        
        brick.sendCommand(c);
        
        // Fetch values
        byte[] responseData = c.response.data;
        
        index = 0;
        double[] values = new double[ports.length];
        
        for (int i = 0; i < ports.length; i++) 
        {
            values[i] = SensorDataConverter.getValue(responseData, index, index+5);
            index += 6;
        }
        
        return values;
    }
}
