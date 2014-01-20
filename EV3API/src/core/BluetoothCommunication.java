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
    public void open()
    {
        try {
            
            System.out.println("Port opened: " + serialPort.openPort());
            System.out.println("Params setted: " + serialPort.setParams(SerialPort.BAUDRATE_115200,
                                                                        SerialPort.DATABITS_8,
                                                                        SerialPort.STOPBITS_1,
                                                                        SerialPort.PARITY_NONE));
        } catch (SerialPortException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close()
    {
        if (serialPort != null && serialPort.isOpened()) {
            try {
                serialPort.purgePort(1);
                serialPort.purgePort(2);
                System.out.println("Port closed: " + serialPort.closePort());
            } catch (SerialPortException e) {
                System.err.println("Error during disconnection : " + e.getMessage());
            }
        }
    }

    @Override
    public void write(byte[] data)
    {
        try {
            serialPort.writeBytes(data);

            for (byte b : data)
                System.out.print((b & 0xff) + " ");

            System.out.print("\n");

        } catch (SerialPortException e) {
            System.err.println("Error to write bytes : " + e.getMessage());
        }
    }

    @Override
    public void finalize() throws Throwable
    {
        super.finalize();
        close();
    }
}
