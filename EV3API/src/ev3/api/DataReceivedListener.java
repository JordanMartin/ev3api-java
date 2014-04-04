package ev3.api;

import java.util.EventListener;

/**
 *
 * @author Jordan Martin & Jonathan Taws
 */
public interface DataReceivedListener extends EventListener {
    public void dataReceived(byte[] data);
}
