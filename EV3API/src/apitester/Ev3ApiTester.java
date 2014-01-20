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
        
        BluetoothCommunication comm = new BluetoothCommunication("COM6");
        comm.open();
        
        Brick ev3 = new Brick(comm);
        ev3.directCommand.stepMotorSync(OutputPort.B, 50, 50, 360, false);
        ev3.directCommand.turnMotorAtPower(OutputPort.C, 50);
        
        Thread.sleep(2000);        
        ev3.directCommand.stopMotor(OutputPort.All, false);

        
    }
    
}
