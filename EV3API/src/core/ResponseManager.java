package core;

import core.EV3Types.ReplyType;
import core.EV3Types.SystemOpcode;
import core.EV3Types.SystemReplyStatus;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jordan
 */
public class ResponseManager 
{    
    private static int nextSequence = 0x0001;
    private static final Map<Integer, Response> responses = new HashMap<>();
    
    
    private static int getSequenceNumber() {
        if(nextSequence == 0xFFFF)
            ResponseManager.nextSequence++;
        
        return nextSequence++;
    }
    
    public static Response createResponse() {
        int sequence = getSequenceNumber();
        
        Response r = new Response(sequence);
        responses.put(sequence, r);
        return r;
    }
    
    public static void waitForResponse(Response r)
    {
        new waitResponseThread(r).start();
    }
    
    public static void HandleResponse(byte[] report) {
        if(report == null || report.length < 3) {
            return;
        }
        
        int sequence = (int) (report[0] | (report[1] << 8)); // << bit shifting of 8 to the left
        int replyType = report[2];
        
        if(sequence > 0) {
            Response r = responses.get(sequence);
            
            if(ReplyType.isMember(replyType)) 
                r.replyType.set(replyType);
            
            if(r.replyType == ReplyType.DirectReply || r.replyType == ReplyType.DirectReplyError) {
                r.data = new byte[report.length - 3];
                System.arraycopy(report, 3, r.data, 0, report.length - 3);
            }
            else if(r.replyType == ReplyType.SystemReply || r.replyType == ReplyType.SystemReplyError) {
                if(SystemOpcode.isMember(report[3]))
                  r.systemCommand.set(report[3]);
                
                if(SystemReplyStatus.isMember(report[4]))
                    r.systemReplyStatus.set(report[4]);
                
                r.data = new byte[report.length - 3];
                System.arraycopy(report, 5, r.data, 0, report.length - 5);
            }
            
            r.event.set();
        }        
    }
    
    static class waitResponseThread extends Thread
    {
        private final Response response;
        
        public waitResponseThread(Response r)
        {
            response = r;
        }
        
        @Override
        public void run()
        {
            try {
                if(response.event.waitOne(1000))
                    responses.remove(response.sequence);
                else
                    response.replyType = ReplyType.DirectReplyError;
                
            } catch (InterruptedException e) {}
        }
    }
}
