/*-------------------------------------------
Progra 2 - IA, Simulación de taxis Karma
Roy Cordero Durán
-------------------------------------------*/

/**
* Class Fsm
* Manage the finite state machine functions
*/
class Fsm {

  constructor(owner, states, fsmId) {
    this._owner = owner;
    this._states = states;
    this._current = states[0];
    this._id = fsmId + this._owner.id();
  }
  
  id() {
    return this._id;
  }
  
  owner() {
    return this._owner;
  } 
  /**
   * Ciclo:
   * 1. Si el mensaje es "update"
   *   - mensaje especial para hacer update de los estados
   * 2. Si el mensaje no es "update"
   *   - recorrer los estados y ver si alguna lo reconoce
   *   - si lo reconoce activar el estado
   */
  onMessage(eventEmitter, event) {
    if (event.msg == "update") {
      if (this._current) {
        this._current.onUpdate(eventEmitter, this);
      }
    } else {
      const state = this._states.find((s) => s.accepts(event, this._current));
      const accepted = state && state !== this._current;
      if (accepted) {
        if (this._current) {
          this._current.onExit(eventEmitter, this);
        }
        this._current = state;
        this._current.onEnter(eventEmitter, this, event);
      } else if (this._current) {
        this._current.onMessage(event);
      }
    }
  }  
}