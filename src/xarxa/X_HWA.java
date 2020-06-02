package xarxa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class X_HWA extends Thread {
    private final static int INCOME_PORT = 45690;
    private final static int OUTGOING_PORT = 45678;
    private final static String TOKEN_B = "TOKEN_B";
    private final static String TOKEN_A = "TOKEN_A";

    private DataOutputStream outgoingDoStream;
    private DataInputStream outgoingDiStream;

    private DataOutputStream incomingDoStream;
    private DataInputStream incomingDiStream;
    private Socket outSocket;

    private boolean LWA1Online;
    private boolean LWA2Online;
    private boolean LWA3Online;
    private boolean LWA1Executed;
    private boolean LWA2Executed;
    private boolean LWA3Executed;

    private final static String LWA1 = "LWA1";
    private final static String LWA2 = "LWA2";
    private final static String LWA3 = "LWA3";

    /** Constants per al algoritme de lamport **/
    private final static String ONLINE = "ONLINE";
    private final static String CONNECT = "CONNECT";
    private final static String PORT = "PORT";
    private final static String PROCESS = "HWA";
    private final static String TOKEN = "TOKEN";
    private final static String LWA_WORK = "LWA_WORK";


    @Override
    public void run() {
        LWA1Online = false;
        LWA2Online = false;
        LWA3Online = false;
        LWA1Executed = false;
        LWA2Executed = false;
        LWA3Executed = false;
        try {
            createOutcomeConnection();
            outgoingDoStream.writeUTF(PORT);
            outgoingDoStream.writeInt(INCOME_PORT);
            outgoingDoStream.writeUTF(PROCESS);
            createIncomeConnection();
            System.out.println("Waiting for everyone to be connected...");

            while (true){
                String request = incomingDiStream.readUTF();
                readRequest(request);
            }
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
                outgoingDoStream = new DataOutputStream(outSocket.getOutputStream());
                outgoingDiStream = new DataInputStream(outSocket.getInputStream());
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

    private void createIncomeConnection() {
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(INCOME_PORT);
            //esperem a la conexio de la xarxa de comunicacions
            Socket incomeSocket = serverSocket.accept();
            incomingDiStream = new DataInputStream(incomeSocket.getInputStream());
            incomingDoStream = new DataOutputStream(incomeSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readRequest(String request) throws IOException {
        switch (request){
            case TOKEN:
                System.out.println("He rebut el token. Notificant als meus fills.");
                outgoingDoStream.writeUTF(LWA_WORK);
                break;
        }
    }
}
