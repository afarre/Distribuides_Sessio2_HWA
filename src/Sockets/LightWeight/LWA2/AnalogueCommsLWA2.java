package Sockets.LightWeight.LWA2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AnalogueCommsLWA2 extends Thread {

    private final static int INCOME_PORT = 55556;

    private DedicatedLWA2 dedicatedLWA2;
    private final S_LWA2 s_lwa2;

    public AnalogueCommsLWA2(S_LWA2 s_lwa2) {
        this.s_lwa2 = s_lwa2;
    }

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

    private synchronized void newDedicatedAnalogueComms(Socket socket) {
        dedicatedLWA2 = new DedicatedLWA2(socket, this);
        dedicatedLWA2.start();
        try {
            wait();
            synchronized (s_lwa2){
                s_lwa2.notify();
                System.out.println("Sockets in AnalogueCommsLWA2 created. Notifying");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addToQueue(long time, String tmstp) {
        dedicatedLWA2.addToQueue(time, tmstp);
    }

    public String peekQueue() {
        return dedicatedLWA2.peekQueue();
    }
}
