package Sockets.LightWeight;

public class LamportRequest {
    private long timeStamp;
    private String process;

    public LamportRequest(long timeStamp, String process){
        this.timeStamp = timeStamp;
        this.process = process;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getProcess() {
        return process;
    }
}
