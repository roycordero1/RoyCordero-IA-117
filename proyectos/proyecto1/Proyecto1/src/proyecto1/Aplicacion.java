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
    public static Mapa mapa;
    private static Instrucciones instrucciones;
    private static PrintThread threadImprimir;
    private static ExecThread threadExec;
    private static int animar = 0;
    public static boolean salir = false;
    public static boolean correr = false;
    public static boolean pasear = false;
    static Semaphore mutex1= new Semaphore(1);
    public static String instrActual = "";
    
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
        return mapa.getMatriz();
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
        threadImprimir = new PrintThread();
        threadExec = new ExecThread();
        threadImprimir.start();
        threadExec.start();
    }
    
    //Crea objeto de mapa
    public void crearMapa() {
        mapa = new Mapa();        
    }
    
    //Recibe instruccion ingresada y la valida
    public int seleccionarInstruccion(String pInstruccion, String pParam1, String pParam2) {
        if ("animar".equals(pInstruccion) && !"".equals(pParam1) && "".equals(pParam2)) {
            animar(pParam1);
            return 1;
        }
        if ("pasear".equals(pInstruccion) && "".equals(pParam1) && "".equals(pParam2)) {
            pasear = true;
            instrActual = "pasear";            
            return 1;
        }
        else {
            return 0;
        }
    }
    
    private void animar(String pParam1) {
        //System.out.println("Entro animar");
        int pAnimar = Integer.parseInt(pParam1);
        if (!(pAnimar == getAnimar()) && pAnimar != 0) {
            //System.out.println("Animando");
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
            //System.out.println("No animando");
            setAnimar(pAnimar);
            try {
                mutex1.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Aplicacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            correr = false;
            mutex1.release();
        }
        //System.out.println("Salio animar");
    }        
    
    //Lee mapa del txt y almacena en variable Matriz
    public void leerArchivo(String pRuta) throws FileNotFoundException, IOException {
        String cadena;
        ArrayList<ArrayList<Character>> tempMatriz = new ArrayList<>();
        FileReader f = new FileReader(pRuta);
        try (BufferedReader b = new BufferedReader(f)) {
            while((cadena = b.readLine()) != null) {
                ArrayList<Character> temp = new ArrayList<>();
                for(int j=0; j<cadena.length(); j++) {
                    temp.add(cadena.charAt(j));
                }
                tempMatriz.add(temp);
            }
        }
        mapa.setMatriz(tempMatriz);
    }
    
    public void imprimirMatriz() {
        ArrayList<ArrayList<Character>> tempMatriz = mapa.getMatriz();
        for(int i=0; i<tempMatriz.size(); i++) {
            for(int j=0; j<tempMatriz.get(i).size(); j++) {
                System.out.print(tempMatriz.get(i).get(j));
            }
            System.out.println();
        }
    }
}
