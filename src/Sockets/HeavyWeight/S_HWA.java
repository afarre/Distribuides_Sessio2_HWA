package Sockets.HeavyWeight;

import Sockets.LightWeight.S_LWA1;
import Sockets.LightWeight.S_LWA2;
import Sockets.LightWeight.S_LWA3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class S_HWA implements Runnable {
    private final static int INCOME_PORT = 33333;
    private final static int OUTCOME_PORT = 33334;

    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket outSocket;
    private Socket incomeSocket;

    public void createLightweights() {
        S_LWA1 s_lwa1 = new S_LWA1();
        S_LWA2 s_lwa2 = new S_LWA2();
        S_LWA3 s_lwa3 = new S_LWA3();
    }

    @Override
    public void run() {
        createOutcomeConnection();
        createIncomeConnection();
        writeToHWB();
        readFromHWB();
    }

    private void readFromHWB() {
        try {
            String read = diStream.readUTF();
            System.out.println("I'm A. I recieved the follwing message: " + read);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToHWB() {
        try {
            doStream.writeUTF("I'm A writting to B");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createIncomeConnection() {
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(INCOME_PORT);
            //esperem a la conexio del HeavyWeight_B
            incomeSocket = serverSocket.accept();
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

                outSocket = new Socket(String.valueOf(IP), OUTCOME_PORT);
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

    public void createChildCommunication() {
        Thread childThread = new Thread(this);

    }

    public void createAnalgousCommunication() {

    }
}
