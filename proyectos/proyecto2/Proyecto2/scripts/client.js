/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/

/*-------------------------------------------
// States classes for the taxi
-------------------------------------------*/
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

class WaitingTaxiToHome extends State {
  accepts(event, current) {
    console.log("[WaitingTaxiToHome] accepts " + JSON.stringify(event));
    return event.msg == "Wait taxi to home";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[WaitingTaxiToHome] onEnter");
    fsm.owner().waitTaxiToHome();
    fsm.owner().setGetOutTime(0);
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[WaitingTaxiToHome] onUpdate");
    fsm.owner().show();
  }
}

class WaitingTaxiToWork extends State {
  accepts(event, current) {
    console.log("[WaitingTaxiToWork] accepts " + JSON.stringify(event));
    return event.msg == "Wait taxi to work";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[WaitingTaxiToWork] onEnter");
    fsm.owner().waitTaxiToWork();
    fsm.owner().setGetOutTime(0);
  }

  onUpdate(eventEmitter, fsm) {
    console.log("[WaitingTaxiToWork] onUpdate");
    fsm.owner().show();
  }
}

/*-------------------------------------------
// Class Client
// Manage client general functions
-------------------------------------------*/
const statesClient = [new InHome(), new Working(), new WaitingTaxiToHome(), new WaitingTaxiToWork()];

class Client {

  constructor(id, pos, ownerMap) {
    this._id = id;
    this._ownerMap = ownerMap;
    this.pos = pos;
    this.homeBuild = null;
    this.workBuild = null;
    this.getOutTime = 0;

    this._state = "en casa";
    const miFsm = new Fsm(this, statesClient, "cliente");
    eventEmiter.register(miFsm);
  }

  id() {
    return this._id;
  }

  state() {
    return this._state;
  }

  getHomeBuild() {
    return this.homeBuild;
  }

  getWorkBuild() {
    return this.workBuild;
  }

  getGetOutTime() {
    return this.getOutTime;
  }

  setWorkHome(work, home) {
    this.workBuild = work;
    this.homeBuild = home;
  }

  setGetOutTime(time) {
    this.getOutTime = time;
  }

  atHome() {
    this._state = "en casa";
  }

  work() {
    this._state = "trabajando";
  }

  waitTaxiToWork() {
    this._state = "esperando taxi para ir al trabajo"; 
  }

  waitTaxiToHome() {
    this._state = "esperando taxi para ir a casa"; 
  }

  show() {
    console.log("++++ Cliente " +  this._id + " está " + this._state);
  }
}