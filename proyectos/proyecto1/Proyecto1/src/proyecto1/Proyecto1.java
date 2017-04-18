package proyecto1;

import java.io.File;
import java.io.IOException;

public class Proyecto1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String filePath = new File("src/proyecto1/files/mapa.txt").getAbsolutePath();
        Aplicacion.getAplicacion().leerArchivo(filePath);
    }
    
}
