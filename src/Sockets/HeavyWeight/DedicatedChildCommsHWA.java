package Sockets.HeavyWeight;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DedicatedChildCommsHWA extends Thread{
    private Socket socket;
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private final S_HWA s_hwa;

    public DedicatedChildCommsHWA(Socket socket, S_HWA s_hwa) {
        this.socket = socket;
        this.s_hwa = s_hwa;
    }

    @Override
    public void run() {
        try {
            diStream = new DataInputStream(socket.getInputStream());
            doStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            try {
                String request = diStream.readUTF();
                actOnRequest(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void actOnRequest(String request) {
        switch (request){
            case "test":
                System.out.println("Switch case test in HWA.");
                synchronized (s_hwa){
                    s_hwa.notify();
                }
                break;
        }
    }
}
