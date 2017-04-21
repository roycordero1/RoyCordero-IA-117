package proyecto1;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecThread extends Thread {
    public void run() {
        //System.out.println("Entró exec");
        while(!Aplicacion.salir) {
            System.out.print("");
            while("pasear".equals(Aplicacion.instrActual)) {
                //System.out.println("Entró pasear");
                while(Aplicacion.pasear) {
                    //System.out.println("Paseando");
                    Aplicacion.mapa.pasear();
                    try {
                        sleep(Aplicacion.getAnimar());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Aplicacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}

