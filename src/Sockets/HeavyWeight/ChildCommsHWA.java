package Sockets.HeavyWeight;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChildCommsHWA extends Thread {
    private final static int OUTGOING_PORT_LWA1 = 44444;
    private final static int OUTGOING_PORT_LWA2 = 44445;
    private final static int OUTGOING_PORT_LWA3 = 44446;
    private final static int INCOME_PORT = 44444;

    public boolean LWA1Online;
    public boolean LWA2Online;
    public boolean LWA3Online;
    private S_HWA s_hwa;
    private ArrayList<DedicatedChildCommsHWA> dedicatedChildCommsList;

    public ChildCommsHWA(S_HWA s_hwa) {
        LWA1Online = false;
        LWA2Online = false;
        LWA3Online = false;
        dedicatedChildCommsList = new ArrayList<>();
        this.s_hwa = s_hwa;
    }

    @Override
    public void run() {
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(INCOME_PORT);
            while (true){
                Socket socket = serverSocket.accept();
                newDedicatedChildComms(socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newDedicatedChildComms(Socket socket) {
        DedicatedChildCommsHWA dedicatedChildCommsHWA = new DedicatedChildCommsHWA(socket/*, s_hwa*/, this);
        dedicatedChildCommsHWA.start();
        dedicatedChildCommsList.add(dedicatedChildCommsHWA);
    }

    public void notifyChildrensToConnect() {
        for (int i = 0; i < dedicatedChildCommsList.size(); i++){
            dedicatedChildCommsList.get(i).connectToAnalogues();
        }
    }
}
