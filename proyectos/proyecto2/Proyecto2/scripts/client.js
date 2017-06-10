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
    return event.msg == "Encasa";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[InHome] onEnter");
    fsm.owner().atHome();
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[InHome] onUpdate");
    fsm.owner().show();
  }
}

class Working extends State {
  accepts(event, current) {
    console.log("[Working] accepts " + JSON.stringify(event));
    return event.msg == "Trabajar";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Working] onEnter");
    fsm.owner().work();
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[Working] onUpdate");
    fsm.owner().show();
  }
}

class Waiting extends State {
  accepts(event, current) {
    console.log("[Waiting] accepts " + JSON.stringify(event));
    return false;
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Waiting] onEnter");
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[Waiting] onUpdate");
    fsm.owner().show();
  }
}

/*
* Class Client
* Manage client general functions
*/
const states = [new InHome(), new Working(), new Waiting()];

class Client {

  constructor(id, pos, ownerMap) {
    this._id = id;
    this._ownerMap = ownerMap;
    this.pos = pos;
    this.homeBuild = null;
    this.workBuild = null;

    this._state = "en casa";
    const miFsm = new Fsm(this, states, "fsm1-cliente");
    eventEmiter.register(miFsm);
  }

  id() {
    return this._id;
  }

  state1() {
    return this._state;
  }

  getHomeBuild() {
    return this.homeBuild;
  }

  getWorkBuild() {
    return this.workBuild;
  }

  setWorkHome(work, home) {
    this.workBuild = work;
    this.homeBuild = home;
  }

  atHome() {
    this._state = "en casa";
  }

  work() {
    this._state = "trabajando";
  }

  show() {
    console.log("++++ Cliente " +  this._id + " está " + this._state);
  }
}