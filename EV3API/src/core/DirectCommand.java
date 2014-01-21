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
        
        int responseSize = 6;
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
    
    public void getTypeMode(InputPort port) throws ArgumentException
    {
        int responseSize = 6;
        int index = 0;
        
        Command c = new Command(CommandType.DirectReply, responseSize, 0);
        c.getTypeMode(port, index, index+1);
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
        c.readRaw(port, MotorMode.Degrees.ordinal(), index+2);
        brick.sendCommand(c); 
    }
    
    public static boolean isMotorPort(InputPort port){
        return (port == InputPort.A || port == InputPort.B ||
            port == InputPort.C || port == InputPort.D);
    }
    
    public static boolean isSensorPort(InputPort port){
        return (port == InputPort.One || port == InputPort.Two ||
            port == InputPort.Three || port == InputPort.Four);
    }
}
