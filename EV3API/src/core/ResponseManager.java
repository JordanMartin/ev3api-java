package core;

import core.EV3Types.ReplyType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jordan
 */
public class ResponseManager {
    private static int nextSequence = 0x0001;
    private static Map<Integer, Response> responses = new HashMap<>();
    
    public ResponseManager() {
        
    }
    
    private static int getSequenceNumber() {
        if(nextSequence == 0xFFFF)
            ResponseManager.nextSequence++;
        return nextSequence;
    }
    
    public static Response CreateResponse() {
        int sequence = getSequenceNumber();
        
        Response r = new Response(sequence);
        responses.put(sequence, r);
        return r;
    }
    
    public static void HandleResponse(byte[] report) {
        if(report == null || report.length < 3) {
            return;
        }
        
        int sequence = (int) (report[0] | (report[1] << 8)); // << bit shifting of 8 to the left
        int replyType = report[2];
        
        if(sequence > 0) {
            Response r = responses.get(sequence);
            
            if(ReplyType.isMember(replyType)) {
                r.ReplyType = (ReplyType) replyType;
            }
        }
        
    }
}
