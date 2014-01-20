package core;

import core.EV3Types.*;

/**
 *
 * @author Jordan
 */
public class Response {
    
    ReplyType replyType;
    int sequence;
    ManualResetEvent event;
    byte[] data;
    SystemOpcode systemCommand;
    SystemReplyStatus systemReplyStatus;
    
    public Response(int sequence) {
        this.sequence = sequence;
        event = new ManualResetEvent(false);
    }
}
