import SharedMemory.HeavyWeight.SM_HWA;
import SharedMemory.HeavyWeight.SM_HWB;
import Sockets.HeavyWeight.S_HWA;
import Sockets.HeavyWeight.S_HWB;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        int opcio = menu.showMenu();

        if (opcio == 1){
            S_HWA s_hwa = new S_HWA();
            /*
            s_hwa.createLightweights();
            s_hwa.createChildCommunication();
            s_hwa.createAnalgousCommunication();

             */
            S_HWB s_hwb = new S_HWB();
            Thread threadSocketHWA = new Thread(s_hwa);
            threadSocketHWA.start();
            Thread threadSocketHWB = new Thread(s_hwb);
            threadSocketHWB.start();
        }else {
            SM_HWA sm_hwa = new SM_HWA();
            SM_HWB sm_hwb = new SM_HWB();
        }
    }
}
