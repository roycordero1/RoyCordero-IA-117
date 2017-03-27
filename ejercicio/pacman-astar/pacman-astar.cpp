#include <cmath>
#include <cstdio>
#include <vector>
#include <list>
#include <iostream>
#include <algorithm>
using namespace std;

//Retorna: 1-si el mas bajo es up || 2-si el mas bajo es left || 3-si el mas bajo es right || 4-si el mas bajo es down
int whoIsLower(int up, int left, int right, int down) {
    int retValue1, retValue2;
    int lower1, lower2;    

    if (up <= left) {
        lower1 = up;
        retValue1 = 1;
    }
    else {
        lower1 = left;
        retValue1 = 2;
    }

    if (right <= down) {
        lower2 = right;
        retValue2 = 3;
    }
    else {
        lower2 = down;
        retValue2 = 4;
    }

    if (lower1 <= lower2)
        return retValue1;
    else         
        return retValue2;
}


int main(void) {
    /* Enter your code here. Read input from STDIN. Print output to STDOUT */   
    int pacmanX, pacmanY, foodX, foodY, cols, rows;
    /*
    scanf("%d", pacmanY);
    scanf("%d", pacmanX);
    scanf("%d", foodY);
    scanf("%d", foodX);
    scanf("%d", rows);
    scanf("%d", cols);    
    */
    
    cin>>pacmanY;
    cin>>pacmanX;
    cin>>foodY;
    cin>>foodX;
    cin>>rows;
    cin>>cols;
    
    /*
    pacmanY = 3;
    pacmanX = 3;
    foodY = 3;
    foodX = 0;
    rows = 4;
    cols = 4;
    */
    
    char grid[rows][cols];
    
    /*
    grid[0][0] = '%';
    grid[0][1] = '%';
    grid[0][2] = '%';
    grid[0][3] = '%';
    grid[1][0] = '-';
    grid[1][1] = '-';
    grid[1][2] = '-';
    grid[1][3] = '-';
    grid[2][0] = '-';
    grid[2][1] = '%';
    grid[2][2] = '%';
    grid[2][3] = '-';
    grid[3][0] = '.';
    grid[3][1] = '%';
    grid[3][2] = '-';
    grid[3][3] = 'P';
    */

    
    //Se llena el grid (hackerrank lo llena con el stdin)
    for (int i=0; i<rows;i++) {
        for (int j=0; j<cols;j++) {
            //scanf("%d", grid[i][j]);
            cin>>grid[i][j];
        }    
    }
    
    
    
    list<int> path;                     //Lista donde se incluiran las posiciones que el pacman ira recorriendo, van de 2 en 2 (X  Y)
    

    int resultUp, resultLeft, resultRight, resultDown;          //Variables para almacenar el resultado del costo de moverse en alguna direccion, -1 en alguno de los resultados significa que encontro la comida

    int lowerCost;

    //Variables utilizadas para guardar la ultima posicion del recorrido realizado 
    int prevX = -1;                                            
    int prevY = -1;
    int contador = 0;

 //   cout<<"pacmanY "<<pacmanY;
    while (!(pacmanY == foodY && pacmanX == foodX)) {        
        resultUp = 1000;
        resultLeft = 1000;
        resultRight = 1000;
        resultDown = 1000;

        //cout<<"ResultRight: "<<resultRight;        

        // Validacion de movimientos y setteo de variables de resultados

        //Valida que se pueda mover hacia arriba y que el movimento que se va a hacer no sea el ultimo movimiento hecho
        if (pacmanY-1 >= 0 && !(pacmanY-1 == prevY && pacmanX == prevX)) {
            //cout<<"Entro a validar UP\n";
            if (grid[pacmanY-1][pacmanX] == '-') {
                //cout<<"Hay espacio UP\n";
                resultUp = abs((pacmanY-1) - foodY) + abs(pacmanX - foodX);
            }
            else {
                if (grid[pacmanY-1][pacmanX] == '.') {
                    resultUp = -1;
                }
                else {
                    if (grid[pacmanY-1][pacmanX] == 'V') {
                        //cout<<"Encontro V Ar\n";
                        resultUp = 1000;
                    }
                }
            }            
        }
        //Valida que se pueda mover hacia izquierda y que el movimento que se va a hacer no sea el ultimo movimiento hecho
        if (pacmanX-1 >= 0 && !(pacmanY == prevY && pacmanX-1 == prevX)) {
            //cout<<"Entro a validar LEFT\n";
            if (grid[pacmanY][pacmanX-1] == '-') {
                //cout<<"Hay espacio LEFT\n";
                resultLeft = abs(pacmanY - foodY) + abs((pacmanX - 1) - foodX);                
            }
            else {
                if (grid[pacmanY][pacmanX-1] == '.') {
                    resultLeft = -1;
                }
                else {
                    if (grid[pacmanY][pacmanX-1] == 'V') {
                        //cout<<"Encontro V L\n";
                        resultLeft = 1000;
                    } 
                }
            }               
        }
        //Valida que se pueda mover hacia derecha y que el movimento que se va a hacer no sea el ultimo movimiento hecho
        if (pacmanX+1 <= cols-1 && !(pacmanY == prevY && pacmanX+1 == prevX)) {
            //cout<<"Entro a validar RIGHT\n";
            if (grid[pacmanY][pacmanX+1] == '-') {
                //cout<<"Hay espacio RIGHT\n";
                resultRight = abs(pacmanY - foodY) + abs((pacmanX + 1) - foodX);                
            }
            else{
                if (grid[pacmanY][pacmanX+1] == '.') {
                    resultRight = -1;
                }
                else {
                    if (grid[pacmanY][pacmanX+1] == 'V') {
                        //cout<<"Encontro V R\n";
                        resultRight = 1000;
                    }
                }
            }            
        }
        //Valida que se pueda mover hacia abajo y que el movimento que se va a hacer no sea el ultimo movimiento hecho
        if (pacmanY+1 <= rows-1 && !(pacmanY+1 == prevY && pacmanX == prevX)) {
            //cout<<"Entro a validar DOWN\n";
            if (grid[pacmanY+1][pacmanX] == '-') {
                //cout<<"Hay espacio DOWN\n";
                resultDown = abs((pacmanY+1) - foodY) + abs(pacmanX - foodX);                
            }
            else{
                if (grid[pacmanY+1][pacmanX] == '.') {
                    resultDown = -1;
                }
                else {
                   if (grid[pacmanY+1][pacmanX] == 'V') {
                        //cout<<"Encontro V Ab\n";
                        resultDown = 1000;
                    } 
                }
            }            
        }

        //cout<<"ResultUp: "<<resultUp<<" ResultLeft: "<<resultLeft<<" ResultRight: "<<resultRight<<" ResultDown: "<<resultDown<<"\n";    

        //Pacman encontro la comida
        if (resultUp == -1 || resultLeft == -1 || resultRight == -1 || resultDown == -1) {
            //cout<<"Encontró comida\n";
            //cout<<"Posicion del pacman al final: "<<pacmanY<<" "<<pacmanX<<"\n";
            //cout<<"Posicion de comida: "<<foodY<<" "<<foodX<<"\n";
            path.push_back(pacmanY);
            path.push_back(pacmanX);
            path.push_back(foodY);
            path.push_back(foodX);
            pacmanY = foodY;
            pacmanX = foodX;
        }
        else {
            //Pacman esta encerrado, debe devolverse
            if (resultUp == 1000 && resultLeft == 1000 && resultRight == 1000 && resultDown == 1000) {
                //cout<<"Entra a pacman encerrado\n";
                grid[pacmanY][pacmanX] = 'V';
                pacmanX = path.back();
                path.pop_back();                
                pacmanY = path.back();
                path.pop_back();

                if (path.size() == 0){
                    prevX = -1;
                    prevY = -1;
                }
                else {                    
                    prevX = path.back();
                    path.pop_back();                    
                    prevY = path.back();
                    path.push_back(prevX);
                }
            }
            else {
                //cout<<"Entra a calcular menor costo\n";
                lowerCost = whoIsLower(resultUp, resultLeft, resultRight, resultDown);
                //cout<<"El menor costo es: "<<lowerCost<<"\n";
                //Se almacena posición en pila y variables antes de moverse                
                path.push_back(pacmanY);
                path.push_back(pacmanX);
                prevY = pacmanY;
                prevX = pacmanX;
                switch (lowerCost) {
                    case 1:
                        pacmanY -= 1;
                        //cout<<"Mueve UP\n";
                        break;
                    case 2:
                        pacmanX -= 1;
                        //cout<<"Mueve LEFT\n";
                        break;
                    case 3:
                        pacmanX += 1;
                        //cout<<"Mueve RIGHT\n";
                        break;
                    case 4:
                        pacmanY += 1;
                        //cout<<"Mueve DOWN\n";
                        break;
                }
            }
        }
        contador++;
    }

    int moves = path.size()/2;
    cout<<moves-1<<"\n";
    
    int x, y;

    for (int i=0; i < moves; i++) {
        x = path.front();
        path.pop_front();
        y = path.front();        
        path.pop_front();
        cout<<x<<" "<<y<<"\n";
    }
    return 0;
}