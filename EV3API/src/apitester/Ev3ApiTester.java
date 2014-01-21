package apitester;

import core.ArgumentException;
import core.BluetoothCommunication;
import core.Brick;
import core.EV3Types.*;
import java.util.Scanner;

/**
 *
 * @author Jordan
 */
public class Ev3ApiTester {

    public static void main(String[] args) throws ArgumentException, InterruptedException {
              
        Brick ev3 = new Brick(new BluetoothCommunication("COM5"));
        ev3.connect();
        
        Scanner s = new Scanner(System.in);
        
        do{
            ev3.directCommand.readTachoCount(InputPort.B);
        }while(!s.nextLine().equals("q"));
        
        
//        ev3.directCommand.readUltrasonic(InputPort.Four);
//        ev3.directCommand.readGyroscope(InputPort.Two);
        
        Thread.sleep(1000);    
        
        ev3.disconnect();
    }
    
}
