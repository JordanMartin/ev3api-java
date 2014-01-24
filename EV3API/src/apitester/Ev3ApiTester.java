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

        boolean next = true;        
        
        while(next)
        {          
            
            switch(s.nextLine().toLowerCase())
            {
                case "c":
                    ev3.directCommand.resetMotorCount(OutputPort.All);
                    ev3.directCommand.resetGyroscope(InputPort.Two);
                    break;
                    
                case "m":
                    ev3.directCommand.stepMotorSync(OutputPort.A, OutputPort.D, 50, 720, true);
                    break;
                    
                case "s":
                    ev3.directCommand.stopMotor(OutputPort.All, false);
                    break;
                    
                case "q": next = false; break;
                    
                default:
                    System.out.println(ev3.directCommand.readTachoCount(InputPort.A));
                    System.out.println(ev3.directCommand.readTachoCount(InputPort.D));
                    System.out.println(ev3.directCommand.readUltrasonic(InputPort.Four));
                    System.out.println(ev3.directCommand.readGyroscope(InputPort.Two));
            }
        }

        ev3.directCommand.stopMotor(OutputPort.All, false);
        ev3.disconnect();
    }
    
}
