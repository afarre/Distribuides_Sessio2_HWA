package Sockets.LightWeight;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DedicatedLWA1 extends Thread {
    private Socket socket;
    private DataInputStream diStream;
    private DataOutputStream doStream;

    public DedicatedLWA1(Socket socket) {
        this.socket = socket;
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
            case "test2":
                System.out.println("Switch case test.");
                break;
        }
    }
}
