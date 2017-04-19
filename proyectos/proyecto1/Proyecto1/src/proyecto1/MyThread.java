package proyecto1;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyThread extends Thread {
    @Override
    public void run() {
        while(Aplicacion.cambio = false) {
            try {
                System.out.println("MyThread running");
                Aplicacion.getAplicacion().imprimirMatriz();
                sleep(Aplicacion.getAnimar());
            } catch (InterruptedException ex) {
                Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}
