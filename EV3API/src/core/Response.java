package core;

import core.EV3Types.*;

/**
 * This class represent a response 
 * @author Jordan
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
    
    public byte[] getData(){
        return data;
    }
}
