package Sockets.HeavyWeight;

import sun.net.ConnectionResetException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DedicatedChildCommsHWA extends Thread{
    private Socket socket;
    private DataInputStream diStream;
    private DataOutputStream doStream;

    private ChildCommsHWA parent;


    public DedicatedChildCommsHWA(Socket socket, ChildCommsHWA childCommsHWA) {
        this.socket = socket;
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
            } catch (ConnectionResetException ignored){

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
                parent.interconnectChilds(childName);
                break;
            case "LWA DONE":
                System.out.println("notify done in HWA from LWA.");
                childName = diStream.readUTF();
                parent.setChildDone(childName);
                break;
            case "RUN STATUS":
                doStream.writeBoolean(parent.childsDoneStatus());
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

    public void work() {
        try {
            System.out.println("Sending work");
            doStream.writeUTF("WORK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
