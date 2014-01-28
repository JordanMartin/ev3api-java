package core;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * This class represents a bluetooth communication
 * @author Jordan
 */
public class BluetoothCommunication extends Communication
{

    SerialPort serialPort;

    /**
     * Create a new bluetooth communication at the specified com port
     *
     * @param commPort String of serial port. You must pair the ev3 before and
     *                 look at the name of the output com port to the ev3 (Eg.
     *                 COM3, COM4...)
     */
    public BluetoothCommunication(String commPort)
    {
        serialPort = new SerialPort(commPort);
    }

    @Override
    public boolean open()
    {
        long startTime = System.currentTimeMillis();
        long timeout = 10000;
        
        while(System.currentTimeMillis() < (startTime + timeout) && !serialPort.isOpened())
        {     
            try {

                System.out.println("Port opened: " + serialPort.openPort());
                System.out.println("Params setted: " + serialPort.setParams(SerialPort.BAUDRATE_115200,
                                                                            SerialPort.DATABITS_8,
                                                                            SerialPort.STOPBITS_1,
                                                                            SerialPort.PARITY_NONE));
            } catch (SerialPortException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }        
        
        
        if(serialPort.isOpened()){
            //new Thread(new DataReaderAsync()).start(); // Active wait (bad)
            
            try {
                serialPort.setEventsMask(SerialPort.MASK_RXCHAR); // Listen input data event
                serialPort.addEventListener(new SerialPortEventListener()
                {
                    @Override
                    public void serialEvent(SerialPortEvent serialPortEvent)
                    {
                        try {
                            short msgLength = EndianConverter.swapToShort(serialPort.readBytes(2));
                            byte[] data = serialPort.readBytes(msgLength);
                            dataReceived(data);
                        } catch (SerialPortException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                });
            } catch (SerialPortException e) {
                System.err.println(e.getMessage());
            }
           
        }
        
        return true;
    }

    @Override
    public void close()
    {
        if (serialPort != null && serialPort.isOpened()) {
            try {
                serialPort.purgePort(1);
                serialPort.purgePort(2);
                System.out.println("Port closed: " + serialPort.closePort());
                System.out.println("Wait complete end of connecton");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                }
            } catch (SerialPortException e) {
                System.err.println("Error during disconnection : " + e.getMessage());
            }
        }
    }

    @Override
    public boolean write(byte[] data)
    {
        try {
            return serialPort.writeBytes(data);
        } catch (SerialPortException e) {
            System.err.println("Error to write bytes : " + e.getMessage());
            return false;
        }
    }
    
    /**
     * This method is trigger when data is available on the serial port and fire 
     * 
     * @param data the data received
     */
    private void dataReceived(byte[] data)
    {
        for (byte b : data)
            System.out.print((b & 0xff) + " ");
        System.out.println();
        fireDataReceived(data);
    }

    
    /**
     * @deprecated This is a bad method to wait data
     * This class make an active wait on the serial port
     */
    class DataReaderAsync implements Runnable
    {
        @Override
        public void run()
        {
            boolean error = false;
            
            do{                
                
                try {
                    if (serialPort.getInputBufferBytesCount() > 0) {
                        short msgLength = EndianConverter.swapToShort(serialPort.readBytes(2));
                        byte[] data = serialPort.readBytes(msgLength);
                        

                        ///////// DEBUG ///////
//                        System.out.print("in : ");
//                        for (byte b : data)
//                            System.out.print((b & 0xff) + " ");
//                        System.out.println();
                        // \\\\\\\\ DEBUG\\\\\\
                        
                        dataReceived(data);
                        
                    }else
                        Thread.yield();
                    
                } catch (SerialPortException e) {
                    System.err.println("Read error : " + e.getMessage());
                    error = true;
                }
            }while(!error);
        }     
    }
    
    @Override
    public void finalize() throws Throwable
    {
        super.finalize();
        close();
    }
}
