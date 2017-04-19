package proyecto1;

//Clase para manejar la aplicación, utiliza Modelo Singleton

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Aplicacion {
    
    private static Aplicacion aplicacion = null;
    private static ArrayList<ArrayList<Character>> Matriz = new ArrayList<>();
    private static int animar = 0;
    public static boolean cambio = false;
    
    //El constructor se omite, se está usando el patrón de diseño Singleton    
    public static Aplicacion getAplicacion() {
        if (aplicacion == null)
            aplicacion = new Aplicacion();
        return aplicacion;
    }
    
    public static int getAnimar() {
        return animar;
    }
    
    public static void setAnimar(int pAnimar) {
        animar = pAnimar;
    }
    
    public static ArrayList<ArrayList<Character>> getMatriz() {
        return Matriz;
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
