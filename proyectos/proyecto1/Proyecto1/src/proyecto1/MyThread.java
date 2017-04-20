package proyecto1;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyThread extends Thread {
    @Override
    public void run() {        
        System.out.println("Entró run");
        while(!Aplicacion.salir) {
            System.out.print("");
            while(Aplicacion.correr) {                
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
}
