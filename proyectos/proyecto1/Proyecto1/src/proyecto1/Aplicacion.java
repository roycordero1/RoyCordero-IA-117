package proyecto1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

//Clase para manejar la aplicación, utiliza Modelo Singleton
public class Aplicacion {
    
    private static Aplicacion aplicacion = null;
    private static ArrayList<ArrayList<Character>> Matriz = new ArrayList<>();
    private static Instrucciones instrucciones;
    private static MyThread threadImprimir;
    private static int animar = 0;
    public static boolean salir = false;
    public static boolean correr = false;
    static Semaphore mutex1= new Semaphore(1);
    
    //El constructor se omite, se está usando el patrón de diseño Singleton    
    public static Aplicacion getAplicacion() {
        if (aplicacion == null)
            aplicacion = new Aplicacion();
        return aplicacion;
    }       
    
    //Getters y Setters
    /*----------------------------------------------------*/
    public static int getAnimar() {
        return animar;
    }
    
    public static void setAnimar(int pAnimar) {
        animar = pAnimar;
    }
    
    public static boolean getCorrer() {        
        return correr;
    }
    
    public static ArrayList<ArrayList<Character>> getMatriz() {
        return Matriz;
    }
    /*----------------------------------------------------*/
    
    //Crea pantalla para enviar instrucciones a la aplicación
    public void crearInstrucciones() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                instrucciones = new Instrucciones();
                instrucciones.setVisible(true);
            }
        });
    }
    
    //Crea pantalla para enviar instrucciones a la aplicación
    public void crearThread() {
        threadImprimir = new MyThread();
        threadImprimir.start();
    }    
    
    //Recibe instruccion ingresada y la valida
    public int seleccionarInstruccion(String pInstruccion, String pParam1, String pParam2) {
        if ("animar".equals(pInstruccion) && !"".equals(pParam1) && "".equals(pParam2)) {
            animar(pParam1);
            return 1;
        }
        else {
            return 0;
        }
    }
    
    private void animar(String pParam1) {
        System.out.println("Entro animar");
        int pAnimar = Integer.parseInt(pParam1);
        if (!(pAnimar == getAnimar()) && pAnimar != 0) {
            System.out.println("Animando");
            setAnimar(pAnimar);
            try {
                mutex1.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Aplicacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            correr = true;
            mutex1.release();
        }
        if (pAnimar == 0) {
            System.out.println("No animando");
            setAnimar(pAnimar);
            try {
                mutex1.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Aplicacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            correr = false;
            mutex1.release();
        }
        System.out.println("Salio animar");
    }
    
    //Lee mapa del txt y almacena en variable Matriz
    public void leerArchivo(String pRuta) throws FileNotFoundException, IOException {
        String cadena;
        FileReader f = new FileReader(pRuta);
        try (BufferedReader b = new BufferedReader(f)) {
            while((cadena = b.readLine()) != null) {
                ArrayList<Character> temp = new ArrayList<>();
                for(int j=0; j<cadena.length(); j++) {
                    temp.add(cadena.charAt(j));
                }
                Matriz.add(temp);
            }
        }        
    }
    
    public void imprimirMatriz() {
        for(int i=0; i<Matriz.size(); i++) {
            for(int j=0; j<Matriz.get(i).size(); j++) {
                System.out.print(Matriz.get(i).get(j));
            }
            System.out.println();
        }
    }
}
