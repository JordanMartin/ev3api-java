package core;

import core.EV3Types.*;


/**
 *
 * @author Jordan
 */
public class Brick {
    
    private Communication comm = null;
    public final DirectCommand directCommand;

    /**
     *
     * @param comm
     */
    public Brick(Communication comm){
        this.comm = comm;
        directCommand = new DirectCommand(this);
    }
    
    public void sendCommand(Command c)
    {
        System.out.println("out : " + c);
        comm.write(c.toBytes());
        
        if(c.commandType == CommandType.DirectReply || c.commandType == CommandType.SystemReply)
            ResponseManager.waitForResponse(c.response);
    }

    public void connect()
    {
        comm.open();
    }   
    
    public void disconnect()
    {
        comm.close();
    }
}
