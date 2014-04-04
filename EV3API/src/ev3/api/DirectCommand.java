package ev3.api;

import ev3.api.EV3Types.*;

/**
 * This class contains method which can be called directly to control the ev3
 * @author Jordan Martin & Jonathan Taws
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
     * @param ports The ports to start
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
    
    /**
     * Start the rotation of a motor at the specified power for a specified time
     * @param port The port to start
     * @param power Power between -100 and 100
     * @param ms The time in milliseconds
     * @param brake End the routine with a brake
     * @throws ArgumentException 
     */
    public void startMotorAtPowerForTime(OutputPort port, int power, int ms, boolean brake) throws ArgumentException {
        startMotorAtPowerForTime(new OutputPort[]{port}, power, 0, ms, 0, brake);        
    }
    
    /**
     * Start the rotation of the motors at the specified power for a specified time
     * @param ports The ports to start
     * @param power Power between -100 and 100
     * @param ms The time in milliseconds
     * @param brake End the routine with a brake
     * @throws ArgumentException 
     */
    public void startMotorAtPowerForTime(OutputPort[] ports, int power, int ms, boolean brake) throws ArgumentException {
        startMotorAtPowerForTime(ports, power, 0, ms, 0, brake);  
    }
    
    /**
     * Start the rotation of a motor at the specified power for a specified time with a certain for ramping up and ramping down
     * @param port The port to start
     * @param power Power between -100 and 100
     * @param msRampUp The time for the ramping up
     * @param msRampConst The time in milliseconds
     * @param msRampDown The time for the ramping down
     * @param brake End the routine with a brake
     * @throws ArgumentException 
     */
    public void startMotorAtPowerForTime(OutputPort port, int power, int msRampUp, int msRampConst, int msRampDown, boolean brake) throws ArgumentException {
        startMotorAtPowerForTime(new OutputPort[]{port}, power, msRampDown, msRampConst, msRampDown, brake);
    }
    
    /**
     * Start the rotation of the motors at the specified power for a specified time with a certain for ramping up and ramping down
     * @param ports The ports to start
     * @param power Power between -100 and 100
     * @param msRampUp The time for the ramping up
     * @param msRampConst The time in milliseconds
     * @param msRampDown The time for the ramping down
     * @param brake End the routine with a brake
     * @throws ArgumentException 
     */
    public void startMotorAtPowerForTime(OutputPort[] ports, int power, int msRampUp, int msRampConst, int msRampDown, boolean brake) throws ArgumentException {
        Command c = new Command(CommandType.DirectNoReply);
        c.turnMotorAtPowerForTime(ports, power, msRampUp, msRampConst, msRampDown, brake);
        c.startMotor(ports);
        brick.sendCommand(c);
    }
    
    /** 
     * Step the rotation of a motor for a specified number of steps at a specified power with a brake option
     * @param port The port to step
     * @param power Power between -100 and 100
     * @param steps Number of steps
     * @param brake End the routine with a brake
     * @throws ArgumentException 
     */
    public void stepMotorAtPower(OutputPort port, int power, int steps, boolean brake) throws ArgumentException {
        stepMotorAtPower(new OutputPort[]{port}, power, 0, steps, 0, brake);
    }
    
    /**
     * Step the rotation of the motors for a specified number of steps at a specified power with a brake option
     * @param ports The ports to step
     * @param power Power between -100 and 100
     * @param steps Number of steps
     * @param brake End the routine with a brake
     * @throws ArgumentException 
     */
    public void stepMotorAtPower(OutputPort[] ports, int power, int steps, boolean brake) throws ArgumentException {
        stepMotorAtPower(ports, power, 0, steps, 0, brake);
    }
    
    /**
     * Step the rotation of a motor for a specified number of steps at a specified power with a brake option. Ramping up and ramping down included
     * @param port The port to step
     * @param power Power between -100 and 100
     * @param rampUpSteps Number of steps to ramp up
     * @param constantSteps Number of steps
     * @param rampDownSteps Number of steps to ramp down
     * @param brake End the routine with a brake
     * @throws ArgumentException 
     */
    public void stepMotorAtPower(OutputPort port, int power, int rampUpSteps, int constantSteps, int rampDownSteps, boolean brake) throws ArgumentException {
        stepMotorAtPower(new OutputPort[]{port}, power, rampUpSteps, constantSteps, rampDownSteps, brake);
    }
    
    /**
     * Step the rotation of the motors for a specified number of steps at a specified power with a brake option. Ramping up and ramping down included
     * @param ports The ports to step
     * @param power Power between -100 and 100
     * @param rampUpSteps Number of steps to ramp up
     * @param constantSteps Number of steps
     * @param rampDownSteps Number of steps to ramp down
     * @param brake End the routine with a brake
     * @throws ArgumentException 
     */
    public void stepMotorAtPower(OutputPort[] ports, int power, int rampUpSteps, int constantSteps, int rampDownSteps, boolean brake) throws ArgumentException {
        Command c = new Command(CommandType.DirectNoReply);
        c.resetInternalTachoMotor(ports);
        c.stepMotorAtPower(ports, power, rampUpSteps, constantSteps, rampDownSteps, brake);
        c.startMotor(ports);
        brick.sendCommand(c);
    }
    
    /**
     * Step two motors at specified ports at a specified power, for a number of steps, with a break option. The step is synced between the two motors
     * @param port1 The first motor (port)
     * @param port2 The second motor (port)
     * @param power Power between -100 and 100
     * @param step The number of steps
     * @param brake End the routine with a brake
     * @throws ArgumentException 
     */
    public void stepMotorSync(OutputPort port1, OutputPort port2, int power, int step, boolean brake) throws ArgumentException
    {    
        Command c = new Command(CommandType.DirectNoReply);
        c.resetInternalTachoMotor(new OutputPort[]{port1, port2});
        c.stepMotorSync(port1, port2, power, step, brake);
        brick.sendCommand(c); 
    }
    
    /**
     * Stop the motor at the specified port with a brake option
     * @param port The motor (port) to stop
     * @param brake End the rountine with a brake
     * @throws ArgumentException 
     */
    public void stopMotor(OutputPort port, boolean brake) throws ArgumentException
    {
        stopMotor(new OutputPort[]{port}, brake);
    }
    
    /**
     * Stop the motors at the specified ports with a brake option
     * @param ports The motors (ports) to stop
     * @param brake End the routine with a brake
     * @throws ArgumentException 
     */
    public void stopMotor(OutputPort[] ports, boolean brake) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectNoReply);
        c.stopMotor(ports, brake);
        brick.sendCommand(c); 
    }
    
    /**
     * Read the ultrasonic value from one sensor 
     * @param port The port on which the ultrasonic sensor is connected
     * @return The current value of the ultrasonic
     * @throws ArgumentException
     * @throws InterruptedException 
     */
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
    
    /**
     * Read the value of the gyroscope on the specified port
     * @param port The port on which the gyroscope is connected
     * @return The current value of the gyroscope
     * @throws ArgumentException 
     */
    public int readGyroscope(InputPort port) throws ArgumentException
    {
        int responseSize = 4;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        c.readGyroscope(port, GyroscopeMode.Angle, index);
        brick.sendCommand(c);     
        
        return SensorDataConverter.toInt(c.response.data, 0, 3);
    }
    
    /**
     * Read the value of the compass on the specified port
     * @param port The port on which the compass is connected
     * @return The current value of the compass
     * @throws ArgumentException 
     */
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
    
    /**
     * Read the tacho count value of a motor
     * @param port The port (motor) to read the tacho count from
     * @return Value of the tacho counter
     * @throws ArgumentException 
     */
    public int readTachoCount(InputPort port) throws ArgumentException
    {
        int responseSize = 4;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        c.readTachoCount(port, MotorMode.Degrees, index);
        brick.sendCommand(c);
        
        return SensorDataConverter.toInt(c.response.data, 0, 3);
    }
    
    /**
     * Reset the gyroscope of the EV3
     * @param port The port on which the gyroscope is connected
     * @throws ArgumentException 
     */
    public void resetGyroscope(InputPort port) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectNoReply);
        // Switch mode to calibrate
        c.readRaw(port, GyroscopeMode.Rate.get(), 0);
        c.readRaw(port, GyroscopeMode.Angle.get(), 0);
        brick.sendCommand(c); 
    }
    
    /**
     * Resey the tacho counter of a motor
     * @param port The port (motor) to reset
     * @throws ArgumentException 
     */
    public void resetMotorCount(OutputPort port) throws ArgumentException
    {
        resetMotorCount(new OutputPort[]{port});
    }
    
    /**
     * Reset the tacho counter of the motors
     * @param ports The ports (motors) to reset
     * @throws ArgumentException 
     */
    public void resetMotorCount(OutputPort[] ports) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectNoReply);
        c.resetTachoMotor(ports);
        brick.sendCommand(c);
    }
    
    /**
     * Returns the type of the mode
     * @param port The port to specify
     * @throws ArgumentException 
     */
    public void getTypeMode(InputPort port) throws ArgumentException
    {
        int responseSize = 6;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        c.getTypeMode(port, index, index+1);
        brick.sendCommand(c); 
    }
    
    /**
     * Play a tone at a specific volume, frequency, and for a certain time
     * @param volume Volume of the tone 
     * @param frequency Frequency of the tone (pitch)
     * @param duration Time in milliseconds
     * @throws ArgumentException 
     */
    public void playTone(int volume, int frequency, int duration) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectNoReply);
        c.playTone(volume, frequency, duration);
        brick.sendCommand(c);
    }
    
    /**
     * Read values from sensors specified with specific mode
     * @param ports The sensors
     * @param modes The mode to read
     * @return Values of the sensors
     * @throws ArgumentException 
     */
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
