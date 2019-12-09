import SharedMemory.HeavyWeight.SM_HWB;
import Sockets.HeavyWeight.S_HWB;

public class MainProcessB {

    public static void main(String[] args) {
        Menu menu = new Menu();
        int opcio = menu.showMenu();

        if (opcio == 1){
            S_HWB s_hwb = new S_HWB();
            Thread threadSocketHWB = new Thread(s_hwb);
            threadSocketHWB.start();
        }else {
            SM_HWB sm_hwb = new SM_HWB();
        }
    }
}
