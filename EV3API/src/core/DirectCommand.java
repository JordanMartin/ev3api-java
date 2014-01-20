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
        Command c = new Command(CommandType.DirectReply);
        c.turnMotorAtPower(ports, power);
        c.startMotor(ports);        
        brick.sendCommand(c);
    }
    
    public void turnMotorAtSpeed(OutputPort ports, int speed) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectReply);
        c.turnMotorAtSpeed(ports, speed);
        c.startMotor(ports);        
        brick.sendCommand(c);
    }

    public void stopMotor(OutputPort ports, boolean brake)
    {
        Command c = new Command(CommandType.DirectReply);
        c.stopMotor(ports, brake);
        brick.sendCommand(c); 
    }
    
    /**
     * DONT USE FOR THE TIME - IT MAKE THE BRICK BUG
     * @param ports
     * @param speed
     * @param turnRatio
     * @param step
     * @param brake
     * @throws ArgumentException 
     */
    public void stepMotorSync(OutputPort ports, int speed, int turnRatio, int step, boolean brake) throws ArgumentException
    {
        Command c = new Command(CommandType.DirectReply);
        c.stepMotorSync(ports, speed, turnRatio, step, brake);
        c.startMotor(ports);  
        brick.sendCommand(c); 
    }
}
