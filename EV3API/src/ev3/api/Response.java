package ev3.api;

import ev3.api.EV3Types.*;

/**
 * This class represents a response 
 * @author Jordan Martin & Jonathan Taws
 */
public class Response {
    
    ReplyType replyType = ReplyType.DirectReplyError;
    int sequence;
    ManualResetEvent event;
    byte[] data;
    SystemOpcode systemCommand = SystemOpcode.BeginDownload;
    SystemReplyStatus systemReplyStatus = SystemReplyStatus.UnknownError;
    
    public Response(int sequence) {
        this.sequence = sequence;
        event = new ManualResetEvent(false);
    }
    
    /**
     * Return data of the response
     * @return 
     */
    public byte[] getData(){
        return data;
    }
}
