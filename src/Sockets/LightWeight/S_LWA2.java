package Sockets.LightWeight;

public class S_LWA2 {
    public void work() {
        System.out.println("Working in LWA2");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done LWA2");
    }
}
