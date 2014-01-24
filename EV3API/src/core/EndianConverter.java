
package core;

/**
 *
 * @author Jordan
 */
public class EndianConverter
{
    public static void main(String args[]){
        System.out.println(EndianConverter.getShort(new byte[]{(byte)0x61, (byte)0x0, (byte)0, (byte) 0}));
    }

    public static int swapToInt(int number)
    {
        return ((number >> 24) & 0xff)
            + (((number >> 16) & 0xff) << 8)
            + (((number >> 8) & 0xff) << 16)
            + (((number) & 0xff) << 24);
    }

    public static short swapToShort(short number)
    {
        return (short) (((number >> 8) & 0xff) + (((number) & 0xff) << 8));
    }

    public static int swapToInt(byte[] bytes)
    {
        if(bytes.length < 4)
            return -1;
        
        return (bytes[0] & 0xff)
            + ((bytes[1] & 0xff) << 8)
            + ((bytes[2] & 0xff) << 16)
            + ((bytes[3] & 0xff) << 24);        
    }

    public static short swapToShort(byte[] bytes)
    {
        if(bytes.length < 2)
            return -1;
        
        return (short)((bytes[0] & 0xff) + ((bytes[1] & 0xff) << 8));
    }  
    
    /**
     * Get the current short representation of the two bytes
     * @param bytes
     * @return 
     */
    public static short getShort(byte[] bytes)
    {
        if(bytes.length < 2)
            return -1;
        
        return (short)((bytes[1] & 0xff) + ((bytes[0] & 0xff) << 8));
    }
    
    /**
     * Get the current int representation of the four bytes
     * @param bytes
     * @return 
     */
    public static int getInt(byte[] bytes)
    {
        if(bytes.length < 2)
            return -1;
        
        return (bytes[3] & 0xff)
            + ((bytes[2] & 0xff) << 8)
            + ((bytes[1] & 0xff) << 16)
            + ((bytes[0] & 0xff) << 24); 
    }
    
}
