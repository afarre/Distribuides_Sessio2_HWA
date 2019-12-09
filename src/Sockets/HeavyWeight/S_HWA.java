package Sockets.HeavyWeight;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class S_HWA implements Runnable {
    private final static int INCOME_PORT = 33333;
    private final static int OUTGOING_PORT = 33334;
    private final static String TOKEN_B = "TOKEN_B";
    private final static String TOKEN_A = "TOKEN_A";

    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket outSocket;


    public S_HWA(){
        ChildCommsHWA childComms = new ChildCommsHWA(this);
        childComms.start();
    }

    private void handShake() {
        try {
            doStream.writeUTF("I'm process A. Writing handshake to B.");
            String read = diStream.readUTF();
            System.out.println("I'm A. I received the following message: \"" + read + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void run() {
        createOutcomeConnection();
        createIncomeConnection();
        //handShake();
        while (true){
            writeToHWB();
            readFromHWB();
            try {
                wait();
                wait();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void readFromHWB() {
        try {
            String read = diStream.readUTF();
            if (read.equals(TOKEN_B)) {
                System.out.println("I'm A. I received the following message: " + read);
            }else {
                readFromHWB();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToHWB() {
        try {
            doStream.writeUTF(TOKEN_A);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createIncomeConnection() {
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(INCOME_PORT);
            //esperem a la conexio del HeavyWeight_B
            System.out.println("Waiting for B");
            Socket incomeSocket = serverSocket.accept();
            System.out.println("B waiting done");
            //generaNouServidorDedicat(socket);
            diStream = new DataInputStream(incomeSocket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createOutcomeConnection() {
        boolean wait = true;
        while (wait) {
            // Averiguem quina direccio IP hem d'utilitzar
            InetAddress iAddress;
            try {
                iAddress = InetAddress.getLocalHost();
                String IP = iAddress.getHostAddress();

                outSocket = new Socket(String.valueOf(IP), OUTGOING_PORT);
                doStream = new DataOutputStream(outSocket.getOutputStream());
            } catch (ConnectException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outSocket != null) {
                    wait = false;
                }
            }
        }
    }

}
