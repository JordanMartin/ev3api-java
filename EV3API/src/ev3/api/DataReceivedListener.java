package ev3.api;

import java.util.EventListener;

/**
 *
 * @author Jonathan
 */
public interface DataReceivedListener extends EventListener {
    public void dataReceived(byte[] data);
}
