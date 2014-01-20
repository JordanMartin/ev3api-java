package core;


/**
 *
 * @author Jordan
 */
public class Brick {
    
    Communication comm = null;

    public Brick(Communication comm){
        this.comm = comm;
    }
    
    public void sendCommand(Command c){
        comm.write(c.toBytes());
    }
}
