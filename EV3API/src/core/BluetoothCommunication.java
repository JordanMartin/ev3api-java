package core;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author Jordan
 */
public class BluetoothCommunication extends Communication
{

    SerialPort serialPort;
    
    
    
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
            new Thread(new DataReaderAsync()).start();
           
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
    
    
    private void dataReceived(byte[] data)
    {
        int messageSize = EndianConverter.swapToShort(new byte[]{data[0], data[1]});
//        System.out.println("Message size : " + messageSize + "\n");
        fireDataReceived(data);
    }

    
    /**
     * 
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
                        dataReceived(data);

                        ///////// DEBUG ///////
                        System.out.print("in : ");
                        for (byte b : data)
                            System.out.print((b & 0xff) + " ");
                        System.out.println();
                        // \\\\\\\\ DEBUG\\\\\\
                        
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
