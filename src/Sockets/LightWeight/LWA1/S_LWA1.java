package Sockets.LightWeight.LWA1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

public class S_LWA1 extends Thread {
    private final static int OUTGOING_HWA_PORT = 44444;
    private final static int OUTGOING_LWA2_PORT = 55556;
    private final static int OUTGOING_LWA3_PORT = 55557;
    public final static String TMSTP = "TIME_STAMP_LWA1";

    private Socket socketHWA;
    private DataInputStream diStreamHWA;
    private DataOutputStream doStreamHWA;

    private Socket socketLWA2;
    private DataInputStream diStreamLWA2;
    private DataOutputStream doStreamLWA2;

    private Socket socketLWA3;
    private DataInputStream diStreamLWA3;
    private DataOutputStream doStreamLWA3;

    private AnalogueCommsLWA1 analogueCommsLWA1;

    public S_LWA1(){
        analogueCommsLWA1 = new AnalogueCommsLWA1(this);
        analogueCommsLWA1.start();
    }

    @Override
    public synchronized void run() {
        try {
            createSockets();
            wait();

            while (true){
                doStreamLWA2.writeUTF(TMSTP);
                doStreamLWA3.writeUTF(TMSTP);

                long time = new java.util.Date().getTime();
                doStreamLWA2.writeLong(time);
                analogueCommsLWA1.addToQueue(time, TMSTP);
                doStreamLWA3.writeLong(time);
                analogueCommsLWA1.addToQueue(time, TMSTP);

                System.out.println("Wrote to LWA2 and LWA3 from LWA1");

                long LWA2Timestamp = diStreamLWA2.readLong();
                long LWA3Timestamp = diStreamLWA3.readLong();

                System.out.println("Received in LWA1: " + LWA2Timestamp);
                System.out.println("Received in LWA1: " + LWA3Timestamp);

                if (LWA2Timestamp > time && LWA3Timestamp > time && analogueCommsLWA1.peekQueue().equals(TMSTP)){
                    System.out.println("Got both ACKs in LWA1");
                    break;
                }
            }

        } catch (ConnectException ignored) {
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createSockets() throws IOException {
        InetAddress iAddress = InetAddress.getLocalHost();
        String IP = iAddress.getHostAddress();

        socketHWA = new Socket(String.valueOf(IP), OUTGOING_HWA_PORT);
        doStreamHWA = new DataOutputStream(socketHWA.getOutputStream());
        diStreamHWA = new DataInputStream(socketHWA.getInputStream());

        socketLWA2 = new Socket(String.valueOf(IP), OUTGOING_LWA2_PORT);
        doStreamLWA2 = new DataOutputStream(socketLWA2.getOutputStream());
        diStreamLWA2 = new DataInputStream(socketLWA2.getInputStream());

        socketLWA3 = new Socket(String.valueOf(IP), OUTGOING_LWA3_PORT);
        doStreamLWA3 = new DataOutputStream(socketLWA3.getOutputStream());
        diStreamLWA3 = new DataInputStream(socketLWA3.getInputStream());

    }

    public void notifyDone() throws IOException {
        doStreamHWA.writeUTF("LWA1_DONE");
    }

}
