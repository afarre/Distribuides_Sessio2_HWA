import xarxa.X_HWA;
import socket.S_HWA;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        int opcio = menu.showMenu();

        if (opcio == 1){
            new S_HWA().run();

        }else {
            new X_HWA().start();
        }
    }
}
