package apitester;

import core.ArgumentException;
import core.BluetoothCommunication;
import core.Brick;
import core.Command;
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
                    
                case "b":
                    ev3.batchCommand = new Command(CommandType.DirectReply, 13, 0);
                    ev3.batchCommand.readRaw(InputPort.A, MotorMode.Degrees.ordinal(), 0);
                    ev3.batchCommand.readRaw(InputPort.D, MotorMode.Degrees.ordinal(), 4);
                    ev3.batchCommand.readRaw(InputPort.Four, 0, 8);
                    ev3.batchCommand.readRaw(InputPort.Two, 0, 12);
                    ev3.sendBatchCommand();
                  
                    for(byte b : ev3.batchCommand.response.getData())
                        System.out.print((b & 0xff) + " ");
                    
                    break;
                    
                case "q": next = false; break;
                    
                case "t":
                    ev3.directCommand.playTone(20, 200, 200);
                    Thread.sleep(200);
                    ev3.directCommand.playTone(20, 500, 200);
                    Thread.sleep(200);
                    ev3.directCommand.playTone(20, 100, 200);
                    Thread.sleep(200);
                    ev3.directCommand.playTone(20, 900, 200);
                    Thread.sleep(200);
                    ev3.directCommand.playTone(20, 1000, 200);
                    Thread.sleep(200);
                    ev3.directCommand.playTone(20, 300, 200);
                    Thread.sleep(200);
                    ev3.directCommand.playTone(20, 700, 200);
                    break;
                    
                default:
                    System.out.println("Tacho left  : " + ev3.directCommand.readTachoCount(InputPort.A));
                    System.out.println("Tacho right : " + ev3.directCommand.readTachoCount(InputPort.D));
                    System.out.println("Ultrasonic  : " + ev3.directCommand.readUltrasonic(InputPort.Four));
                    System.out.println("      Gyro  : " + ev3.directCommand.readGyroscope(InputPort.Two));

                    
                    System.out.println();
            }
        }

        ev3.directCommand.stopMotor(OutputPort.All, false);
        ev3.disconnect();
    }
    
}
