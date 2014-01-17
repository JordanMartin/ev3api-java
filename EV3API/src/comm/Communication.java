package comm;

import java.io.IOException;
import jssc.*;

/**
 *
 * @author Jordan
 */
public class Communication {

    public static void main(String args[]) throws Exception {
        
        SerialPort serialPort = new SerialPort("COM5");
        
        try {
            System.out.println("Port opened: " + serialPort.openPort());
            System.out.println("Params setted: " + serialPort.setParams(115200, 8, 1, 0));
            serialPort.writeBytes(new byte[]{(byte)17,
                                             (byte)0,
                                             (byte)68,
                                             (byte)0,
                                             (byte)128,
                                             (byte)0,
                                             (byte)0,
                                             (byte)164,
                                             (byte)129,
                                             (byte)0,
                                             (byte)129,
                                             (byte)4,
                                             (byte)129,
                                             (byte)50,
                                             (byte)129,
                                             (byte)0,
                                             (byte)129,
                                             (byte)4                                             
            });
            
            int byteCount = 0;
            
             try {
                do{
                    byte[] bytes = serialPort.readBytes(byteCount);
                    
                    if(byteCount > 0){
                        for(byte b : bytes)
                            System.out.print(b);
                    }                                     
                }while(true);
                
            }catch(Exception e){
                
            }
             
            System.out.println("Port closed: " + serialPort.closePort());
        }
        catch (SerialPortException ex){
            System.out.println(ex);
        }
    }
    

/*
    /**
     *      
    public static class SerialWriter implements Runnable {

        OutputStream out;

        public SerialWriter(OutputStream out) {
            this.out = out;
        }

        public void run() {
            try {
                int c = 0;
                while ((c = System.in.read()) > -1) {
                    this.out.write(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void listPorts() {
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            System.out.println(portIdentifier.getName() + " - " + getPortTypeName(portIdentifier.getPortType()));
        }
    }

    static String getPortTypeName(int portType) {
        switch (portType) {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }
*/
}
