package core;

import java.util.List;

/**
 *
 * @author Jordan
 */
public class Command {
    
    private List<Byte> data;
    private int  commandType;
    
    /**
     * Create a new command
     * @param commandType EV3Types.CommandType The type of the request
     */
    public void Command(int commandType){
        this.commandType = commandType;
        initialize();
    }
    
    private void initialize() {

        // Two first byte for the command length (gets filled in later)
        data.add((byte) 0);
        data.add((byte) 0);

        // Two byte for the request identification
        // TODO : Use response class and manage a id        
        data.add((byte) 0);
        data.add((byte) 0);

        // One byte for the type of command
        data.add((byte) commandType);

        if (commandType == EV3Types.CommandType.DirectReply || commandType == EV3Types.CommandType.DirectNoReply) {
            data.add((byte) 0);
            data.add((byte) 0);
        }
    }
    
    private void addOpcode(int opcode){    
        
        // Write on two bytes if the command if to large
        if(opcode > EV3Types.Opcode.Tst)
            data.add((byte)(opcode >> 8));
        
        data.add((byte)opcode);
    }
}