"use strict";

const State = require('./state')
const Fsm = require('./fsm')
const eventEmitter = require('./event-emiter')

var inc = 0;
//EFA = Elemento fuera de su área
//EDA = Elemento en su área

class Descansando extends State {
  accepts(event, current) {
    console.log("[Descansando]" + JSON.stringify(event));
    return current instanceof Molesto && (event.msg === "Sanando" || event.msg === "EFA");
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Descansando] onEnter" );
    fsm.owner().descansar();
  }

  onUpdate(eventEmitter, fsm) {
    fsm.owner().show();
  }
}

class Molesto extends State {
  accepts(event, current) {
    console.log("[Molesto]" + JSON.stringify(event));
    return (current instanceof Descansando && event.msg === "EDA") || (current instanceof Enojado && event.msg === "Sanando");
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Molesto] onEnter");
    fsm.owner().molestar();
  }

  onUpdate(eventEmitter, fsm) {
    fsm.owner().show();
  }
}

class Enojado extends State {
  accepts(event, current) {
    console.log("[Enojado]" + JSON.stringify(event));
    return ((current instanceof Descansando || current instanceof Molesto) && event.msg === "Herido") || (current instanceof Furioso && event.msg === "Sanando");
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Enojado] onEnter");
    fsm.owner().enojar();
  }

  onUpdate(eventEmitter, fsm) {
    fsm.owner().show();
  }
}

class Furioso extends State {
  accepts(event, current) {
    console.log("[Furioso]" + JSON.stringify(event));
    return current instanceof Enojado && event.msg === "Herido";    
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Furioso] onEnter");
    fsm.owner().enfurecer();
  }

  onUpdate(eventEmitter, fsm) {
    fsm.owner().show();
  }
}

const states = [new Descansando(), new Molesto(), new Enojado(), new Furioso()];

class NPC {
  constructor(id) {
    this._id = id;
    this._estado = "descansando";
    const miFsm = new Fsm(this, states);
    eventEmitter.register(miFsm);
  }

  id() {
    return this._id;
  }

  estado() {
    return this._estado;
  }

  descansar() {
    this._estado = "descansando";
  }

  molestar() {
    this._estado = "molesto";
  }

  enojar() {
    this._estado = "enojado";
  }

  enfurecer() {
    this._estado = "furioso";
  }

  show() {
    console.log(`[NPC] ${this.id()} ${this.estado()}`);
  }
}

module.exports = NPC