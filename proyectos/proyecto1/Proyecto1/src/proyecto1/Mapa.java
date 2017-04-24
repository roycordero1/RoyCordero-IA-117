package proyecto1;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mapa {
    private ArrayList<ArrayList<Character>> matriz;
    private Map<Character, int[]> cuadras = new HashMap<>();
    private Map<Integer, int[]> clientes = new HashMap<>();
    private ArrayList<int[]> cuadrasVacias = new ArrayList<>();
    private Stack<int[]> pila = new Stack<int[]>();
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
    
    public void buscar() {
        //Valida si está a la par de algún 0
        if(matriz.get(taxiX-1).get(taxiY) == '0' || matriz.get(taxiX+1).get(taxiY) == '0' || matriz.get(taxiX).get(taxiY-1) == '0' || matriz.get(taxiX).get(taxiY+1) == '0') {
            int clave;
            int idCliente = -1;
            ArrayList<Character> tempFila = new ArrayList<>();
            
            Iterator<Integer> clientesColl = clientes.keySet().iterator();
            while(clientesColl.hasNext()) {
                clave = clientesColl.next();
                //Verifica cual cliente encontró dentro de la lista de clientes
                //Valida si el de arriba
                if(clientes.get(clave)[0]==taxiX-1 && clientes.get(clave)[1]==taxiY) {
                    tempFila = matriz.get(taxiX-1);
                    tempFila.set(taxiY, '/');
                    matriz.set(taxiX-1, tempFila);
                    idCliente = clave;
                    break;
                }
                else {
                    //Valida si el de abajo
                    if(clientes.get(clave)[0]==taxiX+1 && clientes.get(clave)[1]==taxiY) {
                        tempFila = matriz.get(taxiX+1);
                        tempFila.set(taxiY, '/');
                        matriz.set(taxiX+1, tempFila);
                        idCliente = clave;
                        break;
                    }
                    else {
                        //Valida si el de izquierda
                        if(clientes.get(clave)[0]==taxiX && clientes.get(clave)[1]==taxiY-1) {
                            tempFila = matriz.get(taxiX);
                            tempFila.set(taxiY-1, '/');
                            matriz.set(taxiX, tempFila);
                            idCliente = clave;
                            break;
                        }
                        else {
                            //Valida si el de derecha
                            if(clientes.get(clave)[0]==taxiX && clientes.get(clave)[1]==taxiY+1) {
                                tempFila = matriz.get(taxiX);
                                tempFila.set(taxiY+1, '/');
                                matriz.set(taxiX, tempFila);
                                idCliente = clave;
                                break;
                            }
                        }
                    }
                }
            }
            int destinoX = clientes.get(idCliente)[2];
            int destinoY = clientes.get(idCliente)[3];
            calcularRuta(destinoX, destinoY);
            hacerViaje();
            
            //Se limpia cliente y destino
            tempFila = matriz.get(clientes.get(idCliente)[0]);
            tempFila.set(clientes.get(idCliente)[1], '-');
            matriz.set(clientes.get(idCliente)[0], tempFila);
            tempFila = matriz.get(clientes.get(idCliente)[2]);
            tempFila.set(clientes.get(idCliente)[3], '-');
            matriz.set(clientes.get(idCliente)[2], tempFila);
        }
        pasear();
    }
    
    public void parquear(Character pCuadra) {
        Random random = new Random();
        int random1;
        
        //Valida que las cuadras existan
        if(cuadras.get(pCuadra)==null)
            return;
        
        //Variable almacenará posición de la cuadra
        int[] temp1 = cuadras.get(pCuadra);
        //Variable para cálculo de ruta
        int[] temp2 = new int[2];
        
        //Random para la cuadra de destino
        random1 = random.nextInt(6);
        switch(random1) {
            case 0:
                temp2[0] = temp1[0]-1;
                temp2[1] = temp1[1]-1;
                break;
            case 1:
                temp2[0] = temp1[0]-1;
                temp2[1] = temp1[1];
                break;
            case 2:
                temp2[0] = temp1[0]-1;
                temp2[1] = temp1[1]+1;
                break;
            case 3:
                temp2[0] = temp1[0]+1;
                temp2[1] = temp1[1]-1;
                break;
            case 4:
                temp2[0] = temp1[0]+1;
                temp2[1] = temp1[1];
                break;
            default:
                temp2[0] = temp1[0]+1;
                temp2[1] = temp1[1]+1;
        }
        
        calcularRuta(temp2[0], temp2[1]);
        hacerViaje();
        
        //Se limpia destino
        ArrayList<Character> tempFila = new ArrayList<>();
        tempFila = matriz.get(temp2[0]);
        tempFila.set(temp2[1], '-');
        matriz.set(temp2[0], tempFila);
    }
    
    //Calcula mejor ruta para llevar al cliente al destino
    public void calcularRuta(int destinoX, int destinoY) {        
        int resultR, resultL, resultU, resultD, costoMenor;
        
        ArrayList<Character> tempFila2 = new ArrayList<>();
        tempFila2 = matriz.get(destinoX);
        tempFila2.set(destinoY, 'X');
        matriz.set(destinoX, tempFila2);
        
        int[] temp = new int[2];                
        temp[0] = taxiX;
        temp[1] = taxiY;
        pila.push(temp);
        
        //Mientras no se llegue a la posición destino
        while(!auxEncontro(taxiX, taxiY, destinoX, destinoY)) {
            resultR = 1000;
            resultL = 1000;
            resultU = 1000;
            resultD = 1000;
            
            //Se valida posición a la derecha
            if(matriz.get(taxiX).get(taxiY+1) == ' ' && taxiY+1 != ultPosY) {
                //Valida si en la posición derecha, está a la par del destino
                if (auxEncontro(taxiX, taxiY+1, destinoX, destinoY)) {
                    resultR = -1;
                }
                else {
                    //Heurística para cálculo de ruta
                    resultR = Math.abs(taxiX - destinoX) + Math.abs(taxiY+1 - destinoY);
                }                
            }
            //Se valida posición a la izquierda
            if(matriz.get(taxiX).get(taxiY-1) == ' ' && taxiY-1 != ultPosY) {
                //Valida si en la posición izquierda, está a la par del destino
                if (auxEncontro(taxiX, taxiY-1, destinoX, destinoY)) {
                    resultL = -1;
                }
                else {
                    //Heurística para cálculo de ruta
                    resultL = Math.abs(taxiX - destinoX) + Math.abs(taxiY-1 - destinoY);
                }                
            }
            //Se valida posición arriba
            if(matriz.get(taxiX-1).get(taxiY) == ' ' && taxiX-1 != ultPosX) {
                //Valida si en la posición arriba, está a la par del destino
                if (auxEncontro(taxiX-1, taxiY, destinoX, destinoY)) {
                    resultU = -1;
                }
                else {
                    //Heurística para cálculo de ruta
                    resultU = Math.abs(taxiX-1 - destinoX) + Math.abs(taxiY - destinoY);
                }                
            }
            //Se valida posición abajo
            if(matriz.get(taxiX+1).get(taxiY) == ' ' && taxiX+1 != ultPosX) {
                //Valida si en la posición abajo, está a la par del destino
                if (auxEncontro(taxiX+1, taxiY, destinoX, destinoY)) {
                    resultD = -1;
                }
                else {
                    //Heurística para cálculo de ruta
                    resultD = Math.abs(taxiX+1 - destinoX) + Math.abs(taxiY - destinoY);
                }                
            }
            
            int[] temp2 = new int[2];
            temp2[0] = taxiX;
            temp2[1] = taxiY;
            pila.push(temp2);
            
            int[] temp3 = new int[2];
            boolean encontro = false;
            //Valida si con el siguiente mov, llega al destino
            if(resultR == -1) {
                temp3[0] = taxiX;
                temp3[1] = taxiY+1;
                pila.push(temp3);
                ultPosX = taxiX;
                ultPosY = taxiY;
                taxiY++;
                encontro = true;
            }
            if(resultL == -1 && !encontro) {
                temp3[0] = taxiX;
                temp3[1] = taxiY-1;
                pila.push(temp3);
                ultPosX = taxiX;
                ultPosY = taxiY;
                taxiY--;
                encontro = true;
            }
            if(resultU == -1 && !encontro) {
                temp3[0] = taxiX-1;
                temp3[1] = taxiY;
                pila.push(temp3);
                ultPosX = taxiX;
                ultPosY = taxiY;
                taxiX--;
                encontro = true;
            }
            if(resultD == -1 && !encontro) {
                temp3[0] = taxiX+1;
                temp3[1] = taxiY;
                pila.push(temp3);
                ultPosX = taxiX;
                ultPosY = taxiY;
                taxiX++;
                encontro = true;
            }            
            
            if(!encontro) {
                
                costoMenor = auxCostoMenor(resultR, resultL, resultU, resultD);
                if (costoMenor == 5) {
                    int costoSimul1 = auxSimular(taxiX+1, taxiY, taxiX, taxiY, destinoX, destinoY);
                    int costoSimul2 = auxSimular(taxiX, taxiY+1, taxiX, taxiY, destinoX, destinoY);
                    if(costoSimul1 < costoSimul2)
                        costoMenor = 1;
                    else
                        costoMenor = 2;
                }
                if (costoMenor == 6) {
                    int costoSimul1 = auxSimular(taxiX-1, taxiY, taxiX, taxiY, destinoX, destinoY);
                    int costoSimul2 = auxSimular(taxiX, taxiY-1, taxiX, taxiY, destinoX, destinoY);
                    if(costoSimul1 < costoSimul2)
                        costoMenor = 3;
                    else
                        costoMenor = 4;
                }
                switch(costoMenor) {
                    //Mover derecha
                    case 2:
                        ultPosX = taxiX;
                        ultPosY = taxiY;
                        taxiY++;
                        break;
                    //Mover izquierda
                    case 4:
                        ultPosX = taxiX;
                        ultPosY = taxiY;
                        taxiY--;
                        break;
                    //Mover arriba
                    case 3:
                        ultPosX = taxiX;
                        ultPosY = taxiY;
                        taxiX--;
                        break;
                    //Mover abajo
                    case 1:
                        ultPosX = taxiX;
                        ultPosY = taxiY;
                        taxiX++;
                        break;
                }
            }            
        }
    }
    
    //Imprime camino de taxi para llevar cliente
    public void hacerViaje() {
        ArrayList<Character> tempFila = new ArrayList<>();
        int[] tempNum = new int[2];
        Stack<int[]> tempPila = new Stack<>();
        
        //Se da vuelta a la pila
        tempNum = pila.pop();
        while(!pila.empty()) {
            tempPila.push(tempNum);
            tempNum = pila.pop();
        }
        
        //        
        while(!tempPila.empty()) {
            tempNum = tempPila.pop();
            if(tempPila.empty()) {
                tempFila = matriz.get(tempNum[0]);
                tempFila.set(tempNum[1], 'D');
                matriz.set(tempNum[0], tempFila);
            }
            else {
                //Se borra el taxi actual
                tempFila = matriz.get(tempNum[0]);
                tempFila.set(tempNum[1], 'G');
                matriz.set(tempNum[0], tempFila);
                //Se saca siguiente posición del taxi
                tempNum = tempPila.get(tempPila.size()-1);
                tempFila = matriz.get(tempNum[0]);
                tempFila.set(tempNum[1], 'D');
                matriz.set(tempNum[0], tempFila);
            }            
            try {
                sleep(Aplicacion.getAnimar());
            } catch (InterruptedException ex) {
                Logger.getLogger(Aplicacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
    }
    
    //Calcula costo menor y devuelve indicador según ganador
    public int auxCostoMenor(int right, int left, int up, int down) {
        int retValue1, retValue2;
        int lower1, lower2;
        
        if (down <= right) {
            if (down == right) {
                lower1 = down;
                retValue1 = 5;
            }
            else {
                lower1 = down;
                retValue1 = 1;
            }            
        }
        else {
            lower1 = right;
            retValue1 = 2;
        }

        if (up <= left) {
            if (up == left) {
                lower2 = up;
                retValue2 = 6;
            }
            else {
                lower2 = up;
                retValue2 = 3;
            }            
        }
        else {
            lower2 = left;
            retValue2 = 4;
        }

        if (lower1 <= lower2)
            return retValue1;
        else         
            return retValue2;
    }
    
    //Calcula costo menor y devuelve dicho costo
    public int auxCostoMenor2(int right, int left, int up, int down) {        
        int lower1, lower2;
        
        if (down <= right) {            
            lower1 = down;
        }
        else {
            lower1 = right;
        }

        if (up <= left) {            
            lower2 = up;
        }
        else {
            lower2 = left;            
        }

        if (lower1 <= lower2)
            return lower1;
        else         
            return lower2;
    }
    
    //Verifica si alrededor de posición 1 está la posición 2
    public boolean auxEncontro(int x1, int y1, int x2, int y2) {
        //Abajo
        if (x1+1 == x2 && y1 == y2) {
            return true;
        }
        //Arriba
        if (x1-1 == x2 && y1 == y2) {
            return true;
        }
        //Izquierda
        if (x1 == x2 && y1-1 == y2) {
            return true;
        }
        //Derecha
        if (x1 == x2 && y1+1 == y2) {
            return true;
        }
        return false;
    }
    
    //Simula calculo de costo en un paso siguiente, usada en caso de empate
    public int auxSimular(int x1, int y1, int pUltX, int pUltY, int destinoX, int destinoY) {
        int resultR, resultL, resultU, resultD;
        resultR = 1000;
        resultL = 1000;
        resultU = 1000;
        resultD = 1000;
        //Se valida posición a la derecha
        if(matriz.get(x1).get(y1+1) == ' ' && y1+1 != pUltY) {
            //Valida si en la posición derecha, está a la par del destino
            if (auxEncontro(x1, y1+1, destinoX, destinoY)) {
                resultR = -1;
            }
            else {
                //Heurística para cálculo de ruta
                resultR = Math.abs(x1 - destinoX) + Math.abs(y1+1 - destinoY);
            }                
        }
        //Se valida posición a la izquierda
        if(matriz.get(x1).get(y1-1) == ' ' && y1-1 != pUltY) {
            //Valida si en la posición izquierda, está a la par del destino
            if (auxEncontro(x1, y1-1, destinoX, destinoY)) {
                resultL = -1;
            }
            else {
                //Heurística para cálculo de ruta
                resultL = Math.abs(x1 - destinoX) + Math.abs(y1-1 - destinoY);
            }                
        }
        //Se valida posición arriba
        if(matriz.get(x1-1).get(y1) == ' ' && x1-1 != pUltX) {
            //Valida si en la posición arriba, está a la par del destino
            if (auxEncontro(x1-1, y1, destinoX, destinoY)) {
                resultU = -1;
            }
            else {
                //Heurística para cálculo de ruta
                resultU = Math.abs(x1-1 - destinoX) + Math.abs(y1 - destinoY);
            }                
        }
        //Se valida posición abajo
        if(matriz.get(x1+1).get(y1) == ' ' && x1+1 != pUltX) {
            //Valida si en la posición abajo, está a la par del destino
            if (auxEncontro(x1+1, y1, destinoX, destinoY)) {
                resultD = -1;
            }
            else {
                //Heurística para cálculo de ruta
                resultD = Math.abs(x1+1 - destinoX) + Math.abs(y1 - destinoY);
            }                
        }
        return auxCostoMenor2(resultR, resultL, resultU, resultD);
    }
    
    //Agrega cliente en cuadra especifica con destino especifico
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
    
    //Agrega un número específico de clientes
    public void agregarClientes(int pCantidad) {
        Random random = new Random();
        int random1;
        ArrayList<int[]> array = new ArrayList<>();
        Iterator<Character> cuadrasColl;        
        //temp1 almacenará pos de cuadra escogida
        int[] temp1;
        //temp2 almacenará nombre de cuadra escogida, en caso de que sea nombrada
        Character temp2;                
        
        //Ciclo para cantidad de clientes a ingresar
        //Se hace multiplicado por 2 para reutilizar código, calculando randoms
        //de origen y destino
        for(int i=1; i<=pCantidad; i++) {
            //temp3 almacenará array para cliente
            int[] temp3 = new int[4];
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
            System.out.println("Agregando cliente en array" + array.size() + " en pos " + temp3[0] + " ," + temp3[1]);
            array.add(temp3);
            modifMatriz('0', temp3);            
        }
        
        for (int i = 0; i<array.size(); i++) {
            System.out.println("En array: " + array.get(i)[0] + ", " + array.get(i)[1]);            
        }
        
        for (int i = 0; i<array.size(); i++) {
            clientes.put(contadorClientes, array.get(i));
            contadorClientes++;
        }
        
        System.out.println("Hay los siguientes clientes:");
        for (int j = 0; j<contadorClientes; j++) {
            System.out.println("i: " + j + " " + clientes.get(j)[0] + ", " + clientes.get(j)[1]);
        }
    }
    
    //Elimina todos los clientes del mapa
    public void eliminarClientes() {
        System.out.println("Cantidad de clientes: " + clientes.size());
        int clave2;
        Iterator<Integer> clientesColl2 = clientes.keySet().iterator();
        System.out.println("Hay los siguientes clientes:");
        while(clientesColl2.hasNext()){
            clave2 = clientesColl2.next();
            System.out.println(clave2 + " " + clientes.get(clave2)[0] + ", " + clientes.get(clave2)[1] + " - " + clientes.get(clave2)[2] + ", " + clientes.get(clave2)[3]);
        }
        
        System.out.println("Hay los siguientes clientes2:");
        for (int i = 0; i<contadorClientes; i++) {
            System.out.println("i " + i + " " + clientes.get(i)[0] + ", " + clientes.get(i)[1]);
        }
        
        int clave;
        ArrayList<Character> tempFila = new ArrayList<>();
        Iterator<Integer> clientesColl = clientes.keySet().iterator();
        while(clientesColl.hasNext()) {
            clave = clientesColl.next();
            System.out.println("Eliminando " + clave + " en pos " + clientes.get(clave)[0] + ", " + clientes.get(clave)[1]);
            tempFila = matriz.get(clientes.get(clave)[0]);
            tempFila.set(clientes.get(clave)[1], '-');
            matriz.set(clientes.get(clave)[0], tempFila);
        }
        clientes.clear();
    }
}
