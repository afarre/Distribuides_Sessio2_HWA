package Sockets.LightWeight;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

public class S_LWA1 extends Thread {
    private Socket socket;
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private final static int OUTGOING_PORT = 44444;

    public S_LWA1(){
        new AnalogueComms().start();
    }

    @Override
    public void run() {
        InetAddress iAddress;
        try {
            iAddress = InetAddress.getLocalHost();
            String IP = iAddress.getHostAddress();

            socket = new Socket(String.valueOf(IP), OUTGOING_PORT);
            doStream = new DataOutputStream(socket.getOutputStream());
            diStream = new DataInputStream(socket.getInputStream());

            while (true){
                try {
                    doStream.writeUTF("test");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (ConnectException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
