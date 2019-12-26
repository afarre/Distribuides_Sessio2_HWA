package Sockets.HeavyWeight;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DedicatedChildCommsHWA extends Thread{
    private Socket socket;
    private DataInputStream diStream;
    private DataOutputStream doStream;
    //private final S_HWA s_hwa;

    private ChildCommsHWA parent;


    public DedicatedChildCommsHWA(Socket socket,/*, S_HWA s_hwa*/ChildCommsHWA childCommsHWA) {
        this.socket = socket;
        //this.s_hwa = s_hwa;
        this.parent = childCommsHWA;
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

    private void actOnRequest(String request) throws IOException {
        switch (request){
            case "ONLINE":
                String childName = diStream.readUTF();
                System.out.println("Got ONLINE call from: " + childName);
                interconnectChilds(childName);
                break;
            case "LWA1_DONE":
                System.out.println("notify done in HWA from LWA1.");
                /*synchronized (s_hwa){
                    s_hwa.notify();
                }

                 */
                break;

            case "LWA2_DONE":
                System.out.println("notify done in HWA from LWA2.");
               /* synchronized (s_hwa){
                    s_hwa.notify();
                }

                */
                break;

            case "LWA3_DONE":
                System.out.println("notify done in HWA from LWA3.");
                /*synchronized (s_hwa){
                    s_hwa.notify();
                }

                 */
                break;
        }
    }

    private void interconnectChilds(String childName) throws IOException {
        switch (childName) {
            case "LWA1":
                parent.LWA1Online = true;
                System.out.println("LWA1 to true");
                break;

            case "LWA2":
                parent.LWA2Online = true;
                System.out.println("LWA2 to true");
                break;

            case "LWA3":
                parent.LWA3Online = true;
                System.out.println("LWA3 to true");
                break;

        }
        if (parent.LWA1Online && parent.LWA2Online && parent.LWA3Online){
            parent.notifyChildrensToConnect();
        }

        /*
        try {
            connectToLiveChilds(childName);
        } catch (IOException e) {
            e.printStackTrace();
        }

         */

    }

    private void connectToLiveChilds(String childName) throws IOException {
        //doStream.writeUTF("CONNECT");
        System.out.println("Comunico a " + childName + " a quins altres childs s'ha de conectar.");
        switch (childName){
            case "LWA1":
                if (parent.LWA2Online && parent.LWA3Online){
                    doStream.writeInt(2);
                    doStream.writeUTF("LWA2");
                    doStream.writeUTF("LWA3");
                }else if (parent.LWA2Online){
                    doStream.writeInt(1);
                    doStream.writeUTF("LWA2");
                }else if (parent.LWA3Online){
                    doStream.writeInt(1);
                    doStream.writeUTF("LWA3");
                }else {
                    doStream.writeInt(0);
                }
                break;

            case "LWA2":
                System.out.println("-- LWA2");
                System.out.println();
                if (parent.LWA1Online && parent.LWA3Online){
                    System.out.println("-- 2");
                    doStream.writeInt(2);
                    doStream.writeUTF("LWA1");
                    doStream.writeUTF("LWA3");
                }else if (parent.LWA1Online){
                    System.out.println("-- 1 first");
                    doStream.writeInt(1);
                    doStream.writeUTF("LWA1");
                }else if (parent.LWA3Online){
                    System.out.println("-- 1 second");
                    doStream.writeInt(1);
                    doStream.writeUTF("LWA3");
                }else {
                    System.out.println(" wtf ");
                    doStream.writeInt(0);
                }
                break;

            case "LWA3":
                if (parent.LWA1Online && parent.LWA2Online){
                    doStream.writeInt(2);
                    doStream.writeUTF("LWA1");
                    doStream.writeUTF("LWA2");
                }else if (parent.LWA1Online){
                    doStream.writeInt(1);
                    doStream.writeUTF("LWA1");
                }else if (parent.LWA2Online){
                    doStream.writeInt(1);
                    doStream.writeUTF("LWA2");
                }else {
                    doStream.writeInt(0);
                }
                break;
        }
    }

    public void connectToAnalogues() {
        try {
            doStream.writeBoolean(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
