package proyecto1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Mapa {
    private ArrayList<ArrayList<Character>> matriz;
    private Map<Character, int[]> cuadras = new HashMap<>();
    private Map<Integer, int[]> clientes = new HashMap<>();
    private ArrayList<int[]> cuadrasVacias = new ArrayList<>();
    private int taxiX;
    private int taxiY;
    private int ultPosX;
    private int ultPosY;
    private String ultMov;
    private int contadorClientes;
    
    public Mapa() {
        matriz = new ArrayList<>();
        ultPosX = -1;
        ultPosY = -1;
        ultMov = "";
        contadorClientes = 0;
    }
    
    public void setMatriz(ArrayList<ArrayList<Character>> pMatriz) {
        matriz = pMatriz;
        setTaxi(matriz);
        setCuadras(matriz);
        setCuadrasVacias(matriz);
        setClientes(matriz);        
    }
    
    public ArrayList<ArrayList<Character>> getMatriz() {
        return matriz;
    }
    
    public void modifMatriz(Character pChar, int[] pPos) {
        ArrayList<Character> temp = new ArrayList<>();
        temp = matriz.get(pPos[0]);
        temp.set(pPos[1], pChar);
    }
    
    public void setTaxi(ArrayList<ArrayList<Character>> pMatriz) {
        for(int i=0; i<pMatriz.size(); i++) {
            for(int j=0; j<pMatriz.get(i).size(); j++) {
                if(pMatriz.get(i).get(j) == 'D') {
                    taxiX = i;
                    taxiY = j;
                    return;
                }
            }            
        }
    }
    
    public int getTaxiX() {
        return taxiX;
    }
    
    public int getTaxiY() {
        return taxiY;
    }
    
    public void setCuadras(ArrayList<ArrayList<Character>> pMatriz) {
        //Se buscan letras, y se agrega a mapa
        //Mapa es Key: Char del nombre, Value: int[] conteniendo la posición        
        for(int i=0; i<pMatriz.size(); i++) {
            for(int j=0; j<pMatriz.get(i).size(); j++) {
                if(!(pMatriz.get(i).get(j)=='D')&&!(pMatriz.get(i).get(j)=='0')&&!(pMatriz.get(i).get(j)=='|')&&!(pMatriz.get(i).get(j)=='-')&&!(pMatriz.get(i).get(j)==' ')) {
                    int[] temp = new int[2];
                    temp[0] = i;
                    temp[1] = j;
                    //Se agrega a mapa de cuadras                    
                    cuadras.put(pMatriz.get(i).get(j), temp);                    
                }
            }
        }
    }
    
    public void setCuadrasVacias(ArrayList<ArrayList<Character>> pMatriz) {
        //Se buscan cuadras vacias y se agregan a lista        
        for(int i=0; i<pMatriz.size(); i++) {
            for(int j=0; j<pMatriz.get(i).size(); j++) {                
                if(i!=0 && j!=0 && i<pMatriz.size() && j<pMatriz.get(i).size()) {
                    if(pMatriz.get(i).get(j)==' '&&(pMatriz.get(i-1).get(j)=='-'||pMatriz.get(i-1).get(j)=='0')&&(pMatriz.get(i+1).get(j)=='-'||pMatriz.get(i+1).get(j)=='0')&&pMatriz.get(i).get(j-1)=='|'&&pMatriz.get(i).get(j+1)=='|') {
                        int[] temp = new int[2];
                        temp[0] = i;
                        temp[1] = j;
                        //Se agrega a mapa de cuadras                    
                        cuadrasVacias.add(temp);
                    }
                }                
            }
        }
    }
    
    public void setClientes(ArrayList<ArrayList<Character>> pMatriz) {
        Random random = new Random();
        char temp3;
        int random1, random2;
        Iterator<Character> cuadrasColl;
        
        for(int i=0; i<pMatriz.size(); i++) {
            for(int j=0; j<pMatriz.get(i).size(); j++) {
                if(pMatriz.get(i).get(j) == '0') {
                    //Posición actual del cliente en variable temp1
                    int[] temp1 = new int[4];
                    temp1[0] = i;
                    temp1[1] = j;                    
                    //Posición aleatoria para destino
                    //Escoge cuadra aleatoria y almacen en temp3
                    random1 = random.nextInt(cuadras.size());                    
                    cuadrasColl = cuadras.keySet().iterator();
                    for(int k=0; k<random1; k++) {
                        cuadrasColl.next();
                    }
                    temp3 = cuadrasColl.next();
                    
                    //Escoge lado aleatorio de la cuadra escogida
                    random2 = random.nextInt(6);
                    switch(random2) {
                        case 0:
                            temp1[2] = cuadras.get(temp3)[0]-1;
                            temp1[3] = cuadras.get(temp3)[1]-1;
                            break;
                        case 1:
                            temp1[2] = cuadras.get(temp3)[0]-1;
                            temp1[3] = cuadras.get(temp3)[1];
                            break;
                        case 2:
                            temp1[2] = cuadras.get(temp3)[0]-1;
                            temp1[3] = cuadras.get(temp3)[1]+1;
                            break;
                        case 3:
                            temp1[2] = cuadras.get(temp3)[0]+1;
                            temp1[3] = cuadras.get(temp3)[1]-1;
                            break;
                        case 4:
                            temp1[2] = cuadras.get(temp3)[0]+1;
                            temp1[3] = cuadras.get(temp3)[1];
                            break;
                        default:
                            temp1[2] = cuadras.get(temp3)[0]+1;
                            temp1[3] = cuadras.get(temp3)[1]+1;
                    }
                    //Se agrega a mapa de clientes
                    clientes.put(contadorClientes, temp1);
                    contadorClientes++;
                }
            }            
        }
    }
    
    public void prueba() {
        System.out.println("Prueba");
        
        Character clave;
        Iterator<Character> cuadrasColl = cuadras.keySet().iterator();
        System.out.println("Hay las siguientes cuadras:");
        while(cuadrasColl.hasNext()){
            clave = cuadrasColl.next();
            System.out.println(clave + " - " + cuadras.get(clave)[0] + ", " + cuadras.get(clave)[1]);
        } 
        System.out.println();
        
        int clave2;
        Iterator<Integer> clientesColl = clientes.keySet().iterator();
        System.out.println("Hay los siguientes clientes:");        
        while(clientesColl.hasNext()){
            clave2 = clientesColl.next();
            System.out.println(clave2 + " " + clientes.get(clave2)[0] + ", " + clientes.get(clave2)[1] + " - " + clientes.get(clave2)[2] + ", " + clientes.get(clave2)[3]);
        } 
    }    
        
    public void pasear() {
        //System.out.println("Entro pasear en Mapa con taxi en " + taxiX + " " + taxiY);
        //Si no están seteadas, se setean las últimas posiciones como un paso a la izquierda
        if (ultPosX == -1 && ultPosY == -1) {
            //System.out.println("Entro setear");
            ultPosX = taxiX;
            ultPosY = taxiY - 1;
            ultMov = "abajo";
        }
        
        ArrayList<Character> temp = new ArrayList<>();
        //Se valida posición a la derecha
        if(matriz.get(taxiX).get(taxiY+1) == ' ' && taxiY+1 != ultPosY) {
            //System.out.println("Mueve derecha");
            temp = matriz.get(taxiX);
            temp.set(taxiY, ' ');
            temp.set(taxiY+1, 'D');
            matriz.set(taxiX, temp);
            ultPosX = taxiX;
            ultPosY = taxiY;
            taxiY++;            
            return;
        }
        //Se valida posición a la izquierda
        if(matriz.get(taxiX).get(taxiY-1) == ' ' && taxiY-1 != ultPosY) {
            //System.out.println("Mueve izquierda");
            temp = matriz.get(taxiX);
            temp.set(taxiY, ' ');
            temp.set(taxiY-1, 'D');
            matriz.set(taxiX, temp);
            ultPosX = taxiX;
            ultPosY = taxiY;
            taxiY--;            
            return;
        }
        //Si ultimo movimiento fue hacia abajo, se prioriza abajo
        if ("abajo".equals(ultMov)) {
            //Se valida posición hacia abajo
            if(matriz.get(taxiX+1).get(taxiY) == ' ' && taxiX+1 != ultPosX) {
                //System.out.println("Mueve abajo");
                temp = matriz.get(taxiX);
                temp.set(taxiY, ' ');
                matriz.set(taxiX, temp);
                temp = matriz.get(taxiX+1);
                temp.set(taxiY, 'D');
                matriz.set(taxiX+1, temp);
                ultPosX = taxiX;
                ultPosY = taxiY;
                taxiX++;
                ultMov = "abajo";
                return;
            }
            //Se valida posición hacia
            if(matriz.get(taxiX-1).get(taxiY) == ' ' && taxiX-1 != ultPosX) {
                //System.out.println("Mueve arriba");
                temp = matriz.get(taxiX);
                temp.set(taxiY, ' ');
                matriz.set(taxiX, temp);
                temp = matriz.get(taxiX-1);
                temp.set(taxiY, 'D');
                matriz.set(taxiX-1, temp);
                ultPosX = taxiX;
                ultPosY = taxiY;
                taxiX--;
                ultMov = "arriba";
            }
        }
        //Si ultimo movimiento fue hacia arriba, se prioriza arriba
        else {
            //Se valida posición hacia arriba
            if(matriz.get(taxiX-1).get(taxiY) == ' ' && taxiX-1 != ultPosX) {
                //System.out.println("Mueve arriba");
                temp = matriz.get(taxiX);
                temp.set(taxiY, ' ');
                matriz.set(taxiX, temp);
                temp = matriz.get(taxiX-1);
                temp.set(taxiY, 'D');
                matriz.set(taxiX-1, temp);
                ultPosX = taxiX;
                ultPosY = taxiY;
                taxiX--;
                ultMov = "arriba";
                return;
            }
            //Se valida posición hacia abajo
            if(matriz.get(taxiX+1).get(taxiY) == ' ' && taxiX+1 != ultPosX) {
                //System.out.println("Mueve abajo");
                temp = matriz.get(taxiX);
                temp.set(taxiY, ' ');
                matriz.set(taxiX, temp);
                temp = matriz.get(taxiX+1);
                temp.set(taxiY, 'D');
                matriz.set(taxiX+1, temp);
                ultPosX = taxiX;
                ultPosY = taxiY;
                taxiX++;
                ultMov = "abajo";                
            }
        }
    }
    
    public void agregarCliente(Character pCuadraOrigen, Character pCuadraDest) {
        Random random = new Random();
        int random1;
        
        //Valida que las cuadras existan
        if(cuadras.get(pCuadraOrigen)==null || cuadras.get(pCuadraDest)==null)
            return;
        //Variables almacenarán posiciones de las cuadras
        int[] temp1 = cuadras.get(pCuadraOrigen);
        int[] temp2 = cuadras.get(pCuadraDest);
        //temp3 almacenará array para cliente
        int[] temp3 = new int[4];
        
        //Se valida que cuadra de origen no esté llena de clientes
        boolean lleno = true;
        for (int i=-1; i<2; i++) {
            if(matriz.get(temp1[0]-1).get(temp1[1]+1) != '0') {
                lleno = false;
            }
        }
        for (int i=-1; i<2; i++) {
            if(matriz.get(temp1[0]+1).get(temp1[1]+1) != '0') {
                lleno = false;
            }
        }
        if(lleno)
            return;
        
        //Se escoge posución de la cuadra en que estará cliente
        //y se valida que la posición escogida esté vacía
        boolean correcto = false;
        while(!correcto) {
            random1 = random.nextInt(6);
            switch(random1) {
                case 0:                    
                    temp3[0] = temp1[0]-1;
                    temp3[1] = temp1[1]-1;
                    if (matriz.get(temp3[0]).get(temp3[1]) == '-')
                        correcto = true;
                    break;
                case 1:
                    temp3[0] = temp1[0]-1;
                    temp3[1] = temp1[1];
                    if (matriz.get(temp3[0]).get(temp3[1]) == '-')
                        correcto = true;
                    break;
                case 2:
                    temp3[0] = temp1[0]-1;
                    temp3[1] = temp1[1]+1;
                    if (matriz.get(temp3[0]).get(temp3[1]) == '-')
                        correcto = true;
                    break;
                case 3:
                    temp3[0] = temp1[0]+1;
                    temp3[1] = temp1[1]-1;
                    if (matriz.get(temp3[0]).get(temp3[1]) == '-')
                        correcto = true;
                    break;
                case 4:
                    temp3[0] = temp1[0]+1;
                    temp3[1] = temp1[1];
                    if (matriz.get(temp3[0]).get(temp3[1]) == '-')
                        correcto = true;
                    break;
                default:
                    temp3[0] = temp1[0]+1;
                    temp3[1] = temp1[1]+1;
                    if (matriz.get(temp3[0]).get(temp3[1]) == '-')
                        correcto = true;
            }
        }
        
        //Random para la cuadra de destino
        random1 = random.nextInt(6);
        switch(random1) {
            case 0:
                temp3[2] = temp2[0]-1;
                temp3[3] = temp2[1]-1;
                break;
            case 1:
                temp3[2] = temp2[0]-1;
                temp3[3] = temp2[1];
                break;
            case 2:
                temp3[2] = temp2[0]-1;
                temp3[3] = temp2[1]+1;
                break;
            case 3:
                temp3[2] = temp2[0]+1;
                temp3[3] = temp2[1]-1;
                break;
            case 4:
                temp3[2] = temp2[0]+1;
                temp3[3] = temp2[1];
                break;
            default:
                temp3[2] = temp2[0]+1;
                temp3[3] = temp2[1]+1;
        }
        //Se agrega al mapa de clientes y se dibuja en matriz
        clientes.put(contadorClientes, temp3);
        contadorClientes++;                
        modifMatriz('0', temp3);
    }
    
    public void agregarClientes(int pCantidad) {
        Random random = new Random();
        int random1;
        Iterator<Character> cuadrasColl;        
        //temp1 almacenará pos de cuadra escogida
        int[] temp1;
        //temp2 almacenará nombre de cuadra escogida, en caso de que sea nombrada
        Character temp2;
        //temp3 almacenará array para cliente
        int[] temp3 = new int[4];
        
        //Ciclo para cantidad de clientes a ingresar
        //Se hace multiplicado por 2 para reutilizar código, calculando randoms
        //de origen y destino
        for(int i=1; i<=pCantidad*2; i++) {            
            random1 = random.nextInt(cuadrasVacias.size()+cuadras.size());
            //Se saca cuadra random, incluyendo tanto vacías como nombradas            
            if(random1>=cuadrasVacias.size()) {
                cuadrasColl = cuadras.keySet().iterator();
                for(int k=0; k<random1-cuadrasVacias.size(); k++) {
                    cuadrasColl.next();
                }
                temp2 = cuadrasColl.next();
                temp1 = cuadras.get(temp2);
            }
            else {
                temp1 = cuadrasVacias.get(random1);
            }
            
            //Se verifica que cuadra escogida no esté llena de clientes
            //Pero solamente en caso de estar escogiendo origen
            if(i%2==1) {
                boolean lleno = true;
                for (int j=-1; j<2; j++) {
                    if(matriz.get(temp1[0]-1).get(temp1[1]+1) != '0') {
                        lleno = false;
                    }
                }
                for (int j=-1; j<2; j++) {
                    if(matriz.get(temp1[0]+1).get(temp1[1]+1) != '0') {
                        lleno = false;
                    }
                }
                if(lleno) {
                    i--;
                    break;
                }
            }            
                        
            //Si se está escogiendo cuadra de origen
            if(i%2==1) {
                //Hasta que la posición escogida esté vacía
                boolean correcto = false;
                while(!correcto) {
                    //Random sobre cual posición de la cuadra escoger
                    random1 = random.nextInt(6);
                    switch(random1) {
                        case 0:                    
                            temp3[0] = temp1[0]-1;
                            temp3[1] = temp1[1]-1;
                            if (matriz.get(temp3[0]).get(temp3[1]) == '-')
                                correcto = true;
                            break;
                        case 1:
                            temp3[0] = temp1[0]-1;
                            temp3[1] = temp1[1];
                            if (matriz.get(temp3[0]).get(temp3[1]) == '-')
                                correcto = true;
                            break;
                        case 2:
                            temp3[0] = temp1[0]-1;
                            temp3[1] = temp1[1]+1;
                            if (matriz.get(temp3[0]).get(temp3[1]) == '-')
                                correcto = true;
                            break;
                        case 3:
                            temp3[0] = temp1[0]+1;
                            temp3[1] = temp1[1]-1;
                            if (matriz.get(temp3[0]).get(temp3[1]) == '-')
                                correcto = true;
                            break;
                        case 4:
                            temp3[0] = temp1[0]+1;
                            temp3[1] = temp1[1];
                            if (matriz.get(temp3[0]).get(temp3[1]) == '-')
                                correcto = true;
                            break;
                        default:
                            temp3[0] = temp1[0]+1;
                            temp3[1] = temp1[1]+1;
                            if (matriz.get(temp3[0]).get(temp3[1]) == '-')
                                correcto = true;
                    }
                }
            }
            //Si se está escogiendo cuadra de destino
            else {
                //Random sobre cual posición de la cuadra escoger
                random1 = random.nextInt(6);
                switch(random1) {
                    case 0:
                        temp3[2] = temp1[0]-1;
                        temp3[3] = temp1[1]-1;
                        break;
                    case 1:
                        temp3[2] = temp1[0]-1;
                        temp3[3] = temp1[1];
                        break;
                    case 2:
                        temp3[2] = temp1[0]-1;
                        temp3[3] = temp1[1]+1;
                        break;
                    case 3:
                        temp3[2] = temp1[0]+1;
                        temp3[3] = temp1[1]-1;
                        break;
                    case 4:
                        temp3[2] = temp1[0]+1;
                        temp3[3] = temp1[1];
                        break;
                    default:
                        temp3[2] = temp1[0]+1;
                        temp3[3] = temp1[1]+1;
                }
                //Se agrega al mapa de clientes y se dibuja en matriz
                clientes.put(contadorClientes, temp3);
                contadorClientes++;
                modifMatriz('0', temp3);
            }
        }
    }
}
