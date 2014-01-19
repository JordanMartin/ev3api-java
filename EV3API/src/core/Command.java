package core;

import core.EV3Types.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 *
 * @author Jordan
 */
public class Command {
    
    private ByteBuffer data;
    private CommandType  commandType;
    
    public static void main(String args[]){
        
        //System.out.println(0x82 + " " + Integer.toBinaryString(0x82));
        //System.out.println( ((byte)1 & 0xff) + " " + Integer.toBinaryString((byte)0x82 & 0xff));
//               
//        ByteBuffer a = ByteBuffer.allocateDirect(200);
//        
//        a.put((byte)-80);
//        a.put((byte)5);
//        a.put((byte)5);
//        
//        System.out.println(a.position());
//        byte b = a.get(0);
//        
//        System.out.println(Integer.toBinaryString(0x100 + b).substring(0));
        
        Command c = new Command(CommandType.DirectNoReply);
        System.out.println(c);
        
    }

    
    /**
     * Create a new command
     * @param commandType CommandType The type of the request
     */
    public Command(CommandType commandType){
        this.commandType = commandType;
        initialize();
    }
    
    private void initialize() {
        
        data = ByteBuffer.allocateDirect(200);
        
        // Two first byte for the command length (gets filled in later)
        data.put((byte) 0);
        data.put((byte) 0);
        
        // Two byte for the request identification
        // TODO : Use response class and manage a id        
        data.put((byte) 0);
        data.put((byte) 0);

        // One byte for the type of command
        data.put((byte)commandType.get());
        
        if (commandType == CommandType.DirectReply || commandType == CommandType.DirectNoReply) {
            data.put((byte) 0);
            data.put((byte) 0);
        }
    }
    
    private void addOpcode(Opcode opcode){    
        
        // Write on two bytes if the command is to large
        if(opcode.get() > Opcode.Tst.get())
            data.put((byte)(opcode.get() >> 8));
        
        data.put((byte)opcode.get());
    }
    
    private void addOpcode(SystemOpcode opcode){    
        
        data.put((byte)opcode.get());
    }
    
    /**
     * Add a string parameter to the message
     * @param parameter 
     */
    private void addParameter(String parameter){
        data.put((byte)ArgumentSize.String.get());
        
        byte[] stringBytes = parameter.getBytes(Charset.defaultCharset());
        
        for(byte b : stringBytes)
            data.put(b);
        
        // End of string
        data.put((byte)0x00);
    }

    
    /**
     * Add a paramter to the message.
     * This function cut up the int in byte array
     * @param parameter 
     */
    private void addParameter(int parameter){
        
        // Maximum value of a unsigned byte
        if(parameter <= 225)
            addParameter(new byte[]{(byte)parameter});
        else if(parameter <= 65535)
            addParameter(new byte[]{(byte)(parameter >> 8), (byte)parameter});
        else
            addParameter(new byte[]{
                (byte)(parameter >> 24),
                (byte)(parameter >> 16),
                (byte)(parameter >> 8),
                (byte)parameter
            });
    }
    
    private void addRawParameter(int parameter){
        
    }
    
    /**
     * Add the bytes in the array
     * A byte are added before the parameters to specify the length (1, 2 or 4 bytes)
     * @param parameters 
     */
    private void addParameter(byte[] parameters){
        
        switch(parameters.length){
            
            case 1:
                data.put((byte)ArgumentSize.Byte.get());
                break;
                
            case 2:
                data.put((byte)ArgumentSize.Short.get());
                break;
                
            case 3:
            case 4:
                data.put((byte)ArgumentSize.Int.get());
                break;
        }
        
        for(byte b : parameters)
            data.put((byte)b);        
    }
    
    /**
     * Return all the bytes of the command in byte[]
     * The two first bytes are filled in with the length of the command
     * @return 
     */
    public byte[] toBytes(){
        
        int bytesCount = data.position() - 2;
        
        // little-endian
        data.put(0, (byte) bytesCount);
        data.put(1, (byte) (bytesCount >> 8));

        byte[] ret = new byte[bytesCount + 2];
        
        for(int i = 0; i < bytesCount+2; i++)
            ret[i] = data.get(i);

        return ret;
      }
    
    @Override
    public String toString(){
        String ret = "";
        byte[] bytes = toBytes();
        
        for(byte b : bytes)
            ret += ((b & 0xff)) + "  ";
        
        ret += "\n";
        
        for(byte b : bytes)
            ret += Integer.toBinaryString((b & 0xff)) + "  ";
 
        return ret;
    }
}