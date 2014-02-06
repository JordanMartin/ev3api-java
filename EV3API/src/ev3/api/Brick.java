package ev3.api;

import ev3.api.EV3Types.*;

/**
 *
 * @author Jordan
 */
public class Brick
{

    private Communication comm = null;
    public final DirectCommand directCommand;
    public Command batchCommand;

    /**
     * Create a new brick with the specified communication (only bluetooth
     * avaialble for now)
     *
     * @param comm
     */
    public Brick(Communication comm)
    {
        this.comm = comm;
        directCommand = new DirectCommand(this);
    }

    /**
     * Send the specified command
     *
     * @param c the command to send
     * @param waitResponse
     */
    public void sendCommand(Command c, boolean waitResponse)
    {
        comm.write(c.toBytes());

        if (c.commandType == CommandType.DirectReply || c.commandType == CommandType.SystemReply)
            ResponseManager.listenForResponse(c.response, waitResponse);
    }
    
    
    public void sendCommand(Command c)
    {
        sendCommand(c, true);
    }

    /**
     * Send the batchCommand Before send this command you must instanciate the
     * attribute batchCommand with a new command and add commands
     * @param waitResponse
     */
    public void sendBatchCommand(boolean waitResponse)
    {
        if (batchCommand != null)
            sendCommand(batchCommand, waitResponse);
        else
            System.err.println("The batch command is not set");
    }
    
    public void sendBatchCommand()
    {
        sendBatchCommand(true);
    }


    /**
     * Connect to the ev3
     *
     * @return true if connection success
     */
    public boolean connect()
    {
        if (comm.open()) {
            // Listen the reception of data
            this.comm.addDataReceivedListener(new DataReceivedListener()
            {
                @Override
                public void dataReceived(byte[] data)
                {
                    ResponseManager.handleResponse(data);
                }
            });

            return true;
        }
        return false;
    }

    /**
     * Close the connection between the ev3 and the computer
     */
    public void disconnect()
    {
        comm.close();
    }
}
