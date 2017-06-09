/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/

/*
* Class Building
* Manage building general functions
*/
class Building {

  constructor(id, i, j, name, ownerMap) {
    this._id = id;
    this._ownerMap = ownerMap;
    this.buildName = name;
    this.pos = [i, j];
    this.sidewalks = [];
    this.buildingType = "";
    this.peopleQuant = 0;
    this._setSidewalks();
  }

  id() {
    return this._id;
  }  

  getPos() {
    return this.pos;
  }

  getBuildingType() {
    return this.buildingType;
  }

  getBuildingName() {
    return this.buildName;
  }

  setBuildingType(buildingType) {
    this.buildingType = buildingType;
  }

  _setSidewalks() {
    for(var i = 0; i < 7; i++) {
      switch(i) {
        case 0:
          this.sidewalks.push([this.pos[0]-1, this.pos[1]]);
        case 1:
          this.sidewalks.push([this.pos[0]-1, this.pos[1]+1])
        case 2:
          this.sidewalks.push([this.pos[0], this.pos[1]+1])
        case 3:
          this.sidewalks.push([this.pos[0]+1, this.pos[1]+1])
        case 4:
          this.sidewalks.push([this.pos[0]+1, this.pos[1]])
        case 5:
          this.sidewalks.push([this.pos[0]+1, this.pos[1]-1])
        case 6:
          this.sidewalks.push([this.pos[0], this.pos[1]-1])
        case 7:
          this.sidewalks.push([this.pos[0]-1, this.pos[1]-1])
      }
    }
  }

}