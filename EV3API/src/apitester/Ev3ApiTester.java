package apitester;

import core.ArgumentException;
import core.BluetoothCommunication;
import core.Brick;
import core.Command;
import core.EV3Types.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jordan
 */
public class Ev3ApiTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ArgumentException {
        BluetoothCommunication comm = new BluetoothCommunication("COM8");
        comm.open();
        Brick ev3 = new Brick(comm);
        Command c = new Command(CommandType.DirectNoReply);
        c.turnMotorAtPower(OutputPort.A, 50);
        ev3.sendCommand(c);
        
    }
    
}
