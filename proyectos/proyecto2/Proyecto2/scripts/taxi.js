/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/

/*
* Class Taxi
* Manage taxi general functions
*/
class Taxi {

  constructor(id, i, j) {
    this._id = id;
    this._state = "detenido";
    this.row = i;
    this.col = j;
    this.route
    //const miFsm = new Fsm(this, states);
    //eventEmitter.register(miFsm);
  }

  id() {
    return this._id;
  }

  state() {
    return this._state;
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