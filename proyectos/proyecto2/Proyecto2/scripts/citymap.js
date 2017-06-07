/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/

/*
* Class CityMap
* Manage map general functions
*/
class CityMap {

  constructor() {
    this._matrix = [[""]];
    this.taxis = []
    this.clients = []
    this.blocks = []
  }

  getMap() {
    return this._matrix;
  }

  loadMap(fileMap) {
    this._createMatrix(fileMap);
  }

  _createMatrix(fileMap) {
    var lines = fileMap.split('\n');
    this._matrix = [[]];
    for (var i = 0; i<lines.length; i++) {
      for (var j = 0; j<lines[i].length; j++) {
        this._matrix[i].push(lines[i][j]);
      }
      this._matrix.push([]);
    }
    this._createMapObjects();
  }

  _createMapObjects() {
    for (var i = 0; i<this._matrix.length; i++) {
      for (var j = 0; j<this._matrix[i].length; j++) {
        if (this._matrix[i][j] == " ") {
          this._matrix[i][j] = "&nbsp";
        }
        else {
          this._initMapObjects(i, j);
        }        
      }      
    }
  }

  _initMapObjects(i, j) {
    var letras = "abcefghijklmnopqrstuvwxyzABCEFGHIJKLMNOPQRSTUVWXYZ";
    var taxiId = 0, clientId = 0, blockId = 0;
    if (this._matrix[i][j] == "D") {
      var taxi = new Taxi(taxiId, i, j, this);
      this.taxis.push(taxi);
      taxiId++;
    }
    else if (this._matrix[i][j] == "0") {
      var client = new Client(clientId, i, j);
      this.clients.push(client);
      clientId++;
    }
    else if (letras.indexOf(this._matrix[i][j], 0) != -1) {
      var block = new Block(blockId, i, j);
      this.blocks.push(block);
      blockId;
    }
  }

  moveTaxi(oldPos, newPos) {
    console.log("moveTaxi " + oldPos + " to " + newPos);
    var taxiInOldPos = false;
    for(var i=0; i<this.taxis.length; i++) {
      console.log("this.taxis[i].pos " + "i " + this.taxis[i].pos);
      console.log("oldPos " + oldPos);
      if (this.taxis[i].pos == oldPos) {
        taxiInOldPos = true;
        break;
      }
    }
    if(!taxiInOldPos)
      this._matrix[oldPos[0]][oldPos[1]] = "&nbsp";
    this._matrix[newPos[0]][newPos[1]] = "D";
  }

  writeToMap(oldPos, newObj1, newPos, newObj2) {
    console.log("writeMap " + oldPos + " to " + newPos);
    this._matrix[oldPos[0]][oldPos[1]] = newObj1;
    this._matrix[newPos[0]][newPos[1]] = newObj2;
  }

  printMap() {
    var printableMatrix = "";
    for (var i = 0; i<this._matrix.length; i++) {
      printableMatrix = printableMatrix + this._matrix[i].join("")  + "<br>";
    }
    document.getElementById("map").innerHTML = printableMatrix;
  }

}