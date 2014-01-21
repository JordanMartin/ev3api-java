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
        Command c = new Command(CommandType.DirectReply, 30, 0);
        c.getTypeMode(port, 0, 1);
        c.readRaw(port, DeviceType.Ultrasonic.get(), 3);
        brick.sendCommand(c);
    }
    
    public void readGyroscope(InputPort port) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectReply, 15, 0);
        c.getTypeMode(port, 0, 1);
        c.readRaw(port, DeviceType.Gyroscope.get(), 3);
        brick.sendCommand(c);        
    }
}
