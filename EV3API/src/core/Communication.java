package core;

import javax.swing.event.EventListenerList;

/**
 *
 * @author Jordan
 */
public abstract class Communication
{
    private final EventListenerList listeners = new EventListenerList();
    
    abstract boolean open();
    abstract void close();
    abstract boolean write(byte[] data);
    
    public void addDataReceivedListener(DataReceivedListener listener) {
        listeners.add(DataReceivedListener.class, listener);
    }
    
    public void fireDataReceived(byte[] data) {
        for(DataReceivedListener listener : getDataListeners()) {
            listener.dataReceived(data);
        }
    }
    
    public DataReceivedListener[] getDataListeners() {
        return listeners.getListeners(DataReceivedListener.class);
    }
}
