package core;

import core.EV3Types.*;

/**
 *
 * @author Jordan
 */
public class DirectCommand {
    
    private final Brick brick;
    private final int responseSize = 11;

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
        Command c = new Command(CommandType.DirectReply, 100, 0);
        //c.getTypeMode(port, 0, 1);
        c.readRaw(port, DeviceType.Ultrasonic.get(), 0);
        brick.sendCommand(c);
    }
    
    public void readGyroscope(InputPort port) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectReply, 100, 0);
        //c.getTypeMode(port, 0, 1);
        c.readRaw(port, DeviceType.Gyroscope.get(), 0);
        brick.sendCommand(c);
        
    }
}
