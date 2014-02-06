package ev3.api;

import javax.swing.event.EventListenerList;

/**
 * This abstract class contains methods and the listener use for the communication
 * @author Jordan
 */
public abstract class Communication
{
    private final EventListenerList listeners = new EventListenerList();
    
    abstract boolean open();
    abstract void close();
    abstract boolean write(byte[] data);
    
    /**
     * Add a new listener
     * @param listener 
     */
    public void addDataReceivedListener(DataReceivedListener listener) {
        listeners.add(DataReceivedListener.class, listener);
    }
    
    /**
     * Fire new data to all listener
     * @param data 
     */
    public void fireDataReceived(byte[] data) {
        for(DataReceivedListener listener : getDataListeners()) {
            listener.dataReceived(data);
        }
    }
   
    
    public DataReceivedListener[] getDataListeners() {
        return listeners.getListeners(DataReceivedListener.class);
    }
}
