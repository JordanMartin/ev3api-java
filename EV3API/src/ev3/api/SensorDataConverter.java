package ev3.api;

import java.util.Arrays;
import ev3.api.EV3Types.*;

/**
 * This class allows the conversion of the bytes received into a number
 * @author Jordan Martin & Jonathan Taws
 */
public class SensorDataConverter
{
    /**
     * Return the int value of the n bytes between firstByteIndex and lastByteIndex (inclusive)
     * These bytes must be in little endian representation
     * @param data
     * @param firstByteIndex
     * @param lastByteIndex
     * @return 
     */
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
    
    /**
     * Get the right value depending on the type of the sensor.
     * The first and second byte must contain the type and the mode given by the command getTypeMode
     * 
     * @param data
     * @param firstByteIndex
     * @param lastByteIndex
     * @return 
     */
    public static double getValue(byte[] data, int firstByteIndex, int lastByteIndex)
    {
       if(data == null)
           return -1;
       
        if((firstByteIndex + 2) > lastByteIndex || lastByteIndex >= data.length)
            return -1;
        
        int type = data[firstByteIndex];
        int mode = data[firstByteIndex+1];
        
        firstByteIndex += 2;

        int value = toInt(data, firstByteIndex, lastByteIndex);
        
        // EV3 ultrasonic return a value in mm so we convert to cm
        if(type == DeviceType.Ultrasonic.get())
            return value / 10.0;
        else
            return value;
        
        /*
        if(type == DeviceType.NxtTouch.get())
        {
            return value;
        }
        else if(type == DeviceType.NxtLight.get())
        {
            return value;
        }
        else if(type == DeviceType.NxtSound.get())
        {
            return value;
        }
        else if(type == DeviceType.NxtColor.get())
        {
            return value;
        }
        else if(type == DeviceType.NxtUltrasonic.get())
        {
            return value;
        }
        else if(type == DeviceType.NxtTemperature.get())
        {
            return value;
        }
        else if(type == DeviceType.LMotor.get())
        {
            return value;
        }
        else if(type == DeviceType.MMotor.get())
        {
            return value;
        }
        else if(type == DeviceType.NxtLight.get())
        {
            return value;
        }
        else if(type == DeviceType.Touch.get())
        {
            return value;
        }
        else if(type == DeviceType.Color.get())
        {
            return value;
        }
        else if(type == DeviceType.Ultrasonic.get())
        {
            return value / 10.0;
        }
        else if(type == DeviceType.Gyroscope.get())
        {
            return value;
        }
        else if(type == DeviceType.Infrared.get())
        {
            return value;
        }
        else if(type == DeviceType.Initializing.get())
        {
            return value;
        }
        else if(type == DeviceType.Empty.get())
        {
            return -1;
        }
        else if(type == DeviceType.WrongPort.get())
        {
            return -1;
        }
        else if(type == DeviceType.Unknown.get())
        {
            return -1;
        }
        */
        
    }
}
