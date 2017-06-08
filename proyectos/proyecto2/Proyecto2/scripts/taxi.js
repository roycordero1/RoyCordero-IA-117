/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/

/*
* States classes for the taxi
*/
class Stopped extends State {
  accepts(event, current) {
    console.log("[Stopped] accepts " + JSON.stringify(event));
    return event.msg == "Detener";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Stopped] onEnter");
    fsm.owner().stopTaxi();
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[Stopped] onUpdate");
    fsm.owner().show();
  }
}

class Walking extends State {
  accepts(event, current) {
    console.log("[Walking] accepts " + JSON.stringify(event));
    return event.msg == "Pasear";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Walking] onEnter");
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[Walking] onUpdate");
    fsm.owner().show();
    fsm.owner().walk();
  }
}

class Searching extends State {
  accepts(event, current) {
    console.log("[Searching] accepts " + JSON.stringify(event));
    return event.msg == "Buscar";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Searching] onEnter");
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[Searching] onUpdate");
    fsm.owner().show();
    fsm.owner().walk();
  }
}

/*
* Class Taxi
* Manage taxi general functions
*/
const states1 = [new Stopped(), new Walking(), new Searching()];
const states2 = [/*new showOff()*/];
const states3 = [/*new routeOff()*/];

class Taxi {

  constructor(id, i, j, ownerMap) {
    this._id = id;
    this._ownerMap = ownerMap;
    this.pos = [i, j];
    this.prevPos = [i, j];
    this.route = [];
    this._lastRowMove = "down";

    this._state1 = "detenido";
    this._state2 = "mostrarOff";
    this._state3 = "rutaOff";
    const fsm1 = new Fsm(this, states1, "fsm1-taxi");
    const fsm2 = new Fsm(this, states2, "fsm2-taxi");
    const fsm3 = new Fsm(this, states3, "fsm3-taxi");
    eventEmiter.register(fsm1);
    eventEmiter.register(fsm2);
    eventEmiter.register(fsm3);
  }

  id() {
    return this._id;
  }

  state1() {
    return this._state1;
  }
  state2() {
    return this._state2;
  }
  state3() {
    return this._state3;
  }

  stopTaxi() {
    this._state1 = "detenido";
  }

  walk() {
    this._state1 = "paseando";
    var move;
    if (this._lastRowMove == "down")
      move = this._auxChooseNextMove([[0, 1], [0, -1], [1, 0], [-1, 0]]);
    else
      move = this._auxChooseNextMove([[0, 1], [0, -1], [-1, 0], [1, 0]]);
    this._auxMakeMove(move);

  }

  _auxChooseNextMove(movesList) {
    var map = this._ownerMap.getMap();
    var taxiRow = this.pos[0];
    var taxiCol = this.pos[1];
    var isPrevPos1 = taxiRow+movesList[0][0] == this.prevPos[0] && taxiCol+movesList[0][1] == this.prevPos[1];
    var isPrevPos2 = taxiRow+movesList[1][0] == this.prevPos[0] && taxiCol+movesList[1][1] == this.prevPos[1];
    var isPrevPos3 = taxiRow+movesList[2][0] == this.prevPos[0] && taxiCol+movesList[2][1] == this.prevPos[1];
    var isPrevPos4 = taxiRow+movesList[3][0] == this.prevPos[0] && taxiCol+movesList[3][1] == this.prevPos[1];
    var pos1toCalc = map[taxiRow+movesList[0][0]][taxiCol+movesList[0][1]];
    var pos2toCalc = map[taxiRow+movesList[1][0]][taxiCol+movesList[1][1]];
    var pos3toCalc = map[taxiRow+movesList[2][0]][taxiCol+movesList[2][1]];
    var pos4toCalc = map[taxiRow+movesList[3][0]][taxiCol+movesList[3][1]];

    if (!isPrevPos1 && (pos1toCalc == "&nbsp" || pos1toCalc == "D"))
      return 1;
    else if (!isPrevPos2 && (pos2toCalc == "&nbsp" || pos2toCalc == "D"))
      return 2;
    else if (!isPrevPos3 && (pos3toCalc == "&nbsp" || pos3toCalc == "D"))
      return 3;
    else if (!isPrevPos4 && (pos4toCalc == "&nbsp" || pos4toCalc == "D"))
      return 4;
    else
      return 0;
  }

  _auxMakeMove(move) {
    switch (move) {
      case 1:
        this.prevPos[0] = this.pos[0];
        this.prevPos[1] = this.pos[1];
        this.pos[1]++;
        this._ownerMap.moveTaxi(this.prevPos, this.pos);
        break;
      case 2:
        this.prevPos[0] = this.pos[0];
        this.prevPos[1] = this.pos[1];
        this.pos[1]--;
        this._ownerMap.moveTaxi(this.prevPos, this.pos);
        break;
      case 3:
        if (this._lastRowMove == "down") {
          this.prevPos[0] = this.pos[0];
          this.prevPos[1] = this.pos[1];
          this.pos[0]++;
          this._ownerMap.moveTaxi(this.prevPos, this.pos);
        }
        else {
          this.prevPos[0] = this.pos[0];
          this.prevPos[1] = this.pos[1];
          this.pos[0]--;
          this._ownerMap.moveTaxi(this.prevPos, this.pos);
        }
        break;
      case 4:
        if (this._lastRowMove == "down") {
          this.prevPos[0] = this.pos[0];
          this.prevPos[1] = this.pos[1];
          this.pos[0]--;
          this._lastRowMove = "up"
          this._ownerMap.moveTaxi(this.prevPos, this.pos);
        }
        else {
          this.prevPos[0] = this.pos[0];
          this.prevPos[1] = this.pos[1];
          this.pos[0]++;
          this._lastRowMove = "down"
          this._ownerMap.moveTaxi(this.prevPos, this.pos);
        }
    }
  }

  lookForClient() {

  }

  show() {
    console.log("+ Taxi " + this._id + " está " + this._state1 + ", " + this._state2 + " y " + this._state3);
  }
}