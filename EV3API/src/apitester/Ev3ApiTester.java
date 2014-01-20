package apitester;

import core.ArgumentException;
import core.BluetoothCommunication;
import core.Brick;
import core.Command;
import core.EV3Types.*;

/**
 *
 * @author Jordan
 */
public class Ev3ApiTester {

    public static void main(String[] args) throws ArgumentException, InterruptedException {
              
        Brick ev3 = new Brick(new BluetoothCommunication("COM6"));
        ev3.connect();
        
        
//        ev3.directCommand.turnMotorAtPower(OutputPort.C, 50);
//        ev3.directCommand.turnMotorAtPower(OutputPort.B, 50);
        
//        Thread.sleep(2000);        
//        ev3.directCommand.stopMotor(OutputPort.All, false);

        
    }
    
}
