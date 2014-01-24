package core;


import core.EV3Types.*;


/**
 *
 * @author Jordan
 */
public class Brick {
    
    private Communication comm = null;
    public final DirectCommand directCommand;
    public Command batchCommand;

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
//        System.out.println("out : " + c);
        comm.write(c.toBytes());
        
        if(c.commandType == CommandType.DirectReply || c.commandType == CommandType.SystemReply)
            ResponseManager.listenForResponse(c.response, true);
    }
    
    public void sendBatchCommand()
    {
        if (batchCommand != null) {
            sendCommand(batchCommand);
        } else
            System.err.println("The batch command is not set");
    }

    public boolean connect()
    {
        if(comm.open()) {
            this.comm.addDataReceivedListener(new DataReceivedListener() {

                @Override
                public void dataReceived(byte[] data) {
                    ResponseManager.handleResponse(data);
                }
            });
            return true;
        }
        return false;
    }
    
    public void disconnect()
    {
        comm.close();
    }
}
