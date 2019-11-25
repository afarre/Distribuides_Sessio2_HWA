package Sockets.HeavyWeight;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class S_HWB extends Thread{
    private final static int INCOME_PORT = 33334;
    private final static int OUTCOME_PORT = 33333;

    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket outSocket;
    private Socket incomeSocket;

    @Override
    public void run() {
        createIncomeConnection();
        createOutcomeConnection();
        readFromHWA();
        writeToHWA();
    }

    private void readFromHWA() {
        try {
            String read = diStream.readUTF();
            System.out.println("I'm B. I recieved the follwing message: " + read);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToHWA() {
        try {
            doStream.writeUTF("I'm B writting to A");
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
            // generaNouServidorDedicat(incomeSocket);
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
}
