/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/

/*-------------------------------------------
// State classes type 1 for the taxi
-------------------------------------------*/
class Stopped extends State {
  accepts(event, current) {
    console.log("[Stopped] accepts " + JSON.stringify(event));
    return event.msg == "Detener";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Stopped] onEnter");
    fsm.owner().setState("state1", "detenido");
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[Stopped] onUpdate");
    fsm.owner().show();
  }

  onExit(eventEmitter, fsm) {
    console.log("[Stopped] onExit");
    if(fsm._previous) {
      switch(fsm._previous.getClass()) {
        case Walking:
          eventEmiter.send({msg: "Pasear", id: "fsm1-taxi" + fsm.owner().id()});
        case Searching:
          eventEmiter.send({msg: "Buscar", id: "fsm1-taxi" + fsm.owner().id()});
        case Parking:
          eventEmiter.send({msg: "Parquear", id: "fsm1-taxi" + fsm.owner().id()});
        case Transporting:
          eventEmiter.send({msg: "Transportar", id: "fsm1-taxi" + fsm.owner().id()});
      }
    }
  }
}

class Walking extends State {
  accepts(event, current) {
    console.log("[Walking] accepts " + JSON.stringify(event));
    return event.msg == "Pasear" && !(current instanceof Transporting);
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Walking] onEnter");
    fsm.owner().setState("state1", "paseando");
    this.onUpdate(eventEmitter, fsm);
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
    return (event.msg == "Buscar" && !(current instanceof Transporting)) || event.msg == "ReanudarBuscar";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Searching] onEnter");
    fsm.owner().setState("state1", "buscando");
    this.onUpdate(eventEmitter, fsm);
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[Searching] onUpdate");
    fsm.owner().show();
    fsm.owner().walk();
    var clientPosition = fsm.owner()._lookForClient();
    if (clientPosition != "NoClient") {
      var destinationBuild = fsm.owner()._askClientDestinationBuild(clientPosition);
      var destinationPosition = destinationBuild.getSidewalks()[fsm.owner()._chooseSidewalkDestination(destinationBuild)];
      fsm.owner().setClientOriginDest(clientPosition, destinationPosition);
      var clientId = fsm.owner()._ownerMap.whichClient(clientPosition);
      fsm.owner().setClientId(clientId);
      fsm.owner()._ownerMap.writeClientOriginDest(clientPosition, destinationPosition);

      var route = fsm.owner()._chooseBetterRoute(destinationBuild);      
      fsm.owner().setRoute(route);

      eventEmiter.send({msg: "Transportar", id: "fsm1-taxi" + fsm.owner().id()});
      console.log("destinationBuild " + destinationBuild.getBuildingName());
      if (destinationBuild.getBuildingType() == "Home")
        eventEmiter.send({msg: "Moving to home", id: "cliente" + (clientId+1)});
      else if (destinationBuild.getBuildingType() == "Work")
        eventEmiter.send({msg: "Moving to work", id: "cliente" + (clientId+1)});
    }
  }

}

class Parking extends State {
  accepts(event, current) {
    console.log("[Parking] accepts " + JSON.stringify(event));
    return event.msg == "Parquear" && !(current instanceof Transporting);
  }

  onEnter(eventEmitter, fsm, event) {
    console.log("[Parking] onEnter");
    fsm.owner().setState("state1", "parqueando");
    var destinationBuild = fsm.owner()._ownerMap.whichBuild(event.param1);
    if (destinationBuild) {
      var route = fsm.owner()._chooseBetterRoute(destinationBuild);
      fsm.owner().setRoute(route);
    }      
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[Parking] onUpdate");
    fsm.owner().show();
    if (fsm.owner().getRoute().length > 0)
      fsm.owner().transport();
    else
      eventEmiter.send({msg: "Detener", id: "fsm1-taxi" + fsm.owner().id()});
  }

}

class Transporting extends State {
  accepts(event, current) {
    console.log("[Transporting] accepts " + JSON.stringify(event));
    return event.msg == "Transportar";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Transporting] onEnter");
    fsm.owner().setState("state1", "transportando");
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[Transporting] onUpdate");
    fsm.owner().show();
    if (fsm.owner().getRoute().length > 0)
      fsm.owner().transport();
    else
      eventEmiter.send({msg: "ReanudarBuscar", id: "fsm1-taxi" + fsm.owner().id()});
  }

  onExit(eventEmitter, fsm) {
    console.log("[Transporting] onExit");
    fsm.owner()._ownerMap.unwriteClientOriginDest(fsm.owner().originClientPos, fsm.owner().destClientPos);
    fsm.owner().setClientOriginDest([], []);
    var client = fsm.owner()._ownerMap.clients[fsm.owner().clientId];
    if (client.state() == "en casa") {
      client.getHomeBuild().substractPerson();
      client.getWorkBuild().addPerson();
      eventEmiter.send({msg: "Trabajar", id: "cliente" + (fsm.owner().clientId+1)});
    }
    else {
      client.getWorkBuild().substractPerson();
      client.getHomeBuild().addPerson();
      eventEmiter.send({msg: "Encasa", id: "cliente" + (fsm.owner().clientId+1)});
    }
    fsm.owner().setClientId(-1);
  }
}

/*-------------------------------------------
// State classes type 2 for the taxi
-------------------------------------------*/
class showOff extends State {
  accepts(event, current) {
    console.log("[showOff] accepts " + JSON.stringify(event));
    return event.msg == "MostrarOff";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[showOff] onEnter");
    fsm.owner().setState("state2", "mostrarOff");
    fsm.owner()._ownerMap.cleanShowRoad();
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[showOff] onUpdate");
    //fsm.owner().show();
  }
}

class showOn extends State {
  accepts(event, current) {
    console.log("[showOn] accepts " + JSON.stringify(event));
    return event.msg == "MostrarOn";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[showOn] onEnter");
    fsm.owner().setState("state2", "mostrarOn");
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[showOn] onUpdate");
    //fsm.owner().show();
  }
}

/*-------------------------------------------
// State classes type 3 for the taxi
-------------------------------------------*/
class routeOff extends State {
  accepts(event, current) {
    console.log("[routeOff] accepts " + JSON.stringify(event));
    return event.msg == "RutaOff";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[routeOff] onEnter");
    fsm.owner().setState("state3", "routeOff");
    fsm.owner()._ownerMap.cleanRouteRoad();
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[routeOff] onUpdate");
    //fsm.owner().show();
  }
}

class routeOn extends State {
  accepts(event, current) {
    console.log("[routeOn] accepts " + JSON.stringify(event));
    return event.msg == "RutaOn";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[routeOn] onEnter");
    fsm.owner().setState("state3", "routeOn");
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[routeOn] onUpdate");
    fsm.owner()._ownerMap.writeRouteRoad(fsm.owner().getRoute());
    //fsm.owner().show();
  }
}

/*-------------------------------------------
// Class Taxi
// Manage taxi general functions
-------------------------------------------*/
const states1Taxi = [new Stopped(), new Walking(), new Searching(), new Parking(), new Transporting()];
const states2Taxi = [new showOff(), new showOn()];
const states3Taxi = [new routeOff(), new routeOn()];

class Taxi {

  constructor(id, i, j, ownerMap) {
    this._id = id;
    this._ownerMap = ownerMap;
    this.pos = [i, j];
    this.prevPos = [i, j];
    this.route = [];
    this.originClientPos = [];
    this.destClientPos = [];
    this.clientId = -1;
    this._lastRowMove = "down";

    this._state1 = "detenido";
    this._state2 = "mostrarOff";
    this._state3 = "rutaOff";
    const fsm1 = new Fsm(this, states1Taxi, "fsm1-taxi");
    const fsm2 = new Fsm(this, states2Taxi, "fsm2-taxi");
    const fsm3 = new Fsm(this, states3Taxi, "fsm3-taxi");
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

  setState(stateNumber, newState) {
    switch(stateNumber) {
      case "state1":
        this._state1 = newState;
        break;
      case "state2":
        this._state2 = newState;
        break;
      case "state3":
        this._state3 = newState;
        break;
    }
  }

  setRoute(route) {
    this.route = route;
  }

  setClientOriginDest(originClientPos, destClientPos) {
    this.originClientPos = originClientPos;
    this.destClientPos = destClientPos; 
  }

  setClientId(clientId) {
    this.clientId = clientId;
  }

  getRoute() {
    return this.route;
  }

  walk() {
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

    var elementsToIgnore = ["&nbsp", "D", "+", "*"];
    if (!isPrevPos1 && elementsToIgnore.indexOf(pos1toCalc) != -1)
      return 1;
    else if (!isPrevPos2 && elementsToIgnore.indexOf(pos2toCalc) != -1)
      return 2;
    else if (!isPrevPos3 && elementsToIgnore.indexOf(pos3toCalc) != -1)
      return 3;
    else if (!isPrevPos4 && elementsToIgnore.indexOf(pos4toCalc) != -1)
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
        this._ownerMap.moveTaxi(this.prevPos, this.pos, this);
        break;
      case 2:
        this.prevPos[0] = this.pos[0];
        this.prevPos[1] = this.pos[1];
        this.pos[1]--;
        this._ownerMap.moveTaxi(this.prevPos, this.pos, this);
        break;
      case 3:
        if (this._lastRowMove == "down") {
          this.prevPos[0] = this.pos[0];
          this.prevPos[1] = this.pos[1];
          this.pos[0]++;
          this._ownerMap.moveTaxi(this.prevPos, this.pos, this);
        }
        else {
          this.prevPos[0] = this.pos[0];
          this.prevPos[1] = this.pos[1];
          this.pos[0]--;
          this._ownerMap.moveTaxi(this.prevPos, this.pos, this);
        }
        break;
      case 4:
        if (this._lastRowMove == "down") {
          this.prevPos[0] = this.pos[0];
          this.prevPos[1] = this.pos[1];
          this.pos[0]--;
          this._lastRowMove = "up"
          this._ownerMap.moveTaxi(this.prevPos, this.pos, this);
        }
        else {
          this.prevPos[0] = this.pos[0];
          this.prevPos[1] = this.pos[1];
          this.pos[0]++;
          this._lastRowMove = "down"
          this._ownerMap.moveTaxi(this.prevPos, this.pos, this);
        }
    }
  }

  _lookForClient() {
    var map = this._ownerMap.getMap();
    var taxiRow = this.pos[0];
    var taxiCol = this.pos[1];
    var clientUp = map[taxiRow-1][taxiCol] == "0"
    var clientDown = map[taxiRow+1][taxiCol] == "0"
    var clientLeft = map[taxiRow][taxiCol-1] == "0"
    var clientRight = map[taxiRow][taxiCol+1] == "0"

    if (clientUp)
      return [[taxiRow-1], [taxiCol]]
    else if (clientDown)
      return [[taxiRow+1], [taxiCol]]
    else if (clientLeft)
      return [[taxiRow], [taxiCol-1]]
    else if (clientRight)
      return [[taxiRow], [taxiCol+1]]
    else
      return "NoClient"
  }

  _askClientDestinationBuild(clientPosition) {
    var clientId = this._ownerMap.whichClient(clientPosition);
    var destinationBuild = this._ownerMap.getClientDestinationBuild(clientId);
    return destinationBuild;
  }

  _chooseBetterRoute(destinationBuild) {
    var map = this._ownerMap.copyMap([]);
    var taxiRow = this.pos[0];
    var taxiCol = this.pos[1];
    var taxiPrevRow = this.pos[0];
    var taxiPrevCol = this.pos[1];
    var betterSidewalkId = this._chooseSidewalkDestination(destinationBuild);
    var sidewalk = destinationBuild.getSidewalks()[betterSidewalkId];

    var route = [];
    var destReached = false;
    var contador = 0;
    while (!destReached && contador<100) {
      var nextMovePos = this._chooseBestMove(map, [taxiRow, taxiCol], [taxiPrevRow, taxiPrevCol], sidewalk);
      if (this._calculateHeuristic([nextMovePos[0], nextMovePos[1]], sidewalk) == 1) {
        destReached = true;
        route.push(nextMovePos);
        break;
      }
      map[taxiRow][taxiCol] = "&nbsp";
      map[nextMovePos[0]][nextMovePos[1]] = "D"
      taxiPrevRow = taxiRow;
      taxiPrevCol = taxiCol;
      taxiRow = nextMovePos[0]
      taxiCol = nextMovePos[1]
      route.push(nextMovePos);
      contador++;
    }
    return route;
  }

  _chooseSidewalkDestination(destinationBuild) {
    var sidewalks = destinationBuild.getSidewalks();
    var betterSidewalkId = -1;
    var bestScore = 1000;
    for (var i = 0; i<sidewalks.length; i++) {
      var score = this._calculateHeuristic([this.pos[0], this.pos[1]], [sidewalks[i][0], sidewalks[i][1]]);
      if (score < bestScore) {
        bestScore = score;
        betterSidewalkId = i;
      }
    }
    return betterSidewalkId;
  }

  _chooseBestMove(map, taxiPos, taxiPrevPos, destPos) {
    var taxiRow = taxiPos[0];
    var taxiCol = taxiPos[1];
    var bestScore = 1000;
    var nextMove = [];
    var isPrevPosR = taxiRow == taxiPrevPos[0] && taxiCol+1 == taxiPrevPos[1];
    var isPrevPosL = taxiRow == taxiPrevPos[0] && taxiCol-1 == taxiPrevPos[1];
    var isPrevPosD = taxiRow+1 == taxiPrevPos[0] && taxiCol == taxiPrevPos[1];
    var isPrevPosU = taxiRow-1 == taxiPrevPos[0] && taxiCol == taxiPrevPos[1];

    var elementsToIgnore = ["&nbsp", "D", "+", "*"];
    if (!isPrevPosR && elementsToIgnore.indexOf(map[taxiRow][taxiCol+1]) != -1) {
      var score = this._calculateHeuristic([taxiRow, taxiCol+1], [destPos[0], destPos[1]]);
      if (score < bestScore) {
        bestScore = score;
        nextMove = [taxiRow, taxiCol+1];
      }
    }
    if (!isPrevPosL && elementsToIgnore.indexOf(map[taxiRow][taxiCol-1]) != -1) {
      var score = this._calculateHeuristic([taxiRow, taxiCol-1], [destPos[0], destPos[1]]);
      if (score < bestScore) {
        bestScore = score;
        nextMove = [taxiRow, taxiCol-1];
      }
    }
    if (!isPrevPosD && elementsToIgnore.indexOf(map[taxiRow+1][taxiCol]) != -1) {
      var score = this._calculateHeuristic([taxiRow+1, taxiCol], [destPos[0], destPos[1]]);
      if (score < bestScore) {
        bestScore = score;
        nextMove = [taxiRow+1, taxiCol];
      }
    }
    if (!isPrevPosU && elementsToIgnore.indexOf(map[taxiRow-1][taxiCol]) != -1) {
      var score = this._calculateHeuristic([taxiRow-1, taxiCol], [destPos[0], destPos[1]]);
      if (score < bestScore) {
        bestScore = score;
        nextMove = [taxiRow-1, taxiCol];
      }
    }
    return nextMove;
  }

  _calculateHeuristic(pos1, pos2) {
    return Math.abs(pos1[0] - pos2[0]) + Math.abs(pos1[1] - pos2[1]);
  }

  transport() {
    this.prevPos[0] = this.pos[0];
    this.prevPos[1] = this.pos[1];
    this.pos[0] = this.route[0][0];
    this.pos[1] = this.route[0][1];
    this.route.splice(0, 1);
    this._ownerMap.moveTaxi(this.prevPos, this.pos, this);
  }

  show() {
    console.log("++++ Taxi " + this._id + " está " + this._state1 + ", " + this._state2 + " y " + this._state3);
  }
}