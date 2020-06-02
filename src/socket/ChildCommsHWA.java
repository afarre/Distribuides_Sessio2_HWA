package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChildCommsHWA extends Thread {
    private final static int INCOME_PORT = 44444;

    private boolean LWA1Online;
    private boolean LWA2Online;
    private boolean LWA3Online;
    private boolean LWA1Executed;
    private boolean LWA2Executed;
    private boolean LWA3Executed;

    private S_HWA parent;
    private ArrayList<DedicatedChildCommsHWA> dedicatedChildCommsList;

    private final static String LWA1 = "LWA1";
    private final static String LWA2 = "LWA2";
    private final static String LWA3 = "LWA3";

    public ChildCommsHWA(S_HWA s_hwa) {
        LWA1Online = false;
        LWA2Online = false;
        LWA3Online = false;
        LWA1Executed = false;
        LWA2Executed = false;
        LWA3Executed = false;
        dedicatedChildCommsList = new ArrayList<>();
        this.parent = s_hwa;
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
        DedicatedChildCommsHWA dedicatedChildCommsHWA = new DedicatedChildCommsHWA(socket, this);
        dedicatedChildCommsHWA.start();
        dedicatedChildCommsList.add(dedicatedChildCommsHWA);
    }

    public void interconnectChilds(String childName) {
        switch (childName) {
            case LWA1:
                LWA1Online = true;
                break;

            case LWA2:
                LWA2Online = true;
                break;

            case LWA3:
                LWA3Online = true;
                break;

        }
        if (LWA1Online && LWA2Online && LWA3Online){
            System.out.println("Interconnecting childs...");
            notifyChildrensToConnect();
        }
    }

    public void notifyChildrensToConnect() {
        for (int i = 0; i < dedicatedChildCommsList.size(); i++){
            dedicatedChildCommsList.get(i).connectToAnalogues();
        }
    }

    private void childsDone() {
        parent.myNotify();
    }

    public void childsWork() {
        LWA1Executed = LWA2Executed = LWA3Executed = false;
        for (DedicatedChildCommsHWA dedicatedChild : dedicatedChildCommsList) {
            dedicatedChild.work();
        }
    }

    public void setChildDone(String childName) {
        switch (childName) {
            case LWA1:
                LWA1Executed = true;
                break;

            case LWA2:
                LWA2Executed = true;
                break;

            case LWA3:
                LWA3Executed = true;
                break;

        }
        if (LWA1Executed && LWA2Executed && LWA3Executed){
            childsDone();
        }
    }

    public boolean childsDoneStatus() {
        return LWA1Executed && LWA2Executed && LWA3Executed;
    }
}