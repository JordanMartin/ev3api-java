package core;

import core.EV3Types.ReplyType;
import core.EV3Types.SystemOpcode;
import core.EV3Types.SystemReplyStatus;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class allow the managment of the response : - Manage the id of
 * command/response - Wait a response
 *
 * @author Jordan
 */
public class ResponseManager
{

    private static int nextSequence = 0x0001;
    private static final Map<Integer, Response> responses = new HashMap<>();

    /**
     * Get the next unique id
     *
     * @return the unique id
     */
    private static int getSequenceNumber()
    {
        if (nextSequence == 0xFFFF)
            ResponseManager.nextSequence++;

        return nextSequence++;
    }

    /**
     * Create a new reponse
     *
     * @return the created response
     */
    public static Response createResponse()
    {
        int sequence = getSequenceNumber();

        Response r = new Response(sequence);
        responses.put(sequence, r);
        return r;
    }

    /**
     * Listen the reception of the response
     *
     * @param response     The response to wait
     * @param waitReceived if true this call is locking until the response is
     *                     not received. Else the function return directly and
     *                     the response is received asynchronously
     */
    public static void listenForResponse(Response response, boolean waitReceived)
    {
        if (waitReceived)
            try {
                if (response.event.waitOne(1000))
                    responses.remove(response.sequence);
                else
                    response.replyType = ReplyType.DirectReplyError;
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        else
            new waitResponseThreadAsync(response).start();
    }

    /**
     * This function is call when data arrived
     * @param report the data received
     */
    public static void handleResponse(byte[] report)
    {
        if (report == null || report.length < 3)
            return;

        int sequence = EndianConverter.getShort(new byte[]{report[0], report[1]});

        int replyType = report[2] & 0xff;

        if (sequence > 0) {
            Response r = responses.get(sequence);

            if (ReplyType.isMember(replyType))
                r.replyType.set(replyType);

            if (r.replyType == ReplyType.DirectReply || r.replyType == ReplyType.DirectReplyError)
                r.data = Arrays.copyOfRange(report, 3, report.length);
            else if (r.replyType == ReplyType.SystemReply || r.replyType == ReplyType.SystemReplyError) {
                if (SystemOpcode.isMember(report[3] & 0xff))
                    r.systemCommand.set(report[3] & 0xff);

                if (SystemReplyStatus.isMember(report[4] & 0xff))
                    r.systemReplyStatus.set(report[4] & 0xff);
            }

            r.event.set();
        }
    }

    /**
     * This class allow the asynchrone reception of a response
     */
    static class waitResponseThreadAsync extends Thread
    {

        private final Response response;

        public waitResponseThreadAsync(Response r)
        {
            response = r;
        }

        @Override
        public void run()
        {
            try {
                if (response.event.waitOne(1000))
                    responses.remove(response.sequence);
                else
                    response.replyType = ReplyType.DirectReplyError;

            } catch (InterruptedException e) {
            }
        }
    }
}
