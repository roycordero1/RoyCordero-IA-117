#include <stdio.h>
#include <string.h>
#include <math.h>
void displayPathtoPrincess(int n, char grid[101][101]){
    //logic here
    if (grid[0][0] == 'p') {
        for(int i=0; i<(n-1)/2; i++) {
            printf("LEFT\n");
        }
        for(int j=0; j<(n-1)/2; j++) {
            printf("UP\n");
        }
    }
    else if (grid[0][n-1] == 'p') {
        for(int i=0; i<(n-1)/2; i++) {
            printf("RIGHT\n");
        }
        for(int j=0; j<(n-1)/2; j++) {
            printf("UP\n");
        }        
    }
    else if (grid[n-1][0] == 'p') {
        for(int i=0; i<(n-1)/2; i++) {
            printf("LEFT\n");
        }
        for(int j=0; j<(n-1)/2; j++) {
            printf("DOWN\n");
        }
    }
    else {
        for(int i=0; i<(n-1)/2; i++) {
            printf("RIGHT\n");
        }
        for(int j=0; j<(n-1)/2; j++) {
            printf("DOWN\n");
        }
    }
}
int main(void) {

    int m;
    scanf("%d", &m);
    char grid[101][101]={};
    char line[101];

    for(int i=0; i<m; i++) {
        scanf("%s", line);
        strcpy(grid[i], line);
    }
    displayPathtoPrincess(m,grid);
    return 0;
}
