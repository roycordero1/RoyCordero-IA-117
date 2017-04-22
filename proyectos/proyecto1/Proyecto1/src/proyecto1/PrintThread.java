package proyecto1;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintThread extends Thread {
    @Override
    public void run() {        
        System.out.println("                -- Bienvenido --");
        System.out.println("    Aquí podrá ver el movimiento en el mapa!");
        System.out.println("Ejecute las instrucciones desde la ventana externa!");
        while(!Aplicacion.salir) {
            System.out.print("");
            while(Aplicacion.correr) {                
                try {                    
                    //System.out.println("MyThread running");
                    Aplicacion.getAplicacion().imprimirMatriz();
                    sleep(Aplicacion.getAnimar());
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrintThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
