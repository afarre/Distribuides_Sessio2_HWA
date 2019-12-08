import SharedMemory.HeavyWeight.SM_HWA;
import SharedMemory.HeavyWeight.SM_HWB;
import Sockets.HeavyWeight.S_HWA;
import Sockets.HeavyWeight.S_HWB;
import Sockets.LightWeight.S_LWA1;
import Sockets.LightWeight.S_LWA2;
import Sockets.LightWeight.S_LWA3;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        int opcio = menu.showMenu();

        if (opcio == 1){
            S_HWA s_hwa = new S_HWA();
            S_HWB s_hwb = new S_HWB();
            Thread threadSocketHWA = new Thread(s_hwa);
            threadSocketHWA.start();
            Thread threadSocketHWB = new Thread(s_hwb);
            threadSocketHWB.start();

            S_LWA1 s_lwa1 = new S_LWA1();
            S_LWA2 s_lwa2 = new S_LWA2();
            S_LWA3 s_lwa3 = new S_LWA3();
            s_lwa1.start();
            //s_lwa2.start();
            //s_lwa3.start();
        }else {
            SM_HWA sm_hwa = new SM_HWA();
            SM_HWB sm_hwb = new SM_HWB();
        }
    }
}
