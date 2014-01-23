package core;

import core.EV3Types.*;

/**
 *
 * @author Jordan
 */
public class DirectCommand {
    
    private final Brick brick;

    public DirectCommand(Brick brick)
    {
        this.brick = brick;
    }
    
    public void turnMotorAtPower(OutputPort ports, int power) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectNoReply);
        c.turnMotorAtPower(ports, power);
        c.startMotor(ports);        
        brick.sendCommand(c);
    }
    
    public void turnMotorAtSpeed(OutputPort ports, int speed) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectNoReply);
        c.turnMotorAtSpeed(ports, speed);
        c.startMotor(ports);        
        brick.sendCommand(c);
    }
    
    public void turnMotorAtPowerForTime(OutputPort ports, int power, int ms, boolean brake) throws ArgumentException {
        Command c = new Command(CommandType.DirectNoReply);
        c.turnMotorAtPowerForTime(ports, power, ms, brake);
        c.startMotor(ports);
        brick.sendCommand(c);
    }
    
    public void turnMotorAtPowerForTime(OutputPort ports, int power, int rampUp, int ms, int rampDown, boolean brake) throws ArgumentException {
        Command c = new Command(CommandType.DirectNoReply);
        c.turnMotorAtPowerForTime(ports, power, rampUp, ms, rampDown, brake);
        c.startMotor(ports);
        brick.sendCommand(c);
    }
    
    public void stepMotorAtPower(OutputPort ports, int power, int steps, boolean brake) throws ArgumentException {
        Command c = new Command(CommandType.DirectNoReply);
        c.stepMotorAtPower(ports, power, steps, brake);
        c.startMotor(ports);
        brick.sendCommand(c);
    }
    
    public void stepMotorAtPower(OutputPort ports, int power, int rampUpSteps, int constantSteps, int rampDownSteps, boolean brake) throws ArgumentException {
        Command c = new Command(CommandType.DirectNoReply);
        c.stepMotorAtPower(ports, power, rampUpSteps, constantSteps, rampDownSteps, brake);
        c.startMotor(ports);
        brick.sendCommand(c);
    }

    public void stopMotor(OutputPort ports, boolean brake) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectNoReply);
        c.stopMotor(ports, brake);
        brick.sendCommand(c); 
    }
    
    public void readUltrasonic(InputPort port) throws ArgumentException
    {
        if(!isSensorPort(port))
            throw new ArgumentException("The specified port is not a sensor port", "port");
        
        int responseSize = 4;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        c.getTypeMode(port, index, index+1);
        c.readRaw(port, UltrasonicMode.Centimeters.ordinal(), index+2);
        brick.sendCommand(c);
    }
    
    public void readGyroscope(InputPort port) throws ArgumentException
    {
        if(!isSensorPort(port))
            throw new ArgumentException("The specified port is not a sensor port", "port");
        
        int responseSize = 6;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        c.getTypeMode(port, index, index+1);
        c.readRaw(port, GyroscopeMode.Angle.ordinal(), index+2);
        brick.sendCommand(c);        
    }
    
    public void readTachoCount(InputPort port) throws ArgumentException
    {
        if(!isMotorPort(port))
            throw new ArgumentException("The specified port is not a motor port", "port");
        
        int responseSize = 6;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        c.getTypeMode(port, index, index+1);
        c.readRaw(port, MotorMode.Degrees.ordinal(), index);
        brick.sendCommand(c); 
    }
    
    public void stepMotorSync(OutputPort port1, OutputPort port2, int power, int step, boolean brake) throws ArgumentException
    {    
        if(!isMotorPort(port1) || !isMotorPort(port2))
            throw new ArgumentException("The specified port is not a motor port", "port");
        
        Command c = new Command(CommandType.DirectReply, 0, 15);
        c.stepMotorSync(port1, port2, power, step, brake);
        brick.sendCommand(c); 
    }
    
    public void resetSensor(InputPort port, DeviceType device) throws ArgumentException
    {
        int responseSize = 6;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        
        switch(device)
        {
            case Gyroscope:
                c.readRaw(port, GyroscopeMode.Calibrate.get(), index);
                break;

            default:
                throw new ArgumentException("This device can't be rested", "device");
        }
               
        brick.sendCommand(c); 
    }
    
    public void resetMotorCount(OutputPort port) throws ArgumentException
    {
        if(!isMotorPort(port))
            throw new ArgumentException("The specified port is not a motor port", "port");
        
        Command c = new Command(CommandType.DirectNoReply);
        c.resetMotorCount(port);
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
    
    public static boolean isMotorPort(InputPort port){
        return (port == InputPort.A || port == InputPort.B ||
            port == InputPort.C || port == InputPort.D);
    }
    
    public static boolean isMotorPort(OutputPort port){
        return (port == OutputPort.A || port == OutputPort.B ||
            port == OutputPort.C || port == OutputPort.D || port == OutputPort.All);
    }
    
    public static boolean isSensorPort(InputPort port){
        return (port == InputPort.One || port == InputPort.Two ||
            port == InputPort.Three || port == InputPort.Four);
    }
}
