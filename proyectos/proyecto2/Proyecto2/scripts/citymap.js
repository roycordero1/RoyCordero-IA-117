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
    this.buildings = []
  }

  getMap() {
    return this._matrix;
  }

  copyMap(map) {
    for (var i = 0; i<this._matrix.length; i++) {
      map.push([]);
      for (var j = 0; j<this._matrix[i].length; j++) {
        map[i].push(this._matrix[i][j]);
      }
    }
    return map;
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
    this._assignTypetoBuildings();
    this._assignWorkHometoClients();
  }

  _initMapObjects(i, j) {
    var letras = "abcefghijklmnopqrstuvwxyzABCEFGHIJKLMNOPQRSTUVWXYZ";
    if (this._matrix[i][j] == "D") {
      var taxi = new Taxi(this.taxis.length+1, i, j, this);
      this.taxis.push(taxi);
    }
    else if (this._matrix[i][j] == "0") {
      var client = new Client(this.clients.length+1, [i, j], this);
      this.clients.push(client);
    }
    else if (letras.indexOf(this._matrix[i][j], 0) != -1) {
      var build = new Building(this.buildings.length+1, i, j, this._matrix[i][j], this);
      this.buildings.push(build);
    }
  }

  _assignTypetoBuildings() {
    for(var i = 0; i<this.buildings.length; i++) {
      if (i<this.buildings.length/2)
        this.buildings[i].setBuildingType("Home");
      else
        this.buildings[i].setBuildingType("Work");
    }
  }

  _assignWorkHometoClients() {
    for(var i = 0; i<this.clients.length; i++) {
      var buildId = this._checkBuildinClientIs([this.clients[i].pos[0], this.clients[i].pos[1]]);
      if (buildId != -1) {
        var actualBuild = this.buildings[buildId];
        var destBuild = this._chooseDestinationBuildforClient(buildId);
        if (this.buildings[buildId].getBuildingType() == "Home")
          this.clients[i].setWorkHome(destBuild, actualBuild);
        else
          this.clients[i].setWorkHome(actualBuild, destBuild);
      }
      else {
        this.clients.splice(i, 1);
      }
    }
  }

  _checkBuildinClientIs(pos) {
    for(var i = 0; i<this.buildings.length; i++) {
      for(var j = 0; j<this.buildings[i].sidewalks.length; j++) {
        if (this.buildings[i].sidewalks[j][0] == pos[0] && this.buildings[i].sidewalks[j][0] == pos[0])
          return i;
      }
    }
    return -1;
  }

  _chooseDestinationBuildforClient(buildId) {
    var buildings = [];
    if (this.buildings[buildId].getBuildingType() == "Home") {
      for(var i = 0; i<this.buildings.length; i++) {
        if (this.buildings[i].getBuildingType() == "Work")
          buildings.push(this.buildings[i])
      }
    }
    else {
      for(var i = 0; i<this.buildings.length; i++) {
        if (this.buildings[i].getBuildingType() == "Home")
          buildings.push(this.buildings[i])
      }
    }
    var random = Math.floor((Math.random() * buildings.length));
    return buildings[random];
  }

  moveTaxi(oldPos, newPos) {
    var taxiInOldPos = false;
    for(var i=0; i<this.taxis.length; i++) {
      if (this.taxis[i].pos[0] == oldPos[0] && this.taxis[i].pos[1] == oldPos[1]) {
        taxiInOldPos = true;
        break;
      }
    }
    if(!taxiInOldPos)
      this._matrix[oldPos[0]][oldPos[1]] = "&nbsp";
    this._matrix[newPos[0]][newPos[1]] = "D";
  }

  whichClient(clientPosition) {
    for(var i = 0; i<this.clients.length; i++) {
      if (this.clients[i].pos[0] == clientPosition[0] && this.clients[i].pos[1] == clientPosition[1])
        return i;
    }
    return -1;
  }

  getClientDestinationBuild(clientId) {
    var client = this.clients[clientId];
    if (client.state1() == "inhome")
      return client.getWorkBuild();
    else
      return client.getHomeBuild();
  }

  writeClientOriginDest(originPos, destPos) {
    this._matrix[originPos[0]][originPos[1]] = "(";
    this._matrix[destPos[0]][destPos[1]] = ")";
  }

  unwriteClientOriginDest(originPos, destPos) {
    if(this._matrix[originPos[0]-1][originPos[1]] == "-")
      this._matrix[originPos[0]][originPos[1]] = "|";
    else
      this._matrix[originPos[0]][originPos[1]] = "-";

    if(this._matrix[destPos[0]-1][destPos[1]] == "-")
      this._matrix[destPos[0]][destPos[1]] = "|";
    else
      this._matrix[destPos[0]][destPos[1]] = "-";
  }

  test1() {
    console.log("Buildings")
    for(var i = 0; i<this.buildings.length; i++) {
      console.log(this.buildings[i].id() + " " + this.buildings[i].getBuildingType() + " " + this.buildings[i].buildName);
    }
    console.log("Clients")
    for(var j = 0; j<this.clients.length; j++) {
      var a = this._matrix[this.clients[j].pos[0]][this.clients[j].pos[1]];
      var b = this._matrix[this.clients[j].home[0]][this.clients[j].home[1]];
      var c = this._matrix[this.clients[j].work[0]][this.clients[j].work[1]];
      console.log(this.clients[j].id() + " " + a + " " + b + " " + c);
    }
  }
  //**Revisar si se usa**
  writeToMap(oldPos, newObj1, newPos, newObj2) {
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