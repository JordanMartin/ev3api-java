
package core;

import java.util.Arrays;

/**
 *
 * @author Jordan
 */
public class SensorDataConverter
{
    public static int toInt(byte[] data, int firstByteIndex, int lastByteIndex)
    {        
        if(firstByteIndex > lastByteIndex || lastByteIndex >= data.length)
            return -1;
        
        lastByteIndex++;
        
        byte[] bytes = Arrays.copyOfRange(data, firstByteIndex, lastByteIndex); 
        
        if(bytes.length == 1)
            return bytes[0] & 0xff;
        if(bytes.length == 2)
            return EndianConverter.swapToShort(bytes);
        else
            return EndianConverter.swapToInt(bytes);
    }     
}
