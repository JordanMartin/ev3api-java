package ev3.api;

/**
 *
 * @author Jordan Martin & Jonathan Taws
 */
public class ResponseReceivedEventArgs {
    // Byte array of the data received from the EV3 brick.
    byte[] report;

    public byte[] get() {
        return report;
    }

    public void setReport(byte[] report) {
        this.report = report;
    }
}
