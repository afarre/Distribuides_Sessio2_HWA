import SharedMemory.SM_HWA;
import Sockets.HeavyWeight.S_HWA;

public class MainProcessA {
    private final static int INCOME_PORT_LWA1 = 55555;
    private final static int INCOME_PORT_LWA2 = 55556;
    private final static int INCOME_PORT_LWA3 = 55557;

    private final static int OUTGOING_HWA_PORT = 44444;
    private final static int OUTGOING_LWA1_PORT = 55555;
    private final static int OUTGOING_LWA2_PORT = 55556;
    private final static int OUTGOING_LWA3_PORT = 55557;

    public static void main(String[] args) {
        Menu menu = new Menu();
        int opcio = menu.showMenu();

        if (opcio == 1){
            new S_HWA().run();

        }else {
            SM_HWA sm_hwa = new SM_HWA();
        }
    }
}
