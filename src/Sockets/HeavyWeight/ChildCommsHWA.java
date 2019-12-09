package Sockets.HeavyWeight;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChildCommsHWA extends Thread {
    private final static int OUTGOING_PORT_LWA1 = 44444;
    private final static int OUTGOING_PORT_LWA2 = 44445;
    private final static int OUTGOING_PORT_LWA3 = 44446;
    private final static int INCOME_PORT = 44444;

    private S_HWA s_hwa;

    public ChildCommsHWA(S_HWA s_hwa) {
        this.s_hwa = s_hwa;
    }

    @Override
    public void run() {
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(INCOME_PORT);
            //esperem a la conexio del HeavyWeight_B
            Socket socket = serverSocket.accept();
            newDedicatedChildComms(socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newDedicatedChildComms(Socket socket) {
        new DedicatedChildCommsHWA(socket, s_hwa).start();
    }

}
