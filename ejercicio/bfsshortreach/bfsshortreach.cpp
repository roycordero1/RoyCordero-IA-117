#include <cmath>
#include <iostream>
#include <vector>
#include <set>
#include <list>
#include <algorithm>
#include <cstdio>
using namespace std;

set<int>::const_iterator pos;					//Iterador utilizado para recorrer y simular las aristas del grafo

int main() {
    int q;										//Cantidad de grafos
    cin>>q;
    while(q > 0) {            
        int n, M;								//Cantidad de nodos, Cantidad de aristas
        cin>>n;
        cin>>M;
        
        vector<set<int>> grafo;		//Vector utilizado para como grafo
        grafo.resize(n+1);			//Se reserva un campo mas en el grafo 
        for(int i = 0; i < M; i++) {
            int u, v;				//Vertices de cada nodo
            cin>>u;
            cin>>v;
            grafo[u].insert(v);		//Se agregan las aristas en ambas direcciones ya que el grafo es no dirigido
            grafo[v].insert(u);
        }
        bool visitados[n+1] = {false};	//Array de para registrar que un nodo fue visitado, cada campo del array representa un nodo (indice del array = numero de nodo), se reserva un campo mas pues en indice cero no representa ningun nodo todos empiezan en 1
        int distancias[n+1] = {0};		//Array para registrar las distancias recorridas (6) de cada nodo, cada campo representa un nodo (indice del array = numero de nodo), se reserva un campo mas pues en indice cero no representa ningun nodo todos empiezan en 1
        
        list<int> cola; 				//Cola de nodos
        int nodo_inicial;				//Numero de nodo inicial
        cin>>nodo_inicial;				
        
        cola.push_back(nodo_inicial);	//Se agrega el nodo inicial a la cola, hace un append
        visitados[nodo_inicial] = true;	//Se marca el nodo inicial como visitado
        
        while(!cola.empty()) {			//Algoritmo BFS
            
            int nodo = cola.front();	// Asigna a nodo el primer elemento de la cola
            // Se agregan todos los nodos hijos a la cola, si no estan visitados aun
            for(pos = grafo[nodo].begin(); pos != grafo[nodo].end(); ++pos) {	
                if(!visitados[*pos]) {
                    cola.push_back(*pos);				//Se agrega el hijo a la cola

                    distancias[*pos] += (distancias[nodo] + 6);	//Hay conexion y el nodo no se ha visitado, se suma la distancia en el correspondiente campo del array de distancias para el nodo
                    visitados[*pos] = true;						//Se marca el nodo como visitado en el correspondiente campo del array de visitados para el nodo
                }
            } 
            // Saca los nodos visitados hasta vaciar la cola
            cola.pop_front();
        }
        for(int i = 1; i <= n; i++) {		//Ciclo de impresion de las distancias
            if(i != nodo_inicial) {
                if(distancias[i] == 0) {
                    cout<<"-1 ";
                } else {
                    cout<<distancias[i]<<" ";
                }
            }
        }
        cout<<"\n";
        q--;			//Se disminuyen el contador de grafos pasar al siguiente
    }
    return 0;
}