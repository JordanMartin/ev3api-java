package core;


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
    
    public void sendCommand(Command c){
        comm.write(c.toBytes());
    }
}
