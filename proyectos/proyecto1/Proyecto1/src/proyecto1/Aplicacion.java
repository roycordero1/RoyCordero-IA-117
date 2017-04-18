package proyecto1;

//Clase para manejar la aplicación, utiliza Modelo Singleton

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Aplicacion {
    
    private static Aplicacion aplicacion = null;
    private ArrayList<ArrayList<Character>> Matriz = new ArrayList<>();
    
    //El constructor se omite, se está usando el patrón de diseño Singleton    
    public static Aplicacion getAplicacion() {
        if (aplicacion == null)
            aplicacion = new Aplicacion();
        return aplicacion;
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
}
