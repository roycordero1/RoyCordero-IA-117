/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/

/*
* States classes for the taxi
*/
class InHome extends State {
  accepts(event, current) {
    console.log("[InHome] accepts " + JSON.stringify(event));
    return event.msg == "Buscar";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[InHome] onEnter");
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[InHome] onUpdate");
    fsm.owner().show();
    fsm.owner().walk();
  }
}

class Working extends State {
  accepts(event, current) {
    console.log("[Working] accepts " + JSON.stringify(event));
    return event.msg == "Pasear";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Working] onEnter");
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[Working] onUpdate");
    fsm.owner().show();
    fsm.owner().walk();
  }
}

class Waiting extends State {
  accepts(event, current) {
    console.log("[Waiting] accepts " + JSON.stringify(event));
    return event.msg == "Detener";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Waiting] onEnter");
    fsm.owner().stopTaxi();
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[Waiting] onUpdate");
    fsm.owner().show();
  }
}

/*
* Class Taxi
* Manage client general functions
*/
const states = [new Stopped(), new Walking(), new Searching()];

class Client {

  constructor(id, pos, ownerMap) {
    this._id = id;
    this._ownerMap = ownerMap;
    this.pos = pos;
    this.home = [];
    this.work = [];

    this._state = "inhome";
    const miFsm = new Fsm(this, states, "fsm1-cliente");
    eventEmiter.register(miFsm);
  }

  id() {
    return this._id;
  }

  state() {
    return this._state;
  }

  setWorkHome(work, home) {
    this.work = work;
    this.home = home;
  }

  descansar() {
    this._state = "descansando";
  }

  molestar() {
    this._state = "molesto";
  }

  enojar() {
    this._state = "enojado";
  }

  enfurecer() {
    this._state = "furioso";
  }

  show() {
    console.log(`[NPC] ${this.id()} ${this.estado()}`);
  }
}