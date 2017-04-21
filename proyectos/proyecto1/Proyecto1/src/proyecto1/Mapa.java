package proyecto1;

import java.util.ArrayList;

public class Mapa {
    private ArrayList<ArrayList<Character>> matriz;
    private int taxiX;
    private int taxiY;
    private int ultPosX;
    private int ultPosY;
    private String ultMov;
    
    public Mapa() {
        matriz = new ArrayList<>();
        ultPosX = -1;
        ultPosY = -1;
    }
    
    public void setMatriz(ArrayList<ArrayList<Character>> pMatriz) {
        matriz = pMatriz;
        setTaxi(matriz);
    }
    
    public ArrayList<ArrayList<Character>> getMatriz() {
        return matriz;
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
}
