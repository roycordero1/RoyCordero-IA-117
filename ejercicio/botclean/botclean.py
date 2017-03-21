#!/usr/bin/python

# Head ends here
def next_move(posr, posc, board):    
    if (board[posr][posc] == 'd'):
        print("CLEAN")
    else:
        #Se recorre la lista buscando dirts
        lista = []
        for i in range(5):
            for j in range(5):
                if (board[i][j] == 'd'):
                    lista.append([i, j])        
        
        #Se define dirt más cercano
        filaCercano = 0
        colCercano = 0
        sumaMenor = 99
        for x in range(len(lista)):
            if ((abs(lista[x][0] - posr) + abs(lista[x][1] - posc)) < sumaMenor):                
                filaCercano = lista[x][0]
                colCercano = lista[x][1]
                sumaMenor = abs(lista[x][0] - posr) + abs(lista[x][1] - posc)                
                
        #Se define la posición a moverse
        if (colCercano > posc):
            print("RIGHT")
        elif (colCercano < posc):
            print("LEFT")
        else:
            if (filaCercano > posr):
                print("DOWN")
            else:
                print("UP")

# Tail starts here
if __name__ == "__main__":
    pos = [int(i) for i in input().strip().split()]
    board = [[j for j in input().strip()] for i in range(5)]
    next_move(pos[0], pos[1], board)
