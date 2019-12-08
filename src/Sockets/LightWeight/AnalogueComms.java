package Sockets.LightWeight;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AnalogueComms extends Thread {

    private final static int INCOME_PORT = 55555;

    @Override
    public void run() {
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(INCOME_PORT);
            //esperem a la conexio del HeavyWeight_B
            Socket socket = serverSocket.accept();
            newDedicatedAnalogueComms(socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newDedicatedAnalogueComms(Socket socket) {
        new DedicatedLWA1(socket).start();
    }
}
